package com.pwrd.war.gameserver.role.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 人物境界模版类
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class JingJieTemplateVO extends TemplateObject {

	/** 境界值 */
	@ExcelCellBinding(offset = 1)
	protected Integer jingjie;

	/** 境界名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/** 需要成长值 */
	@ExcelCellBinding(offset = 3)
	protected Integer grow;

	/** 攻击 */
	@ExcelCellBinding(offset = 4)
	protected Double atk;

	/** 防御 */
	@ExcelCellBinding(offset = 5)
	protected Double def;

	/** 血量 */
	@ExcelCellBinding(offset = 6)
	protected Double hp;

	/** 伤害 */
	@ExcelCellBinding(offset = 7)
	protected Double shanghai;


	public Integer getJingjie() {
		return this.jingjie;
	}

	public void setJingjie(Integer jingjie) {
		if (jingjie == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[境界值]jingjie不可以为空");
		}	
		this.jingjie = jingjie;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[境界名称]name不可以为空");
		}
		this.name = name;
	}
	
	public Integer getGrow() {
		return this.grow;
	}

	public void setGrow(Integer grow) {
		if (grow == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[需要成长值]grow不可以为空");
		}	
		this.grow = grow;
	}
	
	public Double getAtk() {
		return this.atk;
	}

	public void setAtk(Double atk) {
		if (atk == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[攻击]atk不可以为空");
		}	
		this.atk = atk;
	}
	
	public Double getDef() {
		return this.def;
	}

	public void setDef(Double def) {
		if (def == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[防御]def不可以为空");
		}	
		this.def = def;
	}
	
	public Double getHp() {
		return this.hp;
	}

	public void setHp(Double hp) {
		if (hp == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[血量]hp不可以为空");
		}	
		this.hp = hp;
	}
	
	public Double getShanghai() {
		return this.shanghai;
	}

	public void setShanghai(Double shanghai) {
		if (shanghai == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[伤害]shanghai不可以为空");
		}	
		this.shanghai = shanghai;
	}
	

	@Override
	public String toString() {
		return "JingJieTemplateVO[jingjie=" + jingjie + ",name=" + name + ",grow=" + grow + ",atk=" + atk + ",def=" + def + ",hp=" + hp + ",shanghai=" + shanghai + ",]";

	}
}