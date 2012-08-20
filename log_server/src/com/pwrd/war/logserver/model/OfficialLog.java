package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class OfficialLog extends BaseLogMessage{
       private int officialLevel;
       private long time;
    
    public OfficialLog() {    	
    }

    public OfficialLog(
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
			int officialLevel,			long time            ) {
        super(MessageType.LOG_OFFICIAL_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.officialLevel =  officialLevel;
            this.time =  time;
    }

       public int getOfficialLevel() {
	       return officialLevel;
       }
       public long getTime() {
	       return time;
       }
        
       public void setOfficialLevel(int officialLevel) {
	       this.officialLevel = officialLevel;
       }
       public void setTime(long time) {
	       this.time = time;
       }
    
    @Override
    protected boolean readLogContent() {
	        officialLevel =  readInt();
	        time =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(officialLevel);
	        writeLong(time);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_OFFICIAL_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_OFFICIAL_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.officialLevel));
		            list.add(String.valueOf(this.time));
				return list;
	}
}