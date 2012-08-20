package com.pwrd.war.gameserver.story.template;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.gameserver.story.StoryEventType;

/**
 * 剧情触发配置模板
 * @author zy
 *
 */

@ExcelRowBinding
public class StoryTemplate extends StoryTemplateVO {
	/** 触发类型 */
	private StoryEventType eventType;
	
//	/** 下一个模板编号*/
//	private int nextId = -1;

	@Override
	public void check() throws TemplateConfigException {
		//验证触发类型
		eventType = StoryEventType.indexOf(type);
		if (eventType == null) {
			throw new TemplateConfigException("剧情触发配置", id, "触发类型错误：" + type);
		}
		
		//验证参数
		if (StringUtils.isEmpty(param)) {
			throw new TemplateConfigException("剧情触发配置", id, "触发参数为空");
		}
	}

	public StoryEventType getEventType() {
		return eventType;
	}
	
//	public int getNextId() {
//		return nextId;
//	}
//
//	public void setNextId(int nextId) {
//		this.nextId = nextId;
//	}

}
