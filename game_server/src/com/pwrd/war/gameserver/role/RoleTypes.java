package com.pwrd.war.gameserver.role;

/**
 * 角色类型定义
 * 
 */
public interface RoleTypes {
	
	short HUMAN = 0x1;	//角色
	short PET = 0x10;		//武将
	short MONSTER = 0x100;	//怪物
	short OTHER_HUMAN=0x2;//其它玩家主角
	short OTHER_PET=0x4;//其它玩家武将
	short MASHINE = 0x8;	//战斗机械
	
	
}
