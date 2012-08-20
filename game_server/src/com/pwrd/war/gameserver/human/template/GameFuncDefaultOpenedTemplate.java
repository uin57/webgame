package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;

/**
 * 游戏功能默认开放配置
 * 
 * @author haijiang.jin 
 * 
 */
@ExcelRowBinding
public class GameFuncDefaultOpenedTemplate extends GameFuncDefaultOpenedTemplateVO {
	@Override
	public void check() throws TemplateConfigException {
	}
}
