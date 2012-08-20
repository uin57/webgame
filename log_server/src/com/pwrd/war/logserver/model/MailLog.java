package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class MailLog extends BaseLogMessage{
       private String senderId;
       private String senderName;
       private String recieverId;
       private String recieverName;
       private String title;
       private int readStatus;
       private long sendTime;
    
    public MailLog() {    	
    }

    public MailLog(
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
			String senderId,			String senderName,			String recieverId,			String recieverName,			String title,			int readStatus,			long sendTime            ) {
        super(MessageType.LOG_MAIL_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.senderId =  senderId;
            this.senderName =  senderName;
            this.recieverId =  recieverId;
            this.recieverName =  recieverName;
            this.title =  title;
            this.readStatus =  readStatus;
            this.sendTime =  sendTime;
    }

       public String getSenderId() {
	       return senderId;
       }
       public String getSenderName() {
	       return senderName;
       }
       public String getRecieverId() {
	       return recieverId;
       }
       public String getRecieverName() {
	       return recieverName;
       }
       public String getTitle() {
	       return title;
       }
       public int getReadStatus() {
	       return readStatus;
       }
       public long getSendTime() {
	       return sendTime;
       }
        
       public void setSenderId(String senderId) {
	       this.senderId = senderId;
       }
       public void setSenderName(String senderName) {
	       this.senderName = senderName;
       }
       public void setRecieverId(String recieverId) {
	       this.recieverId = recieverId;
       }
       public void setRecieverName(String recieverName) {
	       this.recieverName = recieverName;
       }
       public void setTitle(String title) {
	       this.title = title;
       }
       public void setReadStatus(int readStatus) {
	       this.readStatus = readStatus;
       }
       public void setSendTime(long sendTime) {
	       this.sendTime = sendTime;
       }
    
    @Override
    protected boolean readLogContent() {
	        senderId =  readString();
	        senderName =  readString();
	        recieverId =  readString();
	        recieverName =  readString();
	        title =  readString();
	        readStatus =  readInt();
	        sendTime =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(senderId);
	        writeString(senderName);
	        writeString(recieverId);
	        writeString(recieverName);
	        writeString(title);
	        writeInt(readStatus);
	        writeLong(sendTime);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_MAIL_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_MAIL_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.senderId));
		            list.add(String.valueOf(this.senderName));
		            list.add(String.valueOf(this.recieverId));
		            list.add(String.valueOf(this.recieverName));
		            list.add(String.valueOf(this.title));
		            list.add(String.valueOf(this.readStatus));
		            list.add(String.valueOf(this.sendTime));
				return list;
	}
}