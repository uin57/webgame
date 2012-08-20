package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class LevelUpLog extends BaseLogMessage{
       private int curLevel;
       private long levelUpTime;
       private int onlineTime;
    
    public LevelUpLog() {    	
    }

    public LevelUpLog(
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
			int curLevel,			long levelUpTime,			int onlineTime            ) {
        super(MessageType.LOG_LEVELUP_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.curLevel =  curLevel;
            this.levelUpTime =  levelUpTime;
            this.onlineTime =  onlineTime;
    }

       public int getCurLevel() {
	       return curLevel;
       }
       public long getLevelUpTime() {
	       return levelUpTime;
       }
       public int getOnlineTime() {
	       return onlineTime;
       }
        
       public void setCurLevel(int curLevel) {
	       this.curLevel = curLevel;
       }
       public void setLevelUpTime(long levelUpTime) {
	       this.levelUpTime = levelUpTime;
       }
       public void setOnlineTime(int onlineTime) {
	       this.onlineTime = onlineTime;
       }
    
    @Override
    protected boolean readLogContent() {
	        curLevel =  readInt();
	        levelUpTime =  readLong();
	        onlineTime =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(curLevel);
	        writeLong(levelUpTime);
	        writeInt(onlineTime);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_LEVELUP_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_LEVELUP_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.curLevel));
		            list.add(String.valueOf(this.levelUpTime));
		            list.add(String.valueOf(this.onlineTime));
				return list;
	}
}