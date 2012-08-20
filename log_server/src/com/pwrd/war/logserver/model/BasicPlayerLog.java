package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class BasicPlayerLog extends BaseLogMessage{
       private String ip;
       private int exp;
       private int rankId;
       private String rankName;
       private String sceneName;
       private int missionId;
       private String missionName;
    
    public BasicPlayerLog() {    	
    }

    public BasicPlayerLog(
                   long logTime,
                   int regionId,
                   int serverId,
                   String accountId,
                   String accountName,
                   String charId,
                   String charName,
                   int level,
                   int allianceId,
                   int vipLevel,
                   int reason,
                   String sceneId,
                   int x,
                   int y,
                   String param,
			String ip,			int exp,			int rankId,			String rankName,			String sceneName,			int missionId,			String missionName            ) {
        super(MessageType.LOG_BASICPLAYER_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.ip =  ip;
            this.exp =  exp;
            this.rankId =  rankId;
            this.rankName =  rankName;
            this.sceneName =  sceneName;
            this.missionId =  missionId;
            this.missionName =  missionName;
    }

       public String getIp() {
	       return ip;
       }
       public int getExp() {
	       return exp;
       }
       public int getRankId() {
	       return rankId;
       }
       public String getRankName() {
	       return rankName;
       }
       public String getSceneName() {
	       return sceneName;
       }
       public int getMissionId() {
	       return missionId;
       }
       public String getMissionName() {
	       return missionName;
       }
        
       public void setIp(String ip) {
	       this.ip = ip;
       }
       public void setExp(int exp) {
	       this.exp = exp;
       }
       public void setRankId(int rankId) {
	       this.rankId = rankId;
       }
       public void setRankName(String rankName) {
	       this.rankName = rankName;
       }
       public void setSceneName(String sceneName) {
	       this.sceneName = sceneName;
       }
       public void setMissionId(int missionId) {
	       this.missionId = missionId;
       }
       public void setMissionName(String missionName) {
	       this.missionName = missionName;
       }
    
    @Override
    protected boolean readLogContent() {
	        ip =  readString();
	        exp =  readInt();
	        rankId =  readInt();
	        rankName =  readString();
	        sceneName =  readString();
	        missionId =  readInt();
	        missionName =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(ip);
	        writeInt(exp);
	        writeInt(rankId);
	        writeString(rankName);
	        writeString(sceneName);
	        writeInt(missionId);
	        writeString(missionName);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_BASICPLAYER_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_BASICPLAYER_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.ip));
		            list.add(String.valueOf(this.exp));
		            list.add(String.valueOf(this.rankId));
		            list.add(String.valueOf(this.rankName));
		            list.add(String.valueOf(this.sceneName));
		            list.add(String.valueOf(this.missionId));
		            list.add(String.valueOf(this.missionName));
				return list;
	}
}