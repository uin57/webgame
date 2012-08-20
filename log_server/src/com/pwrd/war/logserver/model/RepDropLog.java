package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class RepDropLog extends BaseLogMessage{
       private String repId;
       private String repName;
       private String dropId;
       private int dropNum;
       private int currencyType;
       private int currency;
       private long dropTime;
       private String dropMonster;
    
    public RepDropLog() {    	
    }

    public RepDropLog(
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
			String repId,			String repName,			String dropId,			int dropNum,			int currencyType,			int currency,			long dropTime,			String dropMonster            ) {
        super(MessageType.LOG_REPDROP_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.repId =  repId;
            this.repName =  repName;
            this.dropId =  dropId;
            this.dropNum =  dropNum;
            this.currencyType =  currencyType;
            this.currency =  currency;
            this.dropTime =  dropTime;
            this.dropMonster =  dropMonster;
    }

       public String getRepId() {
	       return repId;
       }
       public String getRepName() {
	       return repName;
       }
       public String getDropId() {
	       return dropId;
       }
       public int getDropNum() {
	       return dropNum;
       }
       public int getCurrencyType() {
	       return currencyType;
       }
       public int getCurrency() {
	       return currency;
       }
       public long getDropTime() {
	       return dropTime;
       }
       public String getDropMonster() {
	       return dropMonster;
       }
        
       public void setRepId(String repId) {
	       this.repId = repId;
       }
       public void setRepName(String repName) {
	       this.repName = repName;
       }
       public void setDropId(String dropId) {
	       this.dropId = dropId;
       }
       public void setDropNum(int dropNum) {
	       this.dropNum = dropNum;
       }
       public void setCurrencyType(int currencyType) {
	       this.currencyType = currencyType;
       }
       public void setCurrency(int currency) {
	       this.currency = currency;
       }
       public void setDropTime(long dropTime) {
	       this.dropTime = dropTime;
       }
       public void setDropMonster(String dropMonster) {
	       this.dropMonster = dropMonster;
       }
    
    @Override
    protected boolean readLogContent() {
	        repId =  readString();
	        repName =  readString();
	        dropId =  readString();
	        dropNum =  readInt();
	        currencyType =  readInt();
	        currency =  readInt();
	        dropTime =  readLong();
	        dropMonster =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(repId);
	        writeString(repName);
	        writeString(dropId);
	        writeInt(dropNum);
	        writeInt(currencyType);
	        writeInt(currency);
	        writeLong(dropTime);
	        writeString(dropMonster);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_REPDROP_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_REPDROP_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.repId));
		            list.add(String.valueOf(this.repName));
		            list.add(String.valueOf(this.dropId));
		            list.add(String.valueOf(this.dropNum));
		            list.add(String.valueOf(this.currencyType));
		            list.add(String.valueOf(this.currency));
		            list.add(String.valueOf(this.dropTime));
		            list.add(String.valueOf(this.dropMonster));
				return list;
	}
}