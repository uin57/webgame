package com.pwrd.war.gameserver.pet.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 酒馆与npc对应模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class NpcToHirePetPubTemplateVO extends TemplateObject {

	/** 酒馆ID */
	@ExcelCellBinding(offset = 1)
	protected int pubId;

	/** npc */
	@ExcelCellBinding(offset = 2)
	protected String npcId;


	public int getPubId() {
		return this.pubId;
	}

	public void setPubId(int pubId) {
		if (pubId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[酒馆ID]pubId不可以为0");
		}
		this.pubId = pubId;
	}
	
	public String getNpcId() {
		return this.npcId;
	}

	public void setNpcId(String npcId) {
		if (StringUtils.isEmpty(npcId)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[npc]npcId不可以为空");
		}
		this.npcId = npcId;
	}
	

	@Override
	public String toString() {
		return "NpcToHirePetPubTemplateVO[pubId=" + pubId + ",npcId=" + npcId + ",]";

	}
}