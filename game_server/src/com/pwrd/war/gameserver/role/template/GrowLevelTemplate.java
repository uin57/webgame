/**
 * 
 */
package com.pwrd.war.gameserver.role.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;

/**
 * 
 * 角色等级对应属性模版
 * @author dengdan
 *
 */
@ExcelRowBinding
public class GrowLevelTemplate extends GrowLevelTemplateVO {

	/* (non-Javadoc)
	 * @see com.pwrd.war.core.template.TemplateObject#check()
	 */
	@Override
	public void check() throws TemplateConfigException {
		// TODO Auto-generated method stub

	}

}
