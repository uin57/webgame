package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

/**
 * 设置已终止取消物品的剩余解绑时间
 * 命令格式:!setfreezetime <包裹索引> <时间(以分钟计)>
 * @author fanghua.cui
 * @since 2011-6-17
 */
public class SetItemFreezeTimeCmd implements IAdminCommand<ISession> {

	@Override
	public void execute(ISession playerSession, String[] commands) {
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		System.out.println(Arrays.toString(commands));
		try {
			if(commands.length < 2)
			{
				return;
			}
			
			int index = new Integer(commands[0]);
			
//			Item toSellItem = player.getHuman().getInventory().getItemByIndex(BagType.PRIM, 0l, index);
			Item toSellItem = player.getHuman().getInventory().getItemByIndex(BagType.PRIM, "", index);
			
			if(toSellItem == null){
				return;
			}
			
			int freezeTime = Integer.parseInt(commands[1]);

			ItemHandlerFactory.getHandler().handleItemCancelFreeze(player,toSellItem.getUUID(),TimeUtils.getAfterMinutes(freezeTime));
			
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_SET_FREEZETIME;
	}

}
