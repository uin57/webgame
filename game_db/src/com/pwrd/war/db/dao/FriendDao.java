package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.FriendEntity;

public class FriendDao extends BaseDao<FriendEntity> {

	public FriendDao(DBService dbService) {
		super(dbService); 
	}

	@Override
	protected Class<FriendEntity> getEntityClass() { 
		return FriendEntity.class;
	}
	@SuppressWarnings("unchecked")
	public List<FriendEntity> getFriendsCharId(String characterId) {
		return this.dbService.findByNamedQueryAndNamedParam("findAllFriends", new String[]{"playerUUID"},
				new Object[] { characterId });
	}
	@SuppressWarnings("unchecked")
	public FriendEntity getFriend(String myId, String friendId){
		List<FriendEntity> list = this.dbService.findByNamedQueryAndNamedParam("findAFriend", new String[]{"playerUUID", "friendUUID"},
				new Object[] { myId, friendId });
		if(list.size() == 0){
			return null;
		}
		return list.get(0);
	}

}
