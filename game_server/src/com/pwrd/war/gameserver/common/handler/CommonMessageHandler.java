package com.pwrd.war.gameserver.common.handler;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.msg.CGPing;
import com.pwrd.war.gameserver.common.msg.CGSelectOption;
import com.pwrd.war.gameserver.common.msg.GCPing;
import com.pwrd.war.gameserver.player.IStatefulHandler;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.StatefulHolder;


/**
 * 各模块通用消息处理器
 * 
 * @author jiliang.lu
 * @since 2010.12.16
 * 
 */
public class CommonMessageHandler {
	/**
	 * 处理 ping 消息
	 * 
	 * @param player
	 * @param cgPing
	 */
	public void handlePing(Player player, CGPing cgPing) {
		GCPing msg = new GCPing(Globals.getTimeService().now());
		player.sendMessage(msg);
	}

	/**
	 * 处理确认框选择消息
	 * 
	 * @param player
	 * @param cgSelectOption
	 */
	public void handleSelectOption(Player player, CGSelectOption cgSelectOption) {
		if (player == null) {
			return;
		}

		StatefulHolder holder = player.getStatefulHolder();

		// 获取状态处理器
		IStatefulHandler handler = holder.getHandler();
		// 获取状态标识
		String tag = holder.getTag();
		
		// 取得保存的数据后清空
		player.getStatefulHolder().clear();
		
		if ((tag != null) && 
			(tag.equals(cgSelectOption.getTag()))) {
			if (handler != null) {
				handler.exec(player, cgSelectOption.getValue());
			}
		}
	}
}
