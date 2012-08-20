package com.pwrd.war.gameserver.quest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.core.util.EnumUtil;
import com.pwrd.war.core.util.JsonUtils;
import com.pwrd.war.db.model.DoingQuestEntity;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.quest.condition.IQuestCondition.QuestConditionType;

/**
 * 记录玩家的任务进度
 * 
 * 
 */
public class DoingQuest implements PersistanceObject<String, DoingQuestEntity> {

	/** 任务id */
	private AbstractQuest questTemplate;
	/** 所有者 */
	private Human owner;
	/** 数据库主键 */
	private String dbId;
	/** 是否已经在数据库中 */
	private boolean inDb;
	/** 生命期 */
	private final LifeCycle lifeCycle;
	/** 任务启动时间 */
	private Timestamp startTime;
	/** 任务状态 */
	private QuestStatus status;
	/** 任务各个目标的状态记录，以条件类型为key，值为条件状态，记录了条件目标值和当前值 */
	private Map<QuestConditionType, Map<String, Integer>> conditionParams;

	public DoingQuest(Human owner, AbstractQuest questTemplate) {
		super();
		this.questTemplate = questTemplate;
		this.owner = owner;
		this.lifeCycle = new LifeCycleImpl(this);
		this.status = QuestStatus.ACCEPTED;
		this.conditionParams = new HashMap<QuestConditionType, Map<String, Integer>>();
	}
	
	public void initParamFromTemplate() {
		this.conditionParams = this.questTemplate.initFinishCondition(this.owner);
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	
	public Timestamp getStartTime() {
		return this.startTime;
	}
	
	public void setStatus(QuestStatus status) {
		this.status = status;
	}
	
	public QuestStatus getStatus() {
		return this.status;
	}
	
	public int getQuestId() {
		return this.questTemplate.getId();
	}
	
	public AbstractQuest getTemplate() {
		return this.questTemplate;
	}
	
	public Human getOwner() {
		return this.owner;
	}
	
	public int getCount(QuestConditionType type, String param1) {
		Map<String, Integer> param = this.conditionParams.get(type);
		if (param != null && param.containsKey(param1)) {
			return param.get(param1);
		}
		return 0;
	}
	
	public boolean increaseParamsCount(QuestConditionType type, String param1, int count) {
		Map<String, Integer> param = this.conditionParams.get(type);
		if (param != null && param.containsKey(param1) && count > 0) {
			param.put(param1, param.get(param1) + count);
			this.setModified();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isInDb() {
		return this.inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.inDb = inDb;
	}
	
	@Override
	public String getGUID() {
		return this.dbId;
	}

	@Override
	public String getDbId() {
		return dbId;
	}

	@Override
	public void setDbId(String dbId) {
		this.dbId = dbId;
	}

	@Override
	public String getCharId() {
		if (owner != null) {
			return owner.getUUID();
		} else {
			return "";
		}
	}

	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	@Override
	public void setModified() {
		if (owner != null) {
			//为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive()) {
				// 物品的生命期处于活动状态,并且该宠物不是空的,则执行通知更新机制进行
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}

	/**
	 * 实例被删除,触发删除机制
	 */
	protected void onDelete() {
		this.lifeCycle.destroy();
		owner.getPlayer().getDataUpdater().addDelete(lifeCycle);
	}

	/**
	 * 通过数据库entity数据初始化条件状态
	 * @param list
	 */
	private void initParamFromEntity(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			int typeIndex = Integer.valueOf(map.get("type").toString());
			QuestConditionType type = QuestConditionType.indexOf(typeIndex);
			String param = map.get("param").toString();
			int count = Integer.valueOf(map.get("count").toString());
			if (type != null) {
				Map<String, Integer> params = conditionParams.get(type);
				if (params == null) {
					params = new HashMap<String, Integer>();
					conditionParams.put(type, params);
				}
				params.put(param, count);
			}
		}
	}
	
	/**
	 * 将初始化条件转换为数据库entity
	 * @return
	 */
	private List<Map<String, Object>> convertParamToList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Iterator<Map.Entry<QuestConditionType, Map<String, Integer>>> itor = conditionParams.entrySet().iterator();
		while (itor.hasNext()) {
			Map.Entry<QuestConditionType, Map<String, Integer>> entry = itor.next();
			QuestConditionType type = entry.getKey();
			Map<String, Integer> params = entry.getValue();
			if (params != null) {
				Iterator<Map.Entry<String, Integer>> pItor = params.entrySet().iterator();
				while(pItor.hasNext()) {
					Map.Entry<String, Integer> pEntry = pItor.next();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("type", type.getIndex());
					map.put("param", pEntry.getKey());
					map.put("count", pEntry.getValue());
					list.add(map);
				}
			}
		}
		return list;
	}
	
	@Override
	public void fromEntity(DoingQuestEntity entity) {
		this.setDbId(entity.getId());
		this.setInDb(true);
		this.setStartTime(entity.getStartTime());
		// 已完成个数转换
		List<Map<String, Object>> list = JsonUtils.jsonToList(entity.getProps());
		this.initParamFromEntity(list);
	}

	@Override
	public DoingQuestEntity toEntity() {
		DoingQuestEntity _task = new DoingQuestEntity();
		_task.setId(this.getDbId());
		_task.setStartTime(this.getStartTime());
		_task.setQuestId(this.getQuestId());
		_task.setCharId(this.getCharId());
		// 已完成个数转换
		List<Map<String, Object>> list = this.convertParamToList();
		String _props = JsonUtils.listToJson(list);
		_task.setProps(_props);
		
		return _task;
	}


	public static enum QuestStatus implements IndexedEnum {
		/** 默认状态 */
		NULL(0),
		/** 不可见状态 */
		INVISIBLE(1),
		/** 可接受状态 */
		CANACCEPT(2),
		/** 已接受但不可完成状态 */
		ACCEPTED(3),
		/** 可完成状态 */
		CANFINISH(4);

		private final int index;

		private QuestStatus(int index) {
			this.index = index;
		}

		@Override
		public int getIndex() {
			return index;
		}

		private static final List<QuestStatus> indexes = IndexedEnum.IndexedEnumUtil
				.toIndexes(QuestStatus.values());

		/**
		 * 根据指定的索引获取枚举的定义
		 * 
		 * @param index
		 * @return
		 */
		public static QuestStatus indexOf(final int index) {
			return EnumUtil.valueOf(indexes, index);
		}

	}
	
}
