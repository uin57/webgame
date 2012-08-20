package com.pwrd.war.gameserver.command.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pwrd.war.common.LogReasons.ItemLogReason;
import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemParam;
import com.pwrd.war.gameserver.item.msg.GCBagUpdate;
import com.pwrd.war.gameserver.item.msg.ItemMessageBuilder;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

/**
 * 清空背包
 * 
 * @author haijiang.jin
 *
 */
public class ClearItemCmd implements IAdminCommand<ISession> {
	@Override
	public void execute(ISession sess, String[] commands) {
		if (!(sess instanceof GameClientSession)) {
			return;
		}

		Player player = ((GameClientSession)sess).getPlayer();
		
		if (player == null) {
			return;
		}

		// 获取当前玩家角色
		Human currHuman = player.getHuman();

		if (currHuman == null) {
			return;
		}
		
		Collection<Item> items = currHuman.getInventory().getAllPrimBagItems();
		List<ItemParam> tmp = new ArrayList<ItemParam>();
		for (Item item : items) {
			tmp.add(new ItemParam(item.getTemplate().getItemSn(), item
					.getOverlap(), BindStatus.BIND_YET));
		}
//		currHuman.getInventory().removeItemsIgnoreBind(tmp,
//				ItemLogReason.DEBUG_REMOVE_ALL_ITEM, "");
		
		GCBagUpdate gcBagUpdate = ItemMessageBuilder.buildGCBagUpdate(currHuman.getInventory().getPrimBag());
		currHuman.sendMessage(gcBagUpdate);
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_CLEAR_ITEM;
	}

}
