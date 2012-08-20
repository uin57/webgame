package com.pwrd.war.logserver.model;
import com.pwrd.war.logserver.MessageType;
import com.pwrd.war.logserver.BaseLogMessage;
import java.util.List;

/**
 * This is an auto generated source,please don't modify it.
 */
 
public class LoginLog extends BaseLogMessage{
       private long loginTime;
       private String ip;
       private String senceId;
       private int onlineTime;
       private int loginFlag;
    
    public LoginLog() {    	
    }

    public LoginLog(
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
			long loginTime,			String ip,			String senceId,			int onlineTime,			int loginFlag            ) {
        super(MessageType.LOG_LOGIN_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,sceneId,x,y,param);
            this.loginTime =  loginTime;
            this.ip =  ip;
            this.senceId =  senceId;
            this.onlineTime =  onlineTime;
            this.loginFlag =  loginFlag;
    }

       public long getLoginTime() {
	       return loginTime;
       }
       public String getIp() {
	       return ip;
       }
       public String getSenceId() {
	       return senceId;
       }
       public int getOnlineTime() {
	       return onlineTime;
       }
       public int getLoginFlag() {
	       return loginFlag;
       }
        
       public void setLoginTime(long loginTime) {
	       this.loginTime = loginTime;
       }
       public void setIp(String ip) {
	       this.ip = ip;
       }
       public void setSenceId(String senceId) {
	       this.senceId = senceId;
       }
       public void setOnlineTime(int onlineTime) {
	       this.onlineTime = onlineTime;
       }
       public void setLoginFlag(int loginFlag) {
	       this.loginFlag = loginFlag;
       }
    
    @Override
    protected boolean readLogContent() {
	        loginTime =  readLong();
	        ip =  readString();
	        senceId =  readString();
	        onlineTime =  readInt();
	        loginFlag =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(loginTime);
	        writeString(ip);
	        writeString(senceId);
	        writeInt(onlineTime);
	        writeInt(loginFlag);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_LOGIN_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_LOGIN_RECORD";
    }
    
    @Override
	public List<String> toPropStrList() {
		List<String> list = super.toPropStrList();
		            list.add(String.valueOf(this.loginTime));
		            list.add(String.valueOf(this.ip));
		            list.add(String.valueOf(this.senceId));
		            list.add(String.valueOf(this.onlineTime));
		            list.add(String.valueOf(this.loginFlag));
				return list;
	}
}