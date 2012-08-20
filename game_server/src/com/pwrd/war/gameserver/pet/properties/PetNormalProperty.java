package com.pwrd.war.gameserver.pet.properties;

/**
 * 一般属性，不需要发送给客户端的
 */
public class PetNormalProperty {

	/** buff增加攻击 **/
	private double buffAtk;
	/** buff增加防御 **/
	private double buffDef;
	/** buff增加最大血量 **/
	private double buffMaxHp;
	
	public double getBuffAtk() {
		return buffAtk;
	}
	public void setBuffAtk(double buffAtk) {
		this.buffAtk = buffAtk;
	}
	public double getBuffDef() {
		return buffDef;
	}
	public void setBuffDef(double buffDef) {
		this.buffDef = buffDef;
	}
	public double getBuffMaxHp() {
		return buffMaxHp;
	}
	public void setBuffMaxHp(double buffMaxHp) {
		this.buffMaxHp = buffMaxHp;
	}
	
	
}
