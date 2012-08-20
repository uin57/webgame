package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class ActivityLog extends BaseLogMessage{
       private String name;
       private long time;
    
    public ActivityLog() {    	
    }

    public ActivityLog(
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
			String name,			long time            ) {
        super(MessageType.LOG_ACTIVITY_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.name =  name;
            this.time =  time;
    }

       public String getName() {
	       return name;
       }
       public long getTime() {
	       return time;
       }
        
       public void setName(String name) {
	       this.name = name;
       }
       public void setTime(long time) {
	       this.time = time;
       }
    
    @Override
    protected boolean readLogContent() {
	        name =  readString();
	        time =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(name);
	        writeLong(time);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_ACTIVITY_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_ACTIVITY_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.name));
		            list.add(String.valueOf(this.time));
				return list;
	}
}