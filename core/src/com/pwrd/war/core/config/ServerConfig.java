package com.pwrd.war.core.config;

import java.io.File;

import com.pwrd.war.common.constants.CommonErrorLogInfo;
import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.core.util.StringUtils;

/**
 * {@link Config}的简单实现
 * 
 */
public abstract class ServerConfig implements Config {
	/**
	 * 服务器类型：1-GameServer 2-WorldServer 3-LoginServer 4-DBSServer 5-AgentServer 6-LogServer
	 */
	private int serverType;
	/** 生产模式:0 调式模式:1 */
	private int debug = 0;
	/** 系统的字符编码 */
	private String charset;
	/** 系统配置的版本号 */
	private String version;
	/** 系统配置的资源版本号 */
	private String resourceVersion;
	/** 大区的ID */
	private String regionId;
	/** 服的ID */
	private String serverGroupId;
	/** 游戏世界ID,提供给Local使用,规则是 regionId*serverGroupId,如101,表示1区,1服 */
	private String localHostId;
	/** 服务器ID 规则是:是 localHostId+serverIndexId,如1011,表示1区,1服的第一个服务器 */
	private String serverId;
	/** 服务绑定的IP */
	private String bindIp;
	/** 服务器在服务器组中的索引值 */
	private int serverIndex;
	/** 服务器监听的端口,多个商品以逗号","分隔 */
	private String ports;
	/** 服务器的名称 */
	private String serverName;
	/** 服务器的主机ip */
	private String serverHost;
	/** 服务器组的域名 s1.l.mop.com */
	private String serverDomain;
	/** 每个端口IO处理的线程个数 */
	private int ioProcessor;
	/** 系统所使用的语言 */
	private String language;
	/** 多语言资源所在的目录 */
	private String i18nDir;
	/** 资源文件根目录 */
	private String baseResourceDir;
	/** 脚本所在的目录 */
	private String scriptDir;
	/** 地图配置文件所在目录 **/
	private String mapDir;
	/** 脚本的头文件 */
	private String scriptHeaderName;


	/** 物品编辑器自动生成的配置目录 */
	private String exportDataDir;
	/** Flash 客户端发送poliyc请求时的响应 */
	private String flashSocketPolicy;
	/** Local的URL地址 */
	private String localDomain;
	/** 是否定时检查玩家的连接 */
	private boolean checkPing;
	/** 如果checkPing为true,表示ping的周期,单位秒 */
	private int pingPeriod;
	/** ping的超时时间，操作该时间未收到ping消息，则断开客户端连接 */
	private int pingTimeOut;
	/** 数据库初始化类型： 0 Hibernate 1 Ibatis */
	private int dbInitType = 0;
	/** 数据库配置文件路径 */
	private String dbConfigName;
	/** h2配置文件路径 */
	private String h2ConfigName;

	/** GameServer个数 */
	private int gameServerCount;

	/** 是否使用H2Cache */
	private boolean turnOnH2Cache = false;
	
	/** 是否开启local接口 */
	private boolean turnOnLocalInterface = true;
	/** 请求接口所在域名 */
	private String requestDomain;
	/** 汇报接口所在域名 */
	private String reportDomain;

	/** Log Server配置 */
	private LogConfig logConfig = new LogConfig();
	
	/** MIS (后台)系统 IP 地址 */
	private String misIps = null;	

	@Override
	public boolean getIsDebug() {
		return getDebug() == 1;
	}

	@Override
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 取得资源文件的绝对路径
	 * 
	 * @param path
	 * @return
	 */
	public String getResourceFullPath(String path) {
		return this.baseResourceDir + File.separator + path;
	}

	/**
	 * 取得资源文件的绝对路径
	 * 
	 * @param pathes
	 *            路径的参数,每个参数将使用路径分隔符连接起来
	 * @return
	 */
	public String getResourceFullPath(String... pathes) {
		StringBuilder _sb = new StringBuilder();
		_sb.append(this.baseResourceDir);
		for (String _path : pathes) {
			_sb.append(File.separator);
			_sb.append(_path);
		}
		return _sb.toString();
	}

	public String getBaseResourceDir() {
		return baseResourceDir;
	}

