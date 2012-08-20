package com.pwrd.war.gameserver.fight.field.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 战场队伍对象
 * @author zy
 *
 */
public class FightTeam {
	private String teamSn = "";	//队伍编号
	private String formSn = "";	//阵型编号
	private List<FightUnit> units = new ArrayList<FightUnit>();	//全部战斗单元列表
	private Map<Integer, FightUnit> alives = new HashMap<Integer, FightUnit>();	//存活战斗单元列表
	
	public FightTeam(String teamSn, String formSn) {
		this.teamSn = teamSn;
		this.formSn = formSn;
	}
	
	/**
	 * 增加一个战斗单元，如果是存活战斗单元一并增加到存活列表中
	 * @param unit
	 */
	public void addUnit(FightUnit unit) {
		if (!unit.isBlock()) {
			units.add(unit);
		}
		if (unit.isAlive()) {
			alives.put(unit.getUnitIndex(), unit);
		}
	}
	
	/**
	 * 从存活列表中移除一个战斗单元
	 * @param unit
	 */
	public void removeUnit(FightUnit unit) {
		alives.remove(unit.getUnitIndex());
	}
	
	/**
	 * 队伍是否存活，即存活列表中是否还有战斗单元
	 * @return
	 */
	public boolean isAlive() {
		return getAliveSize() > 0;
	}
	
	/**
	 * 获取平均等级
	 * @return
	 */
	public int getAvgLevel() {
		int avg = 0;
		for (FightUnit unit : units) {
			if (!unit.isBlock()) {
				avg += unit.getUnitLevel();
			}
		}
		if (units.size() > 0) {
			return avg / units.size();
		} else {
			return 0;
		}
	}
	
	/**
	 * 获取存活人数
	 * @return
	 */
	private int getAliveSize() {
		int size = 0;
		for (FightUnit unit : alives.values()) {
			if (!unit.isBlock()) {
				size ++;
			}
		}
		return size;
	}
	
	/**
	 * 获取存活比例
	 * @return
	 */
	public double getAliveRate() {
		return (double)getAliveSize() / (double)units.size();
	}

	public String getTeamSn() {
		return teamSn;
	}

	public String getFormSn() {
		return formSn;
	}

	public List<FightUnit> getUnits() {
		return units;
	}

	public Collection<FightUnit> getAlives() {
		return alives.values();
	}
	
}
