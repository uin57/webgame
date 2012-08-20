package com.pwrd.war.gameserver.delay;


public enum DelayType {
	FORM_UPDATE(0), SKILL_UPDATE(1);

	private int id;

	DelayType(int id) {
		this.id = id;
	}

	public static DelayType getDelayTypeById(int id) {
		for (DelayType delayType : DelayType.values()) {
			if (delayType.getId() == id) {
				return delayType;
			}
		}
		return null;
	}

	public int getId() {
		return id;
	}

}
