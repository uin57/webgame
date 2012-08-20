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
 * 兵法实体类
 *
 */
@Entity
@Table(name = "t_warcraft")
public class WarcraftEntity implements BaseEntity<String> {
	
	private static final long serialVersionUID = 3985612659872293492L;
	/** 兵法实例id */
	private String id;
	/** 兵法模版id */
	private String templateId;
	/** 兵法经验 */
	private int exp;
	/** 兵法等级 */
	private int level;
	/** 所属角色id */
	private String charId;
	/** 所属背包 */
	private int bagId;
	/** 背包索引 */
	private int bagIndex;
	/** 创建时间 */
	private long createTime;
	/** 装备角色id*/
	private String wearerId;
	
	@Id
	@Column
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	@Column
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	@Column
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	@Column
	public int getBagId() {
		return bagId;
	}
	public void setBagId(int bagId) {
		this.bagId = bagId;
	}
	@Column
	public int getBagIndex() {
		return bagIndex;
	}
	public void setBagIndex(int bagIndex) {
		this.bagIndex = bagIndex;
	}
	@Column
	public String getCharId() {
		return charId;
	}
	public void setCharId(String charId) {
		this.charId = charId;
	}
	@Column
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	@Column
	public String getWearerId() {
		return wearerId;
	}
	public void setWearerId(String wearerId) {
		this.wearerId = wearerId;
	}
	
}
