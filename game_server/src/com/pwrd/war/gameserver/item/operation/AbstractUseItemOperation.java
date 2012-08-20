package com.pwrd.war.gameserver.item.operation;

import com.pwrd.war.common.LogReasons.ItemLogReason;
import com.pwrd.war.gameserver.common.i18n.constants.ItemLangConstants_20000;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemDef;
import com.pwrd.war.gameserver.item.ItemDef.IdentityType;
import com.pwrd.war.gameserver.item.ItemUseInfo;
import com.pwrd.war.gameserver.item.template.ConsumeItemTemplate;
import com.pwrd.war.gameserver.item.template.ItemTemplate;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.role.Role;

/**
 * UseItemOperation的骨架实现
 * 
 * 
 */
public abstract class AbstractUseItemOperation implements UseItemOperation<Human> {

	@Override
	public abstract boolean isSuitable(Human user, Item item, Role target);
	
	@Override
	public abstract ItemUseInfo collectItemUseInfo(Item item);

	/**
	 * 是否可以执行使用道具的操作，即是否满足所有使用此道具的条件，此方法返回true时调用use方法应该使用成功
	 * 
	 * @param user
	 *            使用者
	 * @param item
	 *            使用的道具
	 * @param target
	 *            使用目标
	 * @return
	 */
	@Override
	public final boolean canUse(Human user, Item item, Role target) {
		// 空道具不能使用
		if (Item.isEmpty(item)) {
			return false;
		}
		// 本Operation处理不了的使用请求一定返回false
		if (!isSuitable(user, item, target)) {
			return false;
		}
		
		
		return canUseImpl(user, item, target);
	}
	
	@Override
	public final boolean useOnce(Human user, ItemUseInfo itemInfo, Role target) {
		// 这里不再检查一遍使用条件了，调用者需要在调用前用canUse检查条件
		return useImpl(user, itemInfo, target);
	}



	/**
	 * 子类实现使用条件的判断，等级、职业、性别、锁定、过期等道具使用的一般条件不需要在这里检查，这里只判断使用此类功能道具的特殊条件
	 * 
	 * 
	 * @return
	 */
	protected abstract boolean canUseImpl(Human user, Item item, Role target);



	/**
	 * 子类实现具体的使用操作
	 * 
	 * @param user
	 *            使用者
	 * @param item
	 *            被使用的item的引用，但调用此方法时已经执行完扣减，因此item可能为空，其关键属性可能已经清楚掉了
	 * @param itemInfo
	 *            在扣减前收集的道具信息，道具的使用效果等信息需要在这个参数中获得
	 * @param target
	 *            目标
	 * @return 如果使用成功返回true，否则返回false
	 */
	protected abstract boolean useImpl(Human user, ItemUseInfo itemInfo, Role target);

	/**
	 * 检查道具使用的一般条件
	 * 
	 * @param user
	 * @param item
	 * @return
	 */
	public static boolean checkCommonConditions(Human user, Item item) {
		if (Item.isEmpty(item)) {
			return false;
		}
		if (item.getOwner() == null || item.getOwner() != user) {
			// 没有owner或者不是自己的道具不能使用
			return false;
		}
		// 锁定了不能用
//		if (item.isLocked()) {
//			return false;
//		}
		if (!item.getTemplate().getCanBeUsed()) {
			return false;
		}
		// 查看此道具是否处于可用状态
		if (!item.checkAvailable()) {
			// 此道具当前不可用
			user.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.ITEM_NOT_AVAILABLE);
			return false;
		}
		// 下面几个条件为可选条件，如果为0则不检查对应的条件
		// 检查等级
		if (item.getTemplate().getNeedLevel() != 0 && user.getLevel() < item.getTemplate().getNeedLevel()) {
			user.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.ITEM_USEFAIL_LEVEL);
			return false;
		}
//		// 检查阵营
//		if (item.getAlliance() != 0 && (user.getAllianceId() & item.getAlliance()) == 0) {
//			user.sendErrorMessage(LangConstants.ITEM_USEFAIL_JOB);
//			return false;
//		}
		return true;
	}
	
	/**
	 * 检查道具使用的一般条件
	 * 
	 * @param user
	 * @param item
	 * @return
	 */
	public static boolean checkCommonConditions(Pet user, Item item) {
		if (Item.isEmpty(item)) {
			return false;
		}
		if (item.getOwner() == null || item.getOwner() != user.getOwner()) {
			// 没有owner或者不是自己的道具不能使用
			return false;
		}
		// 锁定了不能用
//		if (item.isLocked()) {
//			return false;
//		}
		if (!item.getTemplate().getCanBeUsed()) {
			return false;
		}
		// 查看此道具是否处于可用状态
		if (!item.checkAvailable()) {
			// 此道具当前不可用
			user.getOwner().getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.ITEM_NOT_AVAILABLE);
			return false;
		}
		
		// 下面几个条件为可选条件，如果为0则不检查对应的条件
		// 检查等级
