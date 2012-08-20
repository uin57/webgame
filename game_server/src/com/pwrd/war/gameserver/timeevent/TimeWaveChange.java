package com.pwrd.war.gameserver.timeevent;


public class TimeWaveChange {
	
	public final static int UP = 1;
	
	public final static int DOWN = 2;
	
	private int direct;
	
	private float change;
	
	private float minValue;
	
	private float maxValue;
	
	private TimeWaveChange(int direct,float change,float minValue, float maxValue)
	{
		this.direct = direct;
		this.change = change;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public int getDirect() {
		return direct;
	}
	
	public boolean isUp()
	{
		return this.direct == UP;
	}
	
	public void up()
	{
		this.direct = UP;
	}
	
	public boolean isDown()
	{
		return this.direct == DOWN;
	}
	
	public void down()
	{
		this.direct = DOWN;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

	public float getChange() {
		return change;
	}

	public void setChange(float change) {
		this.change = change;
	}

	public float getMinValue() {
		return minValue;
	}

	public float getMaxValue() {
		return maxValue;
	}

	public static TimeWaveChange createTimeWaveChange(int direct,float change,float minValue,float maxValue)
	{
		return new TimeWaveChange(direct, change, minValue, maxValue);
	}
	
}
