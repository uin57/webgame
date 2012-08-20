package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class TaskLog extends BaseLogMessage{
       private int taskId;
    
    public TaskLog() {    	
    }

    public TaskLog(
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
			int taskId            ) {
        super(MessageType.LOG_TASK_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.taskId =  taskId;
    }

       public int getTaskId() {
	       return taskId;
       }
        
       public void setTaskId(int taskId) {
	       this.taskId = taskId;
       }
    
    @Override
    protected boolean readLogContent() {
	        taskId =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(taskId);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_TASK_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_TASK_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.taskId));
				return list;
	}
}