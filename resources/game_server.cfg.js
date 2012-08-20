/*
 * Server基本信息 
 */
config.serverType=1
config.debug=1
config.charset="UTF-8"
config.version="0.2.0.0"
config.resourceVersion="0.2.0.0"
config.dbVersion="0.2.0.0"
config.regionId="1"
config.localHostId="1002"
config.serverGroupId="2"
config.serverIndex=1
config.serverId="1002"
config.bindIp="0.0.0.0"
config.ports="8084"
config.serverName="gameserver1"
config.serverHost="0.0.0.0"
config.serverDomain="s1.war.renren.com"
config.ioProcessor=1
config.language="zh_CN"
config.i18nDir="i18n"
config.baseResourceDir="..//resources"
config.scriptDir="scripts"
config.mapDir="maps"
config.battleReportRootPath=".\\report"
config.battleReportServiceType=0

config.dbInitType=0;
config.dbConfigName="game_server_hibernate.cfg.xml,game_server_hibernate_query.xml"
config.h2ConfigName="game_server_h2.cfg.xml,game_server_h2_query.xml"
config.battleReportDbConfigName="battle_report_ibatis_config.xml"
config.turnOnH2Cache=false;
	
config.flashSocketPolicy="<cross-domain-policy>\r\n<allow-access-from domain=\"*\" to-ports=\"80-65535\" />\r\n </cross-domain-policy>\r\n"
config.gameServerCount=1;
config.authType=1

config.maxOnlineUsers=1500
config.openNewerGuide=1;

/*
 *配置Log Server 
 */
config.logConfig.logServerIp="127.0.0.1"
config.logConfig.logServerPort=9890

/*
 *配置Telnet 
 */
config.telnetServerName="GameServer_telnet"
config.telnetBindIp="0.0.0.0"
config.telnetPort=7000

/*
 *配置local接口相关参数
 */
config.localReportOnlinePeriod=300
config.localReportStatusPeriod=60
config.turnOnLocalInterface=true;
config.requestDomain="127.0.0.1";
config.reportDomain="127.0.0.1";

config.operationCom="hithere";

 
config.chargeEnabled=true;
//配置封测礼包是否开启
config.testGiftEnabled=true;
