package com.pwrd.war.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;
@Entity
@Table(name = "t_human_robbery")
public class HumanRobberyEntity implements BaseEntity<String> {
	private static final long serialVersionUID = -4449779831264963853L;
	//这个id=玩家角色id
	private String id;
	private boolean robberyStatus;//未夺宝,夺宝中
	private short robberyTodayTimes;//夺宝,今日剩余次数
	private short robberyTodayRobTimes;//夺宝,今日剩余抢劫次数
	
	private short robberySelectIndex = -1;//随机到的结果,-1表示未随机.	
	private short robberyRefreshTimes;//刷新次数,每次开始重置次数为0
	private short robberyThisFreeRefreshTimes;//免费刷新次数,每次天开始重置次数为0
	
	private String robberyInviteFriendUUID;//正在邀请的好友UUID,有或者空
	private long robberyInviteFriendTime;//好友接受邀请的时间
	private boolean robberyInviteResult;	//正在要求的好友是否同意
	
	private short robberyProtectTimes; //夺宝今日剩余护送次数
	private long robberyProtectFriendEndTime;//护送好友结束时间,结束后可以接受其他人护送
	private long robberyProtectBeInviteTime;//被邀请护送接受时间,有60秒期限,期限内无法被其他人邀请
	private short robberyFreeFreshTimes;	//今日剩余免费刷新次数
	
	@Id
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public boolean isRobberyStatus() {
		return robberyStatus;
	}

	public void setRobberyStatus(boolean robberyStatus) {
		this.robberyStatus = robberyStatus;
	}

	public short getRobberyTodayTimes() {
		return robberyTodayTimes;
	}

	public void setRobberyTodayTimes(short robberyTodayTimes) {
		this.robberyTodayTimes = robberyTodayTimes;
	}

	public short getRobberyTodayRobTimes() {
		return robberyTodayRobTimes;
	}

	public void setRobberyTodayRobTimes(short robberyTodayRobTimes) {
		this.robberyTodayRobTimes = robberyTodayRobTimes;
	}

	public short getRobberySelectIndex() {
		return robberySelectIndex;
	}

	public void setRobberySelectIndex(short robberySelectIndex) {
		this.robberySelectIndex = robberySelectIndex;
	}

	public short getRobberyRefreshTimes() {
		return robberyRefreshTimes;
	}

	public void setRobberyRefreshTimes(short robberyRefreshTimes) {
		this.robberyRefreshTimes = robberyRefreshTimes;
	}

	public short getRobberyThisFreeRefreshTimes() {
		return robberyThisFreeRefreshTimes;
	}

	public void setRobberyThisFreeRefreshTimes(short robberyThisFreeRefreshTimes) {
		this.robberyThisFreeRefreshTimes = robberyThisFreeRefreshTimes;
	}

	public String getRobberyInviteFriendUUID() {
		return robberyInviteFriendUUID;
	}

	public void setRobberyInviteFriendUUID(String robberyInviteFriendUUID) {
		this.robberyInviteFriendUUID = robberyInviteFriendUUID;
	}

	public long getRobberyInviteFriendTime() {
		return robberyInviteFriendTime;
	}

	public void setRobberyInviteFriendTime(long robberyInviteFriendTime) {
		this.robberyInviteFriendTime = robberyInviteFriendTime;
	}

	public boolean isRobberyInviteResult() {
		return robberyInviteResult;
	}

	public void setRobberyInviteResult(boolean robberyInviteResult) {
		this.robberyInviteResult = robberyInviteResult;
	}

	public short getRobberyProtectTimes() {
		return robberyProtectTimes;
	}

	public void setRobberyProtectTimes(short robberyProtectTimes) {
		this.robberyProtectTimes = robberyProtectTimes;
	}

	public long getRobberyProtectFriendEndTime() {
		return robberyProtectFriendEndTime;
	}

	public void setRobberyProtectFriendEndTime(long robberyProtectFriendEndTime) {
		this.robberyProtectFriendEndTime = robberyProtectFriendEndTime;
	}

	public long getRobberyProtectBeInviteTime() {
		return robberyProtectBeInviteTime;
	}

	public void setRobberyProtectBeInviteTime(long robberyProtectBeInviteTime) {
		this.robberyProtectBeInviteTime = robberyProtectBeInviteTime;
	}

	public short getRobberyFreeFreshTimes() {
		return robberyFreeFreshTimes;
	}

	public void setRobberyFreeFreshTimes(short robberyFreeFreshTimes) {
		this.robberyFreeFreshTimes = robberyFreeFreshTimes;
	}

}
