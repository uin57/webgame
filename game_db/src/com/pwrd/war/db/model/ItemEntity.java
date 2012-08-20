package com.pwrd.war.db.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.SoftDeleteEntity;

/**
 * 道具实例实体
 * 
 */
@Entity
@Table(name = "t_item_info")
public class ItemEntity implements SoftDeleteEntity<String>, CharSubEntity {
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private String id;
	/** 所属角色ID */
	private String charId;
	/** 佩戴武将ID */
	private String wearerId;
	/** 物品所在背包编号 */
	private int bagId;
	/** 物品所在背包位置 */
	private int bagIndex;
	/** 物品类型编号 */
	private int templateId;
	/** 叠加数量 */
	private int overlap;
	/** 是否绑定 */
	private int bind;
	/** 是否已删除 */
	private int deleted;
	/** 创建时间 */
	private Timestamp createTime;
	/** 删除时间 */
	private Timestamp deleteDate;
	/** 使用期限 */
	private Timestamp deadline;
	/** 物品自身的属性 */
	private String properties;

	/** 当前耐久 */
	private int curEndure;
	/** 冷却的绝对时间点，存库时需要存相对时间，即还需要多长时间可以冷却 */
	private long coolDownTimePoint;
	/** 星数 */
	private int star;

	@Id
	@Override
	@Column(length = 36)
	public String getId() {
		return this.id;
	}
	
	@Override
	@Column
	public String getCharId() {
		return charId;
	}

	@Column
	public String getWearerId() {
		return wearerId;		
	}

	@Column
	public int getBagId() {
		return bagId;
	}
	
	@Column
	public int getBagIndex() {
		return bagIndex;
	}

	@Column
	public int getTemplateId() {
		return templateId;
	}
	
	@Column
	public int getOverlap() {
		return overlap;
	}
	
	@Column
	public int getBind() {
		return bind;
	}
	
	@Column
	public int getDeleted() {
		return deleted;
	}

	@Column
	public Timestamp getDeadline() {
		return deadline;
	}

	@Column
	public Timestamp getDeleteDate() {
		return deleteDate;
	}

	@Column
	public Timestamp getCreateTime() {
		return createTime;
	}
	
	
	@Column(length = 1024)
	public String getProperties() {
		return properties;
	}
	
	
	@Column
	public int getCurEndure() {
		return curEndure;
	}

	@Column
	public long getCoolDownTimePoint() {
		return coolDownTimePoint;
	}
	
	@Column
	public int getStar() {
		return star;
	}

	public void setId(String id) {
		this.id = id;
	}


	public void setCharId(String charId) {
		this.charId = charId;
	}
	
	
	public void setWearerId(String wearerId) {
		this.wearerId = wearerId;
	}


	public void setBagId(int bagId) {
		this.bagId = bagId;
	}


	public void setBagIndex(int bagIndex) {
		this.bagIndex = bagIndex;
	}


	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}


	public void setOverlap(int overlap) {
		this.overlap = overlap;
	}


	public void setBind(int bind) {
		this.bind = bind;
	}


	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}


	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}


	public void setDeleteDate(Timestamp deleteDate) {
		this.deleteDate = deleteDate;
	}


	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}


	public void setProperties(String properties) {
		this.properties = properties;
	}


	public void setCurEndure(int curEndure) {
		this.curEndure = curEndure;
	}


	public void setCoolDownTimePoint(long coolDownTimePoint) {
		this.coolDownTimePoint = coolDownTimePoint;
	}


	public void setStar(int star) {
		this.star = star;
	}


	@Override
	public String toString() {
		return "ItemEntity [id=" + id 
				+ ", charId=" + charId 
				+ ", bagId=" + bagId 
				+ ", wearerId=" + wearerId 
				+ ", index=" + bagIndex 
				+ ", templateId=" + templateId
				+ ", overlap=" + overlap 
				+ ", bind=" + bind 
				+ ", deleted=" + deleted 
				+ ", createTime=" + createTime 
				+ ", deleteDate=" + deleteDate 
				+ ", deadline=" + deadline 
				+ ", properties=" + properties 
				+ ", curEndure=" + curEndure
				+ ", coolDownTimePoint=" + coolDownTimePoint 
				+ ", star=" + star
				+ "]";
	}
}
