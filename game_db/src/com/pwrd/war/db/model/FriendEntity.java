package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

import com.pwrd.war.core.orm.BaseEntity;

@Entity
@Table(name = "t_friend")
public class FriendEntity implements BaseEntity<String> {

	private static final long serialVersionUID = 4130843481786303246L;

	private String id;
 

	@Id
	@Column(length = 36)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	//玩家UUID
 
	private String playerUUID;
	//好友UUID
	private String friendUUID;
	private String friendName;

	//离线聊天记录,json数组
	private String chats;
	//是否有离线消息
	private boolean hasNewMessage;
  
	//上次联系时间
	private  long lastConcatTime;
	//性别
	private int sex;
	private int vocation;
	
	private boolean isFriend;//是否是好友,因为有可能是最近联系人
	
	private boolean isInBlack;//是否在黑名单中
	
	private int waterFlag;//是否能浇水 -1未开放 0未浇 1已经
	
	private long lastWaterTime;//上次浇水时间
	
	

	public String getFriendUUID() {
		return friendUUID;
	}

	public void setFriendUUID(String friendUUID) {
		this.friendUUID = friendUUID;
	}
	
	@Column(length = 10240)
	public String getChats() {
		return chats;
	}

	public void setChats(String chats) {
		this.chats = chats;
	}

	public boolean isInBlack() {
		return isInBlack;
	}

	public void setInBlack(boolean isInBlack) {
		this.isInBlack = isInBlack;
	}

	public long getLastConcatTime() {
		return lastConcatTime;
	}

	public void setLastConcatTime(long lastConcatTime) {
		this.lastConcatTime = lastConcatTime;
	}
	@Index(name="t_friend_playerUUID")
	public String getPlayerUUID() {
		return playerUUID;
	}

	public void setPlayerUUID(String playerUUID) {
		this.playerUUID = playerUUID;
	}

	public boolean isHasNewMessage() {
		return hasNewMessage;
	}

	public void setHasNewMessage(boolean hasNewMessage) {
		this.hasNewMessage = hasNewMessage;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public boolean isFriend() {
		return isFriend;
	}

	public void setFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}

	public int getVocation() {
		return vocation;
	}

	public void setVocation(int vocation) {
		this.vocation = vocation;
	}

	public long getLastWaterTime() {
		return lastWaterTime;
	}

	public void setLastWaterTime(long lastWaterTime) {
		this.lastWaterTime = lastWaterTime;
	}

	public int getWaterFlag() {
		return waterFlag;
	}

	public void setWaterFlag(int waterFlag) {
		this.waterFlag = waterFlag;
	}
}
