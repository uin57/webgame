package com.pwrd.war.db.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * 用户信息
 * 
 */
@Entity
@Table(name = "t_user_info")
public class UserInfo implements BaseEntity<String> {

	private static final long serialVersionUID = -6420558996304842663L;

	/** 登陆标识 */
	private String passportId;
	/** 用户名 */
	private String name;
	/** 密码 */
	private String password;
	/** 邮箱 */
	private String email;
	/** 安全问题 */
	private String question;
	/** 安全问题答案 */
	private String answer;
	/** 帐号创建时间 */
	private long joinTime;
	/** 上次登陆时间 */
	private long lastLoginTime;
	/** 上次登陆退出时间 */
	private long lastLogoutTime;
	/** 当天在线时长 */
	private Integer todayOnlineTime = 0;
	/** 登陆失败次数 */
	private int failedLogins;
	/** 上次登陆IP */
	private String lastLoginIp;
	/** 上次登陆的地域 */
	private String locale;
	/** 上次登陆的游戏版本 */
	private String version;
	

	/** 权限 */
	private int role;
	/** 锁定状态 */
	private int lockStatus;
	/** 锁定时间 */
	private int muteTime;
	/** 锁定原因 */
	private String props;
	
	/** 防沉迷标记 */
	private Integer wallowFlag = 0;
	
	@Id
	public String getId() {
		return passportId;
	}

	@Column(nullable = false, length = 50, unique = true)
	public String getName() {
		return name;
	}

	@Column(nullable = true, length = 50)
	public String getPassword() {
		return password;
	}
	
	@Column(nullable = true, length = 50)
	public String getEmail() {
		return email;
	}

	@Column(nullable = true, length = 50)
	public String getQuestion() {
		return question;
	}

	@Column(nullable = true, length = 50)
	public String getAnswer() {
		return answer;
	}

	@Column
	public long getLastLoginTime() {
		return lastLoginTime;
	}

	
	@Column
	public long getLastLogoutTime() {
		return lastLogoutTime;
	}
	
	@Column(columnDefinition = " int default 0", nullable = false)
	public Integer getTodayOnlineTime() {
		return todayOnlineTime;
	}


	@Column
	public long getJoinTime() {
		return joinTime;
	}

	@Column(columnDefinition = " int default 0", nullable = false)
	public int getFailedLogins() {
		return failedLogins;
	}
	
	@Column(nullable = true, length = 50)
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	
	@Column(nullable = true, length = 50)
	public String getLocale() {
		return locale;
	}

	@Column(nullable = true, length = 50)
	public String getVersion() {
		return version;
	}

	@Column(columnDefinition = " int default 0", nullable = false)
	public int getRole() {
		return role;
	}

	@Column(columnDefinition = " int default 0", nullable = false)
	public int getLockStatus() {
		return lockStatus;
	}

	@Column(columnDefinition = " int default 0", nullable = false)
	public int getMuteTime() {
		return muteTime;
	}

	@Column(nullable = true, length = 256)
	public String getProps() {
		return props;
	}

	public void setId(String passportId) {
		this.passportId = passportId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public void setLastLogoutTime(long lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}
	
	public void setTodayOnlineTime(Integer todayOnlineTime) {
		this.todayOnlineTime = todayOnlineTime;
	}

	public void setRole(int role) {
		this.role = role;
	}
	
	public void setProps(String props) {
		this.props = props;
	}

	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}

	public void setFailedLogins(int failedLogins) {
		this.failedLogins = failedLogins;
	}

	public void setLockStatus(int lockStatus) {
		this.lockStatus = lockStatus;
	}

	public void setMuteTime(int muteTime) {
		this.muteTime = muteTime;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Transient
	public Integer getWallowFlag() {
		return wallowFlag;
	}
	
	public void setWallowFlag(Integer wallowFlag) {
		this.wallowFlag = wallowFlag;
	}

	/**
	 * 得到一个默认的userinfo,平台passportID第一进入时创建的
	 * 
	 * 
	 */
	public static UserInfo getDefaultUserInfo(){
		UserInfo userInfo = new UserInfo();
		userInfo.setQuestion("您的小学老师叫什么");
		userInfo.setAnswer("第三帝国");
		userInfo.setLastLoginTime(System.currentTimeMillis());
		userInfo.setLastLoginIp("127.0.0.1");
		userInfo.setPassword("warwsarf");
		return userInfo;
	}

}
