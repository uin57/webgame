package com.pwrd.war.gameserver.interactive.domain;

public class EquipmentInformation {
	/** 装备的唯一标识 */
	private String sn;
	/** 战斗属性 */
	private String battleProps;
	/** 强化属性 */
	private String feature;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getBattleProps() {
		return battleProps;
	}

	public void setBattleProps(String battleProps) {
		this.battleProps = battleProps;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

}
