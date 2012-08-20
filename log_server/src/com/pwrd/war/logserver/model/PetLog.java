package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class PetLog extends BaseLogMessage{
       private String petId;
       private String petName;
       private long hireTime;
    
    public PetLog() {    	
    }

    public PetLog(
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
			String petId,			String petName,			long hireTime            ) {
        super(MessageType.LOG_PET_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.petId =  petId;
            this.petName =  petName;
            this.hireTime =  hireTime;
    }

       public String getPetId() {
	       return petId;
       }
       public String getPetName() {
	       return petName;
       }
       public long getHireTime() {
	       return hireTime;
       }
        
       public void setPetId(String petId) {
	       this.petId = petId;
       }
       public void setPetName(String petName) {
	       this.petName = petName;
       }
       public void setHireTime(long hireTime) {
	       this.hireTime = hireTime;
       }
    
    @Override
    protected boolean readLogContent() {
	        petId =  readString();
	        petName =  readString();
	        hireTime =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(petId);
	        writeString(petName);
	        writeLong(hireTime);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_PET_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_PET_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.petId));
		            list.add(String.valueOf(this.petName));
		            list.add(String.valueOf(this.hireTime));
				return list;
	}
}