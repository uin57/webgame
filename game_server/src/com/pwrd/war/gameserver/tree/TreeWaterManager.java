package com.pwrd.war.gameserver.tree;

import java.util.ArrayList;
import java.util.List;

import com.pwrd.war.core.util.Assert;
import com.pwrd.war.core.util.KeyUtil;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.db.dao.HumanDao;
import com.pwrd.war.db.dao.TreeWaterDao;
import com.pwrd.war.db.model.TreeWaterEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;

/**
 * 神秘商店管理器
 * 
 * @author xf
 */
public class TreeWaterManager {
	private HumanDao humanDao;
	private TreeWaterDao treeWaterDao;
	private Human human;
	private List<HumanTreeWaterInfo> treeWaterInfos;

	public TreeWaterManager(Human human) {
		Assert.notNull(human);
		this.human = human;
		treeWaterInfos = new ArrayList<HumanTreeWaterInfo>();
		humanDao = Globals.getDaoService().getHumanDao();
		treeWaterDao = Globals.getDaoService().getTreeWaterDao();
		this.load();
	}

	// 初始化,从数据库读取每日任务信息列表
	public void load() {
		this.treeWaterInfos.clear();
		long time = TimeUtils.getTodayBegin(Globals.getTimeService());
		List<TreeWaterEntity> list = treeWaterDao.getTreeWaterInfosByHumanId(human.getCharId(), time);
		for(TreeWaterEntity t : list){
			this.addEntityToList(t);
		}
	}

	/**
	 * 将entity保存到内存
	 * 
	 * @author xf
	 */
	public void addEntityToList(TreeWaterEntity entity) {
		HumanTreeWaterInfo info = new HumanTreeWaterInfo(human);
		info.fromEntity(entity);
		info.setInDb(true);
		info.getLifeCycle().activate();
		this.treeWaterInfos.add(info);
	}

	public void addTowerInfoToDb(Human human, Human toWater) {
		if(human == null || toWater == null){
			return;
		}
		HumanTreeWaterInfo info = new HumanTreeWaterInfo();
		
		info.setCharId(human.getCharId());
		info.setCharName(human.getName());
		info.setToCharId(toWater.getCharId());
		info.setToCharName(toWater.getName());
		info.setWaterTime(Globals.getTimeService().now());
		info.setDbId(KeyUtil.UUIDKey());
		
		TreeWaterEntity  entity = new TreeWaterEntity();
		entity = info.toEntity();
		this.treeWaterDao.save(entity);	
		
		if(Globals.getOnlinePlayerService().getPlayerById(toWater.getCharId()) != null){
			toWater.getTreeWaterManager().addHumanTreeWaterInfo(info);
		}
	}

	public HumanDao getHumanDao() {
		return humanDao;
	}

	public Human getHuman() {
		return human;
	}

	public void setHumanDao(HumanDao humanDao) {
		this.humanDao = humanDao;
	}

	public void setHuman(Human human) {
		this.human = human;
	}
	
	public void addHumanTreeWaterInfo(HumanTreeWaterInfo info){
		treeWaterInfos.add(info);
	}

	public TreeWaterDao getTreeWaterDao() {
		return treeWaterDao;
	}

	public void setTreeWaterDao(TreeWaterDao treeWaterDao) {
		this.treeWaterDao = treeWaterDao;
	}

	public List<HumanTreeWaterInfo> getTreeWaterInfos() {
		return treeWaterInfos;
	}

	public void setTreeWaterInfos(List<HumanTreeWaterInfo> treeWaterInfos) {
		this.treeWaterInfos = treeWaterInfos;
	}
}
