package com.pwrd.war.gameserver.buff.enums;

public enum BigType {
    use(1),
    system(2);
    
    private int id;
    
    BigType(int id){
       this.id=id;
    }

	public int getId() {
		return id;
	}
    
	
}
