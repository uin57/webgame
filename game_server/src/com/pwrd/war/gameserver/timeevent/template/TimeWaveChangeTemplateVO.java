package com.pwrd.war.gameserver.timeevent.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import java.util.List;
import com.pwrd.war.core.template.ExcelCollectionMapping;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 时间波动
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class TimeWaveChangeTemplateVO extends TemplateObject {

	/**  名称多语言 Id */
	@ExcelCellBinding(offset = 1)
	protected int nameLangId;

	/**  名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/** $field.comment */
	@ExcelCellBinding(offset = 3)
	protected String waveChangeTimeIds;

	/**  建筑功能信息列表 */
	@ExcelCollectionMapping(clazz = com.pwrd.war.gameserver.timeevent.template.WaveChangeLimit.class, collectionNumber = "4,5,6;7,8,9;10,11,12;13,14,15")
	protected List<com.pwrd.war.gameserver.timeevent.template.WaveChangeLimit> phases;


	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 名称多语言 Id]nameLangId的值不得小于0");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 名称]name不可以为空");
		}
		this.name = name;
	}
	
	public String getWaveChangeTimeIds() {
		return this.waveChangeTimeIds;
	}

	public void setWaveChangeTimeIds(String waveChangeTimeIds) {
		this.waveChangeTimeIds = waveChangeTimeIds;
	}
	
	public List<com.pwrd.war.gameserver.timeevent.template.WaveChangeLimit> getPhases() {
		return this.phases;
	}

	public void setPhases(List<com.pwrd.war.gameserver.timeevent.template.WaveChangeLimit> phases) {
		if (phases == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 建筑功能信息列表]phases不可以为空");
		}	
		this.phases = phases;
	}
	

	@Override
	public String toString() {
		return "TimeWaveChangeTemplateVO[nameLangId=" + nameLangId + ",name=" + name + ",waveChangeTimeIds=" + waveChangeTimeIds + ",phases=" + phases + ",]";

	}
}