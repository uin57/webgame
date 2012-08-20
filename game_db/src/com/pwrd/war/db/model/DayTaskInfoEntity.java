package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;

@Entity
@Table(name = "t_day_task_info")
public class DayTaskInfoEntity implements BaseEntity<String>{
	private static final long serialVersionUID = 1L;

	/** 主键 */	
	private String id;
	
	/** 所属角色ID */
	private String charId;
	
	/** 当天任务的次数 根据弹药获得 */
	private int dayTimes;	
	
	/** 当前任务的次数 */
	private int taskTimes;
	
	/** 剩余弹药 */
	private int bullet;
	
	/** true为完成任务 */
	private boolean taskFlag;	
	
	/** 当前任务id */
	private String taskId;
	

	@Id
	@Column(length = 36)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	@Override
	public String getId() {
		return id;
	}
	
	@Column
	public String getCharId() {
		return charId;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	public void setCharId(String charId) {
		this.charId = charId;
	}

	public int getDayTimes() {
		return dayTimes;
	}

	public void setDayTimes(int dayTimes) {
		this.dayTimes = dayTimes;
	}

	public int getTaskTimes() {
		return taskTimes;
	}

	public void setTaskTimes(int taskTimes) {
		this.taskTimes = taskTimes;
	}

	public int getBullet() {
		return bullet;
	}

	public void setBullet(int bullet) {
		this.bullet = bullet;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public boolean isTaskFlag() {
		return taskFlag;
	}

	public void setTaskFlag(boolean taskFlag) {
		this.taskFlag = taskFlag;
	}
}
