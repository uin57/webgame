package com.pwrd.war.gameserver.behavior.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;

/**
 * 投资配置模板
 * 
 * @author haijiang.jin
 * 
 */
@ExcelRowBinding
public class BehaviorTemplate extends BehaviorTemplateVO {
	@Override
	public void check() throws TemplateConfigException {
	}
}