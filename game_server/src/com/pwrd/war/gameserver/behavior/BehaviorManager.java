package com.pwrd.war.gameserver.behavior;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.pwrd.war.core.template.TemplateService;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.gameserver.behavior.template.BehaviorTemplate;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.JsonPropDataHolder;

/**
 * 行为记录管理器
 * 
 * @author haijiang.jin
 *
 */
public class BehaviorManager implements JsonPropDataHolder{

	/** 玩家角色 */
	private Human human = null;
	/** 行为记录字典 */
	private Map<BehaviorTypeEnum, BehaviorRecord> behaviorMap;

	/**
	 * 类参数构造器
	 * 
	 * @param human
	 * @throws IllegalArgumentException if human == null
	 * 
	 */
	public BehaviorManager(Human human) {
		if (human == null) {
			throw new IllegalArgumentException("human is null");
		}

		this.human = human;
		this.behaviorMap = new HashMap<BehaviorTypeEnum, BehaviorRecord>();

		for (BehaviorTypeEnum behaviorType : BehaviorTypeEnum.values()) {
			// 设置行为记录字典
			this.behaviorMap.put(
				behaviorType, new BehaviorRecord());
		}
	}

	/**
	 * 初始化
	 * 
	 * @param tempServ
	 */
	public void init(TemplateService tempServ) {
		if (tempServ == null) {
			return;
		}

		// 获取行为字典
		Map<Integer, BehaviorTemplate> behavTplMap = tempServ.getAll(
			BehaviorTemplate.class);

		if (behavTplMap == null) {
			return;
		}

		for (BehaviorTypeEnum behavType : BehaviorTypeEnum.values()) {
			// 获取行为配置模版
			BehaviorTemplate behavTpl = behavTplMap.get(
				behavType.getIndex());

			if (behavTpl == null) {
				continue;
			}

			// 获取行为记录
			BehaviorRecord br = this.behaviorMap.get(behavType);

			if (br == null) {
				continue;
			}

			// 操作次数上限
			br.setOpCountMax(behavTpl.getOpCountMax());
			// 重置时间
			br.setResetTime(behavTpl.getResetTime());
		}
	}

	/**
	 * 获取玩家角色
	 * 
	 * @return
	 */
	public Human getHuman() {
		return this.human;
	}

	/**
	 * 获取操作行为次数
	 * 
	 * @param behaviorType
	 * @return
	 * 
	 */
	public int getCount(BehaviorTypeEnum behaviorType) {
		if (behaviorType == null) {
			return Integer.MAX_VALUE;
		}

		if (!this.behaviorMap.containsKey(behaviorType)) {
			return Integer.MAX_VALUE;
		}

		// 获取行为记录
		BehaviorRecord br = this.behaviorMap.get(behaviorType);

		if (br == null) {
			return Integer.MAX_VALUE;
		}

		// 获取当前时间
		long currTime = Globals.getTimeService().now();
		// 获取上次操作时间
		long lastOpTime = br.getLastOpTime();
		
		if (TimeUtils.isSameDay(currTime, lastOpTime)) {
			// 如果是同一天
			return br.getOpCount();
		} else {
			// 如果不是同一天, 
			// 则直接返回 0
			return 0;
		}
	}
	
	/**
	 * 获取操作行为次数
	 * 
	 * @param behaviorType
	 * @return
	 * 
	 */
	public int getMaxCount(BehaviorTypeEnum behaviorType) {
		// 获取行为记录
		BehaviorRecord br = this.behaviorMap.get(behaviorType);
		return br.getOpCountMax();
	}
	
	/**
	 * 
	 * 根据行为返回重置时间
	 * 
	 * @param behaviorType
	 * @return
	 */
	public long getResetTime(BehaviorTypeEnum behaviorType){
		if (behaviorType == null) {
			return -1;
		}

		// 获取行为记录
		BehaviorRecord br = this.behaviorMap.get(behaviorType);

		if (br == null) {
			return -1;
		}
		
		long resetTime = TimeUtils.getTodayBegin(Globals.getTimeService()) + br.getResetTime();
		
		return resetTime;
	}
	

	/**
	 * 是否可以做指定类型的行为操作
	 * 
	 * @param behaviorType
	 * @return
	 * 
	 */
	public boolean canDo(BehaviorTypeEnum behaviorType) {
		if (behaviorType == null) {
			return false;
		}

		// 获取行为记录
		BehaviorRecord br = this.behaviorMap.get(behaviorType);

		if (br == null) {
			return false;
		}

		// 获取重置时间
		long resetTime = TimeUtils.getTodayBegin(Globals.getTimeService()) + br.getResetTime();
		// 获取上次操作时间
		long lastOpTime = br.getLastOpTime();
		
		if (lastOpTime < resetTime) {
			// 如果上次操作时间小于重置时间, 
			// 则将操作次数清零
			br.setOpCount(0);
			// 保存玩家信息
			this.human.setModified();
		}

		return br.getOpCount() < br.getOpCountMax();
	}

	/**
	 * 将指定行为类型的操作次数 +1
	 * 
	 * @param behaviorType 行为类型
	 * @return
	 * 
	 */
	public boolean doBehavior(BehaviorTypeEnum behaviorType) {
		if (!this.canDo(behaviorType)) {
			return false;
		}

		// 获取行为记录
		BehaviorRecord br = this.behaviorMap.get(behaviorType);

		// 令操作次数 +1, 操作时间等于当前时间
		br.setOpCount(br.getOpCount() + 1);
		br.setLastOpTime(Globals.getTimeService().now());

		// 保存玩家信息
		this.human.setModified();
		return true;
	}

	/**
	 * 序列化为 JSON 字符串
	 * 
	 * @return
	 */
	@Override
	public String toJsonProp() {
		// 行为类型列表
		BehaviorTypeEnum[] behavTypes = BehaviorTypeEnum.values();
		// JSON 数组
		JSONObject json = new JSONObject();

		for (BehaviorTypeEnum behavType : behavTypes) {
			if (behavType == BehaviorTypeEnum.UNKNOWN) {
				// 如果是未知行为类型, 
				// 则直接跳过
				continue;
			}
	
			// 获取行为记录
			BehaviorRecord br = this.behaviorMap.get(behavType);

			if (br == null) {
				continue;
			}

			// 以行为类型作为关键字
			String key = String.valueOf(behavType.getIndex());
			// 将行为记录序列化为 JSON
			String value = br.toJson();
			
			json.put(key, value);
		}

		return json.toString();
	}

	/**
	 * 从 JSON 字符串中反序列化
	 * 
	 * @param value
	 */
	@Override
	public void loadJsonProp(String value) {
		if (value == null) {
			return;
		}

		// 行为类型列表
		BehaviorTypeEnum[] behaviorTypes = BehaviorTypeEnum.values();
		// JSON 数组
		JSONObject json = JSONObject.fromObject(value);

		for (BehaviorTypeEnum behaviorType : behaviorTypes) {
			// 以行为类型作为关键字
			String key = String.valueOf(behaviorType.getIndex());

			if (!json.containsKey(key)) {
				continue;
			}

			// 获取行为记录 JSON 字符串
			String brStr = json.getString(key);
			// 创建行为记录
			BehaviorRecord br = this.behaviorMap.get(behaviorType);
			// 反序列化
			br.loadJson(brStr);
		}
	}

	/**
	 * 清除操作行为, <font color='#990000'>注意: 只能在 GM 命令类中使用!</font>
	 * 
	 * @param behaviorType 
	 * 
	 */
	public void gmClear(BehaviorTypeEnum behaviorType) {
		// 获取行为记录
		BehaviorRecord br = this.behaviorMap.get(behaviorType);

		if (br == null) {
			return;
		}

		// 操作数置 0
		br.setOpCount(0);
		// 保存玩家信息
		this.human.setModified();
	}
}
