package com.pwrd.war.gameserver.pet;

import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.gameserver.common.db.POUpdater;
import com.pwrd.war.gameserver.player.Player;

/**
 * 武将基本信息更新器
 * 
 * @author yue.yan
 *
 */
public class PetUpdater implements POUpdater  {

	@Override
	public void save(PersistanceObject<?, ?> obj) {
		//做一些判断，避免意外
		final Pet pet = (Pet)obj;
		if(pet == null || pet.getOwner() == null) {
			return;
		}
		Player player = pet.getOwner().getPlayer();
		if(player == null) {
			return;
		}
		//异步存库
		PetDbManager.getInstance().savePet(pet, true);
	}
	
	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		PetDbManager.getInstance().deletePet((Pet)obj);
//		//暂不支持删除武将
//		throw new UnsupportedOperationException();
	}

}
