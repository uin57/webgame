package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class GetItemLog extends BaseLogMessage{
       private String itemSn;
       private long time;
    
    public GetItemLog() {    	
    }

    public GetItemLog(
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
			String itemSn,			long time            ) {
        super(MessageType.LOG_GETITEM_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.itemSn =  itemSn;
            this.time =  time;
    }

       public String getItemSn() {
	       return itemSn;
       }
       public long getTime() {
	       return time;
       }
        
       public void setItemSn(String itemSn) {
	       this.itemSn = itemSn;
       }
       public void setTime(long time) {
	       this.time = time;
       }
    
    @Override
    protected boolean readLogContent() {
	        itemSn =  readString();
	        time =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(itemSn);
	        writeLong(time);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_GETITEM_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_GETITEM_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.itemSn));
		            list.add(String.valueOf(this.time));
				return list;
	}
}