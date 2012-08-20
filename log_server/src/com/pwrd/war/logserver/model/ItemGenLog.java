package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class ItemGenLog extends BaseLogMessage{
       private int templateId;
       private String itemName;
       private int count;
       private int bind;
       private long deadline;
       private String properties;
       private String itemGenId;
    
    public ItemGenLog() {    	
    }

    public ItemGenLog(
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
			int templateId,			String itemName,			int count,			int bind,			long deadline,			String properties,			String itemGenId            ) {
        super(MessageType.LOG_ITEMGEN_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.templateId =  templateId;
            this.itemName =  itemName;
            this.count =  count;
            this.bind =  bind;
            this.deadline =  deadline;
            this.properties =  properties;
            this.itemGenId =  itemGenId;
    }

       public int getTemplateId() {
	       return templateId;
       }
       public String getItemName() {
	       return itemName;
       }
       public int getCount() {
	       return count;
       }
       public int getBind() {
	       return bind;
       }
       public long getDeadline() {
	       return deadline;
       }
       public String getProperties() {
	       return properties;
       }
       public String getItemGenId() {
	       return itemGenId;
       }
        
       public void setTemplateId(int templateId) {
	       this.templateId = templateId;
       }
       public void setItemName(String itemName) {
	       this.itemName = itemName;
       }
       public void setCount(int count) {
	       this.count = count;
       }
       public void setBind(int bind) {
	       this.bind = bind;
       }
       public void setDeadline(long deadline) {
	       this.deadline = deadline;
       }
       public void setProperties(String properties) {
	       this.properties = properties;
       }
       public void setItemGenId(String itemGenId) {
	       this.itemGenId = itemGenId;
       }
    
    @Override
    protected boolean readLogContent() {
	        templateId =  readInt();
	        itemName =  readString();
	        count =  readInt();
	        bind =  readInt();
	        deadline =  readLong();
	        properties =  readString();
	        itemGenId =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(templateId);
	        writeString(itemName);
	        writeInt(count);
	        writeInt(bind);
	        writeLong(deadline);
	        writeString(properties);
	        writeString(itemGenId);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_ITEMGEN_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_ITEMGEN_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.templateId));
		            list.add(String.valueOf(this.itemName));
		            list.add(String.valueOf(this.count));
		            list.add(String.valueOf(this.bind));
		            list.add(String.valueOf(this.deadline));
		            list.add(String.valueOf(this.properties));
		            list.add(String.valueOf(this.itemGenId));
				return list;
	}
}