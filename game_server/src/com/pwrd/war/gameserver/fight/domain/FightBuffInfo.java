package com.pwrd.war.gameserver.fight.domain;

/**
 * 技能产生的buff信息
 * @author zy
 *
 */
public class FightBuffInfo {
	public static int TYPE_ADD = 1;
	public static int TYPE_REMOVE = 2;
	public static int TYPE_REPLACE = 3;
	
	private int index;		//角色编号
	private int buffSn;		//buff编号
	private int type;		//变化类型，1为增加，2为移除，3为替换
	private int targetPos;	//区域buff位置
	private int targetLine;	//区域buff所在线，-1为全部线
	private int oldBuffSn;	//移除时原有buff编号
	
	public FightBuffInfo() {
	}
	
	public FightBuffInfo(int index, int buffSn, int type, int targetPos, int targetLine, int oldBuffSn) {
		this.index = index;
		this.buffSn = buffSn;
		this.type = type;
		this.targetPos = targetPos;
		this.targetLine = targetLine;
		this.oldBuffSn = oldBuffSn;
	}
	
	public int getBuffSn() {
		return buffSn;
	}
	public void setBuffSn(int buffSn) {
		this.buffSn = buffSn;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getTargetPos() {
		return targetPos;
	}
	public void setTargetPos(int targetPos) {
		this.targetPos = targetPos;
	}
	public int getTargetLine() {
		return targetLine;
	}
	public void setTargetLine(int targetLine) {
		this.targetLine = targetLine;
	}
	public int getOldBuffSn() {
		return oldBuffSn;
	}
	public void setOldBuffSn(int oldBuffSn) {
		this.oldBuffSn = oldBuffSn;
	}
	
}
