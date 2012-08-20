package com.pwrd.war.gameserver.fight.domain;

import java.util.List;


/**
 * 换线动作
 * @author zy
 *
 */
public class FightChangeLineAction {
	private int index;			//角色编号
	private int oldLine;		//角色原线路
	private int newLine;		//角色新线路
	private int newPosition;	//角色新位置
	private FightBuffInfo[] buffers;	//换线产生的buffer
	
	public FightChangeLineAction() {
	}
	
	public FightChangeLineAction(int index, int oldLine, int newLine, int newPosition, List<FightBuffInfo> buffers) {
		this.index = index;
		this.oldLine = oldLine;
		this.newLine = newLine;
		this.newPosition = newPosition;
		this.buffers = buffers.toArray(new FightBuffInfo[0]);
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getOldLine() {
		return oldLine;
	}
	public void setOldLine(int oldLine) {
		this.oldLine = oldLine;
	}
	public int getNewLine() {
		return newLine;
	}
	public void setNewLine(int newLine) {
		this.newLine = newLine;
	}
	public int getNewPosition() {
		return newPosition;
	}
	public void setNewPosition(int newPosition) {
		this.newPosition = newPosition;
	}
	public FightBuffInfo[] getBuffers() {
		return buffers;
	}
	public void setBuffers(FightBuffInfo[] buffers) {
		this.buffers = buffers;
	}
	
	
}
