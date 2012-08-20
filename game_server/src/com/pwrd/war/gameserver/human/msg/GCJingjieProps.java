package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返还境界变化信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCJingjieProps extends GCMessage{
	
	/** 目标UUID */
	private String targetUUID;
	/** 当前境界名称 */
	private String nowName;
	/** 下一境界增加攻击值 */
	private int addAtk;
	/** 下一境界增加防御值 */
	private int addDef;
	/** 下一境界增加血量值 */
	private int addMaxHp;
	/** 下一境界增加伤害值 */
	private double addDamage;
	/** 当前境界值 */
	private int jingjie;
	/** 当前境界值需要的成长值 */
	private int nowNeedGrow;
	/** 下一境界需要的成长值 */
	private int nextNeedGrow;

	public GCJingjieProps (){
	}
	
	public GCJingjieProps (
			String targetUUID,
			String nowName,
			int addAtk,
			int addDef,
			int addMaxHp,
			double addDamage,
			int jingjie,
			int nowNeedGrow,
			int nextNeedGrow ){
			this.targetUUID = targetUUID;
			this.nowName = nowName;
			this.addAtk = addAtk;
			this.addDef = addDef;
			this.addMaxHp = addMaxHp;
			this.addDamage = addDamage;
			this.jingjie = jingjie;
			this.nowNeedGrow = nowNeedGrow;
			this.nextNeedGrow = nextNeedGrow;
	}

	@Override
	protected boolean readImpl() {
		targetUUID = readString();
		nowName = readString();
		addAtk = readInteger();
		addDef = readInteger();
		addMaxHp = readInteger();
		addDamage = readDouble();
		jingjie = readInteger();
		nowNeedGrow = readInteger();
		nextNeedGrow = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(targetUUID);
		writeString(nowName);
		writeInteger(addAtk);
		writeInteger(addDef);
		writeInteger(addMaxHp);
		writeDouble(addDamage);
		writeInteger(jingjie);
		writeInteger(nowNeedGrow);
		writeInteger(nextNeedGrow);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_JingJie_Props;
	}
	
	@Override
	public String getTypeName() {
		return "GC_JingJie_Props";
	}

	public String getTargetUUID(){
		return targetUUID;
	}
		
	public void setTargetUUID(String targetUUID){
		this.targetUUID = targetUUID;
	}

	public String getNowName(){
		return nowName;
	}
		
	public void setNowName(String nowName){
		this.nowName = nowName;
	}

	public int getAddAtk(){
		return addAtk;
	}
		
	public void setAddAtk(int addAtk){
		this.addAtk = addAtk;
	}

	public int getAddDef(){
		return addDef;
	}
		
	public void setAddDef(int addDef){
		this.addDef = addDef;
	}

	public int getAddMaxHp(){
		return addMaxHp;
	}
		
	public void setAddMaxHp(int addMaxHp){
		this.addMaxHp = addMaxHp;
	}

	public double getAddDamage(){
		return addDamage;
	}
		
	public void setAddDamage(double addDamage){
		this.addDamage = addDamage;
	}

	public int getJingjie(){
		return jingjie;
	}
		
	public void setJingjie(int jingjie){
		this.jingjie = jingjie;
	}

	public int getNowNeedGrow(){
		return nowNeedGrow;
	}
		
	public void setNowNeedGrow(int nowNeedGrow){
		this.nowNeedGrow = nowNeedGrow;
	}

	public int getNextNeedGrow(){
		return nextNeedGrow;
	}
		
	public void setNextNeedGrow(int nextNeedGrow){
		this.nextNeedGrow = nextNeedGrow;
	}
}