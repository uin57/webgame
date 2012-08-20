package com.pwrd.war.gameserver.common.event;

import java.util.List;

import com.pwrd.war.core.event.IEvent;
import com.pwrd.war.gameserver.human.Human;

public class QuestKillEvent implements IEvent<Human> {
	private Human human;
	private List<KillInfo> list;
	
	public QuestKillEvent(Human human, List<KillInfo> list) {
		this.human = human;
		this.list = list;
	}

	public List<KillInfo> getList() {
		return list;
	}

	@Override
	public Human getInfo() {
		return human;
	}

	public static class KillInfo {
		private String monsterSn;
		private int count;
		
		public KillInfo(){
			
		}
		
		public KillInfo(String monsterSn, int count) {
			this.monsterSn = monsterSn;
			this.count = count;
		}

		public String getMonsterSn() {
			return monsterSn;
		}

		public void setMonsterSn(String monsterSn) {
			this.monsterSn = monsterSn;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}
		
	}
}
