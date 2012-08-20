package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class WarcraftLog extends BaseLogMessage{
       private int flag;
       private long time;
       private String warcraftId;
       private String warcraftName;
    
    public WarcraftLog() {    	
    }

    public WarcraftLog(
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
			int flag,			long time,			String warcraftId,			String warcraftName            ) {
        super(MessageType.LOG_WARCRAFT_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.flag =  flag;
            this.time =  time;
            this.warcraftId =  warcraftId;
            this.warcraftName =  warcraftName;
    }

       public int getFlag() {
	       return flag;
       }
       public long getTime() {
	       return time;
       }
       public String getWarcraftId() {
	       return warcraftId;
       }
       public String getWarcraftName() {
	       return warcraftName;
       }
        
       public void setFlag(int flag) {
	       this.flag = flag;
       }
       public void setTime(long time) {
	       this.time = time;
       }
       public void setWarcraftId(String warcraftId) {
	       this.warcraftId = warcraftId;
       }
       public void setWarcraftName(String warcraftName) {
	       this.warcraftName = warcraftName;
       }
    
    @Override
    protected boolean readLogContent() {
	        flag =  readInt();
	        time =  readLong();
	        warcraftId =  readString();
	        warcraftName =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(flag);
	        writeLong(time);
	        writeString(warcraftId);
	        writeString(warcraftName);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_WARCRAFT_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_WARCRAFT_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.flag));
		            list.add(String.valueOf(this.time));
		            list.add(String.valueOf(this.warcraftId));
		            list.add(String.valueOf(this.warcraftName));
				return list;
	}
}