package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class XinghunLog extends BaseLogMessage{
       private String xinghunId;
       private String xinghunName;
       private String feature;
       private long completeTime;
    
    public XinghunLog() {    	
    }

    public XinghunLog(
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
			String xinghunId,			String xinghunName,			String feature,			long completeTime            ) {
        super(MessageType.LOG_XINGHUN_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.xinghunId =  xinghunId;
            this.xinghunName =  xinghunName;
            this.feature =  feature;
            this.completeTime =  completeTime;
    }

       public String getXinghunId() {
	       return xinghunId;
       }
       public String getXinghunName() {
	       return xinghunName;
       }
       public String getFeature() {
	       return feature;
       }
       public long getCompleteTime() {
	       return completeTime;
       }
        
       public void setXinghunId(String xinghunId) {
	       this.xinghunId = xinghunId;
       }
       public void setXinghunName(String xinghunName) {
	       this.xinghunName = xinghunName;
       }
       public void setFeature(String feature) {
	       this.feature = feature;
       }
       public void setCompleteTime(long completeTime) {
	       this.completeTime = completeTime;
       }
    
    @Override
    protected boolean readLogContent() {
	        xinghunId =  readString();
	        xinghunName =  readString();
	        feature =  readString();
	        completeTime =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(xinghunId);
	        writeString(xinghunName);
	        writeString(feature);
	        writeLong(completeTime);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_XINGHUN_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_XINGHUN_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.xinghunId));
		            list.add(String.valueOf(this.xinghunName));
		            list.add(String.valueOf(this.feature));
		            list.add(String.valueOf(this.completeTime));
				return list;
	}
}