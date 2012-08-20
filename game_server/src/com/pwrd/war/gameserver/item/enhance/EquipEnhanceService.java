package com.pwrd.war.gameserver.item.enhance;

import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;

import com.pwrd.war.core.msg.sys.CronScheduledMessage;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.item.ItemService;
import com.pwrd.war.gameserver.item.msg.GCEnhanceEquipInfo;
import com.pwrd.war.gameserver.player.ChannelService.ChannelType;

/**
 * 强化装备概率变化
 * 
 * @author xf
 */
public class EquipEnhanceService {
	private ItemService itemService;
	public static final int MAX_LEVEL = 69;//最大强化等级69
	public static final int FREE_GOLD = 5;//免费强化需要元宝5
	public static final int DOUBLE_GOLD = 10;//暴击强化需要元宝10
	/*******废弃*********
	private String dbID;
	private boolean isInDB;
	/** 当前成功率 **/
//	private double nowRate;
	/** 下次成功率 **/
//	private double nextRate;
	/** 下次成功率的对象 **/
//	private EquipEnhanceSuccesRate nextSuccesRate;
	/** 当前阶段可用的概率 **/
//	private List<EquipEnhanceSuccesRate> canUseRate;
	/** 已经使用过的概率 **/
//	private List<EquipEnhanceSuccesRate> usedRate;
	/*******废弃***********/
	
	
	private boolean isFree;//是否进入免费强化阶段
	
	private boolean isDouble;//是否进入暴击强化阶段
	
	private CronScheduledMessage freeStart;
	private CronScheduledMessage freeEnd;
	private CronScheduledMessage doubleStart;
	private CronScheduledMessage doubleEnd;
	
	public EquipEnhanceService(ItemService itemService) {
		this.itemService = itemService;
//		usedRate = new ArrayList<EquipEnhanceSuccesRateTemplate.EquipEnhanceSuccesRate>();
//		canUseRate = new ArrayList<EquipEnhanceSuccesRateTemplate.EquipEnhanceSuccesRate>();

	}
	
	public void start(){
		isFree = false;
		isDouble = false;
		Date now = new Date();
		freeStart = new CronScheduledMessage("0 15 * * * ?") {//0 15 12
			@Override
			public void execute() {
				super.execute();
				EquipEnhanceService.this.isFree = true;
				Globals.getOnlinePlayerService().getChannelService().sendGCMessageToChannel(getEquipInfoMsg(), ChannelType.EQUIP_ENHANCE);
			}
		}; 
		Globals.getScheduleService().schedule(freeStart);
		
		
		freeEnd = new CronScheduledMessage("0 30 * * * ?") {//0 30 12
			@Override
			public void execute() {
				super.execute();
				EquipEnhanceService.this.isFree = false;
				Globals.getOnlinePlayerService().getChannelService().sendGCMessageToChannel(getEquipInfoMsg(), ChannelType.EQUIP_ENHANCE);
			}
		}; 
		Globals.getScheduleService().schedule(freeEnd);
		
		
		doubleStart = new CronScheduledMessage("0 45 * * * ?") {//0 45 19
			@Override
			public void execute() {
				super.execute();
				EquipEnhanceService.this.isDouble = true;
				Globals.getOnlinePlayerService().getChannelService().sendGCMessageToChannel(getEquipInfoMsg(), ChannelType.EQUIP_ENHANCE);
			}
		}; 
		Globals.getScheduleService().schedule(doubleStart);
		
		doubleEnd = new CronScheduledMessage("0 0 * * * ?") {//0 0 20
			@Override
			public void execute() {
				super.execute();
				EquipEnhanceService.this.isDouble = false;
				Globals.getOnlinePlayerService().getChannelService().sendGCMessageToChannel(getEquipInfoMsg(), ChannelType.EQUIP_ENHANCE);
			}
		};
		Globals.getScheduleService().schedule(doubleEnd);
		long t0 = freeStart.getTrigger().getFireTimeAfter(now).getTime();
		long t1 = freeEnd.getTrigger().getFireTimeAfter(now).getTime();
		if(t0 > t1){
			freeStart.execute();
		}
		long t2 = doubleStart.getTrigger().getFireTimeAfter(now).getTime();
		long t3 = doubleEnd.getTrigger().getFireTimeAfter(now).getTime();
		if( t2 >= t3){
			doubleStart.execute();
		}
	}

	public boolean isFree() {
		return isFree;
	}

	public boolean isDouble() {
		return isDouble;
	}

	public boolean isFreeJustRate() {
		return  RandomUtils.nextInt(100) < 35;
	}
	public boolean isRealFree() {
		return isFree && RandomUtils.nextInt(100) < 35;
	}

	public boolean isRealDouble() {
		return isDouble && RandomUtils.nextInt(100) < 35;
	}
	public GCEnhanceEquipInfo getEquipInfoMsg(){
		Date now = new Date();
		GCEnhanceEquipInfo rmsg = new GCEnhanceEquipInfo();
		rmsg.setDoubleGold(EquipEnhanceService.DOUBLE_GOLD);
		rmsg.setFreeGold(EquipEnhanceService.FREE_GOLD);
		rmsg.setIsDouble(Globals.getItemService().getEquipEnhanceService().isDouble());
		long t0 = doubleStart.getTrigger().getFireTimeAfter(now).getTime();
		long t1 = doubleEnd.getTrigger().getFireTimeAfter(now).getTime();
//		System.out.println(doubleStart.getTrigger().getNextFireTime());
//		System.out.println(doubleStart.getLastFireTime());
//		System.out.println(Globals.getItemService().getEquipEnhanceService().isDouble());
//		System.out.println(Globals.getItemService().getEquipEnhanceService().isFree());
//
//		System.out.println(freeStart.getTrigger().getNextFireTime());
//		System.out.println(freeStart.getLastFireTime());
		if(rmsg.getIsDouble()){
			long t0_0 = doubleStart.getLastFireTime();
//			long t0_0 = doubleStart.getTrigger().getPreviousFireTime().getTime();
			if(t0_0 == 0)t0_0 = doubleStart.getTrigger().getNextFireTime().getTime();
			rmsg.setDoubleTimeDesc(TimeUtils.formatHMTime(t0_0)+"-"+TimeUtils.formatHMTime(t1));
		}else{
			rmsg.setDoubleTimeDesc(TimeUtils.formatHMTime(t0)+"-"+TimeUtils.formatHMTime(t1));
		}
		
		rmsg.setIsFree(Globals.getItemService().getEquipEnhanceService().isFree());
		
		long t2 = freeStart.getTrigger().getFireTimeAfter(now).getTime();
		long t3 = freeEnd.getTrigger().getFireTimeAfter(now).getTime();
//		System.out.println(TimeUtils.formatHMTime(t2)+"-"+TimeUtils.formatHMTime(t3));
		if(rmsg.getIsFree()){
			long t2_0 = freeStart.getLastFireTime();
			if(t2_0 == 0)t2_0 = freeStart.getTrigger().getNextFireTime().getTime();
			rmsg.setFreeTimeDesc(TimeUtils.formatHMTime(t2_0)+"-"+TimeUtils.formatHMTime(t3));
		}else{
			rmsg.setFreeTimeDesc(TimeUtils.formatHMTime(t2)+"-"+TimeUtils.formatHMTime(t3));
		}
		
		//在非活动时间内，计算下一个该是哪个
		long now1 = now.getTime();
		if(t0 < t2 && now1 < t0){			
			rmsg.setNextDesc(1);//双倍
		}else if(t2 < t0 && now1 < t2){
			rmsg.setNextDesc(2);//免费
		}
		return rmsg;
	}
//	public void start() {
//		this.initFromDB();
//		//// 0 0 0/6  * * ?
//		CronScheduledMessage msg = new CronScheduledMessage("0 0/10 * * * ?") {
//			@Override
//			public void execute() {
//				EquipEnhanceService.this.changeSuccessRateRange();
//			}
//		};
//		// 区间变化
//		Globals.getScheduleService().schedule(msg);
//																		
//		//0 0/30 * * * ?
//		CronScheduledMessage msg1 = new CronScheduledMessage("0 0/3 * * * ?") {
//			@Override
//			public void execute() {
//				EquipEnhanceService.this.changeSuccessRate();
//			}
//		};
//		// 值变化
//		Globals.getScheduleService().schedule(msg1);
//
//	}

//	/**
//	 * 从数据库恢复
//	 * 
//	 * @author xf
//	 */
//	public void initFromDB() {
//		ServerInfoEntity e = Globals.getDaoService().getServerInfoDAO()
//				.getByKey(ServerInfoKey.EQUIP_ENHANCE_RATE_INFO.toString());
//		if (e == null) {
//			this.firstInit();
//		} else {
//			// 读取数据库
//			JSONObject map = JSONObject.fromObject(e.getValue());
//			this.nowRate = MapUtils.getDoubleValue(map, "nowRate");
//			this.nextRate = MapUtils.getDoubleValue(map, "nextRate");
//			this.nextSuccesRate = new EquipEnhanceSuccesRate(
//					map.getJSONObject("nextSuccesRate"));
//			JSONArray arr = JSONArray.fromObject(map.get("canUseRate"));
//			for (int i = 0; i < arr.size(); i++) {
//				this.canUseRate.add(new EquipEnhanceSuccesRate(arr
//						.getJSONObject(i)));
//			}
//			this.isInDB = true;
//			this.dbID = e.getId();
//		}
//	}

//	/**
//	 * 保存到数据库
//	 * 
//	 * @author xf
//	 */
//	public void saveToDB() {
//		JSONObject map = new JSONObject();
//		map.put("nowRate", nowRate);
//		map.put("nextRate", nextRate);
//		map.put("nextSuccesRate", nextSuccesRate);
//		map.put("canUseRate", canUseRate);
//		if (isInDB) {
//			ServerInfoEntity e = new ServerInfoEntity();
//			e.setId(dbID);
//			e.setKey(ServerInfoKey.EQUIP_ENHANCE_RATE_INFO.toString());
//			e.setValue(map.toString());
//			Globals.getDaoService().getServerInfoDAO().update(e);
//		} else {
//			ServerInfoEntity e = new ServerInfoEntity();
//			e.setKey(ServerInfoKey.EQUIP_ENHANCE_RATE_INFO.toString());
//			e.setValue(map.toString());
//			Globals.getDaoService().getServerInfoDAO().save(e);
//			this.isInDB = true;
//			this.dbID = e.getId();
//		}
//	}

//	/**
//	 * 从可取的列表中随机取一个概率
//	 * 
//	 * @author xf
//	 */
//	private EquipEnhanceSuccesRate getRandom() {
//		if (canUseRate.size() == 0) {
//			// 没有的话，强行刷新区间
//			this.changeSuccessRateRange();
//		}
//		int weight = 0;
//		for (EquipEnhanceSuccesRate r : canUseRate) {
//			weight += r.weight;
//		}
//		EquipEnhanceSuccesRate select = null;
//		while (select == null) {
//			for (EquipEnhanceSuccesRate r : canUseRate) {
//				if (RandomUtils.nextDouble() < r.weight * 1D / weight) {
//					select = r;
//					break;
//				}
//			}
//		}
//		return select;
//	}

//	/**
//	 * 初始化
//	 * 
//	 * @author xf
//	 */
//	private void firstInit() {
//		// 初始化
//		this.changeSuccessRateRange();
//		EquipEnhanceSuccesRate rate = this.getRandom();
//		rate.weight -= 1;
//		if (rate.weight <= 0) {
//			// 不再出现
//			this.usedRate.add(rate);
//			this.canUseRate.remove(rate);
//			Loggers.msgLogger.info("概率" + rate.succesRate + "权重变为0了");
//		}
//		this.nowRate = rate.succesRate;
//
//		this.nextSuccesRate = this.getRandom();
//		this.nextRate = nextSuccesRate.succesRate;
//
//		this.saveToDB();
//	}

//	/**
//	 * 改变区间
//	 * 
//	 * @author xf
//	 */
//	public void changeSuccessRateRange() {
//		Loggers.msgLogger.info("改变成功率区间。。。");
//		int hour = TimeUtils.getHourTime(Globals.getTimeService().now());
//		List<EquipEnhanceSuccesRate> list = new ArrayList<EquipEnhanceSuccesRateTemplate.EquipEnhanceSuccesRate>();
//		if (hour < 6) {
//			list = itemService.getEquipEnhanceSuccessRate().get(0);
//		} else if (hour < 12) {
//			list = itemService.getEquipEnhanceSuccessRate().get(1);
//		} else if (hour < 18) {
//			list = itemService.getEquipEnhanceSuccessRate().get(2);
//		} else if (hour < 24) {
//			list = itemService.getEquipEnhanceSuccessRate().get(3);
//		}
//		canUseRate.clear();
//		for (EquipEnhanceSuccesRate r : list) {
//			canUseRate.add(r.clone());
//		}
//		usedRate.clear();
//
//		// this.saveToDB();
//
//		Globals.getOnlinePlayerService()
//				.getChannelService()
//				.sendGCMessageToChannel(this.getRateMsg(),
//						ChannelType.EQUIP_ENHANCE);
//	}

//	/**
//	 * 成功率开始发生变化
//	 * 
//	 * @author xf
//	 */
//	public void changeSuccessRate() {
//		// 为下一个
//		this.nowRate = this.nextRate;
//
//		// 从canuseRate取得一个同时删除其，放到used里
//		nextSuccesRate.weight -= 1;
//		if (nextSuccesRate.weight <= 0) {
//			// 不再显示
//			this.usedRate.add(nextSuccesRate);
//			this.canUseRate.remove(nextSuccesRate);
//			Loggers.msgLogger.info("概率" + nextSuccesRate.succesRate + "权重变为0了");
//		}
//
//		// 计算下一个
//		EquipEnhanceSuccesRate r = this.getRandom();
//		if (r != null) {
//			this.nextSuccesRate = r;
//			this.nextRate = this.nextSuccesRate.succesRate;
//		}
//		this.saveToDB();
//
//		Loggers.msgLogger.info("改变成功率。。。=>" + this.nowRate + ","
//				+ this.nextRate);
//
//		Globals.getOnlinePlayerService()
//				.getChannelService()
//				.sendGCMessageToChannel(this.getRateMsg(),
//						ChannelType.EQUIP_ENHANCE);
//	}

//	/**
//	 * 返回需要多少元宝可强化100%成功
//	 * 
//	 * @author xf
//	 */
//	public int getNeedGlod() {
//		return (int) ((1 - nowRate) * 100);
//	}
//
//	/**
//	 * 取得下次的成功率
//	 * 
//	 * @author xf
//	 */
//	public int getNowSuccesRate() {
//		return (int) (nowRate * 100);
//	}
//
//	/**
//	 * 成功概率是否在上升
//	 * 
//	 * @author xf
//	 */
//	public boolean isUpRate() {
//		return nextRate > nowRate;
//	}
//
//	/**
//	 * 取得是否成功
//	 * 
//	 * @author xf
//	 */
//	public boolean getIsSuccess() {
//		if (nowRate == 1.0) {
//			return true;
//		}
//		if (RandomUtils.nextBoolean()) {
//			return RandomUtils.nextDouble() < nowRate + 0.01;
//		} else {
//			return RandomUtils.nextDouble() < nowRate - 0.01;
//		}
//
//	}
//
//	/**
//	 * 取得当前成功率的信息
//	 * 
//	 * @author xf
//	 */
//	public GCEnhanceEquipRate getRateMsg() {
//		GCEnhanceEquipRate rmsg = new GCEnhanceEquipRate();
//		rmsg.setBUp(this.isUpRate());
//		rmsg.setRate(this.getNowSuccesRate());
//		rmsg.setGoldNum(this.getNeedGlod());
//		return rmsg;
//	}

}
