package com.pwrd.war.gameserver.common.config;

import java.io.File;

import com.pwrd.war.common.constants.FunctionSwitches;
import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.core.config.ServerConfig;

/**
 * 服务器配置信息
 * 
 * 一些key/value对 获取资源的路径
 * 
 */
public class GameServerConfig extends ServerConfig {
	
	/** 系统配置的数据库版本号 */
	private String dbVersion;
	
	/** 最大允许在线人数 */
	private int maxOnlineUsers;
	
	/** 心跳线程个数[暂时只保持单线程] */
	private int heartbeatThreadNum = 10;

	/** 记录统计值开关 */
	private boolean logStatistics = true;
	
	/* Telnet服务参数定义 */
	/** Telnet服务器名称 */
	private String telnetServerName;
	/** Telnet绑定的ip */
	private String telnetBindIp;
	/** Telnet绑定的端口 */
	private String telnetPort;
	
	/** 定时向Local汇报在线人数的间隔 单位：秒 */
	private int localReportOnlinePeriod = 300;	
	/** 定时向Local汇报游戏服务器状态的间隔 单位：秒 */
	private int localReportStatusPeriod = 60;
	
	/** 认证方式, 默认平台认证 */
	private int authType = SharedConstants.AUTH_TYPE_INTERFACE;	
	/** 登陆墙是否打开，默认关闭 */
	private volatile boolean loginWallEnabled = false;
	
	/** 反沉迷累计时长同步间隔 5分钟 :5 * 60,单位：秒 */
	private long wallowPeriod = 5 * 60;
	/** 防沉迷配置 */
	private boolean wallowControlled = true;
	
	/** 战报服务的类型,0 file 1 db */
	private int battleReportServiceType = 0;
	
	/** 战报数据库配置文件*/
	private String battleReportDbConfigName;
	/** 战报文件存储目录 */
	private String battleReportRootPath;
	
	/** 运营公司 */
	private String operationCom;
	
	/** 功能开关 */
	private FunctionSwitches funcSwitches = new FunctionSwitches();
	/** 开启新手引导 */
	private int openNewerGuide = 1;
	/** 最大玩家等级 */
	private int maxHumanLevel = 120;
	/** 世界聊天所需钻石 */
	private int worldChatNeedDiamond = 2;
	
	public GameServerConfig() {

	}

	/**
	 * 取得资源文件的绝对路径
	 * 
	 * @param pathes
	 *            路径的参数,每个参数将使用路径分隔符连接起来
	 * @return
	 */
	@Override
	public String getResourceFullPath(String... pathes) {
		StringBuilder _sb = new StringBuilder();
		_sb.append(this.getBaseResourceDir());
		for (String _path : pathes) {
			_sb.append(File.separator);
			_sb.append(_path);
		}
		return _sb.toString();
	}
	

	@Override
	public void validate() {
		super.validate();
	}

	/**
	 * 登陆墙是否打开
	 * @return
	 */
	public boolean isLoginWallEnabled() {
		return loginWallEnabled;
	}

	/**
	 * 设置登陆墙是否打开
	 * @param loginWallEnabled
	 */
	public void setLoginWallEnabled(boolean loginWallEnabled) {
		this.loginWallEnabled = loginWallEnabled;
	}
	
	/**
	 * 获得脚本文件路径
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public String getResourceFilePath(String fileName) {
		return this.getResourceFullPath(this.getScriptDir(), fileName);
	}

	/**
	 * @return the maxOnlineUsers
	 */
	public int getMaxOnlineUsers() {
		return maxOnlineUsers;
	}

	/**
	 * @param maxOnlineUsers
	 *            the maxOnlineUsers to set
	 */
	public void setMaxOnlineUsers(int maxOnlineUsers) {
		this.maxOnlineUsers = maxOnlineUsers;
	}

	
	public int getHeartbeatThreadNum() {
		return heartbeatThreadNum;
	}

	public void setHeartbeatThreadNum(int heartbeatThreadNum) {
		this.heartbeatThreadNum = heartbeatThreadNum;
	}

