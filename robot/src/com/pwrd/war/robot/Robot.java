package com.pwrd.war.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.common.model.human.HumanInfo;
import com.pwrd.war.core.client.NIOClient;
import com.pwrd.war.core.handler.BaseMessageHandler;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.recognizer.BaseMessageRecognizer;
import com.pwrd.war.core.server.IMessageProcessor;
import com.pwrd.war.core.server.QueueMessageProcessor;
import com.pwrd.war.core.session.MinaSession;
import com.pwrd.war.core.util.Assert;
import com.pwrd.war.core.util.KeyValuePair;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.gameserver.common.msg.CGHandshake;
import com.pwrd.war.gameserver.human.msg.GCPropertyChangedNumber;
import com.pwrd.war.gameserver.human.msg.GCPropertyChangedString;
import com.pwrd.war.gameserver.player.msg.CGCreateRole;
import com.pwrd.war.gameserver.player.msg.CGEnterScene;
import com.pwrd.war.gameserver.player.msg.CGPlayerCookieLogin;
import com.pwrd.war.gameserver.player.msg.CGPlayerEnter;
import com.pwrd.war.gameserver.player.msg.CGRoleTemplate;
import com.pwrd.war.gameserver.player.msg.GCRoleInfo;
import com.pwrd.war.gameserver.role.properties.RoleBaseIntAProperties;
import com.pwrd.war.gameserver.role.properties.RoleBaseIntProperties;
import com.pwrd.war.gameserver.role.properties.RoleBaseStrProperties;
import com.pwrd.war.gameserver.scene.msg.CGPlayerMove;
import com.pwrd.war.gameserver.scene.msg.CGPlayerPos;
import com.pwrd.war.gameserver.scene.msg.CGSwitchScene;
import com.pwrd.war.gameserver.scene.msg.GCSwitchScene;
import com.pwrd.war.robot.manager.BagManager;
import com.pwrd.war.robot.manager.MailManager;
import com.pwrd.war.robot.manager.PetManager;
import com.pwrd.war.robot.msg.RobotClientSessionClosedMsg;
import com.pwrd.war.robot.msg.RobotClientSessionOpenedMsg;
import com.pwrd.war.robot.startup.IRobotClientSession;
import com.pwrd.war.robot.startup.RobotClientIoHandler;
import com.pwrd.war.robot.startup.RobotClientMsgHandler;
import com.pwrd.war.robot.startup.RobotClientMsgRecognizer;
import com.pwrd.war.robot.strategy.IRobotStrategy;

public class Robot {
	
	/** robot相关的日志 */
	public static final Logger robotLogger = LoggerFactory.getLogger("war.robot");
	
	private String passportId;
	
	private String roleUUid;
	
	private String password = "1";
	
	private int pid;
	
	private String serverIp;
	
	private int port;
	
	private RobotState state;
	
	private int allianceId;
	
	public String sceneId;//场景ID
	public int lineNo;//场景分线
	public int x;
	public int y;
	
	/** 是否在活动中 */
	public boolean isInAct;
	
	private List<IRobotStrategy> strategyList;
	
	private NIOClient nioclient;
	
	/** 玩家与GameServer的会话 */
	private IRobotClientSession session;	
	/** 背包管理器 */
	private BagManager bagManager;
	/** 邮件管理器 */
	private MailManager mailManager;
	/** 武将管理器 */
	private PetManager petManager;
	
	/** 机器人自动战斗的怪物列表 */
	private List<String> monsterSnList;
	
	public Robot(String passportId,int pid, String serverIp ,int port)
	{
		this.passportId = passportId;
		this.pid = pid;
		this.serverIp = serverIp;
		this.port = port;
		this.strategyList = new ArrayList<IRobotStrategy>();
		bagManager = new BagManager(this);
		mailManager = new MailManager(this);
		petManager = new PetManager(this);
		this.state = RobotState.init;
		this.monsterSnList = new ArrayList<String>();
		this.isInAct = false;
	}
	
	public boolean isInAct() {
		return isInAct;
	}

	public void setInAct(boolean isInAct) {
		this.isInAct = isInAct;
	}



	/**
	 * 启动连接
	 */
	public void start()
	{
		nioclient = buildConnection();		
		nioclient.getMessageProcessor().start();
		nioclient.open();
		
	}
	
	/**
	 * 销毁连接
	 */
	public void destory()
	{
		if (this.nioclient != null) {
			this.nioclient.getMessageProcessor().stop();
			this.nioclient.close();
			this.nioclient = null;
		}
	}
		
	/**
	 * 是否连接
	 * @return
	 */
	public boolean isConnected()
	{
		if (this.session != null) {
			return this.session.isConnected();
		}
		return false;
	}
	
	public NIOClient buildConnection()
	{
		BaseMessageHandler _messageHandler = new BaseMessageHandler();
		
		_messageHandler.registerHandler(new RobotClientMsgHandler());

		
		IMessageProcessor _messageProcessor = new QueueMessageProcessor(_messageHandler);
		BaseMessageRecognizer _recognizer = new RobotClientMsgRecognizer();
		RobotClientIoHandler _ioHandler = new RobotClientIoHandler(); 
		ExecutorService _executorService = Executors.newSingleThreadExecutor();
		NIOClient _client = new NIOClient("Game Server", 
											this.serverIp, 
											this.port, 
											_executorService,
											_messageProcessor, 
											_recognizer, 
											_ioHandler, 
											this.new ConnectionCallback());
		return _client;
	}

	/**
	 * 获取机器人执行策略
	 * 
	 * @param index
	 * @return
	 */
	public IRobotStrategy getStrategy(int index) {
		if (index >= 0 && index < this.strategyList.size()) {
			return this.strategyList.get(index);
		} else {
			return null;
		}
	}

	/**
	 * 添加机器人执行策略
	 * 
	 * @param strategy
	 */
	public void addRobotStrategy(IRobotStrategy strategy) {
		this.strategyList.add(strategy);
	}
	
	public List<IRobotStrategy> getStrategyList() {
		return strategyList;
	}

	public String getPassportId() {
		return passportId;
	}

	public int getPid() {
		return pid;
	}

	public String getServerIp() {
		return serverIp;
	}

	public int getPort() {
		return port;
	}

	public RobotState getState() {
		return state;
	}
	
	public int getAllianceId() {
		return allianceId;
	}

	public void setState(RobotState state) {
		this.state = state;
	}
	
	public void setSession(IRobotClientSession session) {
		this.session = session;
	}

	public IRobotClientSession getSession()
	{
		return this.session;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 *
	 * 发送消息 
	 * @param msg
	 */
	public void sendMessage(IMessage msg) {
		Assert.notNull(msg);
		if (session != null) {
			session.write(msg);			
		}
	}
	

	private class ConnectionCallback implements NIOClient.ConnectionCallback
	{		
		@Override
		public void onOpen(NIOClient client, IMessage msg) {
			RobotClientSessionOpenedMsg message = (RobotClientSessionOpenedMsg)msg;
			RobotManager.getInstance().addRobot(message.getSession().getIoSession(),Robot.this);
			Robot.this.setSession(message.getSession());
			message.getSession().setRobot(Robot.this);
			Robot.this.setState(RobotState.connected);
			RobotManager.getInstance().addRobot((MinaSession)message.getSession(), Robot.this);

			// 连接服务器端成功之后, 发送握手消息
			Robot.this.sendMessage(new CGHandshake());
		}

		@Override
		public void onClose(NIOClient client, IMessage msg) {
			RobotClientSessionClosedMsg message = (RobotClientSessionClosedMsg)msg;
			RobotManager.getInstance().removeRobot(message.getSession().getIoSession());
			Robot robot = RobotManager.getInstance().removeRobot((MinaSession)message.getSession());
			if(robot != null)
			{
				robot.setState(RobotState.logout);
				robot.setSession(null);
			}
			message.getSession().setRobot(null);
		}		
	}
	
	public void doLogin()
	{
		this.state = RobotState.login;
//		CGPlayerLogin loginMsg = new CGPlayerLogin();
//		loginMsg.setAccount(this.passportId);
//		loginMsg.setPassword(this.password);
//		sendMessage(loginMsg);
		CGPlayerCookieLogin msg = new CGPlayerCookieLogin();
		msg.setCookieValue(this.passportId);
		sendMessage(msg);
	}

	
	public void doQueryRoleTemplate() {
		CGRoleTemplate cgRoleTemplate = new CGRoleTemplate();
		this.sendMessage(cgRoleTemplate);
	}
	
	public void doCreateRole(int templateId, int hair, int feature, int job, int allianceId)
	{
		CGCreateRole cgCreateRole = new CGCreateRole();
		cgCreateRole.setName("机器人" + this.getPid()); 
		cgCreateRole.setCamp(1);
		cgCreateRole.setVocation(1);
		cgCreateRole.setSex(1);
		this.sendMessage(cgCreateRole);
	}

	public void doSelectRoleAndEnterGame(String roleUUID)
	{
		this.state = RobotState.entergame;
		CGPlayerEnter cgPlayerEnter = new CGPlayerEnter();
		cgPlayerEnter.setRoleUUID(roleUUID);
		this.sendMessage(cgPlayerEnter);		
	}
	
	public void handleGCRoleList(Robot robot,GCRoleInfo  roleInfo )
	{
		if(StringUtils.isEmpty(roleInfo.getRoleUUID()))
		{			
//			robot.doQueryRoleTemplate();
			robot.doCreateRole(41011001, 1, 1, 1, allianceId);
		}
		else
		{			
			roleUUid = roleInfo.getRoleUUID();		
			robot.doSelectRoleAndEnterGame(roleUUid);
		}
	}
	
	public void handleGCHumanDetailInfo(HumanInfo humanInfo) {
//		allianceId = humanInfo.getAllianceId();
	}	
	
	public void handleGCPropertyChangedNumber(GCPropertyChangedNumber msg) {
//		allianceId = humanInfo.getAllianceId();
		KeyValuePair<Integer,Integer>[]  keys = msg.getProperties();
		int sceneid = RoleBaseStrProperties.TYPE.genPropertyKey(RoleBaseStrProperties.SCENEID);
		for(KeyValuePair<Integer,Integer> kv : keys){ 
			if(kv.getKey().intValue() == RoleBaseIntProperties.TYPE.genPropertyKey(RoleBaseIntProperties.LOCATIONX)){
				this.x = kv.getValue();
				continue;
			}
			if(kv.getKey().intValue() == RoleBaseIntProperties.TYPE.genPropertyKey(RoleBaseIntProperties.LOCATIONY)){
				this.y = kv.getValue();
				continue;
			}
		}
	}	
	public void handleGCPropertyChangedString(GCPropertyChangedString msg) {
//		allianceId = humanInfo.getAllianceId();
		KeyValuePair<Integer,String>[]  keys = msg.getProperties();
		int sceneid = RoleBaseStrProperties.TYPE.genPropertyKey(RoleBaseStrProperties.SCENEID);
		for(KeyValuePair<Integer,String> kv : keys){ 
			if(kv.getKey().intValue() == RoleBaseStrProperties.TYPE.genPropertyKey(RoleBaseStrProperties.SCENEID)){
				this.sceneId = kv.getValue();
				continue;
			}
			 
		}
	}	
	
	
	public void handleGcRoleTemplate(Robot robot) {

		//TODO 修改角色创建
		robot.doCreateRole(41011001, 1, 1, 1, allianceId);
	}

	public void handleGCSceneInfo(Robot robot) {	
		CGEnterScene cgEnterScene = new CGEnterScene();		
		robot.sendMessage(cgEnterScene);
	}
	
	/**
	 * 被动切换场景
	 * @param msg
	 */
	public void handleGCSwitchScend(GCSwitchScene msg){
		String scendId = msg.getSceneId();
		int line = msg.getLineNo();
		this.sceneId = scendId;
		this.lineNo = line;
		CGEnterScene cgEnterScene = new CGEnterScene();
		cgEnterScene.setLine(line);
		this.sendMessage(cgEnterScene);
	}
	
	
	/**
	 * 处理战斗
	 */
	public void handlerBattle(int x,int y){
		CGPlayerMove m = new CGPlayerMove();
		m.setSrcx(this.x);
		m.setSrcy(this.y);
		m.setTox(x);
		m.setToy(y);
		CGPlayerPos msg = new CGPlayerPos();
		msg.setSrcx(x);
		msg.setSrcy(y);
		msg.setSceneId(this.sceneId);
		msg.setLineNo(this.lineNo);
		sendMessage(m);
		sendMessage(msg);
	}
	
	/**
	 * 主动切换场景
	 * @param sceneId
	 */
	public void switchScene(String sceneId){
		CGSwitchScene msg = new CGSwitchScene();
		msg.setSceneId(sceneId);
		this.sendMessage(msg);
	}
	
//	/**
//	 * 处理显示场景列表消息
//	 * 
//	 * @param robot
//	 * @param sceneList
//	 */
//	public void handleGCShowSceneList(Robot robot, GCShowSceneList sceneList) {
//		if (robot == null || sceneList == null) {
//			return;
//		}
//
//		SceneInfo[] siArray = sceneList.getSceneList();
//
//		if (siArray != null) {
//			for (SceneInfo si : siArray) {
//				System.out.print(String.format(
//					"{ Scene: { id: %d, name: %s, typeId: %d, image: %s, isAvailable: %d }}", 
//					si.getId(), si.getName(), si.getTypeId(), si.getImage(), si.getAvailable() ? 1 : 0
//				));
//			}
//		}
//	}


	public void doEnterScene() {
		this.state = RobotState.gaming;

		for (int i = 0; i < this.strategyList.size(); i++) {
			// 获取机器人执行策略
			IRobotStrategy strategy = this.getStrategy(i);
	
			if (strategy == null) {
				return;
			}

			// 创建机器人行为
			RobotAction action = new RobotAction(strategy);
			// 获取第一次执行的时间延迟
			int delay = strategy.getDelay();
			// 获取循环执行时的时间间隔
			int period = strategy.getPeriod();
			
			if (strategy.isRepeatable()) {
				// 循环执行机器人操作
				RobotManager.getInstance().scheduleWithFixedDelay(action, delay, period);
			} else/* if (!strategy.isLoopExecute()) */{
				// 仅执行一次机器人操作
				RobotManager.getInstance().scheduleOnce(action, delay);
			}
		}
	}


	// -------------------  所有相关业务逻辑都封装到Manager --------------------
	
	public BagManager getBagManager() {
		return bagManager;
	}
	
	public MailManager getMailManager() {
		return mailManager;
	}

	public PetManager getPetManager() {
		return petManager;
	}

	public String getRoleUUid() {
		return roleUUid;
	}
	
	public List<String> getMonsterSnList(){
		return this.monsterSnList;
	}
}
