package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;

/**
 * 游戏功能配置模版
 * 
 * @author haijiang.jin
 *
 */
@ExcelRowBinding
public class GameFuncTemplate extends GameFuncTemplateVO {
	@Override
	public void check() throws TemplateConfigException {
	}
}
