package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class CheatLog extends BaseLogMessage{
       private String details;
    
    public CheatLog() {    	
    }

    public CheatLog(
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
			String details            ) {
        super(MessageType.LOG_CHEAT_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.details =  details;
    }

       public String getDetails() {
	       return details;
       }
        
       public void setDetails(String details) {
	       this.details = details;
       }
    
    @Override
    protected boolean readLogContent() {
	        details =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(details);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_CHEAT_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_CHEAT_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.details));
				return list;
	}
}