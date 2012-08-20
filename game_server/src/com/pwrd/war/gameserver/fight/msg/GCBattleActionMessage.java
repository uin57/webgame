package com.pwrd.war.gameserver.fight.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 战报消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBattleActionMessage extends GCMessage{
	
	/** 战斗场景 */
	private String sceneId;
	/** 攻击方玩家id */
	private String attackSn;
	/** 攻击方阵法 */
	private String attackMatrixID;
	/** 防守方id */
	private String defenceSn;
	/** 防守方阵法 */
	private String defenceMatrixID;
	/** 角色信息 */
	private com.pwrd.war.gameserver.fight.domain.FightRoleInfo[] fightRoleInfo;
	/** 回合战报 */
	private com.pwrd.war.gameserver.fight.domain.FightAction[] fightActions;
	/** 战斗时间 */
	private long battleTime;
	/** 是否战胜 */
	private boolean isWin;
	/** 进攻方掉落 */
	private com.pwrd.war.gameserver.fight.domain.FightLostItem[] lostItems;
	/** 攻击方剩余比率 */
	private double attackLeftRatio;
	/** 防御方剩余比率 */
	private double defenseLeftRatio;
	/** 战斗sn */
	private String battleSn;
	/** 技能资源 */
	private int[] skillResources;
	/** buff资源 */
	private int[] buffResources;
	/** 围观npcid */
	private int npcId;
	/** 战斗背景id */
	private String bgId;

	public GCBattleActionMessage (){
	}
	
	public GCBattleActionMessage (
			String sceneId,
			String attackSn,
			String attackMatrixID,
			String defenceSn,
			String defenceMatrixID,
			com.pwrd.war.gameserver.fight.domain.FightRoleInfo[] fightRoleInfo,
			com.pwrd.war.gameserver.fight.domain.FightAction[] fightActions,
			long battleTime,
			boolean isWin,
			com.pwrd.war.gameserver.fight.domain.FightLostItem[] lostItems,
			double attackLeftRatio,
			double defenseLeftRatio,
			String battleSn,
			int[] skillResources,
			int[] buffResources,
			int npcId,
			String bgId ){
			this.sceneId = sceneId;
			this.attackSn = attackSn;
			this.attackMatrixID = attackMatrixID;
			this.defenceSn = defenceSn;
			this.defenceMatrixID = defenceMatrixID;
			this.fightRoleInfo = fightRoleInfo;
			this.fightActions = fightActions;
			this.battleTime = battleTime;
			this.isWin = isWin;
			this.lostItems = lostItems;
			this.attackLeftRatio = attackLeftRatio;
			this.defenseLeftRatio = defenseLeftRatio;
			this.battleSn = battleSn;
			this.skillResources = skillResources;
			this.buffResources = buffResources;
			this.npcId = npcId;
			this.bgId = bgId;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		sceneId = readString();
		attackSn = readString();
		attackMatrixID = readString();
		defenceSn = readString();
		defenceMatrixID = readString();
		count = readShort();
		count = count < 0 ? 0 : count;
		fightRoleInfo = new com.pwrd.war.gameserver.fight.domain.FightRoleInfo[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.gameserver.fight.domain.FightRoleInfo obj = new com.pwrd.war.gameserver.fight.domain.FightRoleInfo();
			obj.setRoleSn(readString());
			obj.setRoleName(readString());
			obj.setSkeletonSn(readString());
			obj.setInitMorale(readInteger());
			obj.setLine(readInteger());
			obj.setInitPos(readInteger());
			obj.setFormPos(readInteger());
			obj.setInitHp(readInteger());
			obj.setFinalHp(readInteger());
			obj.setMaxHp(readInteger());
			obj.setIsAttack(readBoolean());
			obj.setIsRemote(readBoolean());
			obj.setIndex(readInteger());
			obj.setRoleType(readInteger());
			obj.setExp(readInteger());
			obj.setMoney(readInteger());
			obj.setLevel(readInteger());
			obj.setStar(readInteger());
			obj.setGender(readInteger());
			obj.setAttackedAnim(readString());
			obj.setCastAnim(readString());
			obj.setVocationAnim(readString());
			obj.setIsVisible(readBoolean());
			obj.setMaxMorale(readInteger());
			obj.setSkillMorale(readInteger());
			obj.setBelongs(readInteger());
			fightRoleInfo[i] = obj;
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		fightActions = new com.pwrd.war.gameserver.fight.domain.FightAction[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.gameserver.fight.domain.FightAction obj = new com.pwrd.war.gameserver.fight.domain.FightAction();
			obj.setRound(readInteger());
			{
				int subCount = readShort();
							com.pwrd.war.gameserver.fight.domain.FightChangeLineAction[] subList = new com.pwrd.war.gameserver.fight.domain.FightChangeLineAction[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = new com.pwrd.war.gameserver.fight.domain.FightChangeLineAction();
									subList[j].setIndex(readInteger());
									subList[j].setOldLine(readInteger());
									subList[j].setNewLine(readInteger());
									subList[j].setNewPosition(readInteger());
									{
				int subsubCount = readShort();
							com.pwrd.war.gameserver.fight.domain.FightBuffInfo[] subsubList = new com.pwrd.war.gameserver.fight.domain.FightBuffInfo[subsubCount];
							for(int k = 0; k < subsubCount; k++){
											subsubList[k] = new com.pwrd.war.gameserver.fight.domain.FightBuffInfo();
													subsubList[k].setIndex(readInteger());
													subsubList[k].setBuffSn(readInteger());
													subsubList[k].setType(readInteger());
													subsubList[k].setTargetPos(readInteger());
													subsubList[k].setTargetLine(readInteger());
													subsubList[k].setOldBuffSn(readInteger());
															}
				subList[j].setBuffers(subsubList);
			}
															}
				obj.setChangeLines(subList);
			}
			{
				int subCount = readShort();
							com.pwrd.war.gameserver.fight.domain.FightLockAction[] subList = new com.pwrd.war.gameserver.fight.domain.FightLockAction[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = new com.pwrd.war.gameserver.fight.domain.FightLockAction();
									subList[j].setUnitIndex(readInteger());
									subList[j].setActionType(readInteger());
									subList[j].setSkillSn(readInteger());
									subList[j].setSkillTarget(readInteger());
									{
				int subsubCount = readShort();
							com.pwrd.war.gameserver.fight.domain.FightLockTargetInfo[] subsubList = new com.pwrd.war.gameserver.fight.domain.FightLockTargetInfo[subsubCount];
							for(int k = 0; k < subsubCount; k++){
											subsubList[k] = new com.pwrd.war.gameserver.fight.domain.FightLockTargetInfo();
													subsubList[k].setUnitIndex(readInteger());
													subsubList[k].setType(readInteger());
													subsubList[k].setHpLost(readInteger());
													subsubList[k].setIsDead(readBoolean());
													subsubList[k].setBackDistance(readInteger());
													subsubList[k].setCurPos(readInteger());
															}
				subList[j].setTargets(subsubList);
			}
									{
				int subsubCount = readShort();
							com.pwrd.war.gameserver.fight.domain.FightBuffInfo[] subsubList = new com.pwrd.war.gameserver.fight.domain.FightBuffInfo[subsubCount];
							for(int k = 0; k < subsubCount; k++){
											subsubList[k] = new com.pwrd.war.gameserver.fight.domain.FightBuffInfo();
													subsubList[k].setIndex(readInteger());
													subsubList[k].setBuffSn(readInteger());
													subsubList[k].setType(readInteger());
													subsubList[k].setTargetPos(readInteger());
													subsubList[k].setTargetLine(readInteger());
													subsubList[k].setOldBuffSn(readInteger());
															}
				subList[j].setBuffers(subsubList);
			}
															}
				obj.setSuperSkills(subList);
			}
			{
				int subCount = readShort();
							com.pwrd.war.gameserver.fight.domain.FightUnitAction[] subList = new com.pwrd.war.gameserver.fight.domain.FightUnitAction[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = new com.pwrd.war.gameserver.fight.domain.FightUnitAction();
									subList[j].setUnitIndex(readInteger());
									subList[j].setActionType(readInteger());
									subList[j].setSpd(readInteger());
									subList[j].setCurPos(readInteger());
									subList[j].setCurHp(readInteger());
									subList[j].setSkillSn(readInteger());
									subList[j].setSkillTarget(readInteger());
									subList[j].setSkillEnd(readBoolean());
									{
				int subsubCount = readShort();
							com.pwrd.war.gameserver.fight.domain.FightTargetInfo[] subsubList = new com.pwrd.war.gameserver.fight.domain.FightTargetInfo[subsubCount];
							for(int k = 0; k < subsubCount; k++){
											subsubList[k] = new com.pwrd.war.gameserver.fight.domain.FightTargetInfo();
													subsubList[k].setUnitIndex(readInteger());
													subsubList[k].setType(readInteger());
													subsubList[k].setHpLost(readInteger());
															}
				subList[j].setTargets(subsubList);
			}
									{
				int subsubCount = readShort();
							com.pwrd.war.gameserver.fight.domain.FightBuffInfo[] subsubList = new com.pwrd.war.gameserver.fight.domain.FightBuffInfo[subsubCount];
							for(int k = 0; k < subsubCount; k++){
											subsubList[k] = new com.pwrd.war.gameserver.fight.domain.FightBuffInfo();
													subsubList[k].setIndex(readInteger());
													subsubList[k].setBuffSn(readInteger());
													subsubList[k].setType(readInteger());
													subsubList[k].setTargetPos(readInteger());
													subsubList[k].setTargetLine(readInteger());
													subsubList[k].setOldBuffSn(readInteger());
															}
				subList[j].setBuffers(subsubList);
			}
									subList[j].setCurMorale(readInteger());
															}
				obj.setRoleActions(subList);
			}
			{
				int subCount = readShort();
							com.pwrd.war.gameserver.fight.domain.FightBuffInfo[] subList = new com.pwrd.war.gameserver.fight.domain.FightBuffInfo[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = new com.pwrd.war.gameserver.fight.domain.FightBuffInfo();
									subList[j].setIndex(readInteger());
									subList[j].setBuffSn(readInteger());
									subList[j].setType(readInteger());
									subList[j].setTargetPos(readInteger());
									subList[j].setTargetLine(readInteger());
									subList[j].setOldBuffSn(readInteger());
															}
				obj.setAreaBuffs(subList);
			}
			fightActions[i] = obj;
		}
		battleTime = readLong();
		isWin = readBoolean();
		count = readShort();
		count = count < 0 ? 0 : count;
		lostItems = new com.pwrd.war.gameserver.fight.domain.FightLostItem[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.gameserver.fight.domain.FightLostItem obj = new com.pwrd.war.gameserver.fight.domain.FightLostItem();
			obj.setSn(readString());
			obj.setNum(readInteger());
			lostItems[i] = obj;
		}
		attackLeftRatio = readDouble();
		defenseLeftRatio = readDouble();
		battleSn = readString();
		count = readShort();
		count = count < 0 ? 0 : count;
		skillResources = new int[count];
		for(int i=0; i<count; i++){
			skillResources[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		buffResources = new int[count];
		for(int i=0; i<count; i++){
			buffResources[i] = readInteger();
		}
		npcId = readInteger();
		bgId = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(sceneId);
		writeString(attackSn);
		writeString(attackMatrixID);
		writeString(defenceSn);
		writeString(defenceMatrixID);
		writeShort(fightRoleInfo.length);
		for(int i=0; i<fightRoleInfo.length; i++){
			writeString(fightRoleInfo[i].getRoleSn());
			writeString(fightRoleInfo[i].getRoleName());
			writeString(fightRoleInfo[i].getSkeletonSn());
			writeInteger(fightRoleInfo[i].getInitMorale());
			writeInteger(fightRoleInfo[i].getLine());
			writeInteger(fightRoleInfo[i].getInitPos());
			writeInteger(fightRoleInfo[i].getFormPos());
			writeInteger(fightRoleInfo[i].getInitHp());
			writeInteger(fightRoleInfo[i].getFinalHp());
			writeInteger(fightRoleInfo[i].getMaxHp());
			writeBoolean(fightRoleInfo[i].getIsAttack());
			writeBoolean(fightRoleInfo[i].getIsRemote());
			writeInteger(fightRoleInfo[i].getIndex());
			writeInteger(fightRoleInfo[i].getRoleType());
			writeInteger(fightRoleInfo[i].getExp());
			writeInteger(fightRoleInfo[i].getMoney());
			writeInteger(fightRoleInfo[i].getLevel());
			writeInteger(fightRoleInfo[i].getStar());
			writeInteger(fightRoleInfo[i].getGender());
			writeString(fightRoleInfo[i].getAttackedAnim());
			writeString(fightRoleInfo[i].getCastAnim());
			writeString(fightRoleInfo[i].getVocationAnim());
			writeBoolean(fightRoleInfo[i].getIsVisible());
			writeInteger(fightRoleInfo[i].getMaxMorale());
			writeInteger(fightRoleInfo[i].getSkillMorale());
			writeInteger(fightRoleInfo[i].getBelongs());
		}
		writeShort(fightActions.length);
		for(int i=0; i<fightActions.length; i++){
			writeInteger(fightActions[i].getRound());
			com.pwrd.war.gameserver.fight.domain.FightChangeLineAction[] changeLines=fightActions[i].getChangeLines();
			writeShort(changeLines.length);
			for(int j=0; j<changeLines.length; j++){
			writeInteger(changeLines[j].getIndex());
			writeInteger(changeLines[j].getOldLine());
			writeInteger(changeLines[j].getNewLine());
			writeInteger(changeLines[j].getNewPosition());
			com.pwrd.war.gameserver.fight.domain.FightBuffInfo[] buffers=changeLines[j].getBuffers();
			writeShort(buffers.length);
			for(int k=0; k<buffers.length; k++){
				writeInteger(buffers[k].getIndex());
				writeInteger(buffers[k].getBuffSn());
				writeInteger(buffers[k].getType());
				writeInteger(buffers[k].getTargetPos());
				writeInteger(buffers[k].getTargetLine());
				writeInteger(buffers[k].getOldBuffSn());
			}
			}
			com.pwrd.war.gameserver.fight.domain.FightLockAction[] superSkills=fightActions[i].getSuperSkills();
			writeShort(superSkills.length);
			for(int j=0; j<superSkills.length; j++){
			writeInteger(superSkills[j].getUnitIndex());
			writeInteger(superSkills[j].getActionType());
			writeInteger(superSkills[j].getSkillSn());
			writeInteger(superSkills[j].getSkillTarget());
			com.pwrd.war.gameserver.fight.domain.FightLockTargetInfo[] targets=superSkills[j].getTargets();
			writeShort(targets.length);
			for(int k=0; k<targets.length; k++){
				writeInteger(targets[k].getUnitIndex());
				writeInteger(targets[k].getType());
				writeInteger(targets[k].getHpLost());
				writeBoolean(targets[k].getIsDead());
				writeInteger(targets[k].getBackDistance());
				writeInteger(targets[k].getCurPos());
			}
			com.pwrd.war.gameserver.fight.domain.FightBuffInfo[] buffers=superSkills[j].getBuffers();
			writeShort(buffers.length);
			for(int k=0; k<buffers.length; k++){
				writeInteger(buffers[k].getIndex());
				writeInteger(buffers[k].getBuffSn());
				writeInteger(buffers[k].getType());
				writeInteger(buffers[k].getTargetPos());
				writeInteger(buffers[k].getTargetLine());
				writeInteger(buffers[k].getOldBuffSn());
			}
			}
			com.pwrd.war.gameserver.fight.domain.FightUnitAction[] roleActions=fightActions[i].getRoleActions();
			writeShort(roleActions.length);
			for(int j=0; j<roleActions.length; j++){
			writeInteger(roleActions[j].getUnitIndex());
			writeInteger(roleActions[j].getActionType());
			writeInteger(roleActions[j].getSpd());
			writeInteger(roleActions[j].getCurPos());
			writeInteger(roleActions[j].getCurHp());
			writeInteger(roleActions[j].getSkillSn());
			writeInteger(roleActions[j].getSkillTarget());
			writeBoolean(roleActions[j].getSkillEnd());
			com.pwrd.war.gameserver.fight.domain.FightTargetInfo[] targets=roleActions[j].getTargets();
			writeShort(targets.length);
			for(int k=0; k<targets.length; k++){
				writeInteger(targets[k].getUnitIndex());
				writeInteger(targets[k].getType());
				writeInteger(targets[k].getHpLost());
			}
			com.pwrd.war.gameserver.fight.domain.FightBuffInfo[] buffers=roleActions[j].getBuffers();
			writeShort(buffers.length);
			for(int k=0; k<buffers.length; k++){
				writeInteger(buffers[k].getIndex());
				writeInteger(buffers[k].getBuffSn());
				writeInteger(buffers[k].getType());
				writeInteger(buffers[k].getTargetPos());
				writeInteger(buffers[k].getTargetLine());
				writeInteger(buffers[k].getOldBuffSn());
			}
			writeInteger(roleActions[j].getCurMorale());
			}
			com.pwrd.war.gameserver.fight.domain.FightBuffInfo[] areaBuffs=fightActions[i].getAreaBuffs();
			writeShort(areaBuffs.length);
			for(int j=0; j<areaBuffs.length; j++){
			writeInteger(areaBuffs[j].getIndex());
			writeInteger(areaBuffs[j].getBuffSn());
			writeInteger(areaBuffs[j].getType());
			writeInteger(areaBuffs[j].getTargetPos());
			writeInteger(areaBuffs[j].getTargetLine());
			writeInteger(areaBuffs[j].getOldBuffSn());
			}
		}
		writeLong(battleTime);
		writeBoolean(isWin);
		writeShort(lostItems.length);
		for(int i=0; i<lostItems.length; i++){
			writeString(lostItems[i].getSn());
			writeInteger(lostItems[i].getNum());
		}
		writeDouble(attackLeftRatio);
		writeDouble(defenseLeftRatio);
		writeString(battleSn);
		writeShort(skillResources.length);
		for(int i=0; i<skillResources.length; i++){
			writeInteger(skillResources[i]);
		}
		writeShort(buffResources.length);
		for(int i=0; i<buffResources.length; i++){
			writeInteger(buffResources[i]);
		}
		writeInteger(npcId);
		writeString(bgId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_Battle_Action_Message;
	}
	
	@Override
	public String getTypeName() {
		return "GC_Battle_Action_Message";
	}

	public String getSceneId(){
		return sceneId;
	}
		
	public void setSceneId(String sceneId){
		this.sceneId = sceneId;
	}

	public String getAttackSn(){
		return attackSn;
	}
		
	public void setAttackSn(String attackSn){
		this.attackSn = attackSn;
	}

	public String getAttackMatrixID(){
		return attackMatrixID;
	}
		
	public void setAttackMatrixID(String attackMatrixID){
		this.attackMatrixID = attackMatrixID;
	}

	public String getDefenceSn(){
		return defenceSn;
	}
		
	public void setDefenceSn(String defenceSn){
		this.defenceSn = defenceSn;
	}

	public String getDefenceMatrixID(){
		return defenceMatrixID;
	}
		
	public void setDefenceMatrixID(String defenceMatrixID){
		this.defenceMatrixID = defenceMatrixID;
	}

	public com.pwrd.war.gameserver.fight.domain.FightRoleInfo[] getFightRoleInfo(){
		return fightRoleInfo;
	}

	public void setFightRoleInfo(com.pwrd.war.gameserver.fight.domain.FightRoleInfo[] fightRoleInfo){
		this.fightRoleInfo = fightRoleInfo;
	}	

	public com.pwrd.war.gameserver.fight.domain.FightAction[] getFightActions(){
		return fightActions;
	}

	public void setFightActions(com.pwrd.war.gameserver.fight.domain.FightAction[] fightActions){
		this.fightActions = fightActions;
	}	

	public long getBattleTime(){
		return battleTime;
	}
		
	public void setBattleTime(long battleTime){
		this.battleTime = battleTime;
	}

	public boolean getIsWin(){
		return isWin;
	}
		
	public void setIsWin(boolean isWin){
		this.isWin = isWin;
	}

	public com.pwrd.war.gameserver.fight.domain.FightLostItem[] getLostItems(){
		return lostItems;
	}

	public void setLostItems(com.pwrd.war.gameserver.fight.domain.FightLostItem[] lostItems){
		this.lostItems = lostItems;
	}	

	public double getAttackLeftRatio(){
		return attackLeftRatio;
	}
		
	public void setAttackLeftRatio(double attackLeftRatio){
		this.attackLeftRatio = attackLeftRatio;
	}

	public double getDefenseLeftRatio(){
		return defenseLeftRatio;
	}
		
	public void setDefenseLeftRatio(double defenseLeftRatio){
		this.defenseLeftRatio = defenseLeftRatio;
	}

	public String getBattleSn(){
		return battleSn;
	}
		
	public void setBattleSn(String battleSn){
		this.battleSn = battleSn;
	}

	public int[] getSkillResources(){
		return skillResources;
	}

	public void setSkillResources(int[] skillResources){
		this.skillResources = skillResources;
	}	

	public int[] getBuffResources(){
		return buffResources;
	}

	public void setBuffResources(int[] buffResources){
		this.buffResources = buffResources;
	}	

	public int getNpcId(){
		return npcId;
	}
		
	public void setNpcId(int npcId){
		this.npcId = npcId;
	}

	public String getBgId(){
		return bgId;
	}
		
	public void setBgId(String bgId){
		this.bgId = bgId;
	}
}