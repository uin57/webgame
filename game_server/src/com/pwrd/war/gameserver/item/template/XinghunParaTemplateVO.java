package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 星魂模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class XinghunParaTemplateVO extends TemplateObject {

	/** SN */
	@ExcelCellBinding(offset = 1)
	protected String xinghunSn;

	/** 道具名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/** 星座id */
	@ExcelCellBinding(offset = 3)
	protected int xingzuoId;

	/** 品质，颜色 */
	@ExcelCellBinding(offset = 4)
	protected String quality;

	/** 品质代码 1红2橙3紫4蓝5绿 */
	@ExcelCellBinding(offset = 5)
	protected int qualityNum;

	/** 卖出魂石 */
	@ExcelCellBinding(offset = 6)
	protected int hunshi;

	/** 卖出价格 */
	@ExcelCellBinding(offset = 7)
	protected int price;

	/** 最小攻击 */
	@ExcelCellBinding(offset = 8)
	protected int minAtk;

	/** 最大攻击 */
	@ExcelCellBinding(offset = 9)
	protected int maxAtk;

	/** 最小防御 */
	@ExcelCellBinding(offset = 10)
	protected int minDef;

	/** 最大防御 */
	@ExcelCellBinding(offset = 11)
	protected int maxDef;

	/** 最小生命 */
	@ExcelCellBinding(offset = 12)
	protected int minHp;

	/** 最大生命 */
	@ExcelCellBinding(offset = 13)
	protected int maxHp;

	/** 最小闪避 */
	@ExcelCellBinding(offset = 14)
	protected double minShanbi;

	/** 最大闪避 */
	@ExcelCellBinding(offset = 15)
	protected double maxShanbi;

	/** 最小命中 */
	@ExcelCellBinding(offset = 16)
	protected double minMingzhong;

	/** 最大命中 */
	@ExcelCellBinding(offset = 17)
	protected double maxMingzhong;

	/** 最小暴击 */
	@ExcelCellBinding(offset = 18)
	protected double minCri;

	/** 最大暴击 */
	@ExcelCellBinding(offset = 19)
	protected double maxCri;

	/** z最低韧性 */
	@ExcelCellBinding(offset = 20)
	protected double minRenxing;

	/** 最高韧性 */
	@ExcelCellBinding(offset = 21)
	protected double maxRenxing;

	/** 最低必杀 */
	@ExcelCellBinding(offset = 22)
	protected double minBisha;

	/** 最高必杀 */
	@ExcelCellBinding(offset = 23)
	protected double maxBisha;


	public String getXinghunSn() {
		return this.xinghunSn;
	}

	public void setXinghunSn(String xinghunSn) {
		if (StringUtils.isEmpty(xinghunSn)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[SN]xinghunSn不可以为空");
		}
		this.xinghunSn = xinghunSn;
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
	
	public int getXingzuoId() {
		return this.xingzuoId;
	}

	public void setXingzuoId(int xingzuoId) {
		if (xingzuoId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[星座id]xingzuoId不可以为0");
		}
		this.xingzuoId = xingzuoId;
	}
	
	public String getQuality() {
		return this.quality;
	}

	public void setQuality(String quality) {
		if (StringUtils.isEmpty(quality)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[品质，颜色]quality不可以为空");
		}
		this.quality = quality;
	}
	
	public int getQualityNum() {
		return this.qualityNum;
	}

	public void setQualityNum(int qualityNum) {
		if (qualityNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[品质代码 1红2橙3紫4蓝5绿]qualityNum不可以为0");
		}
		this.qualityNum = qualityNum;
	}
	
	public int getHunshi() {
		return this.hunshi;
	}

	public void setHunshi(int hunshi) {
		if (hunshi < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[卖出魂石]hunshi的值不得小于0");
		}
		this.hunshi = hunshi;
	}
	
	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		if (price < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[卖出价格]price的值不得小于0");
		}
		this.price = price;
	}
	
	public int getMinAtk() {
		return this.minAtk;
	}

	public void setMinAtk(int minAtk) {
		if (minAtk < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[最小攻击]minAtk的值不得小于0");
		}
		this.minAtk = minAtk;
	}
	
	public int getMaxAtk() {
		return this.maxAtk;
	}

	public void setMaxAtk(int maxAtk) {
		if (maxAtk < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[最大攻击]maxAtk的值不得小于0");
		}
		this.maxAtk = maxAtk;
	}
	
	public int getMinDef() {
		return this.minDef;
	}

	public void setMinDef(int minDef) {
		if (minDef < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[最小防御]minDef的值不得小于0");
		}
		this.minDef = minDef;
	}
	
	public int getMaxDef() {
		return this.maxDef;
	}

	public void setMaxDef(int maxDef) {
		if (maxDef < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[最大防御]maxDef的值不得小于0");
		}
		this.maxDef = maxDef;
	}
	
	public int getMinHp() {
		return this.minHp;
	}

	public void setMinHp(int minHp) {
		if (minHp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[最小生命]minHp的值不得小于0");
		}
		this.minHp = minHp;
	}
	
	public int getMaxHp() {
		return this.maxHp;
	}

	public void setMaxHp(int maxHp) {
		if (maxHp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[最大生命]maxHp的值不得小于0");
		}
		this.maxHp = maxHp;
	}
	
	public double getMinShanbi() {
		return this.minShanbi;
	}

	public void setMinShanbi(double minShanbi) {
		if (minShanbi < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[最小闪避]minShanbi的值不得小于0");
		}
		this.minShanbi = minShanbi;
	}
	
	public double getMaxShanbi() {
		return this.maxShanbi;
	}

	public void setMaxShanbi(double maxShanbi) {
		if (maxShanbi < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[最大闪避]maxShanbi的值不得小于0");
		}
		this.maxShanbi = maxShanbi;
	}
	
	public double getMinMingzhong() {
		return this.minMingzhong;
	}

	public void setMinMingzhong(double minMingzhong) {
		if (minMingzhong < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[最小命中]minMingzhong的值不得小于0");
		}
		this.minMingzhong = minMingzhong;
	}
	
	public double getMaxMingzhong() {
		return this.maxMingzhong;
	}

	public void setMaxMingzhong(double maxMingzhong) {
		if (maxMingzhong < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[最大命中]maxMingzhong的值不得小于0");
		}
		this.maxMingzhong = maxMingzhong;
	}
	
	public double getMinCri() {
		return this.minCri;
	}

	public void setMinCri(double minCri) {
		if (minCri < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[最小暴击]minCri的值不得小于0");
		}
		this.minCri = minCri;
	}
	
	public double getMaxCri() {
		return this.maxCri;
	}

	public void setMaxCri(double maxCri) {
		if (maxCri < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[最大暴击]maxCri的值不得小于0");
		}
		this.maxCri = maxCri;
	}
	
	public double getMinRenxing() {
		return this.minRenxing;
	}

	public void setMinRenxing(double minRenxing) {
		if (minRenxing < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[z最低韧性]minRenxing的值不得小于0");
		}
		this.minRenxing = minRenxing;
	}
	
	public double getMaxRenxing() {
		return this.maxRenxing;
	}

	public void setMaxRenxing(double maxRenxing) {
		if (maxRenxing < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					22, "[最高韧性]maxRenxing的值不得小于0");
		}
		this.maxRenxing = maxRenxing;
	}
	
	public double getMinBisha() {
		return this.minBisha;
	}

	public void setMinBisha(double minBisha) {
		if (minBisha < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					23, "[最低必杀]minBisha的值不得小于0");
		}
		this.minBisha = minBisha;
	}
	
	public double getMaxBisha() {
		return this.maxBisha;
	}

	public void setMaxBisha(double maxBisha) {
		if (maxBisha < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					24, "[最高必杀]maxBisha的值不得小于0");
		}
		this.maxBisha = maxBisha;
	}
	

	@Override
	public String toString() {
		return "XinghunParaTemplateVO[xinghunSn=" + xinghunSn + ",name=" + name + ",xingzuoId=" + xingzuoId + ",quality=" + quality + ",qualityNum=" + qualityNum + ",hunshi=" + hunshi + ",price=" + price + ",minAtk=" + minAtk + ",maxAtk=" + maxAtk + ",minDef=" + minDef + ",maxDef=" + maxDef + ",minHp=" + minHp + ",maxHp=" + maxHp + ",minShanbi=" + minShanbi + ",maxShanbi=" + maxShanbi + ",minMingzhong=" + minMingzhong + ",maxMingzhong=" + maxMingzhong + ",minCri=" + minCri + ",maxCri=" + maxCri + ",minRenxing=" + minRenxing + ",maxRenxing=" + maxRenxing + ",minBisha=" + minBisha + ",maxBisha=" + maxBisha + ",]";

	}
}