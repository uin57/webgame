package com.pwrd.war.db.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.SoftDeleteEntity;

/**
 * 武将数据库实体对象
 * 
 */
@Entity
@Table(name = "t_pet_info")
public class PetEntity extends RoleEntity implements SoftDeleteEntity<String>, CharSubEntity {
	/** */
	private static final long serialVersionUID = 6931213926527000159L;

	/** 主键 UUID */
	private String id;
	
	/** 所属角色 */
	private String charId;
 
	 
	/** 武将状态 */
	private int state;
	
	/** 模板ID */
	private int templateId;	

	/** 创建日期 */
	private Timestamp createDate;
	
	/** 删除时间 */
	private Timestamp deleteDate;
	
	/** 是否已删除 */
	private int deleted;
	
	/** 武将ID*/
	private String petSn;
	
	/** 武将成长等级*/
	private int growType;
	
	/** 武将成长等级对应成长*/
	private int baseGrow;
	
	/** 武将特有成长*/
	private int specialGrow;
	
	/** 大头像*/
	private String bigAvatar;
	
	/** 武将星级*/
	private int star;
	
 
	
	/** 参战状态*/
	private boolean inBattle;
	
	@Id
	@Override
	@Column(length = 36)
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	public String getId() {
		return this.id;
	}

	 
	@Column
	public Timestamp getCreateDate() {
		return createDate;
	}

	@Column
	public int getDeleted() {
		return deleted;
	}

	@Column
	public Timestamp getDeleteDate() {
		return deleteDate;
	}

	@Column
	public String getCharId() {
		return charId;
	}
	
	@Column(columnDefinition = " int default 0", nullable = false)
	public int getState() {
		return state;
	}

	@Column
	public int getTemplateId() {
		return templateId;
	}
 
	@Column
	public String getPetSn() {
		return petSn;
	}

	@Column
	public int getGrowType() {
		return growType;
	}

	@Column
	public int getBaseGrow() {
		return baseGrow;
	}
	
	@Column
	public int getSpecialGrow() {
		return specialGrow;
	}

	@Column
	public String getBigAvatar() {
		return bigAvatar;
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

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public void setDeleteDate(Timestamp deleteDate) {
		this.deleteDate = deleteDate;
	}

	public void setPetSn(String petSn) {
		this.petSn = petSn;
	}
	
	public void setBaseGrow(int baseGrow) {
		this.baseGrow = baseGrow;
	}


	public void setGrowType(int growType) {
		this.growType = growType;
	}

	public void setSpecialGrow(int specialGrow) {
		this.specialGrow = specialGrow;
	}

	public void setBigAvatar(String bigAvatar) {
		this.bigAvatar = bigAvatar;
	}
	
	public void setStar(int star) {
		this.star = star;
	}
 
	
	public boolean isInBattle() {
		return inBattle;
	}

	public void setInBattle(boolean inBattle) {
		this.inBattle = inBattle;
	}
}
