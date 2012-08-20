package com.pwrd.war.gameserver.human.template;

import org.apache.commons.lang.math.RandomUtils;

import com.pwrd.war.common.constants.ProbabilityConstants;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;

@ExcelRowBinding
public class XiulianLevelTemplate extends XiulianLevelTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		// TODO Auto-generated method stub

	}
	
	public int getSymbolId(){
		int fenbu = RandomUtils.nextInt(ProbabilityConstants.MAX_PROB);
		if(fenbu >= this.getXiulianRangeStart1() && fenbu <= this.getXiulianRangeEnd1()){
			return this.getXiulianSymbol1();
		}else if(fenbu >= this.getXiulianRangeStart2() && fenbu <= this.getXiulianRangeEnd2()){
			return this.getXiulianSymbol2();
		}else if(fenbu >= this.getXiulianRangeStart3() && fenbu <= this.getXiulianRangeEnd3()){
			return this.getXiulianSymbol3();
		}else if(fenbu >= this.getXiulianRangeStart4() && fenbu <= this.getXiulianRangeEnd4()){
			return this.getXiulianSymbol4();
		}else if(fenbu >= this.getXiulianRangeStart5() && fenbu <= this.getXiulianRangeEnd5()){
			return this.getXiulianSymbol5();
		}
		return this.getXiulianSymbol1();				
		
	}

}
