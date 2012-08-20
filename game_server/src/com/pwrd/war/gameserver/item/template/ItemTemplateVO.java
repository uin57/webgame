package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 道具模板中间层
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class ItemTemplateVO extends TemplateObject {

	/** 道具SN */
	@ExcelCellBinding(offset = 1)
	protected String itemSn;

	/** 道具名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/** 道具身份类型，一级类型 */
	@ExcelCellBinding(offset = 3)
	protected int identityType;

	/** 品质，颜色 */
	@ExcelCellBinding(offset = 5)
	protected int quantity;

	/** 背包分类id */
	@ExcelCellBinding(offset = 6)
	protected int tid;

	/** 文字描述 */
	@ExcelCellBinding(offset = 7)
	protected String desc;

	/** 职业类型 */
	@ExcelCellBinding(offset = 8)
	protected int vocationLimit;

	/** 武将形象限制 */
	@ExcelCellBinding(offset = 9)
	protected int figureLimit;

	/** 使用等级 */
	@ExcelCellBinding(offset = 10)
	protected int needLevel;

	/** 性别限制 */
	@ExcelCellBinding(offset = 11)
	protected int sexLimit;

	/** 钱币ID */
	@ExcelCellBinding(offset = 12)
	protected int currency;

	/** 出售价格 */
	@ExcelCellBinding(offset = 13)
	protected int sellPrice;

	/** 可否丢弃 */
	@ExcelCellBinding(offset = 14)
	protected boolean canDiscard;

	/** 可否出售 */
	@ExcelCellBinding(offset = 15)
	protected boolean canSell;

	/** 绑定模式 */
	@ExcelCellBinding(offset = 16)
	protected int bindModeId;

	/** 叠加上限 */
	@ExcelCellBinding(offset = 17)
	protected int maxOverlap;

	/** 使用间隔 */
	@ExcelCellBinding(offset = 18)
	protected int coolDown;

	/** 持续时间 */
	@ExcelCellBinding(offset = 19)
	protected int duration;

	/** 图标 */
	@ExcelCellBinding(offset = 20)
	protected String img;


	public String getItemSn() {
		return this.itemSn;
	}

	public void setItemSn(String itemSn) {
		if (StringUtils.isEmpty(itemSn)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[道具SN]itemSn不可以为空");
		}
		this.itemSn = itemSn;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[道具名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getIdentityType() {
		return this.identityType;
	}

	public void setIdentityType(int identityType) {
		if (identityType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[道具身份类型，一级类型]identityType不可以为0");
		}
		this.identityType = identityType;
	}
	
	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		if (quantity == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[品质，颜色]quantity不可以为0");
		}
		this.quantity = quantity;
	}
	
	public int getTid() {
		return this.tid;
	}

	public void setTid(int tid) {
		if (tid < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[背包分类id]tid的值不得小于0");
		}
		this.tid = tid;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public int getVocationLimit() {
		return this.vocationLimit;
	}

	public void setVocationLimit(int vocationLimit) {
		if (vocationLimit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[职业类型]vocationLimit的值不得小于0");
		}
		this.vocationLimit = vocationLimit;
	}
	
	public int getFigureLimit() {
		return this.figureLimit;
	}

	public void setFigureLimit(int figureLimit) {
		if (figureLimit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[武将形象限制]figureLimit的值不得小于0");
		}
		this.figureLimit = figureLimit;
	}
	
	public int getNeedLevel() {
		return this.needLevel;
	}

	public void setNeedLevel(int needLevel) {
		if (needLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[使用等级]needLevel的值不得小于0");
		}
		this.needLevel = needLevel;
	}
	
	public int getSexLimit() {
		return this.sexLimit;
	}

	public void setSexLimit(int sexLimit) {
		if (sexLimit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[性别限制]sexLimit的值不得小于0");
		}
		this.sexLimit = sexLimit;
	}
	
	public int getCurrency() {
		return this.currency;
	}

	public void setCurrency(int currency) {
		if (currency < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[钱币ID]currency的值不得小于0");
		}
		this.currency = currency;
	}
	
	public int getSellPrice() {
		return this.sellPrice;
	}

	public void setSellPrice(int sellPrice) {
		if (sellPrice < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[出售价格]sellPrice的值不得小于0");
		}
		this.sellPrice = sellPrice;
	}
	
	public boolean isCanDiscard() {
		return this.canDiscard;
	}

	public void setCanDiscard(boolean canDiscard) {
		this.canDiscard = canDiscard;
	}
	
	public boolean isCanSell() {
		return this.canSell;
	}

	public void setCanSell(boolean canSell) {
		this.canSell = canSell;
	}
	
	public int getBindModeId() {
		return this.bindModeId;
	}

	public void setBindModeId(int bindModeId) {
		this.bindModeId = bindModeId;
	}
	
	public int getMaxOverlap() {
		return this.maxOverlap;
	}

	public void setMaxOverlap(int maxOverlap) {
		if (maxOverlap < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[叠加上限]maxOverlap的值不得小于0");
		}
		this.maxOverlap = maxOverlap;
	}
	
	public int getCoolDown() {
		return this.coolDown;
	}

	public void setCoolDown(int coolDown) {
		if (coolDown < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[使用间隔]coolDown的值不得小于0");
		}
		this.coolDown = coolDown;
	}
	
	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		if (duration < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[持续时间]duration的值不得小于0");
		}
		this.duration = duration;
	}
	
	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	

	@Override
	public String toString() {
		return "ItemTemplateVO[itemSn=" + itemSn + ",name=" + name + ",identityType=" + identityType + ",quantity=" + quantity + ",tid=" + tid + ",desc=" + desc + ",vocationLimit=" + vocationLimit + ",figureLimit=" + figureLimit + ",needLevel=" + needLevel + ",sexLimit=" + sexLimit + ",currency=" + currency + ",sellPrice=" + sellPrice + ",canDiscard=" + canDiscard + ",canSell=" + canSell + ",bindModeId=" + bindModeId + ",maxOverlap=" + maxOverlap + ",coolDown=" + coolDown + ",duration=" + duration + ",img=" + img + ",]";

	}
}