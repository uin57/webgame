package com.pwrd.war.gameserver.human.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pwrd.war.db.model.UserArchiveEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.msg.GCMessage;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.domain.Archive;
import com.pwrd.war.gameserver.human.msg.GCArchiveList;

public class ArchiveManager {
	private Human human;
	private Map<Archive.ArchiveType, Archive> archives;
	
	public ArchiveManager(Human human){
		this.human = human;
		archives = new HashMap<Archive.ArchiveType, Archive>();
	}
	
	public void init(){
		List<UserArchiveEntity> list = Globals.getDaoService().getUserArchiveEntityDAO().getByHuman(human.getUUID());
		for(UserArchiveEntity e : list){
			Archive a = new Archive();
			a.setType(e.getType());
			a.setTitle(e.getTitle());
			a.setNumber(e.getNumber());
			a.setUuid(e.getId());
			a.setHumanUUID(human.getUUID());
			archives.put(a.getEnumType(), a);
		}
	}
	
	/**
	 * 增加成就数量
	 * @author xf
	 */
	public Archive addArchiveNumber(Archive.ArchiveType type, int num){
		Archive arch = archives.get(type);
		if(arch == null){
			arch = new Archive();
			arch.setType(type);
			arch.setNumber(num);
			arch.setHumanUUID(human.getUUID());
			archives.put(type, arch);
			
			Globals.getDaoService().getUserArchiveEntityDAO().save(arch.toEntity());
		}else{
			arch.setNumber(arch.getNumber() + num);
			Globals.getDaoService().getUserArchiveEntityDAO().saveOrUpdate(arch.toEntity());
		}
		return arch;
	}
	
	/**
	 * 取得所有成就的消息
	 * @author xf
	 */
	public GCMessage getAllArchives(){
		GCArchiveList msg = new GCArchiveList();
		msg.setProperties(this.archives.values().toArray(new Archive[0]));
		return msg;
	}
}
