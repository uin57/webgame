package com.pwrd.war.gameserver.common.container;

import com.pwrd.war.gameserver.common.container.Bag.BagType;


/**
 * 可以放在Container中的物品，同类型的可以叠放在一起，但有堆叠上限，占据Container的一个index
 * 
 * 
 */
public interface Containable {
	/**
	 * 所属容器类型
	 * 
	 * @return 如果还没分配给某容器，返回{@link BagType#NULL}
	 */
	BagType getBagType();

	/**
	 * 在所属容器中的索引
	 * 
	 * @return 如果还没分配给某容器，返回-1
	 */
	int getIndex();

	/**
	 * 堆叠数量
	 * 
	 * @return
	 */
	int getOverlap();

	/**
	 * 最大堆叠数量
	 * 
	 * @return
	 */
	int getMaxOverlap();
	
}
