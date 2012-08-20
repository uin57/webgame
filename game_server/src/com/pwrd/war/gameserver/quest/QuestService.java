package com.pwrd.war.gameserver.quest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.common.exception.ConfigException;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.quest.template.QuestTemplate;
import com.pwrd.war.gameserver.story.template.StoryTemplate;

/**
 * 
 * 全部任务模板管理器，树状结构保存，也可通过questId快查
 * 
 * 
 */
public class QuestService implements InitializeRequired {
	/** 通过任务id查找任务对象的映射表 */
	private Map<Integer, AbstractQuest> questMap;

	public QuestService() {
		questMap = new HashMap<Integer, AbstractQuest>();
	}

	/**
	 * 通过任务Id获取任务
	 * 
	 * @param questId
	 * @return
	 */
	public AbstractQuest getQuestById(int questId) {
		return questMap.get(questId);
	}
	
	/**
	 * 获取全部任务列表
	 * @return
	 */
	public Collection<AbstractQuest> getQuests() {
		return questMap.values();
	}
	
	@Override
	public void init() {
		//获取所有普通任务模板
		Collection<QuestTemplate> temps = Globals.getTemplateService().getAll(QuestTemplate.class).values();
		for (QuestTemplate template : temps) {
			//转换任务类型
			AbstractQuest quest = convertQuest(template);
			if (quest == null) {
				throw new ConfigException("发现未知的任务类型:" + template.getQuestType() + ", questId=" + template.getQuestId());
			}
			
			//加入任务映射表
			questMap.put(quest.getId(), quest);
		}
		
		//构造普通任务树结构关系，组装前后置任务
		for (AbstractQuest quest : questMap.values()) {
			String preQuestIds = quest.getPreQuestIds();
			if (StringUtils.isNotEmpty(preQuestIds)) {
				String[] ids = preQuestIds.split(",");
				for (String id : ids) {
					int preQuestId = Integer.valueOf(id);
					AbstractQuest preQuest = questMap.get(preQuestId);
					if (preQuest != null) {
						quest.addPreQuest(preQuest);
						preQuest.addNextQuest(quest);
					} else {
						throw new ConfigException("错误的前置任务, preId=" + preQuestId + ", questId=" + quest.getId());
					}
				}
			}
		}
	}
	
	/**
	 * 将任务模板对象转换为相应的子类对象
	 * @param template
	 * @return
	 */
	private AbstractQuest convertQuest(QuestTemplate template) {
		//根据任务类型转换为对应的子类对象
		switch (template.getQuestTypeEnum()) {
		case MAIN:
		case BRANCH:	//主线支线任务转换为普通任务类型
			return new CommonQuest(template);
			
		case DAILY:		//日常任务类型
			//TODO 实现日常任务类型，暂时使用普通任务代替
			return new CommonQuest(template);
			
		default:		//错误的类型返回空对象
			return null;
		}
	}

	public List<Integer> getQuestByPara(String para){
		List<Integer> questList = new ArrayList<Integer>();
		for(Map.Entry<Integer, AbstractQuest> m : questMap.entrySet()){
			if(m.getValue().getTemplate().getRepId().equals(para)){
				questList.add(m.getValue().getTemplate().getQuestId());
			}
		}
	
		return questList;
	}
}