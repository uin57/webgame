package com.pwrd.war.gameserver.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pwrd.war.core.util.Assert;
import com.pwrd.war.core.util.KeyUtil;
import com.pwrd.war.db.dao.HumanDao;
import com.pwrd.war.db.dao.TreeInfoDao;
import com.pwrd.war.db.model.TreeInfoEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;

/**
 * 神秘商店管理器
 * 
 * @author xf
 */
public class TreeInfoManager {
	private HumanDao humanDao;
	private TreeInfoDao treeInfoDao;
	private Human human;
	private Map<String, HumanTreeInfo> treeInfos;

	public TreeInfoManager(Human human) {
		Assert.notNull(human);
		this.human = human;
		treeInfos = new HashMap<String, HumanTreeInfo>();
		humanDao = Globals.getDaoService().getHumanDao();
		treeInfoDao = Globals.getDaoService().getTreeInfoDao();
		this.load();
	}

	// 初始化,从数据库读取每日任务信息列表
	public void load() {
		this.treeInfos.clear();
		
		List<TreeInfoEntity> list = treeInfoDao.getTreeInfosByHumanId(human.getCharId());
		for(TreeInfoEntity t : list){
			this.addEntityToList(t);
		}
	}

	/**
	 * 将entity保存到内存
	 * 
	 * @author xf
	 */
	public void addEntityToList(TreeInfoEntity entity) {
		HumanTreeInfo info = new HumanTreeInfo(human);
		info.fromEntity(entity);
		info.setInDb(true);
		info.getLifeCycle().activate();
		this.treeInfos.put(entity.getCharId(), info);
	}

//	public void addTowerInfoToDb(HumanTreeInfo info) {
//		TreeInfoEntity entity = new TreeInfoEntity();
//		entity = info.toEntity();
//		entity.setCharId(this.human.getCharId());
//
//		this.treeInfoDao.save(entity);
//	}

	
	public TreeInfoEntity getTreeInfoEntity() {
		List<TreeInfoEntity> list = treeInfoDao
				.getTreeInfosByHumanId(human.getCharId());
		if (!list.isEmpty()) {
			TreeInfoEntity entity = list.get(0);
			return entity;
		} else {
			return null;
		}
	}
	
	public HumanTreeInfo creatHumanTreeInfo(){
		HumanTreeInfo info = new HumanTreeInfo();
		
		info.setCharId(human.getUUID());
		info.setOwner(human);
		info.setDbId(KeyUtil.UUIDKey());
		info.setShakeTimes(0);
		info.setLastShakeTime(0);
		info.setWaterTimes(0);
		info.setLastWaterTime(0);
		info.setFriendWaterTimes(0);
		info.setLastFriendWaterTime(0);
		info.setCurTreeExp(9);
		info.setTreeLevel(1);
		info.setFruitLevel(0);
		info.setMaxTreeExp(Globals.getTreeService().getTreeExpTemplateMap().get(1).getExp());
		info.setInDb(false);
		info.getLifeCycle().activate();
		info.setModified();
		
		this.addTreeInfo(info);

		return info;
	}
	
	public HumanTreeInfo creatOffLineHumanTreeInfo(){
		HumanTreeInfo info = new HumanTreeInfo();
		
		info.setCharId(human.getUUID());
		info.setOwner(human);
		info.setDbId(KeyUtil.UUIDKey());
		info.setShakeTimes(0);
		info.setLastShakeTime(0);
		info.setWaterTimes(0);
		info.setLastWaterTime(0);
		info.setFriendWaterTimes(0);
		info.setLastFriendWaterTime(0);
		info.setCurTreeExp(9);
		info.setTreeLevel(1);
		info.setFruitLevel(0);
		info.setMaxTreeExp(Globals.getTreeService().getTreeExpTemplateMap().get(1).getExp());
		info.setInDb(false);
			
		treeInfoDao.save(info.toEntity());
		return info;
	}

	public void updateOffLineHumanTreeInfo(TreeInfoEntity entity) {
		if (entity != null) {
			treeInfoDao.updateOffLineHumanTreeInfo(entity.getCharId(),
					entity.getCurTreeExp(), entity.getMaxTreeExp(),
					entity.getTreeLevel());
		}
	}
	
	public HumanDao getHumanDao() {
		return humanDao;
	}

	public Human getHuman() {
		return human;
	}

	public TreeInfoDao getTreeInfoDao() {
		return treeInfoDao;
	}

	public void setTreeInfoDao(TreeInfoDao treeInfoDao) {
		this.treeInfoDao = treeInfoDao;
	}

	public Map<String, HumanTreeInfo> getTreeInfos() {
		return treeInfos;
	}

	public void setTreeInfos(Map<String, HumanTreeInfo> treeInfos) {
		this.treeInfos = treeInfos;
	}

	public void setHumanDao(HumanDao humanDao) {
		this.humanDao = humanDao;
	}

	public void setHuman(Human human) {
		this.human = human;
	}
	
	public void addTreeInfo(HumanTreeInfo info){
		this.treeInfos.put(info.getCharId(), info);
	}
}
