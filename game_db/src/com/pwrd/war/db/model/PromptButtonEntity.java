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
 * 提示按钮信息实体类
 * @author dengdan
 *
 */
@Entity
@Table(name = "t_prompt_button_info")
public class PromptButtonEntity implements BaseEntity<String> {

	private static final long serialVersionUID = 622292544994093240L;
	
	/** 实例id*/
	private String id;
	/** 角色id */
	private String charId;
	/** 内容 */
	private String content;
	/** 功能id */
	private int functionId;
	/** 提示时间 */
	private long time;

	@Id
	@Column
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Column
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Column
	public String getCharId() {
		return charId;
	}

	public void setCharId(String charId) {
		this.charId = charId;
	}
	
	@Column
	public int getFunctionId() {
		return functionId;
	}

	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
