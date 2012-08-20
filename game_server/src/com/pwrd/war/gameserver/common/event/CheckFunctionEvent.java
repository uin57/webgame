package com.pwrd.war.gameserver.common.event;

import com.pwrd.war.core.event.IEvent;
import com.pwrd.war.gameserver.human.Human;

/**
 * 检查功能开启事件
 * @author xf
 */
public class CheckFunctionEvent implements IEvent<Human>{
	public CheckFunctionEvent(Human human, String repSN) {
		this.human = human; 
		this.repSN = repSN;
	} 
	public CheckFunctionEvent(Human human) {
		this.human = human; 
		this.repSN = "";
	} 
	private Human human; 
	private String repSN = "";	//通关的副本sn，否则为空
	@Override
	public Human getInfo() { 
		return human;
	}
	public Human getHuman() {
		return human;
	}
	public void setHuman(Human human) {
		this.human = human;
	} 
	public String getRepSN() {
		return repSN;
	}
	public void setRepSN(String repSN) {
		this.repSN = repSN;
	}

}