//		if (item.getLevel() != 0 && user.getLevel() < item.getLevel()) {
//			user.getOwner().sendErrorMessage(LangConstants.ITEM_USEFAIL_LEVEL);
//			return false;
//		}
//		// 检查阵营
//		if (item.getAlliance() != 0 && (user.getAllianceId() & item.getAlliance()) == 0) {
//			user.getOwner().sendErrorMessage(LangConstants.ITEM_USEFAIL_JOB);
//			return false;
//		}
		return true;
	}
	
	/**
	 * 扣减一个的默认操作，一般情况下使用消耗品都是这种形式
	 * 
	 * @param item
	 * @return
	 */
	protected static boolean consumeOne(Human user, Item item) {
		if (Item.isEmpty(item)) {
			return false;
		}
		int left = item.getOverlap() - 1;
		if (left < 0) {
			left = 0;
		}
		// 执行扣减
		item.changeOverlap(left, ItemLogReason.USED, "", "", true);
		// 发送消息
		user.sendMessage(item.getUpdateMsgAndResetModify());
		return true;
	}

	/**
	 * 由于某些物品是根据耐久做的，如生命，法力等，<Br/>
	 * 所以当耐久为0时就需要将这些东西清掉，请勿乱用，<br/>
	 * 一般情况下使用ocnsumeOne即可
	 * 
	 * @param user
	 *            玩家
	 * @param item
	 *            物品
	 * @param endure
	 *            要消耗的耐久
	 * @return
	 */
	protected static boolean consumeAmount(Human user, Item item, int endure) {
		if (Item.isEmpty(item)) {
			return false;
		}

//		if (item.getCurEndure() > 0) {
//			ItemFeature feature = item.getFeature();
//			if (feature == null) {
//				return false;
//			}
//
//			int after = item.getCurEndure() - endure;
//			after = after < 0 ? 0 : after;
//			feature.setCurEndure(after);
//
//			if (after == 0) {
//				int left = item.getOverlap() - 1;
//				if (left < 0) {
//					left = 0;
//				}
//				// 执行扣减
//				item.changeOverlap(left, ItemLogReason.USED, "", "", true);
//				// 发送消息
//			}
//			user.sendMessage(item.getUpdateMsgAndResetModify());
//		}
		return true;
	}

	/**
	 * 只收集消耗品道具模板，一般情况下使用消耗品只需要知道他的函数功能和参数即可
	 * 
	 * @param item
	 * @return
	 */
	protected static ItemUseInfo collectItemAndTemplate(Item item) {
		ItemUseInfo info = new ItemUseInfo();
		info.setItemToUse(item);
		info.setTemplate( item.getTemplate());
		collectIdInfo(item, info);
		return info;
	}

	/**
	 * 只收集道具本身
	 * 
	 * @param item
	 * @return
	 */
	protected static ItemUseInfo collectItemOnly(Item item) {
		ItemUseInfo info = new ItemUseInfo();
		info.setItemToUse(item); 
		collectIdInfo(item, info);
		return info;
	}

	/**
	 * 收集道具模板及物品耐久
	 * 
	 * @param item
	 * @return
	 */
	protected static ItemUseInfo collectItemTmpAndEndure(Item item) {
		ItemUseInfo info = collectItemAndTemplate(item);
//		info.setEndure(item.getCurEndure());
//		collectIdInfo(item, info);
		return info;
	}

	/**
	 * 收集道具验证信息，用于实现吟唱时的第二部校验
	 * 
	 * @param item
	 * @param info
	 */
	private static void collectIdInfo(Item item, ItemUseInfo info) {
		info.setBag(item.getBagType());
		info.setIndex(item.getIndex());
		info.setUuid(item.getUUID());
	}

	/**
	 * 检查一个道具是否具有指定的消耗品功能
	 * 
	 * @param item
	 * @param func
	 * @return
	 */
	protected static boolean hasConsumableFunc(Item item,
			ItemDef.ConsumableFunc func) {
		ItemTemplate itemTmpl = item.getTemplate();
		if (itemTmpl.getIdendityType() != ItemDef.IdentityType.CONSUMABLE) {
			return false;
		}
		if (itemTmpl.getIdendityType() == IdentityType.CONSUMABLE) {
			ConsumeItemTemplate template = (ConsumeItemTemplate) itemTmpl;
			if (template.getFunction() == func) {
				return true;
			}
		}
		return false;
	}

	

}
