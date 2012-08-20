package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class EquipmentLog extends BaseLogMessage{
       private int flag;
       private String equipmentId;
       private int enhanceLevle;
       private String petName;
       private long time;
    
    public EquipmentLog() {    	
    }

    public EquipmentLog(
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
			int flag,			String equipmentId,			int enhanceLevle,			String petName,			long time            ) {
        super(MessageType.LOG_EQUIPMENT_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.flag =  flag;
            this.equipmentId =  equipmentId;
            this.enhanceLevle =  enhanceLevle;
            this.petName =  petName;
            this.time =  time;
    }

       public int getFlag() {
	       return flag;
       }
       public String getEquipmentId() {
	       return equipmentId;
       }
       public int getEnhanceLevle() {
	       return enhanceLevle;
       }
       public String getPetName() {
	       return petName;
       }
       public long getTime() {
	       return time;
       }
        
       public void setFlag(int flag) {
	       this.flag = flag;
       }
       public void setEquipmentId(String equipmentId) {
	       this.equipmentId = equipmentId;
       }
       public void setEnhanceLevle(int enhanceLevle) {
	       this.enhanceLevle = enhanceLevle;
       }
       public void setPetName(String petName) {
	       this.petName = petName;
       }
       public void setTime(long time) {
	       this.time = time;
       }
    
    @Override
    protected boolean readLogContent() {
	        flag =  readInt();
	        equipmentId =  readString();
	        enhanceLevle =  readInt();
	        petName =  readString();
	        time =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(flag);
	        writeString(equipmentId);
	        writeInt(enhanceLevle);
	        writeString(petName);
	        writeLong(time);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_EQUIPMENT_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_EQUIPMENT_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.flag));
		            list.add(String.valueOf(this.equipmentId));
		            list.add(String.valueOf(this.enhanceLevle));
		            list.add(String.valueOf(this.petName));
		            list.add(String.valueOf(this.time));
				return list;
	}
}