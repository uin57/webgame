package com.pwrd.war.gameserver.giftBag.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 礼包模版类
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class GiftBagTemplateVO extends TemplateObject {

	/**  礼包id */
	@ExcelCellBinding(offset = 1)
	protected String giftId;

	/**  礼包名称 */
	@ExcelCellBinding(offset = 2)
	protected String giftName;

	/**  生命周期(-1为永久) */
	@ExcelCellBinding(offset = 3)
	protected Integer cycle;

	/**  礼包图标 */
	@ExcelCellBinding(offset = 4)
	protected String img;

	/**  礼包描述 */
	@ExcelCellBinding(offset = 5)
	protected String desc;

	/**  奖励id */
	@ExcelCellBinding(offset = 6)
	protected String prizeId;


	public String getGiftId() {
		return this.giftId;
	}

	public void setGiftId(String giftId) {
		if (StringUtils.isEmpty(giftId)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 礼包id]giftId不可以为空");
		}
		this.giftId = giftId;
	}
	
	public String getGiftName() {
		return this.giftName;
	}

	public void setGiftName(String giftName) {
		if (StringUtils.isEmpty(giftName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 礼包名称]giftName不可以为空");
		}
		this.giftName = giftName;
	}
	
	public Integer getCycle() {
		return this.cycle;
	}

	public void setCycle(Integer cycle) {
		if (cycle == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 生命周期(-1为永久)]cycle不可以为空");
		}	
		this.cycle = cycle;
	}
	
	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		if (StringUtils.isEmpty(img)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 礼包图标]img不可以为空");
		}
		this.img = img;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 礼包描述]desc不可以为空");
		}
		this.desc = desc;
	}
	
	public String getPrizeId() {
		return this.prizeId;
	}

	public void setPrizeId(String prizeId) {
		if (StringUtils.isEmpty(prizeId)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 奖励id]prizeId不可以为空");
		}
		this.prizeId = prizeId;
	}
	

	@Override
	public String toString() {
		return "GiftBagTemplateVO[giftId=" + giftId + ",giftName=" + giftName + ",cycle=" + cycle + ",img=" + img + ",desc=" + desc + ",prizeId=" + prizeId + ",]";

	}
}