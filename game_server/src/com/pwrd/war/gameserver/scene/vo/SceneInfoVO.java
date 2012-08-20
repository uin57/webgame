package com.pwrd.war.gameserver.scene.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;

import com.pwrd.war.gameserver.scene.vo.SceneInfoVO.Block.Point;

/**
 * 地图VO对象
 * @author xf
 */
public class SceneInfoVO {
	/**  地图的ID */ 
	private String sceneId;
	
	/** 地图类型 **/
	private SceneType sceneType;
	/**  最多容纳多少玩家 **/
	private int maxPlayer = 50;
	/**  所属大区 */ 
	private int regionId;

	/**  区域名称 */ 
	private String name;

	/**  类型 */ 
	private int type;

	/**  进入最低等级要求 */ 
	private int needLevel;

	/**  进入最高等级限制 */ 
	private int maxLevel;

	/** 原始势力 */ 
	private int orignCamp;

//	/**  当前势力 */ 
//	private int nowCamp;

	/**  复活的地图ID */ 
	private String reliveMapId;

	/**  当前地图复活X坐标 */ 
	private int reliveX;

	/**  当前地图复活Y坐标 */ 
	private int reliveY;

	/**  切换地图到的地图ID-块信息 */ 
	private Map<String, SwitchInfo> nextMapId = new HashMap<String, SwitchInfo>();
	
	/** 切换入该地图的初始点x **/
	private int startX;
	/** 切换入该地图的初始点y **/
	private int startY;
	
	/** 所有地图 有阻挡的块**/
	private List<Block> blocks = new ArrayList<Block>();
	
	/** 每个块的起点对应的快，可根据起点查询 **/
	private Map<Point, Block> pointBlocks = new HashMap<Point, Block>();
	/**
	 * 判断x,y点是否在可行走区域
	 * @author xf
	 */
	public boolean isInMap(long diffTime, int srcx, int srcy, int tox, int toy){
		//TODO 
		return true;
//		diffTime /= 1000;
//		int speed = 10;//每秒10的速度
//		speed =  (int) (diffTime * speed) ;
//		speed = speed * speed;
//		int dx = tox - srcx;
//		int dy = toy - srcy;
//		if(speed < dx * dx + dy * dy){
//			//速度过快
//			return false;
//		}
//		tox = tox/Block.size * Block.size ;
//		toy = toy/Block.size * Block.size ;
//		if(pointBlocks.containsKey(new Point(tox, toy))){
//			return true;
//		}
//		return false;
	} 
	/**
	 * 判断x,y点是，获取要切换的目标地图，如果不能切换，则返回空
	 * switchId为当前切换点id
	 * @author xf
	 */
	public SwitchInfo switchScene(int x, int y, String switchId){
		//TODO
		SwitchInfo s = this.nextMapId.get(switchId);
		if(s == null)return null; 
//		if(s.x - Block.size * 3 <= x && s.x + Block.size* 3  >= x &&
//				s.y - Block.size* 3  <= y && s.y + Block.size* 3  >= y  ){ 
//				return s; 
//		} 
		return s;
	}
	/**************************/
	public int getStartX() {
		return startX;
	}
	public void setStartX(int startX) {
		this.startX = startX;
	}
	public int getStartY() {
		return startY;
	}
	public void setStartY(int startY) {
		this.startY = startY;
	}
	 
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getNeedLevel() {
		return needLevel;
	}
	public void setNeedLevel(int needLevel) {
		this.needLevel = needLevel;
	}
	public int getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	public int getOrignCamp() {
		return orignCamp;
	}
	public void setOrignCamp(int orignCamp) {
		this.orignCamp = orignCamp;
	}
//	public int getNowCamp() {
//		return nowCamp;
//	}
//	public void setNowCamp(int nowCamp) {
//		this.nowCamp = nowCamp;
//	}
	 
	public int getReliveX() {
		return reliveX;
	}
	public void setReliveX(int reliveX) {
		this.reliveX = reliveX;
	}
	public int getReliveY() {
		return reliveY;
	}
	public void setReliveY(int reliveY) {
		this.reliveY = reliveY;
	}
	 
	 
	 
	public String getSceneId() {
		return sceneId;
	}
	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}
	public String getReliveMapId() {
		return reliveMapId;
	}
	public void setReliveMapId(String reliveMapId) {
		this.reliveMapId = reliveMapId;
	}
	public Map<String, SwitchInfo> getNextMapId() {
		return nextMapId;
	}
	public void setNextMapId(Map<String, SwitchInfo> nextMapId) {
		this.nextMapId = nextMapId;
	}
	public List<Block> getBlocks() {
		return blocks;
	} 
	public void addBlock(Block block){
		blocks.add(block);
		pointBlocks.put(block.getPoint(), block);
	}
	
	/**
	 * 地图切换信息
	 * @author xf
	 */
	public static class SwitchInfo{
		public String id;//id
		public int x;//中点x
		public int y;//中点y
		public String nextId;//切换到另一个地图的切换点
		public String nextSceneId;
		public int toX;//切换后放置的点
		public int toY;//切换后放置的点
		@Override
		public String toString() {
			return "SwitchInfo [id=" + id + ", x=" + x + ", y=" + y
					+ ", nextId=" + nextId + ", nextSceneId=" + nextSceneId
					+ "]";
		}//切换到另一个地图的id	
		//x的随机范围内
		public Point getRPoint(){
			Point p = new Point(x, y);
			if(RandomUtils.nextBoolean()){
				if(RandomUtils.nextBoolean()){
					p.x -= 50;
					p.y = p.y - 100 + RandomUtils.nextInt(100);
				}else{
					p.x += 50;
					p.y = p.y - 100 + RandomUtils.nextInt(100);
				}
			}else{
				if(RandomUtils.nextBoolean()){
					p.y -= 50;
					p.x = p.x - 100 + RandomUtils.nextInt(100);
				}else{
					p.y += 50;
					p.x = p.x - 100 + RandomUtils.nextInt(100);
				}
			}
			return p;
		} 
	}
	/**
	 * 地图块
	 * @author xf
	 */
	public static class Block{
		public static int size = 32;//每个格子的大小i
		public int x;
		public int y;
		public int type; //类型，1阻挡的块
		
		public Point getPoint(){
			return new Point(this.x, this.y);
		}
		public static class Point{
			public Point(int x, int y){
				this.x = x;
				this.y = y;
			}
			public int x;
			public int y;
		}
	}
	public int getMaxPlayer() {
		return maxPlayer;
	}
	public void setMaxPlayer(int maxPlayer) {
		this.maxPlayer = maxPlayer;
	}
	
	public static enum SceneType{
		NORMAL(1),REP(5),ACTIVITY(9);
		private int type;
		private SceneType(int type){
			this.type = type;
		}
		public int getType() {
			return type;
		}
		public static SceneType getByType(int type){
			for(SceneType t : values()){
				if(t.getType() == type)return t;
			}
			return null;
		}
	}

	public SceneType getSceneType() {
		return sceneType;
	}
	public void setSceneType(SceneType sceneType) {
		this.sceneType = sceneType;
	}
}
