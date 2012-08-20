package com.pwrd.war.robot.manager;

import com.pwrd.war.common.model.pet.PetInfo;
import com.pwrd.war.gameserver.pet.msg.GCPetList;
import com.pwrd.war.robot.Robot;

/**
 * 武将管理器
 * 
 * @author haijiang.jin
 *
 */
public class PetManager extends AbstractManager {
	/** 武将列表 */
	private PetInfo[] _petInfos;

	/**
	 * 类参数构造器
	 * 
	 * @param owner
	 */
	public PetManager(Robot owner) {
		super(owner);
	}

	/**
	 * 初始化
	 * 
	 * @param petListMsg 
	 * 
	 */
	public void init(GCPetList petListMsg) {
		this._petInfos = petListMsg.getPets();
	}

	/**
	 * 获取武将
	 * 
	 * @param index
	 * @return
	 */
	public PetInfo getPetByIndex(int index) {
		if (this._petInfos == null) {
			return null;
		}

		if (index < 0 || 
			index >= this._petInfos.length) {
			return null;
		}

		return this._petInfos[index];
	}

	/**
	 * 是否有武将
	 * 
	 * @return
	 */
	public boolean hasPet() {
		return this._petInfos != null 
			&& this._petInfos.length > 0;
	}

	/**
	 * 获取武将数量
	 * 
	 * @return
	 */
	public int getPetCount() {
		if (this._petInfos != null) {
			return this._petInfos.length;
		} else {
			return 0;
		}
	}
}
