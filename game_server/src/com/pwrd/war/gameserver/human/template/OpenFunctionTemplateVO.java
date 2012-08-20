package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 开放功能模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class OpenFunctionTemplateVO extends TemplateObject {

	/** 功能ID */
	@ExcelCellBinding(offset = 1)
	protected int funcid;

	/** 功能名称 */
	@ExcelCellBinding(offset = 2)
	protected String funcname;

	/** 开启等级 */
	@ExcelCellBinding(offset = 3)
	protected int level;

	/** 需要完成的副本 */
	@ExcelCellBinding(offset = 4)
	protected String repSN;


	public int getFuncid() {
		return this.funcid;
	}

	public void setFuncid(int funcid) {
		this.funcid = funcid;
	}
	
	public String getFuncname() {
		return this.funcname;
	}

	public void setFuncname(String funcname) {
		if (StringUtils.isEmpty(funcname)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[功能名称]funcname不可以为空");
		}
		this.funcname = funcname;
	}
	
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getRepSN() {
		return this.repSN;
	}

	public void setRepSN(String repSN) {
		this.repSN = repSN;
	}
	

	@Override
	public String toString() {
		return "OpenFunctionTemplateVO[funcid=" + funcid + ",funcname=" + funcname + ",level=" + level + ",repSN=" + repSN + ",]";

	}
}