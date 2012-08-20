package com.pwrd.war.gameserver.wallow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.constants.WallowConstants;
import com.pwrd.war.common.constants.WallowConstants.WallowStatus;
import com.pwrd.war.core.schedule.ScheduleService;
import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.core.util.HttpUtil;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.PlayerLangConstants_30000;
import com.pwrd.war.gameserver.player.OnlinePlayerService;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.PlayerExitReason;
import com.pwrd.war.gameserver.player.msg.GCNotifyException;
import com.pwrd.war.gameserver.wallow.async.FetchOnlineTimeOperation;
import com.pwrd.war.gameserver.wallow.msg.GCWallowInfo;
import com.pwrd.war.gameserver.wallow.msg.GCWallowOpenPanel;
import com.pwrd.war.gameserver.wallow.msg.ScheduleSyncWallowOnlineTime;
import com.pwrd.war.gameserver.wallow.msg.SysWallowTickerServiceStart;
import com.pwrd.war.gameserver.wallow.msg.WallowOnlineTimeMsg;

/**
 * 防沉迷服务
 * 
 * 
 */
public class WallowService {
	private final static Logger logger = Loggers.wallowLogger;
	private WallowLogicalProcessor wallowProcessor;
	private OnlinePlayerService onlinePlayerSerice;

	/** 定时任务服务 */
	private ScheduleService schService; 
	
//	private static final String VALID_URL = "http://219.238.234.185:9080/rest/";
	private static final String VALID_URL = "http://10.2.4.124/rest/";
	
	public WallowService(ScheduleService schService,
			WallowLogicalProcessor wallowProcessor,
			OnlinePlayerService onlinePlayerSerice) {
		this.schService = schService;
		this.wallowProcessor = wallowProcessor;
		this.onlinePlayerSerice = onlinePlayerSerice; 
	}

	public void onPlayerEnter(Player _player) {
		if (_player == null) {
			if (logger.isWarnEnabled()) {
				logger.warn(ErrorsUtil.error("",
						"#WS.WallowLogicalProcessor.onPlayerEnter", ""));
			}
			return;
		}
		if (!Globals.getServerConfig().isWallowControlled()) {
			return;
		}
		
		// 加入反沉迷列表吧
		wallowProcessor.addWallowUser(_player.getPassportId());
		
		// 立即获取玩家的防沉迷状态
		List<String> _players = new ArrayList<String>(1);
		_players.add(_player.getPassportId());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(new FetchOnlineTimeOperation(_players, 0));
		
		
	}

	/**
	 * 玩家退出请离防沉迷列表
	 * 
	 * @param wallowExitMsg
	 */
	public void onPlayerExit(String passportId) {
		boolean rs = wallowProcessor.removeWallowUser(passportId);
		if(rs){
			Player p = Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
			long df = (Globals.getTimeService().now() - p.getLastAccTime())/1000;
			// 立即获取玩家的防沉迷状态
			List<String> _players = new ArrayList<String>(1);
			_players.add(passportId);
			Globals.getAsyncService().createOperationAndExecuteAtOnce(new FetchOnlineTimeOperation(_players, (int) df));
		}
	}

	/**
	 * 当从接口成功取得玩家的累计时长时，调用本方法处理防沉迷状态
	 * 
	 * @param player
	 * @param second
	 */
	public void onPlayerOnlineTimeSynced(WallowOnlineTimeMsg timeMsg) {
		List<String> _players = timeMsg.getPlayers();
		List<Long> _seconds = timeMsg.getSeconds();
		if (_players.size() != _seconds.size()) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error("",
						"#WS.WallowLogicalProcessor.onPlayerOnlineTimeSynced",
						""));
			}
			return;
		}

		for (int i = 0; i < _players.size(); i++) {
			String _passportId = _players.get(i);
			if(_passportId == null){
				continue;
			}
			long _second = _seconds.get(i);
			Player _player = onlinePlayerSerice.getPlayerByPassportId(_passportId);
			if (_player == null) {
				// 玩家已经下线了
				continue;
			}
			if (_second == -1) {
				// 调用累计时长接口时发生错误
				continue;
			}
			if(_second == 0){
//				continue;
			}
			// 以此处理吧
			processPlayerOnlineTime(_player, _second);
		}
	}

	/**
	 * 异步地同步反沉迷玩家的在线累计时长
	 */
	public void syncWallowPlayerOnlineTime() {
		if (!Globals.getServerConfig().isWallowControlled()) {
			// 开关没有打开
			return;
		}

		if (wallowProcessor.isAllWaitersSync()) {
			wallowProcessor.clearWaiters();
			wallowProcessor.pumpWaiters();
		}
		List<String> _syncList = wallowProcessor.getNextWaiters();
		if (_syncList.size() > 0) {
			Globals.getAsyncService().createOperationAndExecuteAtOnce(
					new FetchOnlineTimeOperation(_syncList, WallowConstants.TIME_EXTEND_UNIT));
			wallowProcessor.incCounter();
		}
	}

	/**
	 * 防沉迷控制关闭相关处理
	 */
	public void onWallowClosed() {
		for (String _pid : wallowProcessor.getAllWallowUsers()) {
			Player _player = onlinePlayerSerice.getPlayerByPassportId(_pid);
			if (_player != null) {
				int _oldUnit = _player.getAccOnlineTime()
						/ WallowConstants.TIME_EXTEND_UNIT;
				// 已经处理非正常收益状态的玩家，发送消息通知其恢复正常收益
				if (_oldUnit >= WallowConstants.NORMAL_UP_INDEX) {
					if (_player.getWallowState() != WallowStatus.NORMAL) {						
						_player.setWallowState(WallowStatus.NORMAL);
						_player.sendSystemMessage(PlayerLangConstants_30000.WALLOW_CLOSE_NORMAL);
					}
				}
			}
		}
	}

	/**
	 * 防沉迷控制开启相关处理
	 */
	public void onWallowOpened() {
		for (String _pid : wallowProcessor.getAllWallowUsers()) {
			Player _player = onlinePlayerSerice
					.getPlayerByPassportId(_pid);
			if (_player != null) {
				int _oldUnit = _player.getAccOnlineTime()
						/ WallowConstants.TIME_EXTEND_UNIT;
				// 已经处于非正常收益状态的玩家，发送消息通知GS
				if (_oldUnit >= WallowConstants.NORMAL_UP_INDEX) {
					WallowStatus state = getWallowState(_oldUnit);
					if (_player.getWallowState() != state) {						
						_player.setWallowState(state);
						
						// 提示
						if (state == WallowStatus.WARN) {
							sendWallowAddInfo(_player);
							_player.sendBoxMessage(PlayerLangConstants_30000.WALLOW_OPEN_WARN);
						} else if (state == WallowStatus.DANGER) {
							sendWallowAddInfo(_player);
							_player.sendBoxMessage(PlayerLangConstants_30000.WALLOW_OPEN_DANGER);
						}
					}
				}
			}
		}
	}


	/**
	 * 处理单个玩家的在线累计时长
	 * 
	 * @param player
	 * @param second
	 */
	private void processPlayerOnlineTime(Player player, long second) {
		
		player.setLastAccTime(Globals.getTimeService().now());
		int _oldsec = player.getAccOnlineTime();

		int _oldUnit = _oldsec / WallowConstants.TIME_EXTEND_UNIT;
		int _newUnit = (int) (second / WallowConstants.TIME_EXTEND_UNIT);

		int _oldExtend = wallowProcessor.getTimeExtend(_oldUnit);
		int _newExtend = wallowProcessor.getTimeExtend(_newUnit);

		player.setAccOnlineTime((int) second);
		

		// _oldsec大于0保证首次登录，有防沉迷提醒到GS,初始化防沉迷系数
//		if (_oldsec > 0 && _oldExtend == _newExtend) {
//			// 时间区间没有发生变化
//			return;
//		}
		// 通知gs玩家时间区间发生改变
		wallowRemind(player, second, _newUnit, _newExtend);
		
		if(_newUnit >= WallowConstants.NORMAL_UP_INDEX)
		{
			player.sendMessage(new GCNotifyException(PlayerLangConstants_30000.WALLOW_CANNOT_LOGIN_STATUS,
					Globals.getLangService().read(  PlayerLangConstants_30000.WALLOW_CANNOT_LOGIN_STATUS)));
			player.exitReason = PlayerExitReason.WALLOW_KICK;
			player.disconnect();
		}
	}

	/**
	 * 向玩家所在的GS发送提示
	 * 
	 * @param player
	 * @param second 累计在线时间
	 * @param _newUnit 多少个以15分钟为单位的单元
	 * @param _newExtend 第几个小时区间
	 */
	public void wallowRemind(Player player, long second, int _newUnit, int _newExtend) {
		
		WallowStatus _state = getWallowState(_newUnit);
		
		if (_state == null) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error("防沉迷状态错误定义",
						"#GS.WallowService.wallowRemind", String
								.valueOf(_newUnit)));
			}
			return;
		}
		
		int _minute = (int) (second / 60);

		// 防沉迷状态发生，修正系数相关
		if (player.getWallowState() != _state) {
			player.setWallowState(_state);
		}
		//发一次时间
		GCWallowInfo rmsg = new GCWallowInfo((int) second, "");
		player.sendMessage(rmsg);
				
		if (Math.abs(60 - _minute) <= WallowConstants.TIME_EXTEND_MIN_UNIT/2 ) 
		{
			String tip = Globals.getLangService().read(PlayerLangConstants_30000.WALLOW_WARN_1);
			GCWallowInfo msg = new GCWallowInfo((int) second, tip);
			player.sendMessage(msg);
		}else if(Math.abs(120 - _minute) <= WallowConstants.TIME_EXTEND_MIN_UNIT/2 ){
			String tip = Globals.getLangService().read(PlayerLangConstants_30000.WALLOW_WARN_2);
			GCWallowInfo msg = new GCWallowInfo((int) second, tip);
			player.sendMessage(msg);
		}else if(Math.abs(180 - _minute) <= WallowConstants.TIME_EXTEND_MIN_UNIT/2 ){
			String tip = Globals.getLangService().read(PlayerLangConstants_30000.WALLOW_BEING_KICK_OFF_STATUS);
			GCWallowInfo msg = new GCWallowInfo((int) second, tip);
			player.sendMessage(msg);
		}
		
		
		
		
		
		if (_minute  > 12 * WallowConstants.TIME_EXTEND_MIN_UNIT && _minute <= 24 * WallowConstants.TIME_EXTEND_MIN_UNIT ) 
		{
			player.sendSystemMessage(PlayerLangConstants_30000.WALLOW_SAFE_STATUS, _minute / 60, _minute % 60);
		}
		else if(_minute  > 24 * WallowConstants.TIME_EXTEND_MIN_UNIT && _minute <= 30 * WallowConstants.TIME_EXTEND_MIN_UNIT) // 2:30
		{
			player.sendSystemMessage(PlayerLangConstants_30000.WALLOW_SAFE_STATUS, _minute / 60, _minute % 60);
		}
		else if(_minute  > 30 * WallowConstants.TIME_EXTEND_MIN_UNIT && _minute <= 33 * WallowConstants.TIME_EXTEND_MIN_UNIT) // 2:45
		{
			player.sendSystemMessage(PlayerLangConstants_30000.WALLOW_ENTERING_WARN_STATUS, 36 * WallowConstants.TIME_EXTEND_MIN_UNIT - _minute);
		}		
		else if(_minute  > 33 * WallowConstants.TIME_EXTEND_MIN_UNIT && _minute <= 34 * WallowConstants.TIME_EXTEND_MIN_UNIT) // 2:50
		{
			player.sendSystemMessage(PlayerLangConstants_30000.WALLOW_ENTERING_WARN_STATUS, 36 * WallowConstants.TIME_EXTEND_MIN_UNIT - _minute);
		}
		else if(_minute  > 34 * WallowConstants.TIME_EXTEND_MIN_UNIT && _minute <= 35 * WallowConstants.TIME_EXTEND_MIN_UNIT) // 2:55
		{
			player.sendSystemMessage(PlayerLangConstants_30000.WALLOW_ENTERING_WARN_STATUS, 36 * WallowConstants.TIME_EXTEND_MIN_UNIT - _minute);
		}
		else if(_minute  > 35 * WallowConstants.TIME_EXTEND_MIN_UNIT && _minute <= 36 * WallowConstants.TIME_EXTEND_MIN_UNIT) // 2:55 ~ 3:00
		{
			player.sendSystemMessage(PlayerLangConstants_30000.WALLOW_BEING_KICK_OFF_STATUS);			
		}
	}
	
	public void sendWallowAddInfo(Player player) {
		player.sendMessage(new GCWallowOpenPanel());
	}

	/**
	 * 获取防沉迷状态
	 * 
	 * @param unit
	 * @return
	 */
	public WallowStatus getWallowState(int unit) {
		if (unit < WallowConstants.NORMAL_UP_INDEX) {
			return WallowStatus.NORMAL;
		} else if (unit < WallowConstants.WARN_UP_INDEX) {
			return WallowStatus.WARN;
		}
		return WallowStatus.DANGER;
	}

	/**
	 * 开始方法
	 * <p>
	 * 该方法会通过内部消息的方式来启动service，这样做是为了让主消息线程来调用{@link #startService()}
	 */
	public void start() {
		Globals.getMessageProcessor().put(new SysWallowTickerServiceStart());
	}

	/**
	 * 开始服务
	 */
	public void startService() {
		// 周期性的同步防沉迷列表的在线时长
		this.schService
				.scheduleWithFixedDelay(new ScheduleSyncWallowOnlineTime(
						Globals.getTimeService().now()), Globals
						.getServerConfig().getWallowPeriod() * 1000, Globals
						.getServerConfig().getWallowPeriod() * 1000);
	}
	
	/**
	 * 取得在线时间
	 */
	public int getWallowTime(String passportId, int addTime){
		String s;
		try {
			s = HttpUtil.getUrl(VALID_URL+"wallow?id="+passportId+"&time="+addTime);
			int time = JSONObject.fromObject(s).optInt("time");
			return time;
		} catch (IOException e) {
			return 0;
		}
	}
	/**
	 * 取得可登陆剩余时间
	 */
	public int getWallowEnableTime(String passportId){
		String s;
		try {
			s = HttpUtil.getUrl(VALID_URL+"wallow?id="+passportId+"&time=0");
			int time = JSONObject.fromObject(s).optInt("leftTime");
			return time;
		} catch (IOException e) {
			return 0;
		}
	}
	/**
	 * 是否需要防沉迷
	 */
	public boolean needWallow(String passportId){
		String w;
		try {
			w = HttpUtil.getUrl(VALID_URL+"is_wallow?id="+passportId);
			boolean bw = JSONObject.fromObject(w).optBoolean("need");
			return bw;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
}
