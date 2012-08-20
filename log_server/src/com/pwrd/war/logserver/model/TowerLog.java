package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class TowerLog extends BaseLogMessage{
       private int constellationId;
       private int starId;
       private int curLevel;
       private long completeTime;
       private int secretFlag;
       private int successful;
    
    public TowerLog() {    	
    }

    public TowerLog(
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
			int constellationId,			int starId,			int curLevel,			long completeTime,			int secretFlag,			int successful            ) {
        super(MessageType.LOG_TOWER_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.constellationId =  constellationId;
            this.starId =  starId;
            this.curLevel =  curLevel;
            this.completeTime =  completeTime;
            this.secretFlag =  secretFlag;
            this.successful =  successful;
    }

       public int getConstellationId() {
	       return constellationId;
       }
       public int getStarId() {
	       return starId;
       }
       public int getCurLevel() {
	       return curLevel;
       }
       public long getCompleteTime() {
	       return completeTime;
       }
       public int getSecretFlag() {
	       return secretFlag;
       }
       public int getSuccessful() {
	       return successful;
       }
        
       public void setConstellationId(int constellationId) {
	       this.constellationId = constellationId;
       }
       public void setStarId(int starId) {
	       this.starId = starId;
       }
       public void setCurLevel(int curLevel) {
	       this.curLevel = curLevel;
       }
       public void setCompleteTime(long completeTime) {
	       this.completeTime = completeTime;
       }
       public void setSecretFlag(int secretFlag) {
	       this.secretFlag = secretFlag;
       }
       public void setSuccessful(int successful) {
	       this.successful = successful;
       }
    
    @Override
    protected boolean readLogContent() {
	        constellationId =  readInt();
	        starId =  readInt();
	        curLevel =  readInt();
	        completeTime =  readLong();
	        secretFlag =  readInt();
	        successful =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(constellationId);
	        writeInt(starId);
	        writeInt(curLevel);
	        writeLong(completeTime);
	        writeInt(secretFlag);
	        writeInt(successful);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_TOWER_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_TOWER_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.constellationId));
		            list.add(String.valueOf(this.starId));
		            list.add(String.valueOf(this.curLevel));
		            list.add(String.valueOf(this.completeTime));
		            list.add(String.valueOf(this.secretFlag));
		            list.add(String.valueOf(this.successful));
				return list;
	}
}