package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class SnapLog extends BaseLogMessage{
       private String snap;
    
    public SnapLog() {    	
    }

    public SnapLog(
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
			String snap            ) {
        super(MessageType.LOG_SNAP_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.snap =  snap;
    }

       public String getSnap() {
	       return snap;
       }
        
       public void setSnap(String snap) {
	       this.snap = snap;
       }
    
    @Override
    protected boolean readLogContent() {
	        snap =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(snap);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_SNAP_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_SNAP_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.snap));
				return list;
	}
}