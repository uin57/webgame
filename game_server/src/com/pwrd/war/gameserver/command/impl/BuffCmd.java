package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;
import java.util.Map;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.buff.domain.Buffer;
import com.pwrd.war.gameserver.buff.enums.BuffBigType;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

public class BuffCmd  implements IAdminCommand<ISession>{
	@Override
	public void execute(ISession playerSession, String[] commands) {
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		System.out.println(Arrays.toString(commands));
		try {
			String cmd = commands[0];
			String buffId = null;
			if(commands.length > 1){
				buffId = commands[1];
			}
			if("add".equals(cmd)){
				boolean rs = player.getHuman().addBuffer(buffId);
				if(!rs){
					player.sendErrorMessage("增加BUFF失败");
				}
			}else if("del".equals(cmd)){
				player.getHuman().removeBuff(BuffBigType.getBufferTypeById(Integer.valueOf(buffId)));
			}else if("list".equals(cmd)){
				Map<BuffBigType, Buffer> buffers = player.getHuman().getBuffers();
				String s = "当前BUFF:";
				for(Buffer b : buffers.values()){
					s = s + b.getBigType()+","+b.getBufferName()+"\r\n";
				}
				player.sendSystemMessage(s);
			}
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return "buff";
	}
}
