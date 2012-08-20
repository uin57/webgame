package com.pwrd.war.gameserver.buff.domain;

public class BufferRole {
	
	private String roleSn;
	
	private BufferUnit[] bufferUnits;
	
	private int leftHp;
	
	private int maxHp;
	
	private int curHp;

	public String getRoleSn() {
		return roleSn;
	}

	public BufferUnit[] getBufferUnits() {
		return bufferUnits;
	}

	public void setRoleSn(String roleSn) {
		this.roleSn = roleSn;
	}

	public void setBufferUnits(BufferUnit[] bufferUnits) {
		this.bufferUnits = bufferUnits;
	}

	public int getLeftHp() {
		return leftHp;
	}

	public void setLeftHp(int leftHp) {
		this.leftHp = leftHp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getCurHp() {
		return curHp;
	}

	public void setCurHp(int curHp) {
		this.curHp = curHp;
	}




}
