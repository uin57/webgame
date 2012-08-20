package com.pwrd.war.gameserver.fight.field.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pwrd.war.gameserver.fight.FightUtils;
import com.pwrd.war.gameserver.fight.buff.FightArea;
import com.pwrd.war.gameserver.fight.domain.FightUnitAction;

/**
 * 地图分线对象，保存线内双方战斗对象和障碍对象，进行移动和换线处理
 * @author zy
 *
 */
public class FightLine {
	private int lineSn = -1;	//分线编号
	private Map<Integer, FightUnit> attUnits = new HashMap<Integer, FightUnit>();	//进攻方战斗单元
	private Map<Integer, FightUnit> attBlocks = new HashMap<Integer, FightUnit>();	//进攻方障碍
	private Map<Integer, FightUnit> defUnits = new HashMap<Integer, FightUnit>();	//防守方战斗单元
	private Map<Integer, FightUnit> defBlocks = new HashMap<Integer, FightUnit>();	//防守方障碍
	private boolean isClosed = false;	//分线是否关闭
	
	public FightLine(int lineSn) {
		this.lineSn = lineSn;
	}
	
	/**
	 * 加入战斗单元到分线中指定位置
	 * @param unit
	 */
	public void addUnit(FightUnit unit) {
		if (unit.isAlive()) {
			if (unit.isBlock()) {
				if (unit.isAttack()) {
					attBlocks.put(unit.getUnitIndex(), unit);
				} else {
					defBlocks.put(unit.getUnitIndex(), unit);
				}
			} else {
				if (unit.isAttack()) {
					attUnits.put(unit.getUnitIndex(), unit);
				} else {
					defUnits.put(unit.getUnitIndex(), unit);
				}
			}
		}
	}
	
	/**
	 * 将战斗单元从分线中移除
	 * @param unit
	 */
	public void removeUnit(FightUnit unit) {
		int index = unit.getUnitIndex();
		attUnits.remove(index);
		attBlocks.remove(index);
		defUnits.remove(index);
		defBlocks.remove(index);
	}
	
	/**
	 * 将指定一方的单元全部移除，并返回单元列表
	 * @param isAttack
	 * @return
	 */
	public List<FightUnit> removeAllUnits(boolean isAttack) {
		List<FightUnit> list = new ArrayList<FightUnit>();
		if (isAttack) {
			for (FightUnit unit : attUnits.values()) {
				list.add(unit);
			}
			attUnits.clear();
		} else {
			for (FightUnit unit : defUnits.values()) {
				if (!unit.isBlock()) {
					list.add(unit);
				}
			}
			defUnits.clear();
		}
		return list;
	}
	
	/**
	 * 获取换线后出现位置信息
	 * @param isAttack
	 * @return
	 */
	public int getChangeLinePos(boolean isAttack) {
		if (isAttack) {
			int pos = FightUtils.flipPosition(0);
			for (FightUnit unit : attUnits.values()) {
				if (unit.getCurPos() < pos) {
					pos = unit.getCurPos();
				}
			}
			return pos;
		} else {
			int pos = 0;
			for (FightUnit unit : defUnits.values()) {
				if (unit.getCurPos() > pos) {
					pos = unit.getCurPos();
				}
			}
			return pos;
		}
	}
	
	/**
	 * 判断分线是否需要关闭，如果某一方单元全部死亡则需要关闭，无论是否存在障碍
	 * @return
	 */
	public boolean needClose() {
		return !isClosed && (attUnits.isEmpty() || defUnits.isEmpty());
	}
	
	/**
	 * 获取分线是否关闭状态
	 * @return
	 */
	public boolean isClosed() {
		return isClosed;
	}
	
	/**
	 * 关闭分线
	 */
	public void closeLine() {
		isClosed = true;
		attUnits.clear();
		defUnits.clear();
		attBlocks.clear();
		defBlocks.clear();
	}
	
	/**
	 * 获取分线编号
	 * @return
	 */
	public int getLineSn() {
		return lineSn;
	}
	
	/**
	 * 获取进攻方数量
	 * @return
	 */
	public int getAttUnitsSize() {
		return attUnits.size();
	}
	
	/**
	 * 获取防守方数量
	 * @return
	 */
	public int getDefUnitsSize() {
		return defUnits.size();
	}
	
	/**
	 * 分线内移动
	 * @param currentRound
	 */
	public void doMove(int currentRound) {
		//遍历全部存活单元，取双方距离最近的两个单元中心作为移动基准中心点，双方最终移动位置以此为准以保证双方都会移动
		int attMinPos = FightUtils.flipPosition(0);
		int defMaxPos = 0;
		for (FightUnit unit : defUnits.values()) {
			int curPos = unit.getCurPos();
			if (curPos > defMaxPos) {
				defMaxPos = curPos;
			}
		}
		for (FightUnit unit : defBlocks.values()) {
			int curPos = unit.getCurPos();
			if (curPos > defMaxPos) {
				defMaxPos = curPos;
			}
		}
		for (FightUnit unit : attUnits.values()) {
			int curPos = unit.getCurPos();
			if (curPos < attMinPos) {
				attMinPos = curPos;
			}
		}
		for (FightUnit unit : attBlocks.values()) {
			int curPos = unit.getCurPos();
			if (curPos < attMinPos) {
				attMinPos = curPos;
			}
		}
		attMinPos = (attMinPos + defMaxPos + FightUtils.MinDistance) / 2;
		defMaxPos = attMinPos - FightUtils.MinDistance;
		
		//首先移动进攻方
		for (FightUnit unit : attUnits.values()) {
			int curPos = unit.getCurPos();
			if (unit.canMove(currentRound)) {
				//计算进攻方当前位置和移动后位置
				int tarPos = curPos - unit.getSpd();
				
				//根据对方最远位置进行校正
				if (tarPos < attMinPos) {
					tarPos = attMinPos;
				}
				
				//判断是否跨越障碍，如果跨越则进行位置校正
				for (FightUnit block : defBlocks.values()) {
					int blockPos = block.getCurPos();
					if (curPos >= blockPos && tarPos <= blockPos) {
						tarPos = Math.min(blockPos + FightUtils.MinDistance, curPos);
					}
				}
				
				//设置目标移动后位置
				unit.setCurPos(tarPos);
			}
		}
		
		//移动防守方
		for (FightUnit unit : defUnits.values()) {
			if (unit.canMove(currentRound)) {
				//计算进攻方当前位置和移动后位置
				int curPos = unit.getCurPos();
				int tarPos = curPos + unit.getSpd();
				
				//根据对方最远位置进行校正
				if (tarPos > defMaxPos) {
					tarPos = defMaxPos;
				}
				
				//判断是否跨越障碍，如果跨越则进行位置校正
				for (FightUnit block : attBlocks.values()) {
					int blockPos = block.getCurPos();
					if (curPos <= blockPos && tarPos >= blockPos) {
						tarPos = Math.max(blockPos - FightUtils.MinDistance, curPos);
					}
				}
				
				//设置目标移动后位置
				unit.setCurPos(tarPos);
			}
		}
	}
	
	/**
	 * 判断区域buff
	 * @param unitActions
	 * @param areaBuffs
	 */
	public void checkAreaBuff(Map<Integer, FightUnitAction> unitActions, List<FightArea> areaBuffs) {
		//依次判断线内双方是否触发buff
		for (FightArea buff : areaBuffs) {
			int buffId = buff.getBuffId();
			int buffPos = buff.getTargetPos();
			boolean trigger = false;
			
			//根据buff所属方分别判断线内指定一方是否可以触发buff
			if (buff.isAttack()) {
				//只要找到一个在buff区域内的单元则线内此方全部单元都触发buff
				for (FightUnit unit : attUnits.values()) {
					int unitPos = unit.getCurPos();
					if (unitPos < buffPos + FightUtils.AreaRadio && unitPos >= buffPos - FightUtils.AreaRadio) {
						trigger = true;
						break;
					}
				}
				
				//触发buff，给所有进攻方成员加入区域buff
				if (trigger) {
					for (FightUnit unit : attUnits.values()) {
						
					}
				}
			} else {
				
			}
			
				
		}
	}
	
}