	public boolean isLogStatistics() {
		return logStatistics;
	}

	public void setLogStatistics(boolean logStatistics) {
		this.logStatistics = logStatistics;
	}


	public String getTelnetServerName() {
		return telnetServerName;
	}


	public void setTelnetServerName(String telnetServerName) {
		this.telnetServerName = telnetServerName;
	}


	public String getTelnetBindIp() {
		return telnetBindIp;
	}


	public void setTelnetBindIp(String telnetBindIp) {
		this.telnetBindIp = telnetBindIp;
	}


	public String getTelnetPort() {
		return telnetPort;
	}


	public void setTelnetPort(String telnetPort) {
		this.telnetPort = telnetPort;
	}


	public int getLocalReportOnlinePeriod() {
		return localReportOnlinePeriod;
	}


	public void setLocalReportOnlinePeriod(int localReportOnlinePeriod) {
		this.localReportOnlinePeriod = localReportOnlinePeriod;
	}


	public int getLocalReportStatusPeriod() {
		return localReportStatusPeriod;
	}


	public void setLocalReportStatusPeriod(int localReportStatusPeriod) {
		this.localReportStatusPeriod = localReportStatusPeriod;
	}
	
	
	public long getWallowPeriod() {
		return wallowPeriod;
	}

	public void setWallowPeriod(long wallowPeriod) {
		this.wallowPeriod = wallowPeriod;
	}

	public boolean isWallowControlled() {
		return wallowControlled;
	}

	public void setWallowControlled(boolean wallowControlled) {
		this.wallowControlled = wallowControlled;
	}
	
	public FunctionSwitches getFuncSwitches() {
		return funcSwitches;
	}
	
	public int getAuthType() {
		return authType;
	}

	public void setAuthType(int authType) {
		this.authType = authType;
	}
	
	public int getBattleReportServiceType() {
		return battleReportServiceType;
	}
	
	public void setBattleReportServiceType(int battleReportServiceType) {
		this.battleReportServiceType = battleReportServiceType;
	}

	public String getBattleReportDbConfigName() {
		return battleReportDbConfigName;
	}
	
	public void setBattleReportDbConfigName(String battleReportDbConfigName) {
		this.battleReportDbConfigName = battleReportDbConfigName;
	}
	
	public String getBattleReportRootPath() {
		return battleReportRootPath;
	}
	
	public void setBattleReportRootPath(String battleReportRootPath) {
		this.battleReportRootPath = battleReportRootPath;
	}
	
	public boolean isBattleReportFileOutputOn()
	{
		return funcSwitches.isBattleReportFileOutput();
	}
	
	public void setBattleReportFileOutputOn(boolean value) {
		funcSwitches.setBattleReportFileOutput(value);
	}
	
	public boolean isChargeEnabled() {
		return funcSwitches.isChargeEnabled();
	}
	
	public void setChargeEnabled(boolean value) {
		funcSwitches.setChargeEnabled(value);
	}
	public boolean isTestGiftEnabled() {
		return funcSwitches.isTestGiftEnabled();
	}
	
	public void setTestGiftEnabled(boolean value) {
		funcSwitches.setTestGiftEnabled(value);
	}
	
	public void setDbVersion(String dbVersion) {
		this.dbVersion = dbVersion;
	}

	public String getDbVersion() {
		return dbVersion;
	}

	public int getOpenNewerGuide() {
		return this.openNewerGuide;
	}

	public void setOpenNewerGuide(int value) {
		this.openNewerGuide = value;
	}

	public int getMaxHumanLevel() {
		return this.maxHumanLevel;
	}

	public void setMaxHumanLevel(int value) {
		this.maxHumanLevel = value;
	}

	public int getWorldChatNeedDiamond() {
		return this.worldChatNeedDiamond;
	}

	public void setWorldChatNeedDiamond(int value) {
		this.worldChatNeedDiamond = value;
	}

	public String getOperationCom() {
		return operationCom;
	}

	public void setOperationCom(String operationCom) {
		this.operationCom = operationCom;
	}
	
	
}
