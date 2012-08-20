package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class CurrencyLog extends BaseLogMessage{
       private int costFlag;
       private int currencyType;
       private int currencyNum;
       private String costType;
       private String costDetailType;
       private String detailReason;
    
    public CurrencyLog() {    	
    }

    public CurrencyLog(
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
			int costFlag,			int currencyType,			int currencyNum,			String costType,			String costDetailType,			String detailReason            ) {
        super(MessageType.LOG_CURRENCY_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.costFlag =  costFlag;
            this.currencyType =  currencyType;
            this.currencyNum =  currencyNum;
            this.costType =  costType;
            this.costDetailType =  costDetailType;
            this.detailReason =  detailReason;
    }

       public int getCostFlag() {
	       return costFlag;
       }
       public int getCurrencyType() {
	       return currencyType;
       }
       public int getCurrencyNum() {
	       return currencyNum;
       }
       public String getCostType() {
	       return costType;
       }
       public String getCostDetailType() {
	       return costDetailType;
       }
       public String getDetailReason() {
	       return detailReason;
       }
        
       public void setCostFlag(int costFlag) {
	       this.costFlag = costFlag;
       }
       public void setCurrencyType(int currencyType) {
	       this.currencyType = currencyType;
       }
       public void setCurrencyNum(int currencyNum) {
	       this.currencyNum = currencyNum;
       }
       public void setCostType(String costType) {
	       this.costType = costType;
       }
       public void setCostDetailType(String costDetailType) {
	       this.costDetailType = costDetailType;
       }
       public void setDetailReason(String detailReason) {
	       this.detailReason = detailReason;
       }
    
    @Override
    protected boolean readLogContent() {
	        costFlag =  readInt();
	        currencyType =  readInt();
	        currencyNum =  readInt();
	        costType =  readString();
	        costDetailType =  readString();
	        detailReason =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(costFlag);
	        writeInt(currencyType);
	        writeInt(currencyNum);
	        writeString(costType);
	        writeString(costDetailType);
	        writeString(detailReason);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_CURRENCY_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_CURRENCY_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.costFlag));
		            list.add(String.valueOf(this.currencyType));
		            list.add(String.valueOf(this.currencyNum));
		            list.add(String.valueOf(this.costType));
		            list.add(String.valueOf(this.costDetailType));
		            list.add(String.valueOf(this.detailReason));
				return list;
	}
}