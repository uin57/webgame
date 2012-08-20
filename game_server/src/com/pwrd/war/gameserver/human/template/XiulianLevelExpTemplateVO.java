package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 修炼等级经验配置模版
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class XiulianLevelExpTemplateVO extends TemplateObject {

	/** 玩家等级 */
	@ExcelCellBinding(offset = 1)
	protected int playerLevel;

	/** 收集者获得经验 */
	@ExcelCellBinding(offset = 2)
	protected int collectorExp;

	/** 好友加成百分比 */
	@ExcelCellBinding(offset = 3)
	protected float collectorFriendAddRate;

	/** 被收集者可以获得经验 */
	@ExcelCellBinding(offset = 4)
	protected int beCollectorExp;

	/** 被收集好友百分比 */
	@ExcelCellBinding(offset = 5)
	protected float beCollectFriendAddRate;


	public int getPlayerLevel() {
		return this.playerLevel;
	}

	public void setPlayerLevel(int playerLevel) {
		if (playerLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[玩家等级]playerLevel的值不得小于0");
		}
		this.playerLevel = playerLevel;
	}
	
	public int getCollectorExp() {
		return this.collectorExp;
	}

	public void setCollectorExp(int collectorExp) {
		if (collectorExp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[收集者获得经验]collectorExp的值不得小于0");
		}
		this.collectorExp = collectorExp;
	}
	
	public float getCollectorFriendAddRate() {
		return this.collectorFriendAddRate;
	}

	public void setCollectorFriendAddRate(float collectorFriendAddRate) {
		if (collectorFriendAddRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[好友加成百分比]collectorFriendAddRate的值不得小于0");
		}
		this.collectorFriendAddRate = collectorFriendAddRate;
	}
	
	public int getBeCollectorExp() {
		return this.beCollectorExp;
	}

	public void setBeCollectorExp(int beCollectorExp) {
		if (beCollectorExp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[被收集者可以获得经验]beCollectorExp的值不得小于0");
		}
		this.beCollectorExp = beCollectorExp;
	}
	
	public float getBeCollectFriendAddRate() {
		return this.beCollectFriendAddRate;
	}

	public void setBeCollectFriendAddRate(float beCollectFriendAddRate) {
		if (beCollectFriendAddRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[被收集好友百分比]beCollectFriendAddRate的值不得小于0");
		}
		this.beCollectFriendAddRate = beCollectFriendAddRate;
	}
	

	@Override
	public String toString() {
		return "XiulianLevelExpTemplateVO[playerLevel=" + playerLevel + ",collectorExp=" + collectorExp + ",collectorFriendAddRate=" + collectorFriendAddRate + ",beCollectorExp=" + beCollectorExp + ",beCollectFriendAddRate=" + beCollectFriendAddRate + ",]";

	}
}