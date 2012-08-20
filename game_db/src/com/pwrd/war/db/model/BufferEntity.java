package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;

@Entity
@Table(name = "t_buffer")
public class BufferEntity implements BaseEntity<String> {

	private static final long serialVersionUID = 4130843481786303246L;

	private String id;

	private String humanSn;

	/** buff名称 */
	private String bufferSn;
	/** buffer名称 */
	private String bufferName;
	/** buffer描述 */
	private String bufferDescription;
	
	private double bufferValue;

	private long startTime;

	private long endTime;

	/** 次数 */
	private int bufferNumber;

	/** 时间 */
	private long bufferTime;
	//附加参数
	private String ext;
	
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

	@Column
	public String getHumanSn() {
		return humanSn;
	}

	public void setHumanSn(String humanSn) {
		this.humanSn = humanSn;
	}

	@Column
	public String getBufferSn() {
		return bufferSn;
	}

	public void setBufferSn(String bufferSn) {
		this.bufferSn = bufferSn;
	}

	@Column
	public long getBufferTime() {
		return bufferTime;
	}

	public void setBufferTime(long bufferTime) {
		this.bufferTime = bufferTime;
	}

	@Column
	public int getBufferNumber() {
		return bufferNumber;
	}

	public void setBufferNumber(int bufferNumber) {
		this.bufferNumber = bufferNumber;
	}

	@Column
	public double getBufferValue() {
		return bufferValue;
	}

	public void setBufferValue(double bufferValue) {
		this.bufferValue = bufferValue;
	}

	@Column
	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	@Column
	public long getStartTime() {
		return startTime;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getBufferName() {
		return bufferName;
	}

	public void setBufferName(String bufferName) {
		this.bufferName = bufferName;
	}

	public String getBufferDescription() {
		return bufferDescription;
	}

	public void setBufferDescription(String bufferDescription) {
		this.bufferDescription = bufferDescription;
	}

}
