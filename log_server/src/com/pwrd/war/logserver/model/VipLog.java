package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class VipLog extends BaseLogMessage{
       private int curVipLevel;
       private long completeTime;
    
    public VipLog() {    	
    }

    public VipLog(
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
			int curVipLevel,			long completeTime            ) {
        super(MessageType.LOG_VIP_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.curVipLevel =  curVipLevel;
            this.completeTime =  completeTime;
    }

       public int getCurVipLevel() {
	       return curVipLevel;
       }
       public long getCompleteTime() {
	       return completeTime;
       }
        
       public void setCurVipLevel(int curVipLevel) {
	       this.curVipLevel = curVipLevel;
       }
       public void setCompleteTime(long completeTime) {
	       this.completeTime = completeTime;
       }
    
    @Override
    protected boolean readLogContent() {
	        curVipLevel =  readInt();
	        completeTime =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(curVipLevel);
	        writeLong(completeTime);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_VIP_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_VIP_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.curVipLevel));
		            list.add(String.valueOf(this.completeTime));
				return list;
	}
}