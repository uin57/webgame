package com.pwrd.war.gameserver.story;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.story.template.StoryTemplate;

/**
 * 
 * 全部剧情触发配置模板管理器，列表结构保存，也可通过id快查
 * 
 * 
 */
public class StoryService implements InitializeRequired {
	/** 通过剧情id查找剧情触发配置对象的映射表 */
	private Map<String, StoryTemplate> storyMap;

	public StoryService() {
		storyMap = new TreeMap<String, StoryTemplate>();
	}

	/**
	 * 通过任务Id获取剧情触发配置
	 * 
	 * @param storyId
	 * @return
	 */
	public StoryTemplate getStoryById(int storyId) {
		return storyMap.get(storyId);
	}
	
//	/**
//	 * 获取全部剧情触发配置列表迭代器，按照id排序
//	 * @return
//	 */
//	public Iterator<Map.Entry<Integer, StoryTemplate>> getItorator() {
//		return storyMap.entrySet().iterator();
//	}
	
	@Override
	public void init() {
		//获取所有剧情触发配置模板	
//		Collection<StoryTemplate> temps = Globals.getTemplateService().getAll(StoryTemplate.class).values();
//		int privId = -1;
//		for (StoryTemplate template : temps) {
//			//加入映射表
//			int storyId = template.getId();
//			storyMap.put(storyId, template);
//			
//			//设置连续关系
//			if (privId > 0 && storyMap.containsKey(privId)) {
//				StoryTemplate privTemp = storyMap.get(privId);
//				privTemp.setNextId(storyId);
//			}
//			
//			//保存上一个剧情编号
//			privId = storyId;
//		}
		Map<Integer, StoryTemplate> map = Globals.getTemplateService().getAll(StoryTemplate.class);
		storyMap.clear();
		for(Map.Entry<Integer, StoryTemplate> m :map.entrySet()){
			storyMap.put(m.getValue().getStoryId(), m.getValue());
		}
	}
	
	public StoryTemplate getStoryIdByEvent(StoryEventType eventType, String para){
		StoryTemplate result = null;
		for(Map.Entry<String, StoryTemplate> s : storyMap.entrySet()){
			if(s.getValue().getEventType() == eventType && s.getValue().getParam().equals(para)){
				result = s.getValue();
			}
		}
		return result;
	}
	
//	public List<Integer> getQuestByPara(String para){
//		List<Integer> questList = new ArrayList<Integer>();
//		for(Map.Entry<String, StoryTemplate> m : storyMap.entrySet()){
//			if(m.getValue().getParam().equals(para)){
//				questList.add(m.getValue().getQuestId());
//			}
//		}
//		
//		return questList;
//	}
}