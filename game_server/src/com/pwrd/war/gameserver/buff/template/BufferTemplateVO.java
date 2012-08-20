package com.pwrd.war.gameserver.buff.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * buffer模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class BufferTemplateVO extends TemplateObject {

	/** buff名称 */
	@ExcelCellBinding(offset = 1)
	protected String bufferName;

	/** 大类型 */
	@ExcelCellBinding(offset = 2)
	protected int bigType;

	/** buff类型 */
	@ExcelCellBinding(offset = 3)
	protected int buffType;

	/** 数值类型 */
	@ExcelCellBinding(offset = 4)
	protected int valueType;

	/** 数值 */
	@ExcelCellBinding(offset = 5)
	protected double value;

	/** 使用次数 */
	@ExcelCellBinding(offset = 6)
	protected int number;

	/** 持续时间 */
	@ExcelCellBinding(offset = 7)
	protected long time;

	/** buff对象 */
	@ExcelCellBinding(offset = 8)
	protected int bufferTarget;

	/** buffer图标 */
	@ExcelCellBinding(offset = 9)
	protected String bufferIcon;

	/** buff描述 */
	@ExcelCellBinding(offset = 10)
	protected String bufferDescription;


	public String getBufferName() {
		return this.bufferName;
	}

	public void setBufferName(String bufferName) {
		this.bufferName = bufferName;
	}
	
	public int getBigType() {
		return this.bigType;
	}

	public void setBigType(int bigType) {
		this.bigType = bigType;
	}
	
	public int getBuffType() {
		return this.buffType;
	}

	public void setBuffType(int buffType) {
		this.buffType = buffType;
	}
	
	public int getValueType() {
		return this.valueType;
	}

	public void setValueType(int valueType) {
		this.valueType = valueType;
	}
	
	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public long getTime() {
		return this.time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public int getBufferTarget() {
		return this.bufferTarget;
	}

	public void setBufferTarget(int bufferTarget) {
		this.bufferTarget = bufferTarget;
	}
	
	public String getBufferIcon() {
		return this.bufferIcon;
	}

	public void setBufferIcon(String bufferIcon) {
		this.bufferIcon = bufferIcon;
	}
	
	public String getBufferDescription() {
		return this.bufferDescription;
	}

	public void setBufferDescription(String bufferDescription) {
		this.bufferDescription = bufferDescription;
	}
	

	@Override
	public String toString() {
		return "BufferTemplateVO[bufferName=" + bufferName + ",bigType=" + bigType + ",buffType=" + buffType + ",valueType=" + valueType + ",value=" + value + ",number=" + number + ",time=" + time + ",bufferTarget=" + bufferTarget + ",bufferIcon=" + bufferIcon + ",bufferDescription=" + bufferDescription + ",]";

	}
}