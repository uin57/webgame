package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.gameserver.common.container.Bag.BagType;

@ExcelRowBinding
public class BagExpandTemplateTemplate extends BagExpandTemplateTemplateVO {
  
	@Override
	public void check() throws TemplateConfigException { 

	}
	
	private BagType bagType;

	@Override
	public void setBagType(int bagType) { 
		super.setBagType(bagType);
		this.bagType = BagType.valueOf(bagType);
	}

	public BagType getBagTypeEnum() {
		return bagType;
	}
	
	
	 
}
