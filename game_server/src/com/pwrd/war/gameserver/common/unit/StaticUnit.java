package com.pwrd.war.gameserver.common.unit;

import java.util.List;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.gameserver.player.Player;

public abstract class StaticUnit extends SceneUnit{
	
	/** 在场景中是否可见，默认为可见的 */
	protected boolean visible;
	
	protected boolean mapVisible;

	public StaticUnit() {
		this.visible = true;
		this.mapVisible = true;
	}
	
	/**
	 * 设置是否可见，此方法只设置属性，并没有给客户端进行广播。如有需要请调用{@link #hide()}或者{@link #show()}。
	 * 
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean getVisible() {
		return visible;
	}

	/**
	 * 设置是否在场景地图上可见，此方法只设置属性，并没有给客户端进行广播。现在还没有提供这样的方法，因为没这种需求。
	 * 
	 * @param visible
	 */
	protected void setMapVisible(boolean mapVisible) {
		this.mapVisible = mapVisible;
	}
	
	public boolean getMapVisible() {
		return mapVisible;
	}

	/**
	 * 当场景内静态元素被玩家点击时调用
	 * 
	 * @param player
	 * 
	 */
	public void onClick(Player player) {
		if (visible) {
			this.doOnClick(player);
		} else {
			Loggers.playerLogger.warn(LogUtils.buildLogInfoStr(player
					.getRoleUUID(), name
					+ " is not visible and can't be clicked!"));
		}
	}
	
	@Override
	public void heartBeat(){
	}

	/**
	 * 由子类实现被点击时具体的业务逻辑
	 * 
	 * @param player
	 */
	public abstract void doOnClick(Player player);

	/**
	 * 获得场景内静态单元的类型
	 * 
	 * @return
	 */
	public abstract UnitType getUnitType();
	
	/**
	 * 在场景中显示，会自动向客户端发送消息
	 */
	public void show() {
		this.visible = true;
		noticeVisibleChanged();
	}

	/**
	 * 在场景中消失，会自动向客户端发送消息
	 */
	public void hide() {
		this.visible = false;
		noticeVisibleChanged();
	}

	/**
	 * 通知客户端该表元素状态
	 */
	private void noticeVisibleChanged() {
		
	}

	/**
	 * 场景内静态元素类型定义
	 * 
	 */
	public static enum UnitType implements IndexedEnum {
		NULL(0), NPC(1), Teleporter(2), Itembox(3), NatureResource(4);

		public final int index;

		@Override
		public int getIndex() {
			return index;
		}

		UnitType(int index) {
			this.index = index;
		}

		private static final List<UnitType> values = IndexedEnumUtil
				.toIndexes(UnitType.values());

		public static UnitType valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}
	}

}
