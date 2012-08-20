package com.pwrd.war.gameserver.pet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pwrd.war.gameserver.human.Human;

/**
 * 武将对象的容器
 * 
 * 	此类仅实现纯粹的数据操作
 * 
 *  {@code PetManager} 进行业务处理
 *  
 *
 */
public class PetContainer {

	/** 玩家当前队伍中所有武将列表,key为武将sn */
	private Map<String, Pet> petsn;
	
	/** 玩家当前队伍中所有武将列表,key为武将petuuid **/
	private Map<String, Pet> petsUUID;
 
	/** 所属玩家 */
	private Human owner;
	
	/** 武将数量上限 */
	private int maxPetCount;
	
	public PetContainer(Human human) {
		this.owner = human;
		petsn = new HashMap<String, Pet>();
		petsUUID = new HashMap<String, Pet>();
	}

	public Human getOwner() {
		return owner;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
	}

	/**
	 * 增加一个武将
	 * @param pet
	 * @return
	 */
	public boolean addPet(Pet pet) {
		petsn.put(pet.getTemplate().getPetSn(), pet);
		petsUUID.put(pet.getUUID(), pet);
		return true;
	}
	
	/**
	 * 删除一个武将
	 * @param pet
	 * @return
	 */
	public boolean remove(Pet pet) {
		petsn.remove(pet.getTemplate().getPetSn());
		petsUUID.remove(pet.getUUID());
		return true;
	}
	
	/**
	 * 根据uuid获得武将实例
	 * @param uuid
	 * @return
	 */
	public Pet getPetByUuid(String uuid) {
		Pet pet = petsUUID.get(uuid);
 
		return pet;
	}
 
	/**
	 * 根据武将姓名取武将
	 * @author xf
	 */
	public Pet getPetByName(String name){
		for(Pet pet : petsUUID.values()) { 
			if(pet.getName().equals(name)){
				return pet;
			}
		}
		return null;
	}
	/**
	 * 获取当前武将数量
	 * 
	 * @return
	 */
	public int getPetCount() {
		return petsUUID.size();
	}
	
	/**
	 * 获取最大武将数量
	 * @return
	 */
	public int getMaxPetCount() {
		return maxPetCount;
	}

	/**
	 * 设置最大武将数量
	 * @param maxPetCount
	 */
	public void setMaxPetCount(int maxPetCount) {
		this.maxPetCount = maxPetCount;
	}

	/**
	 * 获取所有武将
	 * 
	 * @return
	 */
	public List<Pet> getPets() {
		List<Pet> list = new ArrayList<Pet>();
		list.addAll(petsUUID.values());
		return Collections.unmodifiableList(list);
	}

	public boolean containsSn(String sn){
		return petsn.containsKey(sn);
	}
	
}
