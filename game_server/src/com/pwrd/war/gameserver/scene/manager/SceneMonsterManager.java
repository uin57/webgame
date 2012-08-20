package com.pwrd.war.gameserver.scene.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pwrd.war.common.HeartBeatAble;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.monster.MonsterService;
import com.pwrd.war.gameserver.monster.SharedMonsterInfo;
import com.pwrd.war.gameserver.monster.VisibleMonster;
import com.pwrd.war.gameserver.monster.enums.MonsterTriggerType;
import com.pwrd.war.gameserver.monster.msg.GCAddMonster;
import com.pwrd.war.gameserver.monster.msg.GCDelMonster;
import com.pwrd.war.gameserver.monster.msg.GCMonsterHpDec;
import com.pwrd.war.gameserver.monster.template.VisibleMonsterTemplate;
import com.pwrd.war.gameserver.rep.Rep;
import com.pwrd.war.gameserver.scene.Scene;

/**
 * 管理场景内怪物列表
 * 
 */
public class SceneMonsterManager implements HeartBeatAble {
	
	/** 当前场景对象 **/
	private Scene scene; 
	
	private MonsterService monsterService;
	//所有明雷，包括小动物和共享怪
	private Map<String, VisibleMonster> monsters = new HashMap<String, VisibleMonster>();
	//所有小动物
	private  List<VisibleMonster> smallAnimals = new ArrayList<VisibleMonster>();
	//分组信息，不包括小动物，包括共享怪
	private Map<Integer, List<VisibleMonster>> groupMonster =  new HashMap<Integer, List<VisibleMonster>>();
 
	/** 组id，只有属于该组的怪物才有心跳 从1开始连续往上 **/
	private int nowGroup = 1;
	//总的组数
	private int allGroupNum = 0;
	
	
	/** 该场景内怪物最高攻击力 **/
	private double maxAtk;
	/** 该场景内怪物最高防御力 **/
	private double maxDef;
	
	
	/** 是否开启怪物心跳 **/
	private boolean isAlive;
	 
	
	public SceneMonsterManager(Scene scene,  MonsterService monsterService) {
		this.scene = scene; 
		this.monsterService = monsterService;
		this.isAlive = true;//默认是活的
	}
	
	public void init(){
		List<VisibleMonsterTemplate> mons = monsterService.getVisibleMonsterTemplatesBySceneId(scene.getSceneId());
		if(mons != null){
			for(VisibleMonsterTemplate m : mons){
				VisibleMonster monster = null;
				 
				monster = new VisibleMonster(scene, m, monsterService, this);
				 
				monster.init();
				monsters.put(monster.getUUID(), monster);
				//如果是小动物,不放到怪物组里
				if(monster.getTemplate().getTriggerTypeEnum() == MonsterTriggerType.SMALL_ANIMAL){
					smallAnimals.add(monster);
				}else{
					if(groupMonster.containsKey(monster.getTemplate().getGroupIndex())){
					}else{
						groupMonster.put(monster.getTemplate().getGroupIndex(), new ArrayList<VisibleMonster>());
						allGroupNum++;
					}
					groupMonster.get(monster.getTemplate().getGroupIndex()).add(monster);
				}
				
			}
		}
		if(allGroupNum == 0)allGroupNum = 1;
	}
	 /**
	 * 取得任何状态的明雷
	 */
	public List<VisibleMonster> getAnyVisibleMonster(){
	    	List<VisibleMonster> monsters = new ArrayList<VisibleMonster>();
	    	for(Map.Entry<String, VisibleMonster> e: this.monsters.entrySet() ){
	    		VisibleMonster monster = e.getValue(); 
	    		monsters.add(e.getValue()); 
	    	}
	    	return monsters;
	    }
    /**
     * 取得所有活着的明雷
     */
    public List<VisibleMonster> getAllVisibleMonster(){
    	List<VisibleMonster> monsters = new ArrayList<VisibleMonster>();
    	for(Map.Entry<String, VisibleMonster> e: this.monsters.entrySet() ){
    		VisibleMonster monster = e.getValue();
    		if(monster.bAlive()){
    			monsters.add(e.getValue());
    		}
    	}
    	return monsters;
    }
    /**
     * 清除怪物数据
     * @author xf
     */
    public void destroy(){
    	this.groupMonster.clear();
    	this.monsters.clear();
    	this.smallAnimals.clear();
    }
	@Override
	public void heartBeat() {
		if(!isAlive){
			return;
		}
		//TODO 刷新怪物，如果怪物死亡，需要刷新
		if(this.scene instanceof Rep){
//			System.out.println("副本怪物服务...");
		}
		if(nowGroup > allGroupNum){
			return;
		}
		boolean needInit = false;
		if(allGroupNum > 1){
			//如果当前组的怪物全死了，心跳下一组
			boolean bAllDead = true;
			for(VisibleMonster  e : groupMonster.get(nowGroup)){ 
				if(!e.bRealDead()){
					bAllDead = false;
					break;
				}
			} 
			if(bAllDead && allGroupNum >= nowGroup){
				scene.onGrounMonsterDead(nowGroup);
				nowGroup++;		
				needInit = true;
			}
		}
		//只有当前组的怪物才刷新
		List<VisibleMonster> list = groupMonster.get(nowGroup);
		if(list != null){
			for(VisibleMonster  e : list){
				if(needInit){
					e.groupFresh();
				}
				e.heartBeat();
			}
		}
		//小动物
		for(VisibleMonster v : smallAnimals){
			v.heartBeat();
		}
	}
	/**
	 * 根据UUID获取明雷信息
	 * @author xf
	 */
	public VisibleMonster getVisibleMonster(String monsterUUID){
		VisibleMonster monster = monsters.get(monsterUUID);
		if(monster != null && monster.bAlive()){
			return monster;
		}
		return null;
	}

	/**
	 * 怪是否全死了
	 * @author xf
	 */
	public boolean bAllDead(){
		boolean bAllDead = false;
		//最后一组怪全死了，就全死了
		if(nowGroup >= allGroupNum){
			if(groupMonster.get(nowGroup) != null){
				for(VisibleMonster  e : groupMonster.get(nowGroup)){ 
					if(!e.bRealDead()){
						bAllDead = false;
						return bAllDead;
					}
				}
			}
			
			bAllDead = true;
		}
		return bAllDead;
	}

	public double getMaxAtk() {
		return maxAtk;
	}

	public void setMaxAtk(double maxAtk) {
		if(maxAtk > this.maxAtk){
			this.maxAtk = maxAtk;
		}
	}

	public double getMaxDef() {
		return maxDef;
	}

	public void setMaxDef(double maxDef) {
		if(maxDef > this.maxDef){
			this.maxDef = maxDef;
		}
	}

	/**
	 * 取得活着的怪物数量
	 * @author xf
	 */
	public int getAliveMonsterNum(){
		int count = 0; 
		if(nowGroup <= allGroupNum){ 
			for(VisibleMonster  e : groupMonster.get(nowGroup)){ 
				if(!e.bRealDead()){
					 count++;
				}
			} 
			for(int i = nowGroup + 1; i<= allGroupNum; i++){ 
				count += groupMonster.get(i).size();
			}
		}
		return count;
	}
	/**
	 * 添加怪物，怪物刷新时调用，通知其他玩家
	 * @author xf
	 */
	public void addMonster(VisibleMonster monster){
		GCAddMonster gcAddMonster = new GCAddMonster();
		gcAddMonster.setMonsterInfo(monster.toVisibleMonsterInfo());
		
		 
		this.scene.getPlayerManager().sendGCMessage(gcAddMonster, null);
		 
	}
	/**
	 * 怪物死亡时调用，通知场景内其他玩家
	 * @author xf
	 */
	public void onMonsterDead(VisibleMonster monster){ 
		GCDelMonster gcDelMonster = new GCDelMonster();
		gcDelMonster.setMonsterUUID(monster.getTemplate().getVisibleMonsterSn());
		if(monster.getTemplate().isShare()){
			//取得该地图的所有分先
			List<Scene> list = Globals.getSceneService().getScene(scene.getSceneId());
			for(Scene s : list){
				s.getPlayerManager().sendGCMessage(gcDelMonster, null);
			}
			this.scene.onMonsterDead(monster.getTemplate().getVisibleMonsterSn());
		}else{
			this.scene.getPlayerManager().sendGCMessage(gcDelMonster, null);
			this.scene.onMonsterDead(monster.getTemplate().getVisibleMonsterSn());
		}
	}
	
	/**
	 * 共享怪掉血
	 * @author xf
	 */
	public void onMonsterDecHp(VisibleMonster monster, int losshp){
		GCMonsterHpDec msg = new GCMonsterHpDec();
		msg.setAllHp( monster.getAllHp() );
		msg.setCurHp(monster.getCurHp());
		msg.setLossHp(losshp);
		msg.setMonsterUUID(monster.getTemplate().getVisibleMonsterSn());
		//取得该地图的所有分先
		List<Scene> list = Globals.getSceneService().getScene(scene.getSceneId());
		for(Scene s : list){
			s.getPlayerManager().sendGCMessage(msg, null);
		}
	}
	
	/**
	 * 删除共享怪
	 * @author xf
	 */
	public static void realeasSharedMonster(String sceneId){
		Iterator<Map.Entry<String, SharedMonsterInfo>> it = VisibleMonster.getSharedInfo().entrySet().iterator();
		while(it.hasNext()){
			String k = it.next().getKey();
			if(k.startsWith(sceneId+"_")){
				it.remove();
			}
		}
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

}
