package com.pwrd.war.gameserver.buff.handler;

import java.util.Arrays;
import java.util.Comparator;

import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.common.LogReasons.MoneyLogReason;
import com.pwrd.war.gameserver.buff.enums.BuffBigType;
import com.pwrd.war.gameserver.buff.msg.CGHpBagMessage;
import com.pwrd.war.gameserver.buff.msg.CGNotHpBagMessage;
import com.pwrd.war.gameserver.buff.msg.CGRomoveBuffer;
import com.pwrd.war.gameserver.buff.msg.GCNotHpBagMessage;
import com.pwrd.war.gameserver.buff.template.BufferTemplate;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.currency.CurrencyCostType;
import com.pwrd.war.gameserver.form.BattleForm;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.template.ConsumeItemTemplate;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.role.Role;

public class BufferMessageHandler {

	private final int costMoney = 2;

	public void handleHpBagMessage(Player player, CGHpBagMessage message) {
		Human human = player.getHuman();
		Item[] items = human.getInventory().getAllHpBuff();
		if (items != null && items.length!=0) {
			// item进行排序，优先使用血量大得血气包
			Arrays.sort(items, new Comparator<Item>() {
				@Override
				public int compare(Item o1, Item o2) {
					ConsumeItemTemplate consumeItemTemplate1 = (ConsumeItemTemplate) o1
							.getTemplate();
					String bufferSn1 = consumeItemTemplate1.getParam1();
					BufferTemplate bufferTemplate1 = Globals
							.getTemplateService().get(
									Integer.parseInt(bufferSn1),
									BufferTemplate.class);
					ConsumeItemTemplate consumeItemTemplate2 = (ConsumeItemTemplate) o2
							.getTemplate();
					String bufferSn2 = consumeItemTemplate2.getParam1();
					BufferTemplate bufferTemplate2 = Globals
							.getTemplateService().get(
									Integer.parseInt(bufferSn2),
									BufferTemplate.class);
					return bufferTemplate1.getValue() > bufferTemplate2
							.getValue() ? 1
							: bufferTemplate1.getValue() < bufferTemplate2
									.getValue() ? -1 : 0;
				}
			});

			// 计算需要的总得血量
			BattleForm form = human.getDefaultForm();
			double needHp = 0;
			for (Role role : form.getAllBattlePets()) {
				needHp = role.getMaxHp() - role.getCurHp();
			}

			// 使用血气包给玩家充血
			for (Item item : items) {
				ConsumeItemTemplate consumeItemTemplate = (ConsumeItemTemplate) item
						.getTemplate();
				String bufferSn = consumeItemTemplate.getParam1();
				BufferTemplate bufferTemplate = Globals.getTemplateService()
						.get(Integer.parseInt(bufferSn), BufferTemplate.class);
				item.use(null, null);
				if (needHp <= bufferTemplate.getValue()) {
					break;
				} else {
					needHp -= bufferTemplate.getValue();
				}
			}
		}

	}

	public void handleNotHpBagMessage(Player player, CGNotHpBagMessage message) {
		GCNotHpBagMessage msg = new GCNotHpBagMessage();
		if (player.getHuman().hasEnoughGold(costMoney,
				false)) {
			player.getHuman().costGold(costMoney, false, null,
					CurrencyLogReason.GGZJ,
					CurrencyLogReason.GGZJ.getReasonText(), 0);
			msg.setResult(true);
		} else {
			msg.setResult(false);
		}
		player.sendMessage(msg);
	}

	public void handleRomoveBuffer(Player player, CGRomoveBuffer cgRomoveBuffer) {
		BuffBigType type = BuffBigType.getBufferTypeById(cgRomoveBuffer.getBigType());
		player.getHuman().removeBuff(type);
	}

}
