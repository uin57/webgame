package com.pwrd.war.gameserver.story;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.slf4j.Logger;

import com.pwrd.war.common.LogReasons.StoryLogReason;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.annotation.SyncIoOper;
import com.pwrd.war.core.orm.DataAccessException;
import com.pwrd.war.core.util.Assert;
import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.core.util.KeyUtil;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.db.model.FinishedStoryEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.GameDaoService;
import com.pwrd.war.gameserver.common.db.RoleDataHolder;
import com.pwrd.war.gameserver.common.log.GameErrorLogInfo;
import com.pwrd.war.gameserver.common.log.LogService;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.JsonPropDataHolder;
import com.pwrd.war.gameserver.story.msg.GCStoryList;
import com.pwrd.war.gameserver.story.msg.GCStoryShow;
import com.pwrd.war.gameserver.story.template.StoryTemplate;

/**
 * 剧情管理器，管理用户已经完成的剧情和即将进行的剧情
 * 
 */
public class StoryManager implements RoleDataHolder,JsonPropDataHolder {

	private final Logger logger = Loggers.StoryLogger;

	/** 任务日志所属玩家 */
	private Human owner;
	
	/** 已经完成的剧情列表 */
	private Map<String, FinishedStory> finishedMap;
	
//	/** 下一个剧情编号 */
//	private int nextStoryId;

	/** 游戏数据管理器 */
	private GameDaoService gameDaoService;
	
	/** 日志管理器 */
	private LogService logService;

	public StoryManager(Human owner, GameDaoService gameDaoService, LogService logService) {
		Assert.notNull(owner);
		this.owner = owner;
		
		this.finishedMap = new TreeMap<String, FinishedStory>();
//		this.nextStoryId = -1;
		
		this.gameDaoService = gameDaoService;
		this.logService = logService;
	}
	
	/**
	 * 加载玩家的剧情
	 */
	@SyncIoOper
	public void load() {
		final String _charId = this.getOwner().getUUID();
		try {
			//加载已经完成的剧情
			List<FinishedStoryEntity> _finishedStories = this.gameDaoService.getFinishedStoryDao().loadByCharId(_charId);
			if (_finishedStories != null) {
				for (FinishedStoryEntity _finishedStory : _finishedStories) {
					String _storyId = _finishedStory.getStoryId();
					FinishedStory story = new FinishedStory(this.owner, _storyId);
					story.fromEntity(_finishedStory);
					story.getLifeCycle().activate();
					this.finishedMap.put(_storyId, story);
				}
			}
			
//			//计算下一个剧情编号
//			Iterator<Map.Entry<Integer, StoryTemplate>> itor = Globals.getStoryService().getItorator();
//			while (itor.hasNext()) {
//				Map.Entry<Integer, StoryTemplate> entry = itor.next();
//				int questId = entry.getKey();
//				if (!finishedMap.containsKey(questId)) {
////					nextStoryId = questId;
//					break;
//				}
//			}
//			
//			System.out.println(">>>>>>>>>>>>>>>> charId=" + this.owner.getId() + ", finishedMap.size="
//			+ this.finishedMap.size() + ", nextId=" + this.nextStoryId);
			
		} catch (DataAccessException e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(GameErrorLogInfo.DB_OPERATE_FAIL, "#GS.StoryManager.load", null), e);
			}
		}
	}
	
	@Override
	public void checkAfterRoleLoad() {
	}

	@Override
	public void checkBeforeRoleEnter() {
	}
	
	/**
	 * 获取人物
	 * 
	 * @return
	 */
	public Human getOwner() {
		return owner;
	}
	
	/**
	 * 发送已播放剧情列表消息
	 */
	public void sendStoriesList() {
		GCStoryList msg = new GCStoryList();
		String[] list = new String[this.finishedMap.size()];
		Iterator<Map.Entry<String, FinishedStory>> itor = this.finishedMap.entrySet().iterator();
		int ptr = 0;
		while (itor.hasNext()) {
			Map.Entry<String, FinishedStory> entry = itor.next();
			list[ptr] = entry.getKey();
			ptr ++;
		}
		msg.setCanShowList(list);
		this.owner.sendMessage(msg);
	}
	
	/**
	 * 触发剧情播放
	 * @param type
	 * @param param
	 */
	public void triggerStory(StoryEventType type, String param) {
		//获取下一个剧情信息
//		StoryTemplate next = Globals.getStoryService().getStoryById(nextStoryId);
		StoryTemplate next = Globals.getStoryService().getStoryIdByEvent(type, param);
		if (next != null && next.getEventType() == type && next.getParam().equals(param) && !this.finishedMap.containsKey(next.getStoryId())
				&& owner.getQuestDiary().isDoingQuest(next.getQuestId())) {
			//剧情触发成功，更新数据库
			FinishedStory finished = new FinishedStory(this.owner, next.getStoryId());
			finished.setDbId(KeyUtil.UUIDKey());
			finished.setInDb(false);
			finished.getLifeCycle().activate();
			finished.setModified();
			
			//加入当前映射表
			this.finishedMap.put(next.getStoryId(), finished);
			
			//更新下一个剧情信息
//			nextStoryId = next.getNextId();
			
			//发送触发消息
			GCStoryShow msg = new GCStoryShow(next.getStoryId());
			this.owner.sendMessage(msg);
		}
		
	}
	
	@Override
	public String toJsonProp() {
		JSONObject json = new JSONObject();		
		return json.toString();	
	}
	
	@Override
	public void loadJsonProp(String jsonStr) {
	}
	
	/**
	 * 发送剧情日志
	 * @param human 玩家角色
	 * @param storyID 剧情ID
	 * @param reason 日志产生原因
	 * @param detailReason 附加信息
	 */
	public void sendTaskLog(Human human, int storyID, StoryLogReason reason, String detailReason) {
		try {
			//TODO 
//			logService.sendStoryLog(human, reason, detailReason, storyID);
		} catch (Exception e) {
			Loggers.questLogger.error(LogUtils.buildLogInfoStr(human.getUUID(), "记录任务日志时出错"), e);
		}
	}

}
