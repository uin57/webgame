package com.pwrd.war.gameserver.map.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 功能对应的战斗背景模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class MapBattleBgTemplateVO extends TemplateObject {

	/** 功能id */
	@ExcelCellBinding(offset = 1)
	protected int funcId;

	/** 战斗背景id */
	@ExcelCellBinding(offset = 3)
	protected String bgId;

	/** 围观npcid */
	@ExcelCellBinding(offset = 4)
	protected int npcId;


	public int getFuncId() {
		return this.funcId;
	}

	public void setFuncId(int funcId) {
		if (funcId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[功能id]funcId不可以为0");
		}
		this.funcId = funcId;
	}
	
	public String getBgId() {
		return this.bgId;
	}

	public void setBgId(String bgId) {
		if (StringUtils.isEmpty(bgId)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[战斗背景id]bgId不可以为空");
		}
		this.bgId = bgId;
	}
	
	public int getNpcId() {
		return this.npcId;
	}

	public void setNpcId(int npcId) {
		if (npcId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[围观npcid]npcId不可以为0");
		}
		this.npcId = npcId;
	}
	

	@Override
	public String toString() {
		return "MapBattleBgTemplateVO[funcId=" + funcId + ",bgId=" + bgId + ",npcId=" + npcId + ",]";

	}
}