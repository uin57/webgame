package com.pwrd.war.gameserver.story.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 剧情触发模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class StoryTemplateVO extends TemplateObject {

	/** 剧情id */
	@ExcelCellBinding(offset = 1)
	protected String storyId;

	/** 触发类型 */
	@ExcelCellBinding(offset = 2)
	protected int type;

	/** 触发参数 */
	@ExcelCellBinding(offset = 3)
	protected String param;

	/** 绑定任务 */
	@ExcelCellBinding(offset = 4)
	protected int questId;


	public String getStoryId() {
		return this.storyId;
	}

	public void setStoryId(String storyId) {
		if (StringUtils.isEmpty(storyId)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[剧情id]storyId不可以为空");
		}
		this.storyId = storyId;
	}
	
	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		if (type == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[触发类型]type不可以为0");
		}
		if (type < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[触发类型]type的值不得小于1");
		}
		this.type = type;
	}
	
	public String getParam() {
		return this.param;
	}

	public void setParam(String param) {
		if (StringUtils.isEmpty(param)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[触发参数]param不可以为空");
		}
		this.param = param;
	}
	
	public int getQuestId() {
		return this.questId;
	}

	public void setQuestId(int questId) {
		if (questId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[绑定任务]questId不可以为0");
		}
		this.questId = questId;
	}
	

	@Override
	public String toString() {
		return "StoryTemplateVO[storyId=" + storyId + ",type=" + type + ",param=" + param + ",questId=" + questId + ",]";

	}
}