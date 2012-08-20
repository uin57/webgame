package com.pwrd.war.gameserver.role.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 角色和技能的对应关系
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class RoleToSkillTemplateVO extends TemplateObject {

	/**  角色编号 */
	@ExcelCellBinding(offset = 1)
	protected String roleSn;

	/**  角色名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/**  武将技 */
	@ExcelCellBinding(offset = 3)
	protected String petSkill;

	/**  被动技1 */
	@ExcelCellBinding(offset = 4)
	protected String passiveSkill1;

	/**  被动技2 */
	@ExcelCellBinding(offset = 5)
	protected String passiveSkill2;

	/**  被动技3 */
	@ExcelCellBinding(offset = 6)
	protected String passiveSkill3;

	/**  被动技4 */
	@ExcelCellBinding(offset = 7)
	protected String passiveSkill4;

	/**  被动技5 */
	@ExcelCellBinding(offset = 8)
	protected String passiveSkill5;

	/**  被动技6 */
	@ExcelCellBinding(offset = 9)
	protected String passiveSkill6;


	public String getRoleSn() {
		return this.roleSn;
	}

	public void setRoleSn(String roleSn) {
		if (StringUtils.isEmpty(roleSn)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 角色编号]roleSn不可以为空");
		}
		this.roleSn = roleSn;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 角色名称]name不可以为空");
		}
		this.name = name;
	}
	
	public String getPetSkill() {
		return this.petSkill;
	}

	public void setPetSkill(String petSkill) {
		this.petSkill = petSkill;
	}
	
	public String getPassiveSkill1() {
		return this.passiveSkill1;
	}

	public void setPassiveSkill1(String passiveSkill1) {
		this.passiveSkill1 = passiveSkill1;
	}
	
	public String getPassiveSkill2() {
		return this.passiveSkill2;
	}

	public void setPassiveSkill2(String passiveSkill2) {
		this.passiveSkill2 = passiveSkill2;
	}
	
	public String getPassiveSkill3() {
		return this.passiveSkill3;
	}

	public void setPassiveSkill3(String passiveSkill3) {
		this.passiveSkill3 = passiveSkill3;
	}
	
	public String getPassiveSkill4() {
		return this.passiveSkill4;
	}

	public void setPassiveSkill4(String passiveSkill4) {
		this.passiveSkill4 = passiveSkill4;
	}
	
	public String getPassiveSkill5() {
		return this.passiveSkill5;
	}

	public void setPassiveSkill5(String passiveSkill5) {
		this.passiveSkill5 = passiveSkill5;
	}
	
	public String getPassiveSkill6() {
		return this.passiveSkill6;
	}

	public void setPassiveSkill6(String passiveSkill6) {
		this.passiveSkill6 = passiveSkill6;
	}
	

	@Override
	public String toString() {
		return "RoleToSkillTemplateVO[roleSn=" + roleSn + ",name=" + name + ",petSkill=" + petSkill + ",passiveSkill1=" + passiveSkill1 + ",passiveSkill2=" + passiveSkill2 + ",passiveSkill3=" + passiveSkill3 + ",passiveSkill4=" + passiveSkill4 + ",passiveSkill5=" + passiveSkill5 + ",passiveSkill6=" + passiveSkill6 + ",]";

	}
}