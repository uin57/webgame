package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 在线礼包模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class NewPlayerPrizeTemplateVO extends TemplateObject {

	/** 步骤 */
	@ExcelCellBinding(offset = 1)
	protected short buzhou;

	/** 上一步骤 */
	@ExcelCellBinding(offset = 2)
	protected short nextbuzhou;

	/** 等级 */
	@ExcelCellBinding(offset = 3)
	protected int level;

	/** 在线时间 */
	@ExcelCellBinding(offset = 4)
	protected int onlinetime;

	/** 获得体力 */
	@ExcelCellBinding(offset = 5)
	protected int getVit;

	/** 道具sn */
	@ExcelCellBinding(offset = 6)
	protected String itemSN;

	/** 提示 */
	@ExcelCellBinding(offset = 7)
	protected String tip;

	/** 奖励说明 */
	@ExcelCellBinding(offset = 8)
	protected String prizeTip;


	public short getBuzhou() {
		return this.buzhou;
	}

	public void setBuzhou(short buzhou) {
		this.buzhou = buzhou;
	}
	
	public short getNextbuzhou() {
		return this.nextbuzhou;
	}

	public void setNextbuzhou(short nextbuzhou) {
		this.nextbuzhou = nextbuzhou;
	}
	
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[等级]level不可以为0");
		}
		this.level = level;
	}
	
	public int getOnlinetime() {
		return this.onlinetime;
	}

	public void setOnlinetime(int onlinetime) {
		if (onlinetime == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[在线时间]onlinetime不可以为0");
		}
		this.onlinetime = onlinetime;
	}
	
	public int getGetVit() {
		return this.getVit;
	}

	public void setGetVit(int getVit) {
		if (getVit == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[获得体力]getVit不可以为0");
		}
		this.getVit = getVit;
	}
	
	public String getItemSN() {
		return this.itemSN;
	}

	public void setItemSN(String itemSN) {
		this.itemSN = itemSN;
	}
	
	public String getTip() {
		return this.tip;
	}

	public void setTip(String tip) {
		if (StringUtils.isEmpty(tip)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[提示]tip不可以为空");
		}
		this.tip = tip;
	}
	
	public String getPrizeTip() {
		return this.prizeTip;
	}

	public void setPrizeTip(String prizeTip) {
		if (StringUtils.isEmpty(prizeTip)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[奖励说明]prizeTip不可以为空");
		}
		this.prizeTip = prizeTip;
	}
	

	@Override
	public String toString() {
		return "NewPlayerPrizeTemplateVO[buzhou=" + buzhou + ",nextbuzhou=" + nextbuzhou + ",level=" + level + ",onlinetime=" + onlinetime + ",getVit=" + getVit + ",itemSN=" + itemSN + ",tip=" + tip + ",prizeTip=" + prizeTip + ",]";

	}
}