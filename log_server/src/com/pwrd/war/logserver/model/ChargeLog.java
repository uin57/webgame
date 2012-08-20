package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class ChargeLog extends BaseLogMessage{
       private int num;
       private long time;
    
    public ChargeLog() {    	
    }

    public ChargeLog(
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
			int num,			long time            ) {
        super(MessageType.LOG_CHARGE_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.num =  num;
            this.time =  time;
    }

       public int getNum() {
	       return num;
       }
       public long getTime() {
	       return time;
       }
        
       public void setNum(int num) {
	       this.num = num;
       }
       public void setTime(long time) {
	       this.time = time;
       }
    
    @Override
    protected boolean readLogContent() {
	        num =  readInt();
	        time =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(num);
	        writeLong(time);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_CHARGE_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_CHARGE_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.num));
		            list.add(String.valueOf(this.time));
				return list;
	}
}