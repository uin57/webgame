package com.pwrd.war.gameserver.skill.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 天赋模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class GeniusTemplateVO extends TemplateObject {

	/** 天赋id */
	@ExcelCellBinding(offset = 1)
	protected String geniusSn;

	/** 名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/** 职业 */
	@ExcelCellBinding(offset = 3)
	protected String career;

	/** 技能说明 */
	@ExcelCellBinding(offset = 4)
	protected String skillDescription;

	/** 技能图标 */
	@ExcelCellBinding(offset = 5)
	protected String skillIcon;

	/** 技能等级 */
	@ExcelCellBinding(offset = 6)
	protected String skillRank;

	/** 等级限制 */
	@ExcelCellBinding(offset = 7)
	protected String rankRestrict;

	/**  技能效果1 */
	@ExcelCellBinding(offset = 8)
	protected String skillEffect1;

	/**  效果参数1 */
	@ExcelCellBinding(offset = 9)
	protected String skillEffectArg11;

	/**  效果参数2 */
	@ExcelCellBinding(offset = 10)
	protected String skillEffectArg12;

	/**  效果2 */
	@ExcelCellBinding(offset = 11)
	protected String skillEffect2;

	/**  效果参数1 */
	@ExcelCellBinding(offset = 12)
	protected String skillEffectArg21;

	/**  效果参数2 */
	@ExcelCellBinding(offset = 13)
	protected String skillEffectArg22;

	/**  效果3 */
	@ExcelCellBinding(offset = 14)
	protected String skillEffect3;

	/**  效果参数1 */
	@ExcelCellBinding(offset = 15)
	protected String skillEffectArg31;

	/**  效果参数2 */
	@ExcelCellBinding(offset = 16)
	protected String skillEffectArg32;

	/**  效果4 */
	@ExcelCellBinding(offset = 17)
	protected String skillEffect4;

	/**  效果参数1 */
	@ExcelCellBinding(offset = 18)
	protected String skillEffectArg41;

	/**  效果参数2 */
	@ExcelCellBinding(offset = 19)
	protected String skillEffectArg42;


	public String getGeniusSn() {
		return this.geniusSn;
	}

	public void setGeniusSn(String geniusSn) {
		if (StringUtils.isEmpty(geniusSn)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[天赋id]geniusSn不可以为空");
		}
		this.geniusSn = geniusSn;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCareer() {
		return this.career;
	}

	public void setCareer(String career) {
		this.career = career;
	}
	
	public String getSkillDescription() {
		return this.skillDescription;
	}

	public void setSkillDescription(String skillDescription) {
		this.skillDescription = skillDescription;
	}
	
	public String getSkillIcon() {
		return this.skillIcon;
	}

	public void setSkillIcon(String skillIcon) {
		this.skillIcon = skillIcon;
	}
	
	public String getSkillRank() {
		return this.skillRank;
	}

	public void setSkillRank(String skillRank) {
		this.skillRank = skillRank;
	}
	
	public String getRankRestrict() {
		return this.rankRestrict;
	}

	public void setRankRestrict(String rankRestrict) {
		this.rankRestrict = rankRestrict;
	}
	
	public String getSkillEffect1() {
		return this.skillEffect1;
	}

	public void setSkillEffect1(String skillEffect1) {
		this.skillEffect1 = skillEffect1;
	}
	
	public String getSkillEffectArg11() {
		return this.skillEffectArg11;
	}

	public void setSkillEffectArg11(String skillEffectArg11) {
		this.skillEffectArg11 = skillEffectArg11;
	}
	
	public String getSkillEffectArg12() {
		return this.skillEffectArg12;
	}

	public void setSkillEffectArg12(String skillEffectArg12) {
		this.skillEffectArg12 = skillEffectArg12;
	}
	
	public String getSkillEffect2() {
		return this.skillEffect2;
	}

	public void setSkillEffect2(String skillEffect2) {
		this.skillEffect2 = skillEffect2;
	}
	
	public String getSkillEffectArg21() {
		return this.skillEffectArg21;
	}

	public void setSkillEffectArg21(String skillEffectArg21) {
		this.skillEffectArg21 = skillEffectArg21;
	}
	
	public String getSkillEffectArg22() {
		return this.skillEffectArg22;
	}

	public void setSkillEffectArg22(String skillEffectArg22) {
		this.skillEffectArg22 = skillEffectArg22;
	}
	
	public String getSkillEffect3() {
		return this.skillEffect3;
	}

	public void setSkillEffect3(String skillEffect3) {
		this.skillEffect3 = skillEffect3;
	}
	
	public String getSkillEffectArg31() {
		return this.skillEffectArg31;
	}

	public void setSkillEffectArg31(String skillEffectArg31) {
		this.skillEffectArg31 = skillEffectArg31;
	}
	
	public String getSkillEffectArg32() {
		return this.skillEffectArg32;
	}

	public void setSkillEffectArg32(String skillEffectArg32) {
		this.skillEffectArg32 = skillEffectArg32;
	}
	
	public String getSkillEffect4() {
		return this.skillEffect4;
	}

	public void setSkillEffect4(String skillEffect4) {
		this.skillEffect4 = skillEffect4;
	}
	
	public String getSkillEffectArg41() {
		return this.skillEffectArg41;
	}

	public void setSkillEffectArg41(String skillEffectArg41) {
		this.skillEffectArg41 = skillEffectArg41;
	}
	
	public String getSkillEffectArg42() {
		return this.skillEffectArg42;
	}

	public void setSkillEffectArg42(String skillEffectArg42) {
		this.skillEffectArg42 = skillEffectArg42;
	}
	

	@Override
	public String toString() {
		return "GeniusTemplateVO[geniusSn=" + geniusSn + ",name=" + name + ",career=" + career + ",skillDescription=" + skillDescription + ",skillIcon=" + skillIcon + ",skillRank=" + skillRank + ",rankRestrict=" + rankRestrict + ",skillEffect1=" + skillEffect1 + ",skillEffectArg11=" + skillEffectArg11 + ",skillEffectArg12=" + skillEffectArg12 + ",skillEffect2=" + skillEffect2 + ",skillEffectArg21=" + skillEffectArg21 + ",skillEffectArg22=" + skillEffectArg22 + ",skillEffect3=" + skillEffect3 + ",skillEffectArg31=" + skillEffectArg31 + ",skillEffectArg32=" + skillEffectArg32 + ",skillEffect4=" + skillEffect4 + ",skillEffectArg41=" + skillEffectArg41 + ",skillEffectArg42=" + skillEffectArg42 + ",]";

	}
}