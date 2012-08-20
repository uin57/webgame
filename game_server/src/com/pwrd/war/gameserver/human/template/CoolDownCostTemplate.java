package com.pwrd.war.gameserver.human.template;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.exception.ConfigException;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.gameserver.human.enums.CoolType;
import com.pwrd.war.gameserver.human.enums.SubCoolType;

@ExcelRowBinding
public class CoolDownCostTemplate extends CoolDownCostTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		if(this.coolType == CoolType.NONE){
			
			throw new ConfigException("类型配置错误");
		}
	}

	private CoolType coolType = CoolType.NONE;
	private SubCoolType subCoolType = SubCoolType.NONE;
	@Override
	public void setCoolType(int coolType) { 
		super.setCoolType(coolType);
		this.coolType = CoolType.getCoolTypeById(coolType);
	}

	public CoolType getCoolTypeEnum() {
		return coolType;
	}
	
	
	public int getNeedTime(int level){
		int needt = this.getFixParam();
		if(this.getLevelParam() != 0){
			needt += this.getLevelParam() * level;
		}
		return needt;
	}
	
	public int getNeedGold(int needt){
		return needt / this.getTimeUnit() * this.getGoldUnit();
	}

	@Override
	public void setSubType(int subType) { 
		super.setSubType(subType);
		this.subCoolType = SubCoolType.getCoolTypeById(subType);
	}

	public SubCoolType getSubCoolType() {
		return subCoolType;
	}
	
	/**
	 * 返回该队列最多可共有几个
	 */
	public int getTypeCount(){
		if(StringUtils.isEmpty(this.getOtherOpenGold())){
			return 1;
		}
		return 1 + this.getOtherOpenGold().split(",").length;
	}
	
	/**
	 * 获取开启队列需要的元宝数目
	 */
	public int[] getOpenNeedGold(){
		if(StringUtils.isEmpty(this.getOtherOpenGold())){
			return new int[]{};
		}
		String[] s = this.getOtherOpenGold().split(",");
		int[] r = new int[s.length];
		int i = 0;
		for(String s1 : s){
			r[i++] = Integer.valueOf(s1);
		}
		return r;
	}
}
