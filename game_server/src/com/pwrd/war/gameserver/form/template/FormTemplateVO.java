package com.pwrd.war.gameserver.form.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 阵型模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class FormTemplateVO extends TemplateObject {

	/** 职业 */
	@ExcelCellBinding(offset = 1)
	protected int vocation;

	/** 人物等级 */
	@ExcelCellBinding(offset = 2)
	protected int level;

	/** 开放位置 */
	@ExcelCellBinding(offset = 3)
	protected String positions;

	/** 功能是否生效 */
	@ExcelCellBinding(offset = 4)
	protected boolean buffValid;

	/** 最大上阵人数 */
	@ExcelCellBinding(offset = 5)
	protected int maxPos;


	public int getVocation() {
		return this.vocation;
	}

	public void setVocation(int vocation) {
		if (vocation == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[职业]vocation不可以为0");
		}
		this.vocation = vocation;
	}
	
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[人物等级]level不可以为0");
		}
		this.level = level;
	}
	
	public String getPositions() {
		return this.positions;
	}

	public void setPositions(String positions) {
		if (StringUtils.isEmpty(positions)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[开放位置]positions不可以为空");
		}
		this.positions = positions;
	}
	
	public boolean isBuffValid() {
		return this.buffValid;
	}

	public void setBuffValid(boolean buffValid) {
		this.buffValid = buffValid;
	}
	
	public int getMaxPos() {
		return this.maxPos;
	}

	public void setMaxPos(int maxPos) {
		if (maxPos == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[最大上阵人数]maxPos不可以为0");
		}
		this.maxPos = maxPos;
	}
	

	@Override
	public String toString() {
		return "FormTemplateVO[vocation=" + vocation + ",level=" + level + ",positions=" + positions + ",buffValid=" + buffValid + ",maxPos=" + maxPos + ",]";

	}
}