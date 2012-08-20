package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class FamilyLog extends BaseLogMessage{
       private int flag;
       private String familyId;
       private String familyName;
       private long time;
    
    public FamilyLog() {    	
    }

    public FamilyLog(
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
			int flag,			String familyId,			String familyName,			long time            ) {
        super(MessageType.LOG_FAMILY_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.flag =  flag;
            this.familyId =  familyId;
            this.familyName =  familyName;
            this.time =  time;
    }

       public int getFlag() {
	       return flag;
       }
       public String getFamilyId() {
	       return familyId;
       }
       public String getFamilyName() {
	       return familyName;
       }
       public long getTime() {
	       return time;
       }
        
       public void setFlag(int flag) {
	       this.flag = flag;
       }
       public void setFamilyId(String familyId) {
	       this.familyId = familyId;
       }
       public void setFamilyName(String familyName) {
	       this.familyName = familyName;
       }
       public void setTime(long time) {
	       this.time = time;
       }
    
    @Override
    protected boolean readLogContent() {
	        flag =  readInt();
	        familyId =  readString();
	        familyName =  readString();
	        time =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(flag);
	        writeString(familyId);
	        writeString(familyName);
	        writeLong(time);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_FAMILY_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_FAMILY_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.flag));
		            list.add(String.valueOf(this.familyId));
		            list.add(String.valueOf(this.familyName));
		            list.add(String.valueOf(this.time));
				return list;
	}
}