package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 修炼标志配置模版
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class XiulianSymbolTemplateVO extends TemplateObject {

	/** $field.comment */
	@ExcelCellBinding(offset = 1)
	protected int symbolId;

	/** $field.comment */
	@ExcelCellBinding(offset = 2)
	protected String symbolName;

	/** $field.comment */
	@ExcelCellBinding(offset = 3)
	protected String symbolIcon;

	/** $field.comment */
	@ExcelCellBinding(offset = 4)
	protected float expRate;

	/** 可被采集次数 */
	@ExcelCellBinding(offset = 5)
	protected int collectTimes;

	/** 附加参数, */
	@ExcelCellBinding(offset = 6)
	protected String params;


	public int getSymbolId() {
		return this.symbolId;
	}

	public void setSymbolId(int symbolId) {
		if (symbolId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[$field.comment]symbolId的值不得小于0");
		}
		this.symbolId = symbolId;
	}
	
	public String getSymbolName() {
		return this.symbolName;
	}

	public void setSymbolName(String symbolName) {
		this.symbolName = symbolName;
	}
	
	public String getSymbolIcon() {
		return this.symbolIcon;
	}

	public void setSymbolIcon(String symbolIcon) {
		this.symbolIcon = symbolIcon;
	}
	
	public float getExpRate() {
		return this.expRate;
	}

	public void setExpRate(float expRate) {
		if (expRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[$field.comment]expRate的值不得小于0");
		}
		this.expRate = expRate;
	}
	
	public int getCollectTimes() {
		return this.collectTimes;
	}

	public void setCollectTimes(int collectTimes) {
		if (collectTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[可被采集次数]collectTimes的值不得小于0");
		}
		this.collectTimes = collectTimes;
	}
	
	public String getParams() {
		return this.params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	

	@Override
	public String toString() {
		return "XiulianSymbolTemplateVO[symbolId=" + symbolId + ",symbolName=" + symbolName + ",symbolIcon=" + symbolIcon + ",expRate=" + expRate + ",collectTimes=" + collectTimes + ",params=" + params + ",]";

	}
}