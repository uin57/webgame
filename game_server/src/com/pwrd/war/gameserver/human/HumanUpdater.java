package com.pwrd.war.gameserver.human;

import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.POUpdater;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.PlayerConstants;
import com.pwrd.war.gameserver.player.async.SavePlayerRoleOperation;

/**
 * 玩家角色基本信息更新器
 * 
 * @see Human#setModified()
 */
public class HumanUpdater implements POUpdater {
	@Override
	public void save(PersistanceObject<?,?>  obj) {
		final Human human = (Human) obj;
		if (human == null) {
			return;
		}
		Player player = human.getPlayer();
		if (player == null) {
			return;
		}
		SavePlayerRoleOperation saveTask = new SavePlayerRoleOperation(player,
				PlayerConstants.CHARACTER_INFO_MASK_BASE, false);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(saveTask);

	}

	@Override
	public void delete(PersistanceObject<?,?>  obj) {
		throw new UnsupportedOperationException();
	}
}
