package com.pwrd.war.gameserver.item.operation;

import java.util.ArrayList;
import java.util.List;

import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.operation.impl.AddBuff;
import com.pwrd.war.gameserver.item.operation.impl.AddHp;
import com.pwrd.war.gameserver.item.operation.impl.AddVipBuff;
import com.pwrd.war.gameserver.item.operation.impl.AddVit;
import com.pwrd.war.gameserver.item.operation.impl.UseChangeBody;
import com.pwrd.war.gameserver.item.operation.impl.UseEquip;
import com.pwrd.war.gameserver.item.operation.impl.UseItemBag;
import com.pwrd.war.gameserver.item.operation.impl.UsePetCard;
import com.pwrd.war.gameserver.item.operation.impl.UseReel;
import com.pwrd.war.gameserver.role.Role;

/**
 * 道具使用支持类，单实例，用于为UseItemAction提供合适的UseItemOperation
 * 
 * 
 */
public enum UseItemOperPool {

	instance;

	private final List<UseItemOperation<Human>> operations;

	private UseItemOperPool() {
		// 将实现了的service都在这里加入services，把最常用的放在上面，提高查找效率
		this.operations = new ArrayList<UseItemOperation<Human>>();		
		this.operations.add(new AddBuff());
//		this.operations.add(new UseAsMove());
		this.operations.add(new UseEquip());
		this.operations.add(new AddHp());
		this.operations.add(new AddVit());
		this.operations.add(new UsePetCard());
		this.operations.add(new UseReel());
		this.operations.add(new UseChangeBody());
		this.operations.add(new UseItemBag());
		this.operations.add(new AddVipBuff());
	}

	/**
	 * 获取所有合适使用关系[user,item,target]的UseItemOperation，一个道具只能对应一个Operation
	 * 
	 * @return
	 */
	public UseItemOperation<Human> getSuitableOperation(Human user, Item item,	Role target) {
		for (UseItemOperation<Human> operation : operations) {
			if (operation.isSuitable(user, item, target)) {
				return operation;
			}
		}
		return null;
	}
}
