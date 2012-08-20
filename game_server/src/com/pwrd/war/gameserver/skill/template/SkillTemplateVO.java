package com.pwrd.war.gameserver.skill.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.template.ExcelCollectionMapping;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 主动技能模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class SkillTemplateVO extends TemplateObject {

	/**  技能id */
	@ExcelCellBinding(offset = 1)
	protected Integer skillSn;

	/**  技能名称 */
	@ExcelCellBinding(offset = 2)
	protected String skillName;

	/**  职业，技能类型为2时有用 */
	@ExcelCellBinding(offset = 3)
	protected Integer vocationType;

	/**  技能类型，1为普通武将技，2为职业普通攻击，3为锁屏武将技 */
	@ExcelCellBinding(offset = 4)
	protected Integer skillType;

	/**  技能说明 */
	@ExcelCellBinding(offset = 5)
	protected String skillDescription;

	/**  技能图标 */
	@ExcelCellBinding(offset = 6)
	protected String skillIcon;

	/**  技能等级 */
	@ExcelCellBinding(offset = 7)
	protected Integer skillLevel;

	/**  攻击类型，0为近战，1为远程 */
	@ExcelCellBinding(offset = 8)
	protected Integer attackType;

	/**  士气消耗 */
	@ExcelCellBinding(offset = 9)
	protected Integer moraleCost;

	/**  攻击范围 */
	@ExcelCellBinding(offset = 10)
	protected Integer attackScope;

	/**  列范围，0为所在列，1为全部列 */
	@ExcelCellBinding(offset = 11)
	protected Integer lineScope;

	/**  目标数量 */
	@ExcelCellBinding(offset = 12)
	protected Integer maxTargets;

	/**  目标半径 */
	@ExcelCellBinding(offset = 13)
	protected Integer targetScope;

	/**  目标方，0敌方人，1敌方障碍，2敌方全体，3我方人，4我方障碍，5我方全体，6仅自己 */
	@ExcelCellBinding(offset = 14)
	protected Integer targetType;

	/**  目标排序规则，0随机，1血量从少到多，2血量从多到少，3优先职业1，4优先职业2，5优先职业3，6优先职业4 */
	@ExcelCellBinding(offset = 15)
	protected Integer targetFilter;

	/**  冷却回合 */
	@ExcelCellBinding(offset = 16)
	protected Integer coolDown;

	/**  是否带攻击 */
	@ExcelCellBinding(offset = 17)
	protected Integer hasAttack;

	/**  效果数组 */
	@ExcelCollectionMapping(clazz = String.class, collectionNumber = "18;19;20;21;22")
	protected String[] skillEffects;

	/**  是否触发攻击后行动冷却，1为触发 */
	@ExcelCellBinding(offset = 23)
	protected Boolean hasCoolDown;

	/**  是否以自己为中心寻找目标，1为自己为中心 */
	@ExcelCellBinding(offset = 24)
	protected Boolean iamCenter;


	public Integer getSkillSn() {
		return this.skillSn;
	}

	public void setSkillSn(Integer skillSn) {
		if (skillSn == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 技能id]skillSn不可以为空");
		}	
		this.skillSn = skillSn;
	}
	
	public String getSkillName() {
		return this.skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	
	public Integer getVocationType() {
		return this.vocationType;
	}

	public void setVocationType(Integer vocationType) {
		this.vocationType = vocationType;
	}
	
	public Integer getSkillType() {
		return this.skillType;
	}

	public void setSkillType(Integer skillType) {
		if (skillType == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 技能类型，1为普通武将技，2为职业普通攻击，3为锁屏武将技]skillType不可以为空");
		}	
		this.skillType = skillType;
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
	
	public Integer getSkillLevel() {
		return this.skillLevel;
	}

	public void setSkillLevel(Integer skillLevel) {
		this.skillLevel = skillLevel;
	}
	
	public Integer getAttackType() {
		return this.attackType;
	}

	public void setAttackType(Integer attackType) {
		this.attackType = attackType;
	}
	
	public Integer getMoraleCost() {
		return this.moraleCost;
	}

	public void setMoraleCost(Integer moraleCost) {
		this.moraleCost = moraleCost;
	}
	
	public Integer getAttackScope() {
		return this.attackScope;
	}

	public void setAttackScope(Integer attackScope) {
		this.attackScope = attackScope;
	}
	
	public Integer getLineScope() {
		return this.lineScope;
	}

	public void setLineScope(Integer lineScope) {
		this.lineScope = lineScope;
	}
	
	public Integer getMaxTargets() {
		return this.maxTargets;
	}

	public void setMaxTargets(Integer maxTargets) {
		this.maxTargets = maxTargets;
	}
	
	public Integer getTargetScope() {
		return this.targetScope;
	}

	public void setTargetScope(Integer targetScope) {
		this.targetScope = targetScope;
	}
	
	public Integer getTargetType() {
		return this.targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}
	
	public Integer getTargetFilter() {
		return this.targetFilter;
	}

	public void setTargetFilter(Integer targetFilter) {
		this.targetFilter = targetFilter;
	}
	
	public Integer getCoolDown() {
		return this.coolDown;
	}

	public void setCoolDown(Integer coolDown) {
		this.coolDown = coolDown;
	}
	
	public Integer getHasAttack() {
		return this.hasAttack;
	}

	public void setHasAttack(Integer hasAttack) {
		this.hasAttack = hasAttack;
	}
	
	public String[] getSkillEffects() {
		return this.skillEffects;
	}

	public void setSkillEffects(String[] skillEffects) {
		if (skillEffects == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[ 效果数组]skillEffects不可以为空");
		}	
		this.skillEffects = skillEffects;
	}
	
	public Boolean getHasCoolDown() {
		return this.hasCoolDown;
	}

	public void setHasCoolDown(Boolean hasCoolDown) {
		this.hasCoolDown = hasCoolDown;
	}
	
	public Boolean getIamCenter() {
		return this.iamCenter;
	}

	public void setIamCenter(Boolean iamCenter) {
		this.iamCenter = iamCenter;
	}
	

	@Override
	public String toString() {
		return "SkillTemplateVO[skillSn=" + skillSn + ",skillName=" + skillName + ",vocationType=" + vocationType + ",skillType=" + skillType + ",skillDescription=" + skillDescription + ",skillIcon=" + skillIcon + ",skillLevel=" + skillLevel + ",attackType=" + attackType + ",moraleCost=" + moraleCost + ",attackScope=" + attackScope + ",lineScope=" + lineScope + ",maxTargets=" + maxTargets + ",targetScope=" + targetScope + ",targetType=" + targetType + ",targetFilter=" + targetFilter + ",coolDown=" + coolDown + ",hasAttack=" + hasAttack + ",skillEffects=" + skillEffects + ",hasCoolDown=" + hasCoolDown + ",iamCenter=" + iamCenter + ",]";

	}
}