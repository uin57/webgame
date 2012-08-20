package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class RepLog extends BaseLogMessage{
       private String repId;
       private int curLevel;
       private long completeTime;
    
    public RepLog() {    	
    }

    public RepLog(
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
			String repId,			int curLevel,			long completeTime            ) {
        super(MessageType.LOG_REP_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.repId =  repId;
            this.curLevel =  curLevel;
            this.completeTime =  completeTime;
    }

       public String getRepId() {
	       return repId;
       }
       public int getCurLevel() {
	       return curLevel;
       }
       public long getCompleteTime() {
	       return completeTime;
       }
        
       public void setRepId(String repId) {
	       this.repId = repId;
       }
       public void setCurLevel(int curLevel) {
	       this.curLevel = curLevel;
       }
       public void setCompleteTime(long completeTime) {
	       this.completeTime = completeTime;
       }
    
    @Override
    protected boolean readLogContent() {
	        repId =  readString();
	        curLevel =  readInt();
	        completeTime =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(repId);
	        writeInt(curLevel);
	        writeLong(completeTime);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_REP_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_REP_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.repId));
		            list.add(String.valueOf(this.curLevel));
		            list.add(String.valueOf(this.completeTime));
				return list;
	}
}