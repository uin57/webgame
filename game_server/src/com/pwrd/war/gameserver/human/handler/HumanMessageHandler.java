package com.pwrd.war.gameserver.human.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.common.LogReasons.ItemGenLogReason;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.gameserver.buff.domain.Buffer;
import com.pwrd.war.gameserver.buff.domain.ChangeBuffer;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.event.DayTaskEvent;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.common.i18n.constants.PlayerLangConstants_30000;
import com.pwrd.war.gameserver.common.msg.GCMessage;
import com.pwrd.war.gameserver.dayTask.DayTaskType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.HumanConstants;
import com.pwrd.war.gameserver.human.OpenFunction;
import com.pwrd.war.gameserver.human.domain.GrowChange;
import com.pwrd.war.gameserver.human.enums.CoolType;
import com.pwrd.war.gameserver.human.msg.CGArchiveList;
import com.pwrd.war.gameserver.human.msg.CGEndXiulian;
import com.pwrd.war.gameserver.human.msg.CGEnterHome;
import com.pwrd.war.gameserver.human.msg.CGGetLoginPrize;
import com.pwrd.war.gameserver.human.msg.CGGrowProps;
import com.pwrd.war.gameserver.human.msg.CGJingjieProps;
import com.pwrd.war.gameserver.human.msg.CGJoinChannel;
import com.pwrd.war.gameserver.human.msg.CGLevelupGrow;
import com.pwrd.war.gameserver.human.msg.CGLoginPrize;
import com.pwrd.war.gameserver.human.msg.CGModifyPersonsign;
import com.pwrd.war.gameserver.human.msg.CGOnlinePrize;
import com.pwrd.war.gameserver.human.msg.CGOpenEquipenhanceCdqueue;
import com.pwrd.war.gameserver.human.msg.CGQueryHumanInfo;
import com.pwrd.war.gameserver.human.msg.CGQueryOtherRoleInfo;
import com.pwrd.war.gameserver.human.msg.CGReleaseCdqueue;
import com.pwrd.war.gameserver.human.msg.CGStartXiulian;
import com.pwrd.war.gameserver.human.msg.CGUnjoinChannel;
import com.pwrd.war.gameserver.human.msg.CGXiulianCollectSymbol;
import com.pwrd.war.gameserver.human.msg.CGXiulianUpLevel;
import com.pwrd.war.gameserver.human.msg.GCGoldGrowCri;
import com.pwrd.war.gameserver.human.msg.GCGrowProps;
import com.pwrd.war.gameserver.human.msg.GCJingjieProps;
import com.pwrd.war.gameserver.human.msg.GCLoginPrize;
import com.pwrd.war.gameserver.human.msg.GCLoginPrizeRes;
import com.pwrd.war.gameserver.human.msg.GCOnlinePrizeRes;
import com.pwrd.war.gameserver.human.msg.GCXiulianCollectSymbol;
import com.pwrd.war.gameserver.human.template.CoolDownCostTemplate;
import com.pwrd.war.gameserver.human.template.DayLoginPrizeTemplate;
import com.pwrd.war.gameserver.human.template.NewPlayerPrizeTemplate;
import com.pwrd.war.gameserver.human.template.XiulianLevelExpTemplate;
import com.pwrd.war.gameserver.human.template.XiulianLevelTemplate;
import com.pwrd.war.gameserver.human.template.XiulianSymbolTemplate;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.human.xiulian.XiulianInfo;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemParam;
import com.pwrd.war.gameserver.item.template.ConsumeItemTemplate;
import com.pwrd.war.gameserver.item.template.ItemTemplate;
import com.pwrd.war.gameserver.player.ChannelService.ChannelType;
import com.pwrd.war.gameserver.player.GamingStateIndex;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.role.Role;
import com.pwrd.war.gameserver.role.template.GrowUpLevelTemplate;
import com.pwrd.war.gameserver.role.template.JingjieTemplate;

public class HumanMessageHandler {
	
	public void handleEnterHome(Player player, CGEnterHome msg) {
		// 直接处理
//		Human human = player.getHuman();
	}

	public void handleQueryHumanInfo(Player player,CGQueryHumanInfo cgQueryHumanInfo){
		
	}
	
	public void handleQueryOtherRoleInfo(Player player,CGQueryOtherRoleInfo cgQueryOtherRoleInfo) 
	{
		
		
	}
	
	/**
	 * 取得成就列表
	 * @author xf
	 */
	public void handleArchiveList(Player player,CGArchiveList msg){
		GCMessage gcmsg = player.getHuman().getArchiveManager().getAllArchives();
		player.sendMessage(gcmsg);
	}
	
	/**
	 * 处理开启新的装备强化队列
	 * @author xf
	 */
	public void handleOpenEquipenhanceCdqueue(Player player, CGOpenEquipenhanceCdqueue msg){
		int nowNum = player.getHuman().getPropertyManager().getEquipEnhanceNum();
		CoolDownCostTemplate tmp = Globals.getHumanService().getCoolTemplateByCoolType(CoolType.ENHANCE_EQUIP);
		if(nowNum >= tmp.getTypeCount()){
			player.sendErrorPromptMessage(PlayerLangConstants_30000.MAX_EQUIP_ENHANCE_QUEUE_NUM);
			return;
		}
		int nextNum = nowNum + 1;
		int needGlod = 0;
		if(nextNum == 2){
			needGlod = tmp.getOpenNeedGold()[0];
		}else if(nextNum == 3){
			needGlod = tmp.getOpenNeedGold()[1];
		} 
		//检查元宝是否足够
		Human human = player.getHuman();
		boolean rs = human.hasEnoughGold(needGlod,  true);
		if(!rs){ 
			return;
		}
		rs = human.costGold(needGlod, true, null, CurrencyLogReason.GGZJ, CurrencyLogReason.GGZJ.getReasonText(), 0);
		if(rs){
			human.getPropertyManager().setEquipEnhanceNum(nextNum);
			human.getCooldownManager().addAEmptyCool(CoolType.ENHANCE_EQUIP);
			//推送变化属性
			human.snapChangedProperty(true);
			return;
		}else{
			player.sendErrorPromptMessage(CommonLangConstants_10000.COMMON_NOT_ENOUGH, 
					CommonLangConstants_10000.CURRENCY_NAME_GOLD);
		}
		
		
	}
	
	/**
	 * 消除等待序列
	 * @author xf
	 */
	public void handleReleaseCdqueue(final Player player, CGReleaseCdqueue msg){
		CoolType type = CoolType.getCoolTypeById(msg.getCdType());
		int index = msg.getIndex();
		Human human = player.getHuman();
		int sec = human.getCooldownManager().getCoolDownRemainTimeInSeconds(type, index);
		if(sec == 0 || type == CoolType.NONE){
//			player.sendErrorPromptMessage(PlayerLangConstants_30000.NO_NEED_TO_CLEAR_QUEUE);
			human.getCooldownManager().remove(type, index); 
			return;
		}
		//取得模版
		CoolDownCostTemplate tmp = Globals.getHumanService().getCoolTemplateByCoolType(type);

		sec =  (int) Math.ceil(sec * 1.0 /60);//s -> m
		int needGold =  tmp.getNeedGold(sec); 
		
		//钱够否
		boolean rs = human.hasEnoughGold(needGold,  true);
		if(!rs){
			//player.sendErrorMessage("元宝不足");
			return;
		}
		//扣钱
		rs = human.costGold(needGold,  true, 0, CurrencyLogReason.GGZJ, CurrencyLogReason.GGZJ.getReasonText(), 0);
		if(!rs){
			player.sendErrorPromptMessage(CommonLangConstants_10000.COMMON_NOT_ENOUGH, 
					CommonLangConstants_10000.CURRENCY_NAME_GOLD);
			return;
		}
		//设置为空
		human.getCooldownManager().remove(type, index); 
		human.snapChangedProperty(true);
		
	}
	//订阅某频道的消息
	public void handleJoinChannel(final Player player, CGJoinChannel msg){
		ChannelType channel = ChannelType.valueOf(msg.getChannel());
		Globals.getOnlinePlayerService().getChannelService().addPlayerToChannel(player, channel);
	}
	//取消订阅某频道的消息
	public void handleUnjoinChannel(final Player player, CGUnjoinChannel msg){
		ChannelType channel = ChannelType.valueOf(msg.getChannel());
		Globals.getOnlinePlayerService().getChannelService().removePlayerFromChannel(player, channel);
	}
	
	//取得成长类型对应的属性变化增加值
	public void handleGrowProps(final Player player, CGGrowProps msg){
		String targetUUID = msg.getTargetUUID();//默认给自己成长
		
		Role target = null;
		if(player.getRoleUUID().equals(targetUUID)){
			target = player.getHuman();
		}else{
			//取武将
			target = player.getHuman().getPetManager().getPetByUuid(targetUUID);			
		}
		if(target == null){
			player.sendErrorPromptMessage(CommonLangConstants_10000.OBJECT_NOT_EXIST);
			return;
		}
		
		
		
		int nowGrow = target.getGrow();
		
		List<GrowChange> list = new ArrayList<GrowChange>();
		int[] toGrows = {nowGrow + 1, nowGrow + 5};
		for(int i = 1; i<= 1; i++){
			GrowChange gc = new GrowChange();
			int toGrow = toGrows[i - 1];
			gc.setGrow(toGrow - nowGrow);
			int needMoney = 0;
			for(int j = nowGrow; j< toGrow; j++){
				GrowUpLevelTemplate template = Globals.getHumanService().getGrowUpLevelByLevel(j);
				if(template == null){
					needMoney += 0;
				}else{
					needMoney += template.getMoney();
				}
			}
			gc.setNeedMoney(needMoney);
			gc.setAddAtk(target.getGrowUpAtkAdd(toGrow));
			gc.setAddDef(target.getGrowUpDefAdd(toGrow));
			gc.setAddMaxHp(target.getGrowUpMaxHpAdd(toGrow));
			gc.setAddAllAtk(target.getGrowAtk());
			gc.setAddAllDef(target.getGrowDef());
			gc.setAddAllMaxHp(target.getGrowMaxHp());
			list.add(gc);
		}
		GCGrowProps rmsg = new GCGrowProps();
		int maxGrow = 0;
		JingjieTemplate nowJingjie = Globals.getHumanService().getJingjieTemplate(nowGrow);
		if(nowJingjie != null){
			JingjieTemplate nextJingjie = Globals.getHumanService().getJingjieTemplateByJingjie(nowJingjie.getJingjie() + 1);
			if(nextJingjie != null){
				maxGrow = nextJingjie.getGrow();
			}else{
				maxGrow = nowJingjie.getGrow();
			}
		}
		rmsg.setMaxGrow(maxGrow);
		rmsg.setGrows(list.toArray(new GrowChange[0]));
		rmsg.setTargetUUID(targetUUID);
		player.sendMessage(rmsg);
		 
	}
	//提升属性成长	
	public void handleLevelupGrow(final Player player, CGLevelupGrow msg){
		int grow = 0;
		int type = msg.getGrowType();//1默认方式，普通成长2元宝成长
		boolean bCoolDown = msg.getCoolDown();
		String targetUUID = msg.getTargetUUID();//默认给自己成长
		boolean flag = false;//是否暴击
		Role target = null;
		if(player.getRoleUUID().equals(targetUUID)){
			target = player.getHuman();
		}else{
			//取武将
			target = player.getHuman().getPetManager().getPetByUuid(targetUUID);			
		}
		if(target == null){
			player.sendErrorPromptMessage(CommonLangConstants_10000.OBJECT_NOT_EXIST);
			return;
		}
		int nowGrow = target.getGrow();
		//成长达上线
		if(nowGrow >= target.getMaxGrow()){
			player.sendErrorPromptMessage(PlayerLangConstants_30000.GROW_UP_TO_LIMIT);
			return;
		}
		
		GrowUpLevelTemplate nowTmp = null, nextTmp = null;
		//普通成长
		if(type == 1){
			grow = 1;
		}
		//元宝成长
		if(type == 2){
			//只有达到指定vip级别才能开启此功能
			if(player.getHuman().getVipLevel() < HumanConstants.GOLD_GROW_NEED_VIP_LEVEL){
				player.sendErrorPromptMessage(Globals.getLangService().read(PlayerLangConstants_30000.GOLD_GROW_MIN_NEED_VIP_LEVEL,
						HumanConstants.GOLD_GROW_NEED_VIP_LEVEL));
				return;
			}
			int num = RandomUtils.nextInt(100);
			if(num < 10){
				grow = 5;
				flag = true;
			}else{
				grow = 2;
			}
			int nextGrow = nowGrow + grow;
			if(nextGrow > target.getMaxGrow()){
				grow = target.getMaxGrow() - nowGrow;
			}
		}
		
		nowTmp = Globals.getHumanService().getGrowUpLevelByLevel(nowGrow); 
		nextTmp = Globals.getHumanService().getGrowUpLevelByLevel(nowGrow + grow);
		
		if(nextTmp == null || nowTmp == null || nowTmp == nextTmp
				|| nextTmp.getGrow() > target.getMaxGrow()){
			player.sendErrorPromptMessage(PlayerLangConstants_30000.GROW_UP_TO_LIMIT);
			return;
		} 
		Human human = player.getHuman();
		//普通成长
		if(type == 1){
			if(!human.getCooldownManager().hasEmptyCool(1, CoolType.GROW_UP_ROLE)){
				player.sendErrorPromptMessage(PlayerLangConstants_30000.CD_QUEUE_CDING);
				return;
			}
			int needCoins = nowTmp.getMoney() * grow;
			boolean rs = human.hasEnoughCoins(needCoins, true);
			if(!rs){
				return;
			}
			rs = human.costCoins(needCoins, true, 0,
					CurrencyLogReason.TISHENGCZ,
					CurrencyLogReason.TISHENGCZ.getReasonText(), 0);
			if (!rs) {
				return;
			}
			//取模版计算需要冷却的时间
			//取得模版
			CoolDownCostTemplate tmp = null;
			for(CoolDownCostTemplate _tmp : Globals.getTemplateService().getAll(CoolDownCostTemplate.class).values()){
				if(_tmp.getCoolTypeEnum() == CoolType.GROW_UP_ROLE){
					tmp = _tmp;
					break;
				}
			}
			if(tmp!=null){
				int needt = tmp.getNeedTime(human.getGrow());
				if(bCoolDown){
					//消耗元宝不加队列
					int needGold =  tmp.getNeedGold(needt); 
					//钱够否
					rs = human.hasEnoughGold(needGold,  true);
					if(!rs){
						return;
					}
					// 扣钱
					rs = human.costGold(needGold, true, 0,
							CurrencyLogReason.TISHENGCZ,
							CurrencyLogReason.TISHENGCZ.getReasonText(), 0);
					if (!rs) {
						player.sendErrorPromptMessage(CommonLangConstants_10000.COMMON_NOT_ENOUGH, 
								CommonLangConstants_10000.CURRENCY_NAME_GOLD);
						return;
					}
					
				}else{
					//冷却
					human.getCooldownManager().put(CoolType.GROW_UP_ROLE, 
							Globals.getTimeService().now(),
							Globals.getTimeService().now() + needt * TimeUtils.MIN);
				}
			}
		}
		//元宝成长
		if(type == 2){
			int needGold = HumanConstants.GOLD_GROW_NEED_MONEY;
			boolean rs = human.hasEnoughGold(needGold, true);
			if(!rs){
				return;
			}
			rs = human.costGold(needGold, true, 0, CurrencyLogReason.TISHENGCZ,
					CurrencyLogReason.TISHENGCZ.getReasonText(), 0);
			if (!rs) {
				return;
			}
		}
		//增加成长
		target.setGrow(target.getGrow() + grow);
		JingjieTemplate nowJingjie = Globals.getHumanService().getJingjieTemplate(nowGrow);
		JingjieTemplate nextJingjie = Globals.getHumanService().getJingjieTemplate(target.getGrow() + grow);
		if(nextJingjie != null){
			int jingjie = nextJingjie.getJingjie();
			if(jingjie > nowJingjie.getJingjie()){
				target.setJingjie(jingjie);
			}
		}
		target.calcProps();
		//暴击的话通知前端
		if(type == 2){
			GCGoldGrowCri gcMsg = new GCGoldGrowCri();
			if(flag){
				gcMsg.setResult(2);
			}else{
				gcMsg.setResult(1);
			}
			player.sendMessage(gcMsg);
			CGGrowProps prop = new CGGrowProps();
			prop.setTargetUUID(target.getUUID());
			this.handleGrowProps(player, prop);
		}
		player.sendImportPromptMessage(CommonLangConstants_10000.CHENGZHANG,grow);
		
		//发送境界变化信息
		this.sendJingjieChangeProps(player,target.getUUID(),nowGrow, target.getGrow());
		
		//发送每日任务的事件
		DayTaskEvent event = new DayTaskEvent(human, DayTaskType.PEIYANG.getValue());
		Globals.getEventService().fireEvent(event);
		
		
	}
	
	/**
	 * 请求境界变化信息
	 * @param player
	 * @param msg
	 */
	public void handleJingjieProps(Player player,CGJingjieProps msg){
		String targetUUID = msg.getTargetUUID();
		
		Role target = null;
		if(player.getRoleUUID().equals(targetUUID)){
			target = player.getHuman();
		}else{
			//取武将
			target = player.getHuman().getPetManager().getPetByUuid(targetUUID);			
		}
		if(target == null){
			player.sendErrorMessage(CommonLangConstants_10000.OBJECT_NOT_EXIST);
			return;
		}
		
		JingjieTemplate nowJingjie = Globals.getHumanService().getJingjieTemplate(target.getGrow());
		if(nowJingjie == null){
			return;
		}
		JingjieTemplate nextJingjie = null;
		if(nowJingjie.getJingjie() == Globals.getHumanService().getMaxJingjie()){
			nextJingjie = nowJingjie;
		}else{
			nextJingjie = Globals.getHumanService().getJingjieTemplateByJingjie(nowJingjie.getJingjie() + 1);
		}
		if(nextJingjie == null){
			return;
		}
		GCJingjieProps gcJingjieProps = new GCJingjieProps();
		gcJingjieProps.setTargetUUID(targetUUID);
		gcJingjieProps.setNowName(nowJingjie.getName());
		if(nowJingjie.getJingjie() == Globals.getHumanService().getMaxJingjie()){
			gcJingjieProps.setAddAtk((int)Globals.getHumanService().getMaxJingjieAtk());
			gcJingjieProps.setAddDef((int)Globals.getHumanService().getMaxJingjieDef());
			gcJingjieProps.setAddMaxHp((int)Globals.getHumanService().getMaxJingjieHp());
			gcJingjieProps.setAddDamage(Globals.getHumanService().getMaxJingjieDamage());
		}else{
			gcJingjieProps.setAddAtk(nextJingjie.getAtk().intValue());
			gcJingjieProps.setAddDef(nextJingjie.getDef().intValue());
			gcJingjieProps.setAddMaxHp(nextJingjie.getHp().intValue());
			gcJingjieProps.setAddDamage(nextJingjie.getShanghai() * 100);
		}
		gcJingjieProps.setJingjie(nowJingjie.getJingjie());
		gcJingjieProps.setNowNeedGrow(nowJingjie.getGrow());
		gcJingjieProps.setNextNeedGrow(nextJingjie.getGrow());
		player.sendMessage(gcJingjieProps);
	}
	
	/**
	 * 根据成长值发送境界变化信息
	 * @param nowGrow
	 * @param nextGrow
	 */
	public void sendJingjieChangeProps(Player player,String targetUUID,int nowGrow,int nextGrow){
		JingjieTemplate nowJingjie = Globals.getHumanService().getJingjieTemplate(nowGrow);
		if(nowJingjie == null){
			return;
		}
		JingjieTemplate nextJingjie = Globals.getHumanService().getJingjieTemplateByJingjie(nowJingjie.getJingjie() + 1);
		if(nextJingjie == null){
			return;
		}
		if(nextGrow >= nextJingjie.getGrow()){
			JingjieTemplate template = null;
			if(nextJingjie.getJingjie() == Globals.getHumanService().getMaxJingjie()){
				template = nextJingjie;
			}else{
				template = Globals.getHumanService().getJingjieTemplateByJingjie(nextJingjie.getJingjie() + 1);
			}
			if(template == null){
				return;
			}
			GCJingjieProps msg = new GCJingjieProps();
			msg.setTargetUUID(targetUUID);
			msg.setNowName(nextJingjie.getName());
			if(nextJingjie.getJingjie() == Globals.getHumanService().getMaxJingjie()){
				msg.setAddAtk((int)Globals.getHumanService().getMaxJingjieAtk());
				msg.setAddDef((int)Globals.getHumanService().getMaxJingjieDef());
				msg.setAddMaxHp((int)Globals.getHumanService().getMaxJingjieHp());
				msg.setAddDamage(Globals.getHumanService().getMaxJingjieDamage());
			}else{
				msg.setAddAtk(template.getAtk().intValue());
				msg.setAddDef(template.getDef().intValue());
				msg.setAddMaxHp(template.getHp().intValue());
				msg.setAddDamage(template.getShanghai() * 100);
			}
			msg.setJingjie(nextJingjie.getJingjie());
			msg.setNowNeedGrow(nextJingjie.getGrow());
			msg.setNextNeedGrow(template.getGrow());
			player.sendMessage(msg);
		}
	}
	
	
	/**
	 * 开始挂机修炼
	 */
	public void handleStartXiulian(final Player player, CGStartXiulian msg){
		if(!OpenFunction.isopen(player.getHuman(), OpenFunction.dazuo, true)){
			return;
		}
		if(player.getHuman().isInRepAgainsg()){
			player.sendErrorPromptMessage(PlayerLangConstants_30000.STOP_AGAINST_FIRST);
			return;
		}
		if(player.getHuman().getGamingState() != GamingStateIndex.IN_NOMAL.getValue()){			
			player.sendErrorPromptMessage(PlayerLangConstants_30000.CUR_STATE_CANNOT_XIULIAN);			
			return;
		}
		boolean rs = Globals.getHumanService().getHumanXiulianService().startXiulian(player);
		if(!rs){
			player.sendErrorPromptMessage(PlayerLangConstants_30000.IN_XIULIANING);
		}
	}
	
	/**
	 * 结束修炼
	 * @author xf
	 */
	public void handleEndXiulian(final Player player, CGEndXiulian msg){
		XiulianInfo info = Globals.getHumanService().getHumanXiulianService().getByUUID(player.getRoleUUID());
		if(info != null){
			boolean rs = Globals.getHumanService().getHumanXiulianService().endXiulian(info);
			if(!rs){
				player.sendErrorPromptMessage(PlayerLangConstants_30000.NOT_IN_XIULIANING);
			}
		}
	}
	
	/**
	 * 采集修炼标志
	 * @author xf
	 */
	public void handleXiulianCollectSymbol(final Player player, CGXiulianCollectSymbol msg){
		if(!OpenFunction.isopen(player.getHuman(), OpenFunction.dazuo, true)){
			return;
		}
		XiulianInfo info = Globals.getHumanService().getHumanXiulianService().getByUUID(player.getRoleUUID());
		XiulianInfo toInfo = Globals.getHumanService().getHumanXiulianService().getByUUID(msg.getTargetUUID());
		if(toInfo != null){
			XiulianSymbolTemplate tmp = toInfo.getXiulianSymbolTemplate();
			if(tmp == null || toInfo.getHasCollectTimes() >= tmp.getCollectTimes()){
				player.sendErrorPromptMessage(PlayerLangConstants_30000.COLLECT_NOTHING);
				return;
			}
			Player toPlayer = Globals.getOnlinePlayerService().getPlayerById(msg.getTargetUUID());
			if(toPlayer == null){
				player.sendErrorPromptMessage(CommonLangConstants_10000.PLAYER_OFFLINE);
				return;
			}
//			是否在冷却中
//			long df = Globals.getTimeService().now() -  player.getHuman().getPropertyManager().getPropertyNormal().getXiulianLastCollectTime() ;
//			
//			if(df < HumanXiulianService.XIULIAN_CD_TIME){
//				df = HumanXiulianService.XIULIAN_CD_TIME/1000 - df/1000;
//				player.sendErrorMessage(PlayerLangConstants_30000.COLLECT_COOLDOWN_ING,
//						(df + 60)/60);
//				return; 
//			}
			//是否有采集次数
			if(info.ent.getXiulianTodayCollectTimes()
					<= 0){
				player.sendErrorPromptMessage(PlayerLangConstants_30000.COLLECT_TODAY_FULL_TIME);
				return;
			}
			if(toInfo.ent.getXiulianTodayBeCollectTimes()
					>= toPlayer.getHuman().getVipTemplate().getBeCollectTimes()){
				player.sendErrorPromptMessage(PlayerLangConstants_30000.COLLECT_TARGET_TODAY_FULL_TIME);
				return;
			}
			
			
			//可以采集
			toInfo.setHasCollectTimes(toInfo.getHasCollectTimes() + 1);
			//判断次数是否满了,满了就清空
			if(toInfo.getHasCollectTimes() >= tmp.getCollectTimes()){
				toInfo.setHasCollectTimes(0);
				toInfo.setXiulianSymbolTemplate(null);
				//采集后等一分钟后刷新
				toInfo.setLastSymbolTime(Globals.getTimeService().now());
			}
			//加经验
			float expRate = tmp.getExpRate();
			XiulianLevelExpTemplate playerExpTmp = Globals.getHumanService().getByXiulianByPlayerLevel(player.getHuman().getLevel());
			XiulianLevelExpTemplate toPlayerExpTmp = Globals.getHumanService().getByXiulianByPlayerLevel(toPlayer.getHuman().getLevel());
			
			int playerAddExp = Math.round(playerExpTmp.getCollectorExp() * expRate);
			int toPlayerAddExp = Math.round(toPlayerExpTmp.getBeCollectorExp() * expRate);
			
			//判断好友加成
			if(player.getHuman().getFriendManager().isFriend(msg.getTargetUUID())){
				//好友加成经验
				
			}
			player.getHuman().addExpToAllFormUnits(playerAddExp);
			toPlayer.getHuman().addExpToAllFormUnits(toPlayerAddExp);
			
//			HumanNormalProperty normalP = player.getHuman().getPropertyManager().getPropertyNormal(); 
			//增加采集次数和被采集次数
			info.ent.setXiulianTodayCollectTimes(info.ent.getXiulianTodayCollectTimes() - 1);
			toInfo.ent.setXiulianTodayBeCollectTimes(toInfo.ent.getXiulianTodayBeCollectTimes() + 1);
			//记录采集时间
			info.ent.setXiulianLastCollectTime(Globals.getTimeService().now());
			//发送属性
			toPlayer.getHuman().snapChangedProperty(true);
			player.getHuman().snapChangedProperty(true);
			
			toPlayer.getHuman().setModified();
			player.getHuman().setModified();
			info.setModified();
			toInfo.setModified();
			
			//采集者消息
			GCXiulianCollectSymbol rmsg = new GCXiulianCollectSymbol();
			rmsg.setGetExp(playerAddExp);
			rmsg.setFriendAddExp(0);
			rmsg.setRandomSymbolId("");
			player.sendMessage(rmsg);
			
			//如果采集者也在修炼也需要发送一下信息
//			XiulianInfo pinfo = Globals.getHumanService().getHumanXiulianService().getByUUID(player.getRoleUUID());
			if(info.isStart() && info.getMsg() != null){
				player.sendMessage(info.getMsg().getGCMessage(player));
			}
			
			//发送最新的修炼消息
			toPlayer.sendMessage(toInfo.getMsg().getGCMessage(toPlayer));
			//被采集者消息
//			GCXiulianBeCollectSymbol toRmsg = new GCXiulianBeCollectSymbol();
//			toRmsg.setGetExp(toPlayerAddExp);
//			toRmsg.setFriendAddExp(0);
//			toRmsg.setRandomSymbolId("");
//			toPlayer.sendMessage(toRmsg);
			
			
			//TODO 这里处理问号的效果
			if(tmp.getSymbolId() == 5){
				//需要给玩家一个变身buff
				String[] items = tmp.getParams().split(",");
				int i = RandomUtils.nextInt(items.length);
				String itemSN = items[i];
				ItemTemplate itemTmp = Globals.getItemService().getTemplateByItemSn(itemSN);
				//8是变身buff
				Buffer buffer = Globals.getBufferService().getInstanceBuffer("8", Globals.getTimeService().now());
				if(buffer != null){
					ChangeBuffer cb = (ChangeBuffer) buffer;
					cb.setOwner(player.getHuman());
					cb.addBuffer(player.getHuman(), (ConsumeItemTemplate) itemTmp); 					
					player.sendInfoChangeMessageToScene();
				} 
				
				i = RandomUtils.nextInt(items.length);
				itemSN = items[i];
				itemTmp = Globals.getItemService().getTemplateByItemSn(itemSN);
				//8是变身buff
				buffer = Globals.getBufferService().getInstanceBuffer("8", Globals.getTimeService().now());
				if(buffer != null){
					ChangeBuffer cb = (ChangeBuffer) buffer;
					cb.setOwner(toPlayer.getHuman());
					cb.addBuffer(toPlayer.getHuman(), (ConsumeItemTemplate) itemTmp); 					
					toPlayer.sendInfoChangeMessageToScene();
				}
			}else{
				//广播被采集者的信息
				toPlayer.sendInfoChangeMessageToScene();
			}
			
			
		}else{
			player.sendErrorPromptMessage(PlayerLangConstants_30000.TARGET_NOT_IN_XIULIAN);
		}
	}

	/**
	 * 提示境界等级
	 * @author xf
	 */
	public void handleXiulianUpLevel(final Player player, CGXiulianUpLevel msg){
		XiulianInfo info = Globals.getHumanService().getHumanXiulianService().getByUUID(player.getRoleUUID());
		if(info == null){
			player.sendErrorPromptMessage(PlayerLangConstants_30000.NOT_IN_XIULIANING);
			return;
		}
//		HumanNormalProperty normalP = player.getHuman().getPropertyManager().getPropertyNormal(); 
		int nowLevel = info.ent.getXiulianLevel();
		if(Globals.getTimeService().now() >= info.ent.getXiulianLevelExpireTime()){
			nowLevel = 1;//默认或者过期了都为1级
		}
		int nextLevel = nowLevel + 1;
		XiulianLevelTemplate nextTmp = Globals.getHumanService().getByXiulianLevel(nextLevel);
		XiulianLevelTemplate nowTmp = Globals.getHumanService().getByXiulianLevel(nowLevel);
		if(nextTmp == null){
			player.sendErrorPromptMessage(PlayerLangConstants_30000.XIULIAN_MAX_LEVEL);
			return;
		}
		int needGold = nowTmp.getNeedGold();
		boolean rs = player.getHuman().hasEnoughGold(needGold,  true);
		if(!rs){
			return;
		}
		rs = player.getHuman().costGold(needGold, true, null,
				CurrencyLogReason.XIULIAN,
				CurrencyLogReason.XIULIAN.getReasonText(), 0);
		if (!rs) {
			return;
		}
		info.ent.setXiulianLevel(nextLevel);
		info.ent.setXiulianLevelExpireTime(Globals.getTimeService().now() + nextTmp.getDurTime() * TimeUtils.HOUR);
		info.setModified();
		player.getHuman().onModified();
		player.getHuman().snapChangedProperty(true);
		//保存当前修炼状态
		info.setXiulianLevelTemplate(nextTmp); 
		info.setLastSymbolTime(0);//重置标记为0,表示下次一定刷新标志
		player.sendMessage(info.getMsg().getGCMessage(player));
	}
	//修改个性签名
	public void handleModifyPersonsign(Player player, CGModifyPersonsign msg){
		if(StringUtils.length(msg.getSign()) > 20){
			player.sendErrorPromptMessage(PlayerLangConstants_30000.PERSONSIGN_LONG);
			return;
		}
		player.getHuman().getPropertyManager().setPersonSign(msg.getSign());
		player.getHuman().snapChangedProperty(true);
		player.sendRightMessage(PlayerLangConstants_30000.PERSONSIGN_MODIFYOK);
	}
	/**
	 * 领取在线奖励
	 */
	public void handleOnlinePrize(final Player player, CGOnlinePrize msg){
		short buzhou = (short) msg.getBuzhou();
		Human human = player.getHuman();
		if(buzhou != human.getPropertyManager().getPropertyNormal().getNewPrize())return;
		NewPlayerPrizeTemplate tmp = Globals.getHumanService().getPrizeTemplateByBuzhou(buzhou);
		if(tmp.getLevel() > human.getLevel()){
			player.sendErrorPromptMessage(PlayerLangConstants_30000.ONLINE_PRIZE_LEVEL);
			human.sendMessage(new GCOnlinePrizeRes(false));
			return;
		}
		long df = (tmp.getOnlinetime() - 
				(Globals.getTimeService().now() - human.getPropertyManager().getPropertyNormal().getNewPrizeSTime())/1000);
		if(df > 0){
			player.sendErrorPromptMessage(PlayerLangConstants_30000.ONLINE_PRIZE_TIME);
			human.sendMessage(new GCOnlinePrizeRes(false));
			return;
		}
		//可以领取
		int vit = tmp.getGetVit();
		String itemSN = tmp.getItemSN();
		if(!StringUtils.isEmpty(itemSN)){
			ItemParam itp = new ItemParam(itemSN, 1, BindStatus.BIND_YET);
			List<ItemParam> cols = new ArrayList<ItemParam>();
			cols.add(itp);
			if(!human.getInventory().checkSpace(cols, true)){
				human.sendMessage(new GCOnlinePrizeRes(false));
				return;
			}
		}
		
		
		Globals.getVitService().addVitaltiy(human, vit, true);
		if(!StringUtils.isEmpty(itemSN)){
			Collection<Item> items = human.getInventory().addItem(itemSN, 1, BindStatus.BIND_YET,
					ItemGenLogReason.ONLINE_PRIZE, "在线奖励", true);
			if(items.size() != 0){
				human.getPlayer().sendImportPromptMessage(PlayerLangConstants_30000.GET_ONLINE_PRIZE,
						vit,  items.iterator().next().getName());
			}
		}else{
			human.getPlayer().sendImportPromptMessage(PlayerLangConstants_30000.GET_ONLINE_PRIZE1,
					vit);			
		}
		
		human.sendMessage(new GCOnlinePrizeRes(true));
		//下一步骤
		human.getPropertyManager().getPropertyNormal().setNewPrize(tmp.getNextbuzhou());
		human.getPropertyManager().getPropertyNormal().setNewPrizeSTime(0);
		human.checkOnlinePrize(true);
		
	}
	public void handleGetLoginPrize(Player player, CGGetLoginPrize msg){
		Human human = player.getHuman();
		int days = human.getPropertyManager().getPropertyNormal().getTotalDays();
		String gets = human.getPropertyManager().getPropertyNormal().getLoginGifts();
		String getss[] = null;
		if(StringUtils.isEmpty(gets)){
			getss = new String[0];
		}else{
			getss = gets.split(",");
		}			
		int[] getsi = new int[getss.length];
		int index = 0;
		for(String s : getss){
			if(StringUtils.isNotEmpty(s)){
				getsi[index++] = Integer.valueOf(s);
			}
		}
		StringBuffer canGet = new StringBuffer();
		StringBuffer hasGet = new StringBuffer();
		for(int i = 1; i <= days; i++){
			boolean get = false;
			for(int g : getsi){
				if(g == i){
					get = true;
					break;
				}
			}
			if(!get){
				canGet.append(i+",");
			}else{
				hasGet.append(i+",");
			}
		}
		GCLoginPrize prize = new GCLoginPrize();
		prize.setCanGetDays(canGet.toString());
		prize.setGotDays(hasGet.toString());
		human.sendMessage(prize);
	}
	public void handleLoginPrize(Player player, CGLoginPrize msg){
		Human human = player.getHuman();
		int days = human.getPropertyManager().getPropertyNormal().getTotalDays();
		String gets = human.getPropertyManager().getPropertyNormal().getLoginGifts();
		String getss[] = null;
		if(StringUtils.isEmpty(gets)){
			getss = new String[0];
			gets = "";
		}else{
			getss = gets.split(",");
		}
		for(String g : getss){
			if(StringUtils.equals(g, ""+msg.getDay())){
				player.sendErrorPromptMessage(PlayerLangConstants_30000.GET_LOGIN_PRIZE1);
				player.sendMessage(new GCLoginPrizeRes(false));
				return;
			}
		}
		if(msg.getDay() > days){
			player.sendErrorPromptMessage(PlayerLangConstants_30000.GET_LOGIN_PRIZE2);
			player.sendMessage(new GCLoginPrizeRes(false));
			return;
		}
		
		//给予物品
		DayLoginPrizeTemplate tnmp = Globals.getHumanService().getDayLoginPrizeByDay(msg.getDay());
		List<ItemParam> cols = new ArrayList<ItemParam>();
		for(String itemSN : tnmp.getGifts().keySet()){
			if(!StringUtils.isEmpty(itemSN)){
				ItemParam itp = new ItemParam(itemSN, 1, BindStatus.BIND_YET);
				cols.add(itp);
			}
		}
		if(!human.getInventory().checkSpace(cols, true)){
			player.sendMessage(new GCLoginPrizeRes(false));
			return;
		}
		gets = gets + "," + msg.getDay();
		human.getPropertyManager().getPropertyNormal().setLoginGifts(gets);
		human.setModified();
		player.sendMessage(new GCLoginPrizeRes(true));
		/*Collection<Item> items = */
		human.getInventory().addAllItems(cols, ItemGenLogReason.DAYLOGIN_PRIZE, "登陆奖励", true);
//		String tip = "连续登录还有好礼相送";
//		player.sendRightMessage(tip);
		
		human.checkLoginGifts();
		this.handleGetLoginPrize(player, null);
		
	}
}