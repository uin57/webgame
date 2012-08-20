package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class QuestLog extends BaseLogMessage{
       private int flag;
       private int questId;
       private long completeTime;
    
    public QuestLog() {    	
    }

    public QuestLog(
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
			int flag,			int questId,			long completeTime            ) {
        super(MessageType.LOG_QUEST_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.flag =  flag;
            this.questId =  questId;
            this.completeTime =  completeTime;
    }

       public int getFlag() {
	       return flag;
       }
       public int getQuestId() {
	       return questId;
       }
       public long getCompleteTime() {
	       return completeTime;
       }
        
       public void setFlag(int flag) {
	       this.flag = flag;
       }
       public void setQuestId(int questId) {
	       this.questId = questId;
       }
       public void setCompleteTime(long completeTime) {
	       this.completeTime = completeTime;
       }
    
    @Override
    protected boolean readLogContent() {
	        flag =  readInt();
	        questId =  readInt();
	        completeTime =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(flag);
	        writeInt(questId);
	        writeLong(completeTime);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_QUEST_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_QUEST_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.flag));
		            list.add(String.valueOf(this.questId));
		            list.add(String.valueOf(this.completeTime));
				return list;
	}
}