package com.pwrd.war.gameserver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.SimpleByteBufferAllocator;
import org.slf4j.Logger;

import com.pwrd.op.LogOp;
import com.pwrd.war.common.constants.GameColor;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.constants.SysMsgShowTypes;
import com.pwrd.war.core.config.ConfigUtil;
import com.pwrd.war.core.msg.recognizer.IMessageRecognizer;
import com.pwrd.war.core.server.AbstractIoHandler;
import com.pwrd.war.core.server.IMessageProcessor;
import com.pwrd.war.core.server.ServerProcess;
import com.pwrd.war.core.server.ServerStatusLog;
import com.pwrd.war.core.util.MemUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.config.GameServerConfig;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.common.msg.GCSystemMessage;
import com.pwrd.war.gameserver.player.PlayerExitReason;
import com.pwrd.war.gameserver.startup.ClientMessageRecognizer;
import com.pwrd.war.gameserver.startup.GameServerIoHandler;
import com.pwrd.war.gameserver.startup.GameServerRuntime;
import com.pwrd.war.gameserver.startup.GameServerVersionCheck;
import com.pwrd.war.gameserver.startup.MinaGameClientSession;
import com.pwrd.war.gameserver.startup.ServerShutdownService;
import com.pwrd.war.gameserver.telnet.TelnetIoHandler;
import com.pwrd.war.gameserver.telnet.TelnetServerProcess;
import com.pwrd.war.gameserver.telnet.command.CharStatusCommand;
import com.pwrd.war.gameserver.telnet.command.ChatForbCommand;
import com.pwrd.war.gameserver.telnet.command.DirectChargeCommand;
import com.pwrd.war.gameserver.telnet.command.GSListCommand;
import com.pwrd.war.gameserver.telnet.command.GSStatusCommand;
import com.pwrd.war.gameserver.telnet.command.GameStatusCommand;
import com.pwrd.war.gameserver.telnet.command.GiveLibaoCommand;
import com.pwrd.war.gameserver.telnet.command.HttpCommand;
import com.pwrd.war.gameserver.telnet.command.KickOutCommand;
import com.pwrd.war.gameserver.telnet.command.LoginCommand;
import com.pwrd.war.gameserver.telnet.command.LoginOpenWallCommand;
import com.pwrd.war.gameserver.telnet.command.NoticeCommand;
import com.pwrd.war.gameserver.telnet.command.PrizeClearCacheCommand;
import com.pwrd.war.gameserver.telnet.command.QuitCommand;
import com.pwrd.war.gameserver.telnet.command.ShutdownCmd;
import com.pwrd.war.gameserver.telnet.command.WallowProtectCommand;

/**
 * 负责游戏服务器的初始化，基础资源的加载，服务器进程的启动
 * 
 */
public class GameServer {
	
	/** 日志 */
	private static final Logger logger = Loggers.gameLogger;
	
	/** 服务器配置信息 */
	private GameServerConfig config;
	
	/** 服务器进程 */
	private ServerProcess serverProcess;
	
	/** Telnet进程*/
	private TelnetServerProcess telnetProcess;

	/**
	 * @param cfgPath
	 *            主配置文件路径
	 * @throws IOException 
	 */
	private GameServer(String cfgPath) throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties gameCofig=new Properties();
		gameCofig.load(classLoader.getResourceAsStream(cfgPath));
		config = ConfigUtil.buildConfig(GameServerConfig.class, new FileInputStream(gameCofig.getProperty("game.config")));
	}

	/**
	 * 初始化各种资源和服务
	 * 
	 * @throws Exception
	 */
	private void init() throws Exception {
		logger.info("Begin to initialize Globals");
		logger.info("Globals initialized");
		Globals.init(config);
		if (!new GameServerVersionCheck(config).check()) {
			throw new RuntimeException("Check version fail");
		}
		IMessageRecognizer msgRecognizer = new ClientMessageRecognizer();		
		AbstractIoHandler<MinaGameClientSession> ioHandler = new GameServerIoHandler(config.getFlashSocketPolicy(), Globals.getExecutorService(), Globals.getSessionService());
		
		IMessageProcessor msgProcessor = Globals.getMessageProcessor();
		serverProcess = new ServerProcess(config.getBindIp(), config
				.getServerName(), config.getPorts(), msgRecognizer, ioHandler,
				msgProcessor, config.getIoProcessor(), config.getMisIps());
		
		telnetProcess = new TelnetServerProcess(config.getTelnetServerName(),
				config.getTelnetBindIp(), config.getTelnetPort(),
				buildTelnetIoHandler());
	}
	
	public static TelnetIoHandler buildTelnetIoHandler() {
		TelnetIoHandler _ioHandler = new TelnetIoHandler();
		_ioHandler.register(new LoginCommand());
		_ioHandler.register(new QuitCommand());
		_ioHandler.register(new ShutdownCmd());
		_ioHandler.register(new HttpCommand());
		_ioHandler.register(new GSStatusCommand());
		_ioHandler.register(new KickOutCommand());
		_ioHandler.register(new CharStatusCommand());
		_ioHandler.register(new WallowProtectCommand());
		_ioHandler.register(new NoticeCommand());
		_ioHandler.register(new LoginOpenWallCommand());
//		_ioHandler.register(new ProbeCommand());
		_ioHandler.register(new PrizeClearCacheCommand());
		_ioHandler.register(new GSListCommand());
		_ioHandler.register(new GameStatusCommand());
		_ioHandler.register(new DirectChargeCommand());
		_ioHandler.register(new GiveLibaoCommand());
		_ioHandler.register(new ChatForbCommand());
		return _ioHandler;
	}


	/**
	 * 启动服务器
	 * 
	 * @throws IOException
	 */
	private void start() throws IOException {
		Globals.getSceneService().start();
		Globals.getRepService().start();
		
		logger.info("Begin to start Server Process");
		serverProcess.start();
		telnetProcess.start();
		logger.info("Server Process started");
		
		logger.info("Begin to start Globals");
		Globals.start();
		logger.info("Globals started");

		logger.info("Begin to schedule fixed rate task");
		// 周期任务
		//TODO
//		Globals.getScheduleService().scheduleWithFixedDelay(
//				new PlayerTimeOutChecker(Globals.getTimeService().now()),
//				PlayerTimeOutChecker.CHECK_PERIOD,
//				PlayerTimeOutChecker.CHECK_PERIOD);
//		Globals.getScheduleService().scheduleWithFixedDelay(
//				new ScheduleReportStatus(Globals.getTimeService().now()),
//				config.getLocalReportStatusPeriod() * 1000,
//				config.getLocalReportStatusPeriod() * 1000);
//		Globals.getScheduleService().scheduleWithFixedDelay(
//				new ScheduleReportOnlines(Globals.getTimeService().now()),
//				config.getLocalReportOnlinePeriod() * 1000,
//				config.getLocalReportOnlinePeriod() * 1000);
		logger.info("Scheduled fixed rate task");
		
		
		// 停机时停止主线程的处理 
		Globals.getShutdownHooker().register(new Runnable() {
			@Override
			public void run() {				
				logger.info("Begin to shutdown Game Server ");
				Globals.getOnlinePlayerService().broadcastMessage(new GCSystemMessage(
						Globals.getLangService().read(CommonLangConstants_10000.SERVER_WILL_SHUTDOWN),
						GameColor.SYSMSG_SYS.getRgb(), 
						SysMsgShowTypes.generic));
				try {
					//等到2秒发送广播消息
					Thread.sleep(2000);
				} catch (InterruptedException e) { 
					e.printStackTrace();
				}
				// 设置GameServer关闭状态
				GameServerRuntime.setShutdowning();
				
				// 设置为STOPPING状态
				Globals.getServerStatusService().stopping();
				Globals.getServerStatusService().reportToLocal();
				
				Globals.getSceneService().stop();
				Globals.stop();
				
				
				// 踢掉所有在线玩家，这个操作在shutdowning状态下不做正常下线的同步存库操作
				Globals.getOnlinePlayerService().offlineAllPlayers(PlayerExitReason.SERVER_SHUTDOWN);
				// 关闭服务器消息接受
				serverProcess.stop();
				telnetProcess.stop();
				// 关闭异步操作服务，这个操作会等5分钟，尽量确保各异步存库任务执行完再关闭线程池
				Globals.getAsyncService().stop();
				logger.info("Begin to syn save all online player data.");
				// 最后做一遍全部数据的同步存库，将PlayerDataUpdater的尚未更新的数据同步到dbs
				new ServerShutdownService().synSaveAllPlayerOnShutdown();
				logger.info("syn save complete.");
				// 关闭系统维护线程池服务
				Globals.getExecutorService().stop();
				
				// 设置为STOPPED状态
				Globals.getServerStatusService().stopped();
//TODO				Globals.getServerStatusService().reportToLocalSync();
				
//				// 注销性能收集
//				PIProbeCollector.stop();
				logger.info("Game Server shutdowned");
			}
		});
		
		
		// 增加JVM停机时的处理,注册停服监听器,用于执行资源的销毁等停服时的处理工作,并设置服务器状态
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					ServerStatusLog.getDefaultLog().logStoppping();
					Globals.getShutdownHooker().run();
				} finally {
					ServerStatusLog.getDefaultLog().logStopped();
				}
			}
		});
		
		
		GameServerRuntime.setOpenOn();
		
		//GM分析平台
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream ins = classLoader.getResourceAsStream("operlog.properties");
		LogOp.init(ins);
//		String url = ConfigUtil.getConfigPath("operlog.properties");
//		LogOp.init(url);
//		Loggers.msgLogger.info("==>"+url);
//		Properties gameCofig=new Properties();
//		gameCofig.load(new FileReader(url));
//		Loggers.msgLogger.info("===>"+gameCofig);
//		LogOp.init(classLoader.getResource(".").getPath()+ "operlog.properties");
	}

	public static void main(String[] args) {
		logger.info("Starting Game Server");
		logger.info(MemUtils.memoryInfo());
		try {
			ServerStatusLog.getDefaultLog().logStarting();
			ByteBuffer.setUseDirectBuffers(false);
			ByteBuffer.setAllocator(new SimpleByteBufferAllocator());
			GameServer server = new GameServer("game.properties");
			server.init();
			server.start();
			ServerStatusLog.getDefaultLog().logRunning();
		} catch (Exception e) {
			logger.error("Failed to start server", e);
			ServerStatusLog.getDefaultLog().logStartFail();
			System.exit(1);
			return;
		}
		logger.info(MemUtils.memoryInfo());
		logger.info("Game Server started");
	}
}
