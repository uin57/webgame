package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class PetLevelLog extends BaseLogMessage{
       private int petTempId;
       private long petId;
       private int levelBeforeAction;
       private int levelAfterAction;
       private long expBeforeAction;
       private long expAfterAction;
    
    public PetLevelLog() {    	
    }

    public PetLevelLog(
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
			int petTempId,			long petId,			int levelBeforeAction,			int levelAfterAction,			long expBeforeAction,			long expAfterAction            ) {
        super(MessageType.LOG_PETLEVEL_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.petTempId =  petTempId;
            this.petId =  petId;
            this.levelBeforeAction =  levelBeforeAction;
            this.levelAfterAction =  levelAfterAction;
            this.expBeforeAction =  expBeforeAction;
            this.expAfterAction =  expAfterAction;
    }

       public int getPetTempId() {
	       return petTempId;
       }
       public long getPetId() {
	       return petId;
       }
       public int getLevelBeforeAction() {
	       return levelBeforeAction;
       }
       public int getLevelAfterAction() {
	       return levelAfterAction;
       }
       public long getExpBeforeAction() {
	       return expBeforeAction;
       }
       public long getExpAfterAction() {
	       return expAfterAction;
       }
        
       public void setPetTempId(int petTempId) {
	       this.petTempId = petTempId;
       }
       public void setPetId(long petId) {
	       this.petId = petId;
       }
       public void setLevelBeforeAction(int levelBeforeAction) {
	       this.levelBeforeAction = levelBeforeAction;
       }
       public void setLevelAfterAction(int levelAfterAction) {
	       this.levelAfterAction = levelAfterAction;
       }
       public void setExpBeforeAction(long expBeforeAction) {
	       this.expBeforeAction = expBeforeAction;
       }
       public void setExpAfterAction(long expAfterAction) {
	       this.expAfterAction = expAfterAction;
       }
    
    @Override
    protected boolean readLogContent() {
	        petTempId =  readInt();
	        petId =  readLong();
	        levelBeforeAction =  readInt();
	        levelAfterAction =  readInt();
	        expBeforeAction =  readLong();
	        expAfterAction =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(petTempId);
	        writeLong(petId);
	        writeInt(levelBeforeAction);
	        writeInt(levelAfterAction);
	        writeLong(expBeforeAction);
	        writeLong(expAfterAction);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_PETLEVEL_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_PETLEVEL_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.petTempId));
		            list.add(String.valueOf(this.petId));
		            list.add(String.valueOf(this.levelBeforeAction));
		            list.add(String.valueOf(this.levelAfterAction));
		            list.add(String.valueOf(this.expBeforeAction));
		            list.add(String.valueOf(this.expAfterAction));
				return list;
	}
}