package com.pwrd.war.gameserver.pet.async;

import com.pwrd.war.db.model.PetEntity;
import com.pwrd.war.gameserver.common.db.operation.BindUUIDIoOperation;
import com.pwrd.war.gameserver.pet.PetDbManager;

/**
 * 从数据库中加载武将，然后进行后续操作（回调）
 */
public class LoadPetAndDoOperation implements BindUUIDIoOperation  {

	private String roleUUID;
	private String petUUID;
	private PetEntity petEntity;
	private ILoadPetAndDoOperationCallback callback;
	
	public LoadPetAndDoOperation(String roleUUID, String petUUID, ILoadPetAndDoOperationCallback callback) {
		this.roleUUID = roleUUID;
		this.petUUID = petUUID;
		this.callback = callback;
	}
	
	@Override
	public String getBindUUID() {
		return roleUUID;
	}
	
	@Override
	public int doStart() {
		return STAGE_START_DONE;
	}

	@Override
	public int doIo() {
		petEntity = PetDbManager.getInstance().loadPetFromDB(petUUID);
		return STAGE_IO_DONE;
	}

	@Override
	public int doStop() {
		if(petEntity != null) {
			callback.callback(petEntity);
		}
		return STAGE_STOP_DONE;
	}
	
	/**
	 * 进行操作然后回调的接口
	 * @author yue.yan
	 *
	 */
	public static interface ILoadPetAndDoOperationCallback {
		public void callback(PetEntity petEntity);
	}

}
