package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class LevelLog extends BaseLogMessage{
       private int buildingId;
       private String buildingName;
       private long upLevelTime;
       private long intervalTime;
    
    public LevelLog() {    	
    }

    public LevelLog(
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
			int buildingId,			String buildingName,			long upLevelTime,			long intervalTime            ) {
        super(MessageType.LOG_LEVEL_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.buildingId =  buildingId;
            this.buildingName =  buildingName;
            this.upLevelTime =  upLevelTime;
            this.intervalTime =  intervalTime;
    }

       public int getBuildingId() {
	       return buildingId;
       }
       public String getBuildingName() {
	       return buildingName;
       }
       public long getUpLevelTime() {
	       return upLevelTime;
       }
       public long getIntervalTime() {
	       return intervalTime;
       }
        
       public void setBuildingId(int buildingId) {
	       this.buildingId = buildingId;
       }
       public void setBuildingName(String buildingName) {
	       this.buildingName = buildingName;
       }
       public void setUpLevelTime(long upLevelTime) {
	       this.upLevelTime = upLevelTime;
       }
       public void setIntervalTime(long intervalTime) {
	       this.intervalTime = intervalTime;
       }
    
    @Override
    protected boolean readLogContent() {
	        buildingId =  readInt();
	        buildingName =  readString();
	        upLevelTime =  readLong();
	        intervalTime =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(buildingId);
	        writeString(buildingName);
	        writeLong(upLevelTime);
	        writeLong(intervalTime);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_LEVEL_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_LEVEL_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.buildingId));
		            list.add(String.valueOf(this.buildingName));
		            list.add(String.valueOf(this.upLevelTime));
		            list.add(String.valueOf(this.intervalTime));
				return list;
	}
}