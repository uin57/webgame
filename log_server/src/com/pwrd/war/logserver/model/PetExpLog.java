package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class PetExpLog extends BaseLogMessage{
       private int petTempId;
       private long petId;
       private long expDelta;
       private long expLeft;
    
    public PetExpLog() {    	
    }

    public PetExpLog(
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
			int petTempId,			long petId,			long expDelta,			long expLeft            ) {
        super(MessageType.LOG_PETEXP_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.petTempId =  petTempId;
            this.petId =  petId;
            this.expDelta =  expDelta;
            this.expLeft =  expLeft;
    }

       public int getPetTempId() {
	       return petTempId;
       }
       public long getPetId() {
	       return petId;
       }
       public long getExpDelta() {
	       return expDelta;
       }
       public long getExpLeft() {
	       return expLeft;
       }
        
       public void setPetTempId(int petTempId) {
	       this.petTempId = petTempId;
       }
       public void setPetId(long petId) {
	       this.petId = petId;
       }
       public void setExpDelta(long expDelta) {
	       this.expDelta = expDelta;
       }
       public void setExpLeft(long expLeft) {
	       this.expLeft = expLeft;
       }
    
    @Override
    protected boolean readLogContent() {
	        petTempId =  readInt();
	        petId =  readLong();
	        expDelta =  readLong();
	        expLeft =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(petTempId);
	        writeLong(petId);
	        writeLong(expDelta);
	        writeLong(expLeft);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_PETEXP_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_PETEXP_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.petTempId));
		            list.add(String.valueOf(this.petId));
		            list.add(String.valueOf(this.expDelta));
		            list.add(String.valueOf(this.expLeft));
				return list;
	}
}