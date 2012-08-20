package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class PrizeLog extends BaseLogMessage{
       private long loginTime;
       private int prizeType;
       private int drawCount;
    
    public PrizeLog() {    	
    }

    public PrizeLog(
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
			long loginTime,			int prizeType,			int drawCount            ) {
        super(MessageType.LOG_PRIZE_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.loginTime =  loginTime;
            this.prizeType =  prizeType;
            this.drawCount =  drawCount;
    }

       public long getLoginTime() {
	       return loginTime;
       }
       public int getPrizeType() {
	       return prizeType;
       }
       public int getDrawCount() {
	       return drawCount;
       }
        
       public void setLoginTime(long loginTime) {
	       this.loginTime = loginTime;
       }
       public void setPrizeType(int prizeType) {
	       this.prizeType = prizeType;
       }
       public void setDrawCount(int drawCount) {
	       this.drawCount = drawCount;
       }
    
    @Override
    protected boolean readLogContent() {
	        loginTime =  readLong();
	        prizeType =  readInt();
	        drawCount =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(loginTime);
	        writeInt(prizeType);
	        writeInt(drawCount);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_PRIZE_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_PRIZE_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.loginTime));
		            list.add(String.valueOf(this.prizeType));
		            list.add(String.valueOf(this.drawCount));
				return list;
	}
}