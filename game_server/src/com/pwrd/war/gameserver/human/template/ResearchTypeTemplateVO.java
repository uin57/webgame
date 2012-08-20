package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 研究类型模版
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class ResearchTypeTemplateVO extends TemplateObject {

	/** 研究类型 */
	@ExcelCellBinding(offset = 1)
	protected int researchType;

	/** 研究名称 */
	@ExcelCellBinding(offset = 2)
	protected String researchName;

	/** 可见等级 */
	@ExcelCellBinding(offset = 3)
	protected int needLevel;

	/** 可见任务SN */
	@ExcelCellBinding(offset = 4)
	protected String questSn;

	/** 图标 */
	@ExcelCellBinding(offset = 5)
	protected String icon;


	public int getResearchType() {
		return this.researchType;
	}

	public void setResearchType(int researchType) {
		if (researchType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[研究类型]researchType的值不得小于0");
		}
		this.researchType = researchType;
	}
	
	public String getResearchName() {
		return this.researchName;
	}

	public void setResearchName(String researchName) {
		if (StringUtils.isEmpty(researchName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[研究名称]researchName不可以为空");
		}
		this.researchName = researchName;
	}
	
	public int getNeedLevel() {
		return this.needLevel;
	}

	public void setNeedLevel(int needLevel) {
		if (needLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[可见等级]needLevel的值不得小于0");
		}
		this.needLevel = needLevel;
	}
	
	public String getQuestSn() {
		return this.questSn;
	}

	public void setQuestSn(String questSn) {
		this.questSn = questSn;
	}
	
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	

	@Override
	public String toString() {
		return "ResearchTypeTemplateVO[researchType=" + researchType + ",researchName=" + researchName + ",needLevel=" + needLevel + ",questSn=" + questSn + ",icon=" + icon + ",]";

	}
}