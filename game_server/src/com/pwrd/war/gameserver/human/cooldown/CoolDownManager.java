package com.pwrd.war.gameserver.human.cooldown;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pwrd.war.db.model.CooldownEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.enums.CoolType;
import com.pwrd.war.gameserver.human.msg.GCQueueInfo;
import com.pwrd.war.gameserver.human.template.CoolDownCostTemplate;

public class CoolDownManager {
	
	/** 所有的冷却队列，key格式(type_index) **/
	private  Map<String, Cooldown> cools = new HashMap<String, Cooldown>();
	
	/** 每个冷却队列的数量 **/
	private  Map<CoolType, Integer> coolTypeNum = new HashMap<CoolType, Integer>();
	private Human owner;
	
	public CoolDownManager(Human human){
		this.owner = human;
	}
	
	public void init(){
		//初始化默认值
		for(CoolType cool : CoolType.values()){
			if(cool != CoolType.NONE){
				if(!coolTypeNum.containsKey(cool)){
					coolTypeNum.put(cool, 0);
				}
			}
		} 
		
		//从数据库读取
		List<CooldownEntity> list = Globals.getDaoService().getCooldownDao().getByHuman(owner.getUUID());
		if(list == null || list.size() == 0){		
			for(CoolType cool : CoolType.values()){
				if(cool != CoolType.NONE){
					CoolDownCostTemplate tmp = Globals.getHumanService().getCoolTemplateByCoolType(cool);
					if(tmp == null)continue;
					int allc = tmp.getTypeCount();
					if(allc == 1){
						this.addCool(cool, coolTypeNum.get(cool),  false);						
					}else{
						this.addCool(cool, coolTypeNum.get(cool),  false);
						this.addCool(cool, coolTypeNum.get(cool),  false);
					}
				}
			} 
		}
		for(CooldownEntity c : list){
			Cooldown cool = new Cooldown(owner);
			String key = c.getCoolType() + "_" + c.getIndex();
			cool.fromEntity(c);
			cools.put(key, cool);			
			CoolType type = CoolType.getCoolTypeById(cool.getCoolType());
			coolTypeNum.put(type, coolTypeNum.get(type) + 1);
		}
//		//看看装备是不是第一次初始化
//		int num = owner.getPropertyManager().getEquipEnhanceNum(); 
//		int equipNum = coolTypeNum.get(CoolType.ENHANCE_EQUIP);
//		if(equipNum < num){
//			//需要增加默认的
//			num = num - equipNum;
//			for(int i = equipNum;i < num; i++){  
//				this.addCool(CoolType.ENHANCE_EQUIP, i, false);
//			}
//		}
//		//人物属性提升也是1个队列
//		int roleGrowUpNum = coolTypeNum.get(CoolType.GROW_UP_ROLE);
//		if(roleGrowUpNum < 1){
//			this.addCool(CoolType.GROW_UP_ROLE, 0, false);
//		}
//		
//		//竞技场队列，在没有冷却时加入默认冷却以便前端显示
//		int arenaNum = coolTypeNum.get(CoolType.ARENA_FIGHT);
//		if (arenaNum < 1) {
//			this.addCool(CoolType.ARENA_FIGHT, 0, false);
//		}
//		
//		//研究也是1个队列		
//		int developNum = coolTypeNum.get(CoolType.DEVELOP);
//		if(developNum < 1){
//			this.addCool(CoolType.DEVELOP, 0, false);
//		}
	}
	
	/**
	 * 增加一个空的队列，是否发送消息
	 * @author xf
	 */
	private void addCool(CoolType coolType, int index, boolean sendMessage){
		Cooldown cool = new Cooldown(owner);
		cool.setHumanUUID(owner.getUUID());
		cool.setIndex((coolType.getId() - 1)*100 + index);
		cool.setCoolType(coolType.getId());
		if(coolTypeNum.get(coolType) > 0){			
			cool.setStartTime(-1);
			cool.setEndTime(-1);
		}else{
			cool.setStartTime(0);
			cool.setEndTime(0);
		}
		String key = cool.getCoolType() + "_" + cool.getIndex();
		cools.put(key, cool);
		
		
		Globals.getDaoService().getCooldownDao().saveOrUpdate(cool.toEntity());		
		coolTypeNum.put(coolType, coolTypeNum.get(coolType) + 1);
		
		if(sendMessage){
			this.sendThisCoolMessage(cool);
		}
	}
	
	/**
	 * 增加一个强化队列，序号为最大序号
	 * @author xf
	 */
	public void addAEmptyCool(CoolType coolType){
		int num = coolTypeNum.get(coolType);
		for(int i = 1; i < num; i++){
			String key = coolType.getId()  + "_" + ((coolType.getId() - 1)*100 + i);
			Cooldown cool = cools.get(key);
			if(cool != null && cool.getStartTime()== -1 && cool.getEndTime() == -1){
				cool.setStartTime(0);
				cool.setEndTime(0);
				Globals.getDaoService().getCooldownDao().saveOrUpdate(cool.toEntity());
				this.sendThisCoolMessage(cool);
				CoolDownCostTemplate tmp = Globals.getHumanService().getCoolTemplateByCoolType(coolType);
				int allc = tmp.getTypeCount();
				if(num + 1 <= allc){
					this.addCool(coolType, i+1, true);
				}
			}
		}
//		this.addCool(coolType, coolTypeNum.get(coolType), true);
	}
	
	public boolean put(CoolType coolType,
			long startTime, long endTime) {
		int coolNumber = Globals.getHumanService().getCoolTemplateByCoolType(coolType).getTypeCount();
		return this.put(coolNumber, coolType, startTime, endTime);
	}
	/**
	 * 增加一个强化队列
	 * coolNumber指该类型总共有几个队列
	 * @author xf
	 */
	public boolean put(int coolNumber, CoolType coolType,
			long startTime, long endTime) {
		for (int i = 0; i < coolNumber; i++) {
			int index = (coolType.getId() - 1)*100 + i;
			String key = coolType.getId() + "_" + index; 
			if (cools.get(key) == null) {
				Cooldown coolDown = new Cooldown(owner);
				coolDown.setHumanUUID(owner.getUUID());
				coolDown.setIndex((coolType.getId() - 1)*100 + i);
				coolDown.setCoolType(coolType.getId());
				coolDown.setStartTime(startTime);
				coolDown.setEndTime(endTime);
				cools.put(key, coolDown);
				
				Globals.getDaoService().getCooldownDao().saveOrUpdate(coolDown.toEntity());				
				this.sendThisCoolMessage(coolDown);
				 
				return true;
			}else if(Globals.getTimeService().now() > cools.get(key).getEndTime()){
				Cooldown coolDown = cools.get(key);
				coolDown.setStartTime(startTime);
				coolDown.setEndTime(endTime);
				
				Globals.getDaoService().getCooldownDao().saveOrUpdate(coolDown.toEntity());
				
				this.sendThisCoolMessage(coolDown);
				return true;
			}
		}
		return false;
	}

 
	/**
	 * 清除队列
	 * @author xf
	 */
	public void remove(CoolType coolType,  int index) {
		String key = coolType.getId() + "_" + index;
		Cooldown coolDown = cools.get(key);
		if(coolDown != null){
			coolDown.setEndTime(Globals.getTimeService().now());			
			Globals.getDaoService().getCooldownDao().saveOrUpdate(coolDown.toEntity());
			this.sendThisCoolMessage(coolDown);
		}
	}

	/**
	 * 返回剩余时间
	 * @author xf
	 */
	public int getCoolDownRemainTimeInSeconds(CoolType coolType, int index){
		String key = coolType.getId() + "_" + index;
		Cooldown coolDown = cools.get(key);
		if(coolDown == null)return  0;
		long t = coolDown.getEndTime() - Globals.getTimeService().now();
		if(t > 0)return (int) t/1000;
		return 0;
	}
	
	/**
	 * 是否有空队列
	 * @author xf
	 */
	public boolean hasEmptyCool( int coolNumber, CoolType coolType) {
		for (int i = 0; i < coolNumber; i++) {
			int index = (coolType.getId() - 1)*100 + i;
			String key = coolType.getId() + "_" + index;
			if(!cools.containsKey(key)) return true;
			Cooldown  c = cools.get(key); 
			if(Globals.getTimeService().now() > c.getEndTime()){
				return true;
			}
		}
		return false;
	}

	/**
	 * 发送一个队列消息到客户端
	 * @author xf
	 */
	private void sendThisCoolMessage(Cooldown c){
		GCQueueInfo msg = new GCQueueInfo();
		msg.setCdType(c.getCoolType());
		msg.setIndex(c.getIndex());
		msg.setIsShow((short) Globals.getHumanService().getCoolTemplateByCoolType(
				CoolType.getCoolTypeById(c.getCoolType())).getIsShowInQueue());
		if(c.getStartTime() == -1 && c.getEndTime() == -1){
			msg.setLeftTime(-1);
		}else{
			long now = Globals.getTimeService().now();
			if(now < c.getEndTime()){
				msg.setLeftTime((int) ((c.getEndTime() - now)/1000));
			}else{
				msg.setLeftTime(0);
			}
		}
		owner.sendMessage(msg);
	}
	/**
	 * 发送所有队列信息到前台
	 * 按照序号从小到达排序
	 * @author xf
	 */
	public void sendAllQueueMsg(){
		List<Cooldown> colls = new ArrayList<Cooldown>(cools.values());
		Collections.sort(colls, new Comparator<Cooldown>() { 
			@Override
			public int compare(Cooldown o1, Cooldown o2) { 
				String key1 = o1.getCoolType() + "_" + o1.getIndex();
				String key2 = o2.getCoolType() + "_" + o2.getIndex();
				return key1.compareTo(key2);
			}
			
		});
		for(Cooldown c : colls){
			this.sendThisCoolMessage(c);
		}
	}
}
