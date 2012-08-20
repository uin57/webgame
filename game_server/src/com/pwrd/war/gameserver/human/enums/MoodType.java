package com.pwrd.war.gameserver.human.enums;

public enum MoodType {
	none(0),
	/** 高兴 */
	smile(1),
	/** 不满 */
	dissatisfied(2),
	/** 悲伤 */
	sad(3),
	/** 生病 */
	ill(4),
	/** 愤怒 */
	anger(5),
	/** 兴奋 */
	exciting(6),
	/** 思念 */
	miss(7);
	
	private int id;
	
	 MoodType(int id){
		this.id=id;
	}

	public int getId() {
		return id;
	}
	public static MoodType getMoodTypeById(int id){
		for (MoodType moodType : MoodType.values()) {
			if(moodType.getId()==id){
				return moodType;
			}
		}
		return MoodType.none;
	}

}
