package com.pwrd.war.gameserver.pet.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 武将招募模版
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class HirePetTemplateVO extends TemplateObject {

	/** 武将ID */
	@ExcelCellBinding(offset = 1)
	protected String petSn;

	/** 武将名 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/** 隐藏可见 玩家等级 */
	@ExcelCellBinding(offset = 3)
	protected int seeLevel;

	/** （隐藏可见）任务ID */
	@ExcelCellBinding(offset = 4)
	protected String seeMessionId;

	/** （隐藏可见）声望值 */
	@ExcelCellBinding(offset = 5)
	protected int seePopularity;

	/** 招募 玩家等级 */
	@ExcelCellBinding(offset = 6)
	protected int hireLevel;

	/** 招募声望 */
	@ExcelCellBinding(offset = 7)
	protected int hirePopularity;

	/** 需要铜钱 */
	@ExcelCellBinding(offset = 8)
	protected int hireCoin;

	/** 需要元宝 */
	@ExcelCellBinding(offset = 9)
	protected int hireGold;

	/** 招募道具1sn */
	@ExcelCellBinding(offset = 10)
	protected String hireItem1Sn;

	/** 招募道具1数量 */
	@ExcelCellBinding(offset = 11)
	protected int hireItem1Num;

	/** 招募道具2sn */
	@ExcelCellBinding(offset = 12)
	protected String hireItem2Sn;

	/** 招募道具2数量 */
	@ExcelCellBinding(offset = 13)
	protected int hireItem2Num;

	/** 所属酒馆 */
	@ExcelCellBinding(offset = 14)
	protected int pub;

	/** 最低可见等级 */
	@ExcelCellBinding(offset = 15)
	protected int seeMinLevel;

	/** 最高可见等级 */
	@ExcelCellBinding(offset = 16)
	protected int seeMaxLevel;


	public String getPetSn() {
		return this.petSn;
	}

	public void setPetSn(String petSn) {
		if (StringUtils.isEmpty(petSn)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[武将ID]petSn不可以为空");
		}
		this.petSn = petSn;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[武将名]name不可以为空");
		}
		this.name = name;
	}
	
	public int getSeeLevel() {
		return this.seeLevel;
	}

	public void setSeeLevel(int seeLevel) {
		this.seeLevel = seeLevel;
	}
	
	public String getSeeMessionId() {
		return this.seeMessionId;
	}

	public void setSeeMessionId(String seeMessionId) {
		this.seeMessionId = seeMessionId;
	}
	
	public int getSeePopularity() {
		return this.seePopularity;
	}

	public void setSeePopularity(int seePopularity) {
		this.seePopularity = seePopularity;
	}
	
	public int getHireLevel() {
		return this.hireLevel;
	}

	public void setHireLevel(int hireLevel) {
		this.hireLevel = hireLevel;
	}
	
	public int getHirePopularity() {
		return this.hirePopularity;
	}

	public void setHirePopularity(int hirePopularity) {
		this.hirePopularity = hirePopularity;
	}
	
	public int getHireCoin() {
		return this.hireCoin;
	}

	public void setHireCoin(int hireCoin) {
		this.hireCoin = hireCoin;
	}
	
	public int getHireGold() {
		return this.hireGold;
	}

	public void setHireGold(int hireGold) {
		this.hireGold = hireGold;
	}
	
	public String getHireItem1Sn() {
		return this.hireItem1Sn;
	}

	public void setHireItem1Sn(String hireItem1Sn) {
		this.hireItem1Sn = hireItem1Sn;
	}
	
	public int getHireItem1Num() {
		return this.hireItem1Num;
	}

	public void setHireItem1Num(int hireItem1Num) {
		this.hireItem1Num = hireItem1Num;
	}
	
	public String getHireItem2Sn() {
		return this.hireItem2Sn;
	}

	public void setHireItem2Sn(String hireItem2Sn) {
		this.hireItem2Sn = hireItem2Sn;
	}
	
	public int getHireItem2Num() {
		return this.hireItem2Num;
	}

	public void setHireItem2Num(int hireItem2Num) {
		this.hireItem2Num = hireItem2Num;
	}
	
	public int getPub() {
		return this.pub;
	}

	public void setPub(int pub) {
		this.pub = pub;
	}
	
	public int getSeeMinLevel() {
		return this.seeMinLevel;
	}

	public void setSeeMinLevel(int seeMinLevel) {
		this.seeMinLevel = seeMinLevel;
	}
	
	public int getSeeMaxLevel() {
		return this.seeMaxLevel;
	}

	public void setSeeMaxLevel(int seeMaxLevel) {
		this.seeMaxLevel = seeMaxLevel;
	}
	

	@Override
	public String toString() {
		return "HirePetTemplateVO[petSn=" + petSn + ",name=" + name + ",seeLevel=" + seeLevel + ",seeMessionId=" + seeMessionId + ",seePopularity=" + seePopularity + ",hireLevel=" + hireLevel + ",hirePopularity=" + hirePopularity + ",hireCoin=" + hireCoin + ",hireGold=" + hireGold + ",hireItem1Sn=" + hireItem1Sn + ",hireItem1Num=" + hireItem1Num + ",hireItem2Sn=" + hireItem2Sn + ",hireItem2Num=" + hireItem2Num + ",pub=" + pub + ",seeMinLevel=" + seeMinLevel + ",seeMaxLevel=" + seeMaxLevel + ",]";

	}
}