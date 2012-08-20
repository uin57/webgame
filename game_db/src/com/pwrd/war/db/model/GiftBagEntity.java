/**
 * 
 */
package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * @author dendgan
 * 
 * 玩家礼包奖励实体类
 *
 */
@Entity
@Table(name = "t_gift_info")
public class GiftBagEntity implements BaseEntity<String>, CharSubEntity {

	/** */
	private static final long serialVersionUID = -4596864745529331047L;
	/** 礼包奖励实体id,主键*/
	private String id;
	/** 所属角色id*/
	private String charId;
	/** 礼包奖励id*/
	private String giftBagId;
	/** 发放时间 */
	private long sendTime;

	public void setId(String id) {
		this.id = id;
	}
	
	@Id
	@Column
	public String getId() {
		return id;
	}
	
	public void setCharId(String charId) {
		this.charId = charId;
	}

	@Column
	public String getCharId() {
		return charId;
	}

	public void setGiftBagId(String giftBagId) {
		this.giftBagId = giftBagId;
	}

	@Column
	public String getGiftBagId() {
		return giftBagId;
	}

	@Column
	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}
}
