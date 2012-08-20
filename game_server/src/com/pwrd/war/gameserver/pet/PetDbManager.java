package com.pwrd.war.gameserver.pet;

import java.util.List;

import com.pwrd.war.common.constants.CommonErrorLogInfo;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.orm.DataAccessException;
import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.db.dao.PetDao;
import com.pwrd.war.db.model.PetEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.operation.SaveObjectOperation;



/**
 * 武将操作数据库的管理器
 * 实现了各种业务操作接口
 * @author yue.yan
 *
 */
public class PetDbManager {

	/** 武将数据库操作管理 */
	private static PetDbManager petDbManager = new PetDbManager();

	private PetDbManager() {

	}
	
	/**
	 * 获取武将数据库操作管理类
	 * 
	 * @return
	 */
	public static PetDbManager getInstance() {
		return petDbManager;
	}
	
	/**
	 * 从数据库中读取武将
	 * @param petUUID
	 * @return
	 */
	public PetEntity loadPetFromDB(String petUUID) {
		PetEntity petEntity = null;
		try {
			petEntity = getPetDao().get(petUUID);
		} catch (DataAccessException e) {
			if (Loggers.petLogger.isErrorEnabled()) {
				Loggers.petLogger.error(ErrorsUtil.error(
						CommonErrorLogInfo.DB_OPERATE_FAIL,
						"#GS.PetDbManager.loadPetFromDB", null), e);
			}
		}
		return petEntity;
	}
	
	/**
	 * 将玩家的所有武将数据从数据库中读取
	 * @param petContainer
	 */
	public void loadAllPetFromDB(PetContainer petContainer) {
		String _charId = petContainer.getOwner().getCharId();
		List<PetEntity> _pets = null;
		try {
			_pets = getPetDao().getPetsByCharId(_charId);
			for (PetEntity _petInfo : _pets) {
								
				Pet _pet = new Pet(_petInfo.getPetSn());
//				_pet.init();
				
				_pet.setOwner(petContainer.getOwner());
				_pet.fromEntity(_petInfo);
				_pet.setPetSn(_petInfo.getPetSn());
				 
				// 添加到武将列表
				petContainer.addPet(_pet);
			}
		} catch (DataAccessException e) {
			if (Loggers.petLogger.isErrorEnabled()) {
				Loggers.petLogger.error(ErrorsUtil.error(
						CommonErrorLogInfo.DB_OPERATE_FAIL,
						"#GS.PetDbManager.loadAllPetFromDB", null), e);
			}
			return;
		}
	}
	
	/**
	 * 保存武将
	 * @param pet
	 * @param async 为true则异步保存，为false则同步保存
	 */
	public void savePet(Pet pet, boolean async) {
		IIoOperation _oper = new SaveObjectOperation<PetEntity, Pet>(pet,
				getPetDao());
		if (async) {
			Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
		} else {
			Globals.getAsyncService()
					.createSyncOperationAndExecuteAtOnce(_oper);
		}
	}
	
	/**
	 * 从数据库与删除武将
	 * @author xf
	 */
	public void deletePet(Pet pet){
		PetDao dao = this.getPetDao();
		dao.delete(pet.toEntity());
	}
	
	/**
	 * 取得武将的Dao实例
	 * 
	 * @return
	 */
	private PetDao getPetDao() {
		return Globals.getDaoService().getPetDao();
	}
}
