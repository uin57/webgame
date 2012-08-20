package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class ChatLog extends BaseLogMessage{
       private int scope;
       private String recCharName;
       private String content;
    
    public ChatLog() {    	
    }

    public ChatLog(
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
			int scope,			String recCharName,			String content            ) {
        super(MessageType.LOG_CHAT_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.scope =  scope;
            this.recCharName =  recCharName;
            this.content =  content;
    }

       public int getScope() {
	       return scope;
       }
       public String getRecCharName() {
	       return recCharName;
       }
       public String getContent() {
	       return content;
       }
        
       public void setScope(int scope) {
	       this.scope = scope;
       }
       public void setRecCharName(String recCharName) {
	       this.recCharName = recCharName;
       }
       public void setContent(String content) {
	       this.content = content;
       }
    
    @Override
    protected boolean readLogContent() {
	        scope =  readInt();
	        recCharName =  readString();
	        content =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(scope);
	        writeString(recCharName);
	        writeString(content);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_CHAT_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_CHAT_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.scope));
		            list.add(String.valueOf(this.recCharName));
		            list.add(String.valueOf(this.content));
				return list;
	}
}