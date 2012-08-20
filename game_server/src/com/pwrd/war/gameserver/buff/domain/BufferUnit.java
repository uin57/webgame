package com.pwrd.war.gameserver.buff.domain;

public class BufferUnit {
	
	private String buffSn;

	private int buffLevel;
	/**buff的增加或驱除 1为增加， -1为驱除*/
	private int dealType;
	
	/**buff 效果 */
	private String buffEffect;
	
	private int roundNumber;

	public String getBuffEffect() {
		return buffEffect;
	}

	public void setBuffEffect(String buffEffect) {
		this.buffEffect = buffEffect;
	}

	public String getBuffSn() {
		return buffSn;
	}

	public BufferUnit(){
		
	}
	
	public BufferUnit(String buffSn,int buffLevel){
		this.buffLevel=buffLevel;
		this.buffSn=buffSn;
	}
	
	public int getBuffLevel() {
		return buffLevel;
	}

	public void setBuffSn(String buffSn) {
		this.buffSn = buffSn;
	}

	public void setBuffLevel(int buffLevel) {
		this.buffLevel = buffLevel;
	}

	public int getDealType() {
		return dealType;
	}

	public void setDealType(int dealType) {
		this.dealType = dealType;
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}



}
