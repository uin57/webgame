package com.pwrd.war.gameserver.fight.herosay.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 武将说话模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class HeroSayTemplateVO extends TemplateObject {

	/** 武将ID */
	@ExcelCellBinding(offset = 1)
	protected String petSn;

	/** 所属地图 */
	@ExcelCellBinding(offset = 2)
	protected String mapSn;

	/** 武将语言id */
	@ExcelCellBinding(offset = 3)
	protected String sayIds;


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
	
	public String getMapSn() {
		return this.mapSn;
	}

	public void setMapSn(String mapSn) {
		if (StringUtils.isEmpty(mapSn)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[所属地图]mapSn不可以为空");
		}
		this.mapSn = mapSn;
	}
	
	public String getSayIds() {
		return this.sayIds;
	}

	public void setSayIds(String sayIds) {
		if (StringUtils.isEmpty(sayIds)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[武将语言id]sayIds不可以为空");
		}
		this.sayIds = sayIds;
	}
	

	@Override
	public String toString() {
		return "HeroSayTemplateVO[petSn=" + petSn + ",mapSn=" + mapSn + ",sayIds=" + sayIds + ",]";

	}
}