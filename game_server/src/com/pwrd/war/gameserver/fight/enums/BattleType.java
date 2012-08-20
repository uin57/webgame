package com.pwrd.war.gameserver.fight.enums;
/**
 * 战斗类型
 * @author zhutao
 *
 */
public enum BattleType {
	BASE(0),		//普通打怪
	PK(1),			//pk
	COMPLETE(2),	//竞技场
	EXCHANG(3),		//切磋
	ROBBERY(4),		//护送
	BOSS(5),		//怪物攻城
	;
	
    private int id;
    
    BattleType(int id){
    	this.id=id;
    }
    
    public static BattleType getBattleTypeById(int id){
    	for(BattleType battleType:BattleType.values()){
    		if(battleType.getId()==id){
    			return battleType;
    		}
    	}
    	return null;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
}
