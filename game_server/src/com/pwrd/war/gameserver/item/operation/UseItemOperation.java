package com.pwrd.war.gameserver.item.operation;

import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemDef;
import com.pwrd.war.gameserver.item.ItemUseInfo;
import com.pwrd.war.gameserver.item.msg.CGUseItem;
import com.pwrd.war.gameserver.role.Role;

/**
 * 
 * 使用道具的策略接口，实现此接口的对象应该无状态可以被公用
 * 
 * 
 */
public interface UseItemOperation<USER extends Role> {

	/**
	 * 此service是否适合处理user向target使用item<br />
	 * 一般是一个{@link ItemDef.ConsumableFunc}
	 * 对应一个operation，如果这个规律不变，可以采用效率更高的表驱动方式查询使用道具的operation
	 * 
	 * @param user
	 * @param item
	 * @param target
	 * @return 如果可以用此service处理，返回true，否则返回false
	 */
	boolean isSuitable(USER user, Item item, Role target);

	/**
	 * 检查是否可以使用
	 * 
	 * @param user
	 * @param item
	 * @param target
	 * @return
	 */
	boolean canUse(USER user, Item item, Role target);

	/**
	 * 执行扣减，扣减后item可能被销毁，因此需要先用{@link #collectItemInfo(Item)}取得属性后再执行扣减
	 * 
	 * @param user
	 * @param item
	 * @param target
	 * @return 扣减是否成功
	 */
	boolean consume(USER user, Item item, Role target);

	/**
	 * 执行一次使用操作，在使用道具时，如果是一次性使用完的，只调用一次此方法，如果是引导使用的，会调用多次，调用的次数和时间间隔有道具的属性指定
	 * 
	 * @param user
	 *            使用者
	 * @param itemInfo
	 *            在扣减前收集的道具信息，道具的使用效果等信息需要在这个参数中获得
	 * @param target
	 * @return 如果使用成功返回true，否则返回false
	 */
	boolean useOnce(USER user, ItemUseInfo itemInfo, Role target);

	/**
	 * 收集此operation感兴趣的道具属性和对应的属性值{@link ItemUseInfo}，这些属性由调用者在使用
	 * {@link #useOnce(Human, ItemUseInfo, Role)}时传入。<br/>
	 * 采用这种方式是因为需要先执行扣减再应用道具效果，当扣减销毁掉item时，则需要备份与效果密切相关的属性及其值
	 * 
	 * @param item
	 *            被使用的item
	 * @return
	 */
	ItemUseInfo collectItemUseInfo(Item item);

	/**
	 * 在不同的operation里面对应的target可能是不同的，有的是专门对角色本身使用的，也可能是对其它目标使用的，<br/>
	 * 消息里面传递过来的是玩家当前所选择的目标，并不一定是物品使用的目标<br/>
	 * 如：使用生命池，目标对象一定是玩家自己，不需要根据消息里面的信息解析目标
	 * 
	 * @param user
	 * @param msg
	 * @return
	 */
	Role getTarget(USER user, String targetUUID, Role target);
}