	public void setBaseResourceDir(String baseResourceDir) {
		this.baseResourceDir = baseResourceDir;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getI18nDir() {
		return i18nDir;
	}

	public void setI18nDir(String dir) {
		i18nDir = dir;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * @return the localHostId
	 */
	public String getLocalHostId() {
		return localHostId;
	}

	/**
	 * @param localHostId
	 *            the localHostId to set
	 */
	public void setLocalHostId(String localHostId) {
		this.localHostId = localHostId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionId() {
		return regionId;
	}

	/**
	 * @return the serverId
	 */
	public String getServerId() {
		return serverId;
	}

	/**
	 * @param serverId
	 *            the serverId to set
	 */
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	/**
	 * @return the serverGroupId
	 */
	public String getServerGroupId() {
		return serverGroupId;
	}

	/**
	 * @param serverGroupId
	 *            the serverGroupId to set
	 */
	public void setServerGroupId(String serverGroupId) {
		this.serverGroupId = serverGroupId;
	}

	/**
	 * @return the serverIndexId
	 */
	public int getServerIndex() {
		return serverIndex;
	}

	/**
	 * @param serverIndexId
	 *            the serverIndexId to set
	 */
	public void setServerIndex(int serverIndex) {
		this.serverIndex = serverIndex;
	}

	public String getBindIp() {
		return bindIp;
	}

	public void setBindIp(String bindIp) {
		this.bindIp = bindIp;
	}

	public String getPorts() {
		return ports;
	}

	public void setPorts(String ports) {
		this.ports = ports;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	/**
	 * @return the ioProcessor
	 */
	public int getIoProcessor() {
		return ioProcessor;
	}

	/**
	 * @param ioProcessor
	 *            the ioProcessor to set
	 */
	public void setIoProcessor(int ioProcessor) {
		this.ioProcessor = ioProcessor;
	}


	public boolean isTurnOnH2Cache() {
		return turnOnH2Cache;
	}

	public void setTurnOnH2Cache(boolean turnOnH2Cache) {
		this.turnOnH2Cache = turnOnH2Cache;
	}

	public LogConfig getLogConfig() {
		return logConfig;
	}

	public void setDebug(int debug) {
		this.debug = debug;
	}

	public int getDebug() {
		return debug;
	}

	public String getScriptDir() {
		return scriptDir;
	}

	public void setScriptDir(String scriptDir) {
		this.scriptDir = scriptDir;
	}

	public String getMapDir() {
		return mapDir;
	}

	public void setMapDir(String mapDir) {
		this.mapDir = mapDir;
	}

	public String getFlashSocketPolicy() {
		return flashSocketPolicy;
	}

	public void setFlashSocketPolicy(String flashSocketPolicy) {
		this.flashSocketPolicy = flashSocketPolicy;
	}

	public boolean isCheckPing() {
		return checkPing;
	}

	public void setCheckPing(boolean checkPing) {
		this.checkPing = checkPing;
	}

	public int getPingPeriod() {
		return pingPeriod;
	}

	public void setPingPeriod(int pingPeriod) {
		this.pingPeriod = pingPeriod;
	}

	public int getDbInitType() {
		return dbInitType;
	}

	public void setDbInitType(int dbInitType) {
		this.dbInitType = dbInitType;
	}

	public String getDbConfigName() {
		return dbConfigName;
	}
	

	public void setDbConfigName(String dbConfigName) {
		this.dbConfigName = dbConfigName;
	}
	
	public String getH2ConfigName() {
		return h2ConfigName;
	}
	
	public void setH2ConfigName(String h2ConfigName) {
		this.h2ConfigName = h2ConfigName;
	}

	public void setExportDataDir(String exportDataDir) {
		this.exportDataDir = exportDataDir;
	}

	public String getExportDataDir() {
		return exportDataDir;
	}

	public void setScriptHeaderName(String scriptHeaderName) {
		this.scriptHeaderName = scriptHeaderName;
	}

	public String getScriptHeaderName() {
		return scriptHeaderName;
	}

	/**
	 * @return the localDomain
	 */
	public String getLocalDomain() {
		return localDomain;
	}

	/**
	 * @param localDomain
	 *            the localDomain to set
	 */
	public void setLocalDomain(String localDomain) {
		this.localDomain = localDomain;
	}

	public int getPingTimeOut() {
		return pingTimeOut;
	}

	public void setPingTimeOut(int pingTimeOut) {
		this.pingTimeOut = pingTimeOut;
	}

	public void setServerType(int serverType) {
		this.serverType = serverType;
	}

	public int getServerType() {
		return serverType;
	}


	
	/**
	 * Log Server 配置
	 * 
	 * 
	 */
	public static class LogConfig {
		/** Log Server地址 */
		private String logServerIp;
		/** Log Server端口 */
		private int logServerPort;

		public void setLogServerIp(String logServerIp) {
			this.logServerIp = logServerIp;
		}

		public String getLogServerIp() {
			return logServerIp;
		}

		public void setLogServerPort(int logServerPort) {
			this.logServerPort = logServerPort;
		}

		public int getLogServerPort() {
			return logServerPort;
		}

	}

	@Override
	public void validate() {
		if (serverType < 1) {
			throw new IllegalArgumentException(
					"The serverType must not be empty");
		}

		if (StringUtils.isEmpty(this.regionId)
				|| StringUtils.isEmpty(this.serverGroupId)) {
			throw new IllegalArgumentException(
					"The regionId,serverGroupId and the serverIndexId must not be empty");
		}
		if (StringUtils.isEmpty(this.localHostId)
				|| StringUtils.isEmpty(this.serverId)) {
			throw new IllegalArgumentException(
					"The localHostId and the serverId must not be empty");
		}
		if (this.ports == null || (ports = ports.trim()).length() == 0) {
			throw new IllegalArgumentException(ErrorsUtil.error(
					CommonErrorLogInfo.ARG_NOT_NULL_EXCEPT, "", "ports"));
		}
		// 版本号配置检查
		if (this.getVersion() == null) {
			throw new IllegalArgumentException("The version  must not be null");
		}
		if (checkPing) {
			// 如果启用ping,那么ping的周期应该大于0
			if (this.pingPeriod <= 0) {
				throw new IllegalArgumentException(ErrorsUtil.error(
						CommonErrorLogInfo.ARG_POSITIVE_NUMBER_EXCEPT, "",
						"pingPeriod"));
			}
		}
		if (serverType == 1) {
			/* 日志服务器检查 */
			if (this.logConfig.logServerIp == null
					|| (this.logConfig.logServerIp = logConfig.logServerIp
							.trim()).length() == 0) {
				throw new IllegalArgumentException(
						"The logsConfig.logServerIp must not be null.");
			}

			if (this.logConfig.logServerPort <= 0) {
				throw new IllegalArgumentException(
						"The logsConfig.logServerPort must be greater than 0.");
			}
		}
	}


	public void setServerDomain(String serverDomain) {
		this.serverDomain = serverDomain;
	}

	public String getServerDomain() {
		return serverDomain;
	}

	/**
	 * 获得脚本文件全目录
	 * 
	 * @return
	 */
	public String getScriptDirFullPath() {
		return this.getResourceFullPath(this.getScriptDir());
	}
	public String getMapDirFullPath(){
		return this.getResourceFullPath(this.getMapDir());
	}
	public void setResourceVersion(String resourceVersion) {
		this.resourceVersion = resourceVersion;
	}

	public String getResourceVersion() {
		return resourceVersion;
	}

	public void setGameServerCount(int gameServerCount) {
		this.gameServerCount = gameServerCount;
	}

	public int getGameServerCount() {
		return gameServerCount;
	}
	
	public boolean isTurnOnLocalInterface() {
		return turnOnLocalInterface;
	}

	public void setTurnOnLocalInterface(boolean turnOnLocalInterface) {
		this.turnOnLocalInterface = turnOnLocalInterface;
	}
	
	public String getRequestDomain() {
		return requestDomain;
	}

	public void setRequestDomain(String requestDomain) {
		this.requestDomain = requestDomain;
	}

	public String getReportDomain() {
		return reportDomain;
	}

	public void setReportDomain(String reportDomain) {
		this.reportDomain = reportDomain;
	}
	
	

	/**
	 * 获取 MIS (后台)系统 IP 地址
	 * 
	 * @return
	 */
	public String getMisIps() {
		return this.misIps;
	}

	/**
	 * 设置 MIS (后台)系统 IP 地址
	 * 
	 * @param allowedIps
	 */
	public void setMisIps(String allowedIps) {
		this.misIps = allowedIps;
	}
}
