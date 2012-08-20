package com.pwrd.war.logserver;

import java.util.List;

import com.google.common.collect.Lists;
import com.pwrd.war.core.msg.BaseMessage;
import com.pwrd.war.core.util.TimeUtils;

/**
 * 实现BaseLogMessage
 * 
 */
public abstract class BaseLogMessage extends BaseMessage {
	
	private String tableName;// 日志表名
	
	private int id;// 对应数据库主键	
	
	private int logType; // 日志类型 SharedConstants.LOG_TYPE_XXX
	
	private long logTime; // 日志时间，Java中的统一时间
	
	private String accountId; // 账号信息
	
	private String charId; // 角色信息
	
	private int regionId; // 服务器区ID
	
	private int serverId; // 服务器ID
	
	private int level; // 用户当前级别	
	
	private int allianceId; //玩家国家id

	private int vipLevel; // 玩家的VIP等级
	
	private int reason; // 日子的原因
	
	private String accountName;
	
	private String charName;
	
	private String param; // 附加参数
	
	private String sceneId;//所在地图
	private int x;//x
	private int y;//y
	private long createTime;// 创建时间
	
	public BaseLogMessage() {

	}

	public BaseLogMessage(int type, long time, int rid, int sid, String aid,
			String accountName, String cid, String charName, int level,int allianceId,int vipLevel,
			int reason, String sceneId, int x, int y,String param) {
		this.logType = type;
		this.logTime = time;
		this.regionId = rid;
		this.serverId = sid;
		this.accountId = aid;
		this.accountName = accountName;
		this.charId = cid;
		this.charName = charName;
		this.level = level;		
		this.allianceId = allianceId;
		this.vipLevel = vipLevel;
		this.reason = reason;
		this.sceneId = sceneId;
		this.x = x;
		this.y = y;
		this.param = param;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName
	 *            the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the charName
	 */
	public String getCharName() {
		return charName;
	}

	/**
	 * @param charName
	 *            the charName to set
	 */
	public void setCharName(String charName) {
		this.charName = charName;
	}

	/**
	 * @return the reason
	 */
	public int getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(int reason) {
		this.reason = reason;
	}

	/**
	 * @return the param
	 */
	public String getParam() {
		return param;
	}

	/**
	 * @param param
	 *            the param to set
	 */
	public void setParam(String param) {
		this.param = param;
	}

	/**
	 * 读取子类的日志内容
	 * 
	 * @return
	 */
	abstract protected boolean readLogContent();

	/**
	 * 写入子类的日志内容
	 * 
	 * @return
	 */
	abstract protected boolean writeLogContent();

	@Override
	protected boolean readImpl() {
		logType = readInt();
		logTime = readLong();
		regionId = readInt();
		serverId = readInt();
		accountId = readString();
		charId = readString();
		level = readInt();
		allianceId = readInt();
		vipLevel = readInt();
		accountName = readString();
		charName = readString();
		this.reason = readInt();
		this.sceneId = readString();
		this.x = readInt();
		this.y = readInt();
		this.param = readString();
		return readLogContent();
	}

	@Override
	protected boolean writeImpl() {
		writeInt(logType);
		writeLong(logTime);
		writeInt(regionId);
		writeInt(serverId);
		writeString(accountId);
		writeString(charId);
		writeInt(level);
		writeInt(allianceId);
		writeInt(vipLevel);
		writeString(accountName);
		writeString(charName);
		writeInt(this.reason);
		writeString(this.sceneId);
		writeInt(this.x);
		writeInt(this.y);
		writeString(this.param);
		return writeLogContent();
	}

	public int getLogType() {
		return logType;
	}

	public void setLogType(int logType) {
		this.logType = logType;
	}

	public long getLogTime() {
		return logTime;
	}

	public void setLogTime(long logTime) {
		this.logTime = logTime;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCharId() {
		return charId;
	}

	public void setCharId(String charId) {
		this.charId = charId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(int allianceId) {
		this.allianceId = allianceId;
	}

	
	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
	}
	
	protected List<String> toPropStrList() {
		List<String> result = Lists.newArrayList();
		result.add(String.valueOf(id));
		result.add(String.valueOf(logType));
		result.add(TimeUtils.formatYMDHMTime(logTime));
		result.add(String.valueOf(regionId));
		result.add(String.valueOf(serverId));
		result.add(String.valueOf(accountId));
		result.add(String.valueOf(accountName));
		result.add(String.valueOf(charId));
		result.add(String.valueOf(charName));
		result.add(String.valueOf(level));
//		result.add(String.valueOf(cityLevel));
//		result.add(String.valueOf(subCityMark));
		result.add(String.valueOf(allianceId));
//		result.add(String.valueOf(guildId));
//		result.add(String.valueOf(guildName));
		result.add(String.valueOf(vipLevel));
		result.add(String.valueOf(sceneId));
		result.add(String.valueOf(x));
		result.add(String.valueOf(y));
		result.add(String.valueOf(reason));
		return result;
	}
	
	public List<String> getPropStrList() {
		List<String> result = this.toPropStrList();
		result.add(param);
		return result;
	}

	public String getSceneId() {
		return sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
