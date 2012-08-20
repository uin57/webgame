package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.gameserver.item.ConsumableFeature;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemDef;
import com.pwrd.war.gameserver.item.ItemDef.ConsumableFunc;
import com.pwrd.war.gameserver.item.ItemDef.Position;
import com.pwrd.war.gameserver.item.ItemFeature;

/**
 * 消耗品道具模板
 * 
 * 
 */
@ExcelRowBinding
public class ConsumeItemTemplate extends ItemTemplate {

	/** */
	private static final String ERROR_MSG = "函数参数配置错误";
	
	
	/** 装备对应的位置 */
	@ExcelCellBinding(offset = 4)
	private int type;
	
	private ItemDef.ConsumableFunc function;
	
	/** 函数功能 */
	@ExcelCellBinding(offset = (START_INDEX +1))
	private String functionId;

 
	/** 参数1*/
	@ExcelCellBinding(offset = (START_INDEX+2))
	private String param1;
	
	/** 参数2*/
	@ExcelCellBinding(offset = (START_INDEX+3))
	private String param2;
	
	/** 参数3*/
	@ExcelCellBinding(offset = (START_INDEX+4))
	private String param3;
	
	
	public ConsumeItemTemplate() {

	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
		this.function = ItemDef.ConsumableFunc.valueOf(this.functionId); 
		// function可以没有
		if (this.function == null) {
			this.function = ItemDef.ConsumableFunc.NULL;
		}
	}
 
	 

	public ItemDef.ConsumableFunc getFunction() {
		return function;
	}

	@Override
	public Position getPosition() {
		return Position.NULL;
	}

	@Override
	public boolean isEquipment() {
		return false;
	}

	@Override
	public void check() throws TemplateConfigException {
		if (function == ItemDef.ConsumableFunc.NULL) {
			return;
		}
		
	}
	
	@Override
	public void patchUp() {
		super.patchUp();
	}

	/**
	 * 检查某些字符串是否为整型
	 * @param funcName
	 * @param args
	 */
	@SuppressWarnings("unused")
	private void tryParseInt(String funcName, String... args) {
		try {
			for (String arg : args) {
				Integer.parseInt(arg);
			}
		} catch (Exception e) {
			throw new TemplateConfigException(this.getSheetName(), getId(), funcName
					+ ERROR_MSG);
		}
	}

	
	@Override
	public String toString() {
		return super.toString() + "\nConsumeItemTemplate [functionId="
				+ functionId + ", function=" + function ;
	}

	@Override
	public ItemFeature initItemFeature(Item item) {
//		ConsumableFeature feature = new ConsumableFeature(item);
// 
//		return feature;
		return null;
	}

	@Override
	public boolean getCanBeUsed() {
		ConsumableFunc fun = getFunction();
		if (fun != null && fun != ConsumableFunc.NULL) {
			return true;
		}
		return false;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		super.setTypeId(type);
	}
}
