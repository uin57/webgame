package com.pwrd.war.gameserver.command.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.pwrd.war.common.LogReasons.ItemGenLogReason;
import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.human.wealth.Bindable.Oper;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemParam;
import com.pwrd.war.gameserver.item.template.ItemTemplate;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

public class AddItemCmd implements IAdminCommand<ISession>{
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
			
			String itemSN = commands[0];
			int count = Integer.parseInt(commands[1]);
			//TODO
			List<ItemParam> params = new ArrayList<ItemParam>();

			ItemTemplate tmpl = Globals.getItemService().getTemplateByItemSn(itemSN);
			if (tmpl == null) {
				player.sendErrorMessage("该物品不存在！");
				return;
			}
			
			BindStatus bind = tmpl.getBindStatusAfterOper(BindStatus.NOT_BIND, Oper.GET);
			params.add(new ItemParam(tmpl.getItemSn(), count, bind));
			Collection<Item> ils = player.getHuman().getInventory().addAllItems(params,
					ItemGenLogReason.DEBUG_GIVE, "debug", true);
			System.out.println("增加装备" + itemSN + "!!!!!"+ count + "个啦!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			
			player.sendSystemMessage(CommonLangConstants_10000.GAME_INPUT_TOO_SHORT,
					player.getSystemPlayerMsgPart(player),
					player.getSystemItemMsgPart(ils.iterator().next().getTemplate()));
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_ADD_ITEM;
	}

}
