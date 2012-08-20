package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class ItemLog extends BaseLogMessage{
       private int bagId;
       private int bagIndex;
       private int templateId;
       private String instUUID;
       private int delta;
       private int resultCount;
       private String itemGenId;
       private byte[] itemData;
    
    public ItemLog() {    	
    }

    public ItemLog(
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
			int bagId,			int bagIndex,			int templateId,			String instUUID,			int delta,			int resultCount,			String itemGenId,			byte[] itemData            ) {
        super(MessageType.LOG_ITEM_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.bagId =  bagId;
            this.bagIndex =  bagIndex;
            this.templateId =  templateId;
            this.instUUID =  instUUID;
            this.delta =  delta;
            this.resultCount =  resultCount;
            this.itemGenId =  itemGenId;
            this.itemData =  itemData;
    }

       public int getBagId() {
	       return bagId;
       }
       public int getBagIndex() {
	       return bagIndex;
       }
       public int getTemplateId() {
	       return templateId;
       }
       public String getInstUUID() {
	       return instUUID;
       }
       public int getDelta() {
	       return delta;
       }
       public int getResultCount() {
	       return resultCount;
       }
       public String getItemGenId() {
	       return itemGenId;
       }
       public byte[] getItemData() {
	       return itemData;
       }
        
       public void setBagId(int bagId) {
	       this.bagId = bagId;
       }
       public void setBagIndex(int bagIndex) {
	       this.bagIndex = bagIndex;
       }
       public void setTemplateId(int templateId) {
	       this.templateId = templateId;
       }
       public void setInstUUID(String instUUID) {
	       this.instUUID = instUUID;
       }
       public void setDelta(int delta) {
	       this.delta = delta;
       }
       public void setResultCount(int resultCount) {
	       this.resultCount = resultCount;
       }
       public void setItemGenId(String itemGenId) {
	       this.itemGenId = itemGenId;
       }
       public void setItemData(byte[] itemData) {
	       this.itemData = itemData;
       }
    
    @Override
    protected boolean readLogContent() {
	        bagId =  readInt();
	        bagIndex =  readInt();
	        templateId =  readInt();
	        instUUID =  readString();
	        delta =  readInt();
	        resultCount =  readInt();
	        itemGenId =  readString();
	        itemData =  readByteArray();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(bagId);
	        writeInt(bagIndex);
	        writeInt(templateId);
	        writeString(instUUID);
	        writeInt(delta);
	        writeInt(resultCount);
	        writeString(itemGenId);
	        writeByteArray(itemData);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_ITEM_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_ITEM_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.bagId));
		            list.add(String.valueOf(this.bagIndex));
		            list.add(String.valueOf(this.templateId));
		            list.add(String.valueOf(this.instUUID));
		            list.add(String.valueOf(this.delta));
		            list.add(String.valueOf(this.resultCount));
		            list.add(String.valueOf(this.itemGenId));
		            list.add(String.valueOf(this.itemData));
				return list;
	}
}