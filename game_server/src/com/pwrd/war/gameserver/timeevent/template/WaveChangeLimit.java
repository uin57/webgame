package com.pwrd.war.gameserver.timeevent.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.BeanFieldNumber;

@ExcelRowBinding
public class WaveChangeLimit {
	
	/** 本次波动最小值[百分比] */
	@BeanFieldNumber(number = 1)
	private float low;
	
	/** 本次波动最大值[百分比] */
	@BeanFieldNumber(number = 2)
	private float high;
	
	/** 本次波动变化量[百分比] */
	@BeanFieldNumber(number = 3)
	private float changeRange;

	public float getLow() {
		return low;
	}

	public void setLow(float low) {
		this.low = low;
	}

	public float getHigh() {
		return high;
	}

	public void setHigh(float high) {
		this.high = high;
	}

	public float getChangeRange() {
		return changeRange;
	}

	public void setChangeRange(float changeRange) {
		this.changeRange = changeRange;
	}
	

}
