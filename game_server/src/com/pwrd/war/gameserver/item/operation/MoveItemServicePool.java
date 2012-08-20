package com.pwrd.war.gameserver.item.operation;

import java.util.HashMap;
import java.util.Map;

import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.item.operation.impl.MoveEquipBag2ShoulderBag;
import com.pwrd.war.gameserver.item.operation.impl.MoveHumanEquipBag2ShoulderBag;
import com.pwrd.war.gameserver.item.operation.impl.MovePetEquipBag2ShoulderBag;
import com.pwrd.war.gameserver.item.operation.impl.MoveShoulderBag2HumanEquipBag;
import com.pwrd.war.gameserver.item.operation.impl.MoveShoulderBag2Itself;
import com.pwrd.war.gameserver.item.operation.impl.MoveShoulderBag2PetEquipBag;

/**
 * MoveItemService的对象池，用于根据源、目的包的id查询取得相应的MoveItemService，这些service对象都是公用的对象，单实例
 * 
 * @author liuli
 * @since 2010-6-7
 */
public enum MoveItemServicePool {

	instance;

	private final Map<BagPair, MoveItemOperation> serviceMap;

	private final Map<BagPair, UseEquipOperation> equipServiceMap;
	
	private MoveItemServicePool() {
		// FIXME 这里设计的太挫了，改成MoveItemOperation自己返回他使用的fromBag集合和toBag集合，在这里统一自动注册
		serviceMap = new HashMap<BagPair, MoveItemOperation>();
		equipServiceMap = new HashMap<BagPair, UseEquipOperation>();
		
		// 把每一个MoveItemService的实现new一个放到serviceMap中
		// 主包，材料，任务，仓库自己往自己里面移动，都用这个service
		serviceMap.put(new BagPair(BagType.PRIM, BagType.PRIM), new MoveShoulderBag2Itself());
		serviceMap.put(new BagPair(BagType.DEPOT, BagType.DEPOT), new MoveShoulderBag2Itself());
		serviceMap.put(new BagPair(BagType.DEPOT, BagType.PRIM), new MoveShoulderBag2Itself());
		serviceMap.put(new BagPair(BagType.PRIM, BagType.DEPOT), new MoveShoulderBag2Itself());
		
		//星魂背包移动
		serviceMap.put(new BagPair(BagType.XINGHUN, BagType.XINGHUN), new MoveShoulderBag2Itself());
		
		//从身上移动装备到主背包
		serviceMap.put(new BagPair(BagType.HUMAN_EQUIP, BagType.PRIM), new MoveEquipBag2ShoulderBag());
		serviceMap.put(new BagPair(BagType.PET_EQUIP, BagType.PRIM), new MoveEquipBag2ShoulderBag());
		
		
		equipServiceMap.put(new BagPair(BagType.PET_EQUIP, BagType.PRIM), new MovePetEquipBag2ShoulderBag());
		equipServiceMap.put(new BagPair(BagType.PRIM, BagType.PET_EQUIP), new MoveShoulderBag2PetEquipBag());		
		equipServiceMap.put(new BagPair(BagType.PRIM, BagType.HUMAN_EQUIP), new MoveShoulderBag2HumanEquipBag());
		equipServiceMap.put(new BagPair(BagType.HUMAN_EQUIP, BagType.PRIM), new MoveHumanEquipBag2ShoulderBag());
		
		
		
	
	}

	/**
	 * 取得一个MoveItemService对象
	 * 
	 * @param fromBag
	 *            来源包id
	 * @param toBag
	 *            目的包id
	 * @return 如果从fromBagId到toBagId]不可以移动，返回null
	 */
	public MoveItemOperation get(BagType fromBag, BagType toBag) {
		BagPair keyBagPair = new BagPair(fromBag, toBag);
		MoveItemOperation service = serviceMap.get(keyBagPair);
		return service;
	}

	public UseEquipOperation getEquipOperation(BagType fromBag, BagType toBag) {
		BagPair keyBagPair = new BagPair(fromBag, toBag);
		UseEquipOperation service = equipServiceMap.get(keyBagPair);
		return service;
	}

	
	private static class BagPair {

		public BagType fromBag;
		public BagType toBag;

		private BagPair(BagType fromBag, BagType toBag) {
			super();
			this.fromBag = fromBag;
			this.toBag = toBag;
		}

		@Override
		public int hashCode() {
			return (fromBag.index << Short.SIZE) | toBag.index;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BagPair other = (BagPair) obj;
			if (fromBag == null) {
				if (other.fromBag != null)
					return false;
			} else if (!fromBag.equals(other.fromBag))
				return false;
			if (toBag == null) {
				if (other.toBag != null)
					return false;
			} else if (!toBag.equals(other.toBag))
				return false;
			return true;
		}
	}
}
