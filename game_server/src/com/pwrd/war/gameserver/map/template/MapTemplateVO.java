package com.pwrd.war.gameserver.map.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 地图对应的战斗背景模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class MapTemplateVO extends TemplateObject {

	/** 地图id */
	@ExcelCellBinding(offset = 1)
	protected String mapId;

	/** 战斗背景id */
	@ExcelCellBinding(offset = 4)
	protected String bgId;


	public String getMapId() {
		return this.mapId;
	}

	public void setMapId(String mapId) {
		if (StringUtils.isEmpty(mapId)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[地图id]mapId不可以为空");
		}
		this.mapId = mapId;
	}
	
	public String getBgId() {
		return this.bgId;
	}

	public void setBgId(String bgId) {
		if (StringUtils.isEmpty(bgId)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[战斗背景id]bgId不可以为空");
		}
		this.bgId = bgId;
	}
	

	@Override
	public String toString() {
		return "MapTemplateVO[mapId=" + mapId + ",bgId=" + bgId + ",]";

	}
}