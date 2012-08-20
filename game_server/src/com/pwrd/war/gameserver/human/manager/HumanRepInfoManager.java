package com.pwrd.war.gameserver.human.manager;

import java.util.Collection;

import com.pwrd.war.common.HeartBeatListener;
import com.pwrd.war.common.LogReasons.SnapLogReason;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.RoleDataHolder;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.JsonPropDataHolder;
import com.pwrd.war.gameserver.rep.HumanRepInfo;
import com.pwrd.war.gameserver.rep.HumanRepInfoContainer;
import com.pwrd.war.gameserver.rep.HumanRepInfoDbManager;

public class HumanRepInfoManager implements RoleDataHolder, JsonPropDataHolder, HeartBeatListener{

	/** 副本信息管理容器 */
	private HumanRepInfoContainer humanRepInfoContainer;
	/** 主人 */
	private Human owner;
//	/** 招募锁，招募武将的异步消息未返回前不允许招募更多武将，避免错误 */
//	private boolean hiringLock = false;
	
	public HumanRepInfoManager(Human owner) {
		humanRepInfoContainer = new HumanRepInfoContainer(owner);
		this.owner = owner;
	}
	
	public Human getOwner() {
		return owner;
	}
	
	/**
	 * 加载副本信息列表并进行初始化
	 * 进入游戏时调用
	 */
	public void load() {
		HumanRepInfoDbManager.getInstance().loadAllHumanRepInfoFromDB(humanRepInfoContainer);
//		this.sendPetPropsMsg(null);
		
		Globals.getPlayerLogService().sendHumanRepInfoSnapLog(owner,SnapLogReason.REASON_SNAP_LOGIN_REPINFO);
	}
	

	
 

	/**
	 * 根据副本id获得副本信息
	 * @param repId
	 * @return
	 */
	public HumanRepInfo getHumanRepInfoByRepId(String repId){
		return humanRepInfoContainer.getHumanRepInfoByRepId(repId);
	}
	
	/**
	 * 获取副本信息列表
	 * @return
	 */
	public Collection<HumanRepInfo> getHumanRepInfos() {
		return humanRepInfoContainer.getHumanRepInfos();
	}
	
	/**
	 * 新增副本信息
	 * @param pet
	 */
	public void addHumanRepInfo(HumanRepInfo humanRepInfo) {
		humanRepInfoContainer.addHumanRepInfo(humanRepInfo);
	}

	/**
	 * 移除副本信息
	 * @param pet
	 * @return
	 */
	public boolean removeHumanRepInfo(HumanRepInfo humanRepInfo) {
		return humanRepInfoContainer.remove(humanRepInfo);
	}
	
//	/**
//	 * 获取副本信息列表
//	 * 
//	 * @return 副本信息信息列表
//	 */
//	public GCPetList getHumanRepInfoListMsg() {
//		return HumanRepInfoMsgBuilder.getRepInfoListMsg(owner, humanRepInfoContainer);
//	}
//	
//	/**
//	 * 取得增加武将消息
//	 * @author xf
//	 */
//	public GCPetAdd getPetAddMsg(Pet pet){
//		GCPetAdd msg = new GCPetAdd();
//		msg.setPets(PetMsgBuilder.getPetInfo(pet));
//		return msg;
//	}
//	
//	/**
//	 * 取得删除武将消息
//	 * @author xf
//	 */
//	public GCPetDel getPetDelMsg(Pet pet){
//		GCPetDel msg = new GCPetDel();
//		msg.setPetUUid(pet.getUUID());
//		return msg;
//	}
//	
//	/**
//	 * 将武将属性信息发送到客户端
//	 * @author xf
//	 */
//	public void sendPetPropsMsg(String petUUid){
//		if(StringUtils.isEmpty(petUUid)){
//			//发送全部
//			for(Pet pet : petContainer.getPets()){
//				pet.snapChangedProperty(true);
//			}
//		}else{
//			Pet pet = petContainer.getPetByUuid(petUUid);
//			pet.snapChangedProperty(true);
//		}
//	}

	@Override
	public void checkAfterRoleLoad() {
		activeAllHumanRepInfos();		
	}
	
//	/**
//	 * 初始化，进行离线经验计算
//	 */
//	public void init() {
////		Globals.getTrainingService().updateAllPetsTraining(owner);
//	}
//	
	@Override
	public void checkBeforeRoleEnter() {
	}

	
	private void activeAllHumanRepInfos() {
		for (HumanRepInfo rep : humanRepInfoContainer.getHumanRepInfos()) {
			rep.activeAndInitProps();
		}
	}

//	public boolean isHiringLock() {
//		return hiringLock;
//	}
//	
//	public void setHiringLock(boolean hiringLock) {
//		this.hiringLock = hiringLock;
//	}
//
	@Override
	public void onHeartBeat() {

	}

	@Override
	public String toJsonProp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadJsonProp(String value) {
		// TODO Auto-generated method stub
		
	}
	
}
