package com.pwrd.war.gameserver.human.manager;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.HeartBeatListener;
import com.pwrd.war.common.LogReasons.SnapLogReason;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.RoleDataHolder;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.JsonPropDataHolder;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.pet.PetContainer;
import com.pwrd.war.gameserver.pet.PetDbManager;
import com.pwrd.war.gameserver.pet.msg.GCPetAdd;
import com.pwrd.war.gameserver.pet.msg.GCPetDel;
import com.pwrd.war.gameserver.pet.msg.GCPetList;
import com.pwrd.war.gameserver.pet.msg.PetMsgBuilder;


/**
 * 玩家的武将管理器
 * 所有武将的数据存在petContainer中
 * @author yue.yan
 *
 */
public class HumanPetManager implements RoleDataHolder, JsonPropDataHolder, HeartBeatListener{

	/** 宠物管理容器 */
	private PetContainer petContainer;
	/** 主人 */
	private Human owner;
	/** 招募锁，招募武将的异步消息未返回前不允许招募更多武将，避免错误 */
	private boolean hiringLock = false;
	
	public HumanPetManager(Human owner) {
		petContainer = new PetContainer(owner);
		this.owner = owner;
	}
	
	public Human getOwner() {
		return owner;
	}
	
	/**
	 * 加载武将列表并进行初始化
	 * 进入游戏时调用
	 */
	public void load() {
		loadOffline();
//		this.sendPetPropsMsg(null);
		
		Globals.getPlayerLogService().sendPetSnapLog(owner,SnapLogReason.REASON_SNAP_LOGIN_PET);
	}
	
	public void loadOffline() {
		PetDbManager.getInstance().loadAllPetFromDB(petContainer);
	}

	
	/**
	 * 根据武将uuid获得武将
	 * @param uuid
	 * @return
	 */
	public Pet getPetByUuid(String uuid) {
		return petContainer.getPetByUuid(uuid);
	}
	public Pet getPetByName(String name) {
		return petContainer.getPetByName(name);
	}
 

	/**
	 * 获取当前武将数量
	 * @return
	 */
	public int getPetCount() {
		return petContainer.getPetCount();
	}
	
	/**
	 * 获取最大武将数量
	 * @return
	 */
	public int getMaxPetCount() {
		return petContainer.getMaxPetCount();
	}
	
	/**
	 * 设置最大武将数量
	 * @param maxPetCount
	 */
	public void setMaxPetCount(int maxPetCount) {
		petContainer.setMaxPetCount(maxPetCount);
	}
	
	/**
	 * 获取武将列表
	 * @return
	 */
	public List<Pet> getPets() {
		return petContainer.getPets();
	}
	
	/**
	 * 新增武将
	 * @param pet
	 */
	public void addPet(Pet pet) {
		petContainer.addPet(pet);
	}

	/**
	 * 移除武将
	 * @param pet
	 * @return
	 */
	public boolean removePet(Pet pet) {
		return petContainer.remove(pet);
	}
	
	/**
	 * 获取宠物信息列表
	 * 
	 * @return 宠物信息列表
	 */
	public GCPetList getPetListMsg() {
		return PetMsgBuilder.getPetListMsg(owner, petContainer);
	}
	
	/**
	 * 取得增加武将消息
	 * @author xf
	 */
	public GCPetAdd getPetAddMsg(Pet pet){
		GCPetAdd msg = new GCPetAdd();
		msg.setPets(PetMsgBuilder.getPetInfo(pet));
		return msg;
	}
	
	/**
	 * 取得删除武将消息
	 * @author xf
	 */
	public GCPetDel getPetDelMsg(Pet pet){
		GCPetDel msg = new GCPetDel();
		msg.setPetUUid(pet.getUUID());
		return msg;
	}
	
	/**
	 * 将武将属性信息发送到客户端
	 * @author xf
	 */
	public void sendPetPropsMsg(String petUUid){
		if(StringUtils.isEmpty(petUUid)){
			//发送全部
			for(Pet pet : petContainer.getPets()){
				pet.snapChangedProperty(true);
			}
		}else{
			Pet pet = petContainer.getPetByUuid(petUUid);
			pet.snapChangedProperty(true);
		}
	}
	
	@Override
	public void checkAfterRoleLoad() {
		activeAllPets();		
	}
	
	/**
	 * 初始化，进行离线经验计算
	 */
	public void init() {
//		Globals.getTrainingService().updateAllPetsTraining(owner);
		for (Pet pet : petContainer.getPets()) {
			pet.calcProps(false, false);
		}
	}
	
	@Override
	public void checkBeforeRoleEnter() {
	}

	
	private void activeAllPets() {
		for (Pet pet : petContainer.getPets()) {
			pet.onInit();
		}
	}
	
	public boolean isHiringLock() {
		return hiringLock;
	}
	
	public void setHiringLock(boolean hiringLock) {
		this.hiringLock = hiringLock;
	}

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
	public boolean containsPetSn(String sn){
		return petContainer.containsSn(sn);
	}
	
	
}
