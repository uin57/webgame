package com.pwrd.war.db.model.msg;

import com.pwrd.war.core.msg.BaseEntityMsg;
import com.pwrd.war.core.msg.EntityType;
import com.pwrd.war.core.msg.EntityType.EntityCreator;
import com.pwrd.war.db.model.ItemEntity;

public class EntityTypeFactory {
	
	public static final EntityType<ItemEntity> ITEMINFO = new EntityType<ItemEntity>(
			ItemEntity.class, 
			EntityMessageType.ItemEntityMsg,
			new EntityCreator<ItemEntity>() {
				@Override
				public BaseEntityMsg<ItemEntity> createEntityMessage() {
					return new ItemEntityMsg();
				}

				@Override
				public ItemEntity createEntity() {
					return new ItemEntity();
				}
			}
	);
	
	

}
