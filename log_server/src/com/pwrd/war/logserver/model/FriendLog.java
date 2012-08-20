package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class FriendLog extends BaseLogMessage{
       private int flag;
       private String friendId;
       private String friendName;
       private long time;
       private int totalNum;
    
    public FriendLog() {    	
    }

    public FriendLog(
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
			int flag,			String friendId,			String friendName,			long time,			int totalNum            ) {
        super(MessageType.LOG_FRIEND_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.flag =  flag;
            this.friendId =  friendId;
            this.friendName =  friendName;
            this.time =  time;
            this.totalNum =  totalNum;
    }

       public int getFlag() {
	       return flag;
       }
       public String getFriendId() {
	       return friendId;
       }
       public String getFriendName() {
	       return friendName;
       }
       public long getTime() {
	       return time;
       }
       public int getTotalNum() {
	       return totalNum;
       }
        
       public void setFlag(int flag) {
	       this.flag = flag;
       }
       public void setFriendId(String friendId) {
	       this.friendId = friendId;
       }
       public void setFriendName(String friendName) {
	       this.friendName = friendName;
       }
       public void setTime(long time) {
	       this.time = time;
       }
       public void setTotalNum(int totalNum) {
	       this.totalNum = totalNum;
       }
    
    @Override
    protected boolean readLogContent() {
	        flag =  readInt();
	        friendId =  readString();
	        friendName =  readString();
	        time =  readLong();
	        totalNum =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(flag);
	        writeString(friendId);
	        writeString(friendName);
	        writeLong(time);
	        writeInt(totalNum);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_FRIEND_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_FRIEND_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.flag));
		            list.add(String.valueOf(this.friendId));
		            list.add(String.valueOf(this.friendName));
		            list.add(String.valueOf(this.time));
		            list.add(String.valueOf(this.totalNum));
				return list;
	}
}