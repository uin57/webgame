package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class GmCommandLog extends BaseLogMessage{
       private String operatorName;
       private String targetIp;
       private String command;
       private String commandDesc;
       private String commandDetail;
       private String returnResult;
    
    public GmCommandLog() {    	
    }

    public GmCommandLog(
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
			String operatorName,			String targetIp,			String command,			String commandDesc,			String commandDetail,			String returnResult            ) {
        super(MessageType.LOG_GMCOMMAND_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.operatorName =  operatorName;
            this.targetIp =  targetIp;
            this.command =  command;
            this.commandDesc =  commandDesc;
            this.commandDetail =  commandDetail;
            this.returnResult =  returnResult;
    }

       public String getOperatorName() {
	       return operatorName;
       }
       public String getTargetIp() {
	       return targetIp;
       }
       public String getCommand() {
	       return command;
       }
       public String getCommandDesc() {
	       return commandDesc;
       }
       public String getCommandDetail() {
	       return commandDetail;
       }
       public String getReturnResult() {
	       return returnResult;
       }
        
       public void setOperatorName(String operatorName) {
	       this.operatorName = operatorName;
       }
       public void setTargetIp(String targetIp) {
	       this.targetIp = targetIp;
       }
       public void setCommand(String command) {
	       this.command = command;
       }
       public void setCommandDesc(String commandDesc) {
	       this.commandDesc = commandDesc;
       }
       public void setCommandDetail(String commandDetail) {
	       this.commandDetail = commandDetail;
       }
       public void setReturnResult(String returnResult) {
	       this.returnResult = returnResult;
       }
    
    @Override
    protected boolean readLogContent() {
	        operatorName =  readString();
	        targetIp =  readString();
	        command =  readString();
	        commandDesc =  readString();
	        commandDetail =  readString();
	        returnResult =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(operatorName);
	        writeString(targetIp);
	        writeString(command);
	        writeString(commandDesc);
	        writeString(commandDetail);
	        writeString(returnResult);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_GMCOMMAND_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_GMCOMMAND_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.operatorName));
		            list.add(String.valueOf(this.targetIp));
		            list.add(String.valueOf(this.command));
		            list.add(String.valueOf(this.commandDesc));
		            list.add(String.valueOf(this.commandDetail));
		            list.add(String.valueOf(this.returnResult));
				return list;
	}
}