package com.pwrd.war.gameserver.telnet.command;

import java.util.List;
import java.util.Map;

import org.apache.mina.common.IoSession;

import com.pwrd.war.db.dao.HumanDao;
import com.pwrd.war.db.model.HumanEntity;
import com.pwrd.war.gameserver.common.Globals;

/**
 * 发礼包
 * 
 */
public class GiveLibaoCommand extends LoginedTelnetCommand {
	public GiveLibaoCommand() {
		super("GIVE_LIBAO");
	}

	@Override
	protected void doExec(String command, Map<String, String> params, IoSession session) {
		System.out.println(params);
		try {
			for(Map.Entry<String, String>  p : params.entrySet()){
				String key = p.getKey();
				String value = p.getValue();
				if(".".equals(value)){
					//给所有人加key
					//从数据库去所有人
					HumanDao dao = Globals.getDaoService().getHumanDao();
					List<HumanEntity> list = dao.queryAllHuman();
					for(HumanEntity e : list){
						Globals.getGiftBagService().addGiftBag(e.getId(), key);
					}
				}else{
					//给key加value
					Globals.getGiftBagService().addGiftBag(key, value);
				}
			}
		} catch (Exception e) { 
			e.printStackTrace();
			this.sendMessage(session, "result=error");
			return;
		}

		this.sendMessage(session, "ok");
	}
}
