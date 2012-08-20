package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class GuildLog extends BaseLogMessage{
       private String guildId;
       private String guildName;
       private int guildLevel;
       private int guildSymbolLevel;
       private int memberNums;
       private int status;
    
    public GuildLog() {    	
    }

    public GuildLog(
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
			String guildId,			String guildName,			int guildLevel,			int guildSymbolLevel,			int memberNums,			int status            ) {
        super(MessageType.LOG_GUILD_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.guildId =  guildId;
            this.guildName =  guildName;
            this.guildLevel =  guildLevel;
            this.guildSymbolLevel =  guildSymbolLevel;
            this.memberNums =  memberNums;
            this.status =  status;
    }

       public String getGuildId() {
	       return guildId;
       }
       public String getGuildName() {
	       return guildName;
       }
       public int getGuildLevel() {
	       return guildLevel;
       }
       public int getGuildSymbolLevel() {
	       return guildSymbolLevel;
       }
       public int getMemberNums() {
	       return memberNums;
       }
       public int getStatus() {
	       return status;
       }
        
       public void setGuildId(String guildId) {
	       this.guildId = guildId;
       }
       public void setGuildName(String guildName) {
	       this.guildName = guildName;
       }
       public void setGuildLevel(int guildLevel) {
	       this.guildLevel = guildLevel;
       }
       public void setGuildSymbolLevel(int guildSymbolLevel) {
	       this.guildSymbolLevel = guildSymbolLevel;
       }
       public void setMemberNums(int memberNums) {
	       this.memberNums = memberNums;
       }
       public void setStatus(int status) {
	       this.status = status;
       }
    
    @Override
    protected boolean readLogContent() {
	        guildId =  readString();
	        guildName =  readString();
	        guildLevel =  readInt();
	        guildSymbolLevel =  readInt();
	        memberNums =  readInt();
	        status =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(guildId);
	        writeString(guildName);
	        writeInt(guildLevel);
	        writeInt(guildSymbolLevel);
	        writeInt(memberNums);
	        writeInt(status);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_GUILD_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_GUILD_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.guildId));
		            list.add(String.valueOf(this.guildName));
		            list.add(String.valueOf(this.guildLevel));
		            list.add(String.valueOf(this.guildSymbolLevel));
		            list.add(String.valueOf(this.memberNums));
		            list.add(String.valueOf(this.status));
				return list;
	}
}