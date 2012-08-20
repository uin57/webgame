package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.gameserver.human.enums.ResearchType;

@ExcelRowBinding
public class ResearchTypeTemplate extends ResearchTypeTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		// TODO Auto-generated method stub

	}
	ResearchType researchType;
	@Override
	public void setResearchType(int researchType) { 
		super.setResearchType(researchType);
		this.researchType = ResearchType.getById(researchType);
	}
	public ResearchType getResearchTypeEnum() {
		return researchType;
	}
	 
	

}
