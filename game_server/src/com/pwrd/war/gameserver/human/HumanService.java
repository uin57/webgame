package com.pwrd.war.gameserver.human;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pwrd.war.core.schedule.ScheduleService;
import com.pwrd.war.core.template.TemplateService;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.enums.VocationType;
import com.pwrd.war.gameserver.human.enums.CoolType;
import com.pwrd.war.gameserver.human.enums.ResearchType;
import com.pwrd.war.gameserver.human.enums.SubCoolType;
import com.pwrd.war.gameserver.human.template.CoolDownCostTemplate;
import com.pwrd.war.gameserver.human.template.DayLoginPrizeTemplate;
import com.pwrd.war.gameserver.human.template.NewPlayerPrizeTemplate;
import com.pwrd.war.gameserver.human.template.OpenFunctionTemplate;
import com.pwrd.war.gameserver.human.template.ResearchTemplate;
import com.pwrd.war.gameserver.human.template.ResearchTypeTemplate;
import com.pwrd.war.gameserver.human.template.XiulianLevelExpTemplate;
import com.pwrd.war.gameserver.human.template.XiulianLevelTemplate;
import com.pwrd.war.gameserver.human.template.XiulianSymbolTemplate;
import com.pwrd.war.gameserver.human.template.XiulianTemplate;
import com.pwrd.war.gameserver.human.xiulian.HumanXiulianService;
import com.pwrd.war.gameserver.role.template.GrowLevelTemplate;
import com.pwrd.war.gameserver.role.template.GrowUpLevelTemplate;
import com.pwrd.war.gameserver.role.template.JingjieTemplate;
import com.pwrd.war.gameserver.role.template.RoleToSkillTemplate;
import com.pwrd.war.gameserver.vitality.template.VipToVitTemplate;

public class HumanService {
	
	/** vip等级模版 **/
	private Map<Integer, VipToVitTemplate> vipTemplate = new HashMap<Integer, VipToVitTemplate>();
	
	/** 成长等级对应模版 **/
	Map<Integer, GrowUpLevelTemplate> mapLevel = new HashMap<Integer, GrowUpLevelTemplate>();
	
	/** 冷却类型对应模版  **/
	Map<Integer, CoolDownCostTemplate> coolTypeTemplate = new HashMap<Integer, CoolDownCostTemplate>();
	
	private Map<VocationType,RoleToSkillTemplate> skillTemplate=new HashMap<VocationType,RoleToSkillTemplate>();
	
	/** 研究等级对应模版信息 **/
	Map<Integer, ResearchTemplate> yanjiuTemplate = new HashMap<Integer, ResearchTemplate>();
	
	/** 研究类型 **/
	Map<ResearchType, ResearchTypeTemplate> yanjiuTypeTemplate = new HashMap<ResearchType, ResearchTypeTemplate>();
	
	/** 修炼模版 **/
	Map<Integer, XiulianTemplate> xiulianTemplate = new HashMap<Integer, XiulianTemplate>();
	
	/** 修炼等级模版 **/
	Map<Integer, XiulianLevelTemplate> xiulianLevelTemplate = new HashMap<Integer, XiulianLevelTemplate>();
	
	/** 修炼等级采集经验模版 **/
	Map<Integer, XiulianLevelExpTemplate> xiulianLevelExpTemplate = new HashMap<Integer, XiulianLevelExpTemplate>();
	
	/** 修炼标记模版 **/
	Map<Integer, XiulianSymbolTemplate> xiulianSymbolTemplate = new HashMap<Integer, XiulianSymbolTemplate>();
	
	/** 境界模版 */
	List<JingjieTemplate> jingjieTemplates = new ArrayList<JingjieTemplate>();
	
	/** 开启功能模板 **/
	Map<Integer, OpenFunctionTemplate> openFunctionTemplate = new HashMap<Integer, OpenFunctionTemplate>();
	
	Map<String, List<OpenFunctionTemplate>> openFunctionTemplateMap = new HashMap<String, List<OpenFunctionTemplate>>();
	
	/** 在线奖励模板 **/
	Map<Short, NewPlayerPrizeTemplate> newPlayerPrizeTemplate = new HashMap<Short, NewPlayerPrizeTemplate>();
	Map<Integer, List<NewPlayerPrizeTemplate>> newPlayerPrizeTemplateMap = 
			new HashMap<Integer, List<NewPlayerPrizeTemplate>>();
	
	/** 角色等级属性模版 */
	Map<Integer,GrowLevelTemplate> growLevelPropMap = new HashMap<Integer,GrowLevelTemplate>();
	
	/** 登陆礼包 **/
	Map<Integer, DayLoginPrizeTemplate> dayLoginPrizeTmps = new HashMap<Integer, DayLoginPrizeTemplate>();
	
	private HumanXiulianService humanXiulianService;
	public HumanService(TemplateService templateService, ScheduleService scheduleService){
		
		
		Map<Integer, GrowUpLevelTemplate> map1 = templateService.getAll(GrowUpLevelTemplate.class);		 
		for(Map.Entry<Integer, GrowUpLevelTemplate> e : map1.entrySet()){
			mapLevel.put(e.getValue().getGrow(), e.getValue()); 
		}
		
		for(CoolDownCostTemplate _tmp : templateService.getAll(CoolDownCostTemplate.class).values()){
			//用大类型*100+子类型区别
			coolTypeTemplate.put(_tmp.getCoolTypeEnum().getId() * 100 + _tmp.getSubCoolType().getId(), _tmp);
		}
		
		for(RoleToSkillTemplate template : Globals.getTemplateService().getAll(RoleToSkillTemplate.class).values()){
			if(VocationType.getByCode(Integer.parseInt(template.getRoleSn()))!=VocationType.NONE){
				skillTemplate.put(VocationType.getByCode(Integer.parseInt(template.getRoleSn())), template);
			}
		}
		
		for(ResearchTemplate e : Globals.getTemplateService().getAll(ResearchTemplate.class).values()){
			yanjiuTemplate.put(e.getLevel(), e ); 
		}
		
		for(ResearchTypeTemplate e : Globals.getTemplateService().getAll(ResearchTypeTemplate.class).values()){
			yanjiuTypeTemplate.put(e.getResearchTypeEnum(), e);
		}
		
		for(XiulianTemplate e : Globals.getTemplateService().getAll(XiulianTemplate.class).values()){
			this.xiulianTemplate.put(e.getLevel(), e);
		}
		
		for(XiulianLevelTemplate e : Globals.getTemplateService().getAll(XiulianLevelTemplate.class).values()){
			this.xiulianLevelTemplate.put(e.getXiulianLevel(), e);
		}
		for(XiulianLevelExpTemplate e : Globals.getTemplateService().getAll(XiulianLevelExpTemplate.class).values()){
			this.xiulianLevelExpTemplate.put(e.getPlayerLevel(), e);
		}
		for(XiulianSymbolTemplate e : Globals.getTemplateService().getAll(XiulianSymbolTemplate.class).values()){
			this.xiulianSymbolTemplate.put(e.getSymbolId(), e);
		}
		for(VipToVitTemplate e : Globals.getTemplateService().getAll(VipToVitTemplate.class).values()){
			this.vipTemplate.put(e.getVipLevel(), e);
		}
		for(JingjieTemplate e : Globals.getTemplateService().getAll(JingjieTemplate.class).values()){
			jingjieTemplates.add(e);
		}
		for(OpenFunctionTemplate e : Globals.getTemplateService().getAll(OpenFunctionTemplate.class).values()){
			openFunctionTemplate.put(e.getFuncid(), e);
			if(!openFunctionTemplateMap.containsKey(e.getRepSN())){ 
				openFunctionTemplateMap.put(e.getRepSN(), new ArrayList<OpenFunctionTemplate>());
			}
			openFunctionTemplateMap.get(e.getRepSN()).add(e);
		}
		for(NewPlayerPrizeTemplate e : Globals.getTemplateService().getAll(NewPlayerPrizeTemplate.class).values()){
			newPlayerPrizeTemplate.put(e.getBuzhou(), e);
			if(!newPlayerPrizeTemplateMap.containsKey(e.getLevel())){
				newPlayerPrizeTemplateMap.put(e.getLevel(), new ArrayList<NewPlayerPrizeTemplate>());
			}
			newPlayerPrizeTemplateMap.get(e.getLevel()).add(e);
		}
		humanXiulianService = new HumanXiulianService(scheduleService);
		for(GrowLevelTemplate e : Globals.getTemplateService().getAll(GrowLevelTemplate.class).values()){
			growLevelPropMap.put(e.getLevel(), e);
		}
		
		for(DayLoginPrizeTemplate tmp : Globals.getTemplateService().getAll(DayLoginPrizeTemplate.class).values()){
			dayLoginPrizeTmps.put(tmp.getDay()+0, tmp);
		}
	}
	
	/**
	 * 取某天的登陆奖励
	 */
	public DayLoginPrizeTemplate getDayLoginPrizeByDay(int day){
		return dayLoginPrizeTmps.get(day);
	}
	/**
	 * 取得所有登陆奖励
	 */
	public Map<Integer, DayLoginPrizeTemplate> getAllDayLoginPrize(){
		return dayLoginPrizeTmps;
	}
	/**
	 * 根据成长等级去模版
	 * @author xf
	 */
	public GrowUpLevelTemplate getGrowUpLevelByLevel(int level){
		return this.mapLevel.get(level);
	}
	
	public RoleToSkillTemplate getRoleToSkillTemplate(VocationType vocationType){
		return skillTemplate.get(vocationType);
	}
	/**
	 * 根据冷却类型取模版
	 * @author xf
	 */
	public CoolDownCostTemplate getCoolTemplateByCoolType(CoolType cool, SubCoolType subCoolType){
		return this.coolTypeTemplate.get(cool.getId() * 100 + subCoolType.getId());
	}
	/**
	 * 根据冷却类型取模版,第一个子类型的
	 * @author xf
	 */
	public CoolDownCostTemplate getCoolTemplateByCoolType(CoolType cool){
		return this.coolTypeTemplate.get(cool.getId() * 100 + SubCoolType.SUB_ONE.getId());
	}

	public HumanXiulianService getHumanXiulianService() {
		return humanXiulianService;
	}
	
	public ResearchTemplate getResearchTemplateByLevel(int level){
		return this.yanjiuTemplate.get(level);
	}
	public ResearchTypeTemplate getByResearchType(ResearchType type){
		return this.yanjiuTypeTemplate.get(type);
	}
	public Collection<ResearchTypeTemplate> getAllResearchType(){
		return this.yanjiuTypeTemplate.values();
	}
	
	/**
	 * 修炼模版
	 * @author xf
	 */
	public XiulianTemplate getXiulianTmpByLevel(int level){
		return this.xiulianTemplate.get(level);
	}
	/**修炼等级模版
	 * @author xf
	 */
	public XiulianLevelTemplate getByXiulianLevel(int level){
		return this.xiulianLevelTemplate.get(level);
	}
	/**
	 * 修炼等级经验模版
	 * @author xf
	 */
	public XiulianLevelExpTemplate getByXiulianByPlayerLevel(int playerlevel){
		return this.xiulianLevelExpTemplate.get(playerlevel);
	}
	
	/**
	 * 修炼标记
	 * @author xf
	 */
	public XiulianSymbolTemplate getByXiulianSymbolId(int id){
		return this.xiulianSymbolTemplate.get(id);
	}
	
	/**
	 * 取得VIp模版
	 */
	public VipToVitTemplate getVipTemplate(int vipLevel){
		return this.vipTemplate.get(vipLevel);
	}
	
	/**
	 * 获取境界模版集合
	 * @return
	 */
	public List<JingjieTemplate> getJingjieTemplates() {
		return jingjieTemplates;
	}

	/**
	 * 根据成长值获取境界模版
	 * @param grow
	 * @return
	 */
	public JingjieTemplate getJingjieTemplate(int grow){
		for(int i = jingjieTemplates.size()-1;i>=0;i--){
			JingjieTemplate e = jingjieTemplates.get(i);
			if(grow >= e.getGrow()){
				return e;
			}
		}
		return null;
	}
	
	/**
	 * 根据境界值获取境界模版
	 * @param jingjie
	 * @return
	 */
	public JingjieTemplate getJingjieTemplateByJingjie(int jingjie){
		if(jingjie <1 || jingjie > jingjieTemplates.size()){
			return null;
		}
		return this.jingjieTemplates.get(jingjie - 1);
	}
	
	/**
	 * 获取最大成长上限
	 * @return
	 */
	public int getMaxGrow(){
		if(jingjieTemplates == null ||jingjieTemplates.size() == 0){
			return 0;
		}
		JingjieTemplate template = jingjieTemplates.get(jingjieTemplates.size() - 1);
		if(template == null){
			return 0;
		}
		return template.getGrow();
	}
	
	/**
	 * 获取最大境界
	 * @return
	 */
	public int getMaxJingjie(){
		if(jingjieTemplates == null ||jingjieTemplates.size() == 0){
			return 0;
		}
		JingjieTemplate template = jingjieTemplates.get(jingjieTemplates.size() - 1);
		if(template == null){
			return 0;
		}
		return template.getJingjie();
	}
	
	/**
	 * 获取境界累积攻击加成
	 * @return
	 */
	public double getMaxJingjieAtk(){
		double value = 0;
		if(jingjieTemplates == null ||jingjieTemplates.size() == 0){
			return 0;
		}
		for(JingjieTemplate e : jingjieTemplates){
			if(e != null){
				value = value + e.getAtk();
			}
		}
		return value;
	}
	
	/**
	 * 获取境界累积防御加成
	 * @return
	 */
	public double getMaxJingjieDef(){
		double value = 0;
		if(jingjieTemplates == null ||jingjieTemplates.size() == 0){
			return 0;
		}
		for(JingjieTemplate e : jingjieTemplates){
			if(e != null){
				value = value + e.getDef();
			}
		}
		return value;
	}
	
	/**
	 * 获取境界累积血量加成
	 * @return
	 */
	public double getMaxJingjieHp(){
		double value = 0;
		if(jingjieTemplates == null ||jingjieTemplates.size() == 0){
			return 0;
		}
		for(JingjieTemplate e : jingjieTemplates){
			if(e != null){
				value = value + e.getHp();
			}
		}
		return value;
	}
	
	/**
	 * 获取境界累积伤害加成
	 * @return
	 */
	public double getMaxJingjieDamage(){
		double value = 0;
		if(jingjieTemplates == null ||jingjieTemplates.size() == 0){
			return 0;
		}
		for(JingjieTemplate e : jingjieTemplates){
			if(e != null){
				value = value + e.getShanghai();
			}
		}
		return value;
	}
	
	/**
	 * 取得开放功能模板
	 */
	public OpenFunctionTemplate getOpenFuncTemplate(int funcid){
		return this.openFunctionTemplate.get(funcid);
	}
	/**
	 * 取得等级和副本sn可开放的所有功能
	 */
	public List<OpenFunctionTemplate> getOpenFuncTemplate(int level, String repSN){
		List<OpenFunctionTemplate>  list = new ArrayList<OpenFunctionTemplate>();
		if(!this.openFunctionTemplateMap.containsKey(repSN))return list;
		for(OpenFunctionTemplate t : this.openFunctionTemplateMap.get(repSN)){
			if(level >= t.getLevel()){
				list.add(t);
			}
		}
		return list;
	}
	
	/**
	 * 取得某步骤的在线奖励
	 */
	public NewPlayerPrizeTemplate getPrizeTemplateByBuzhou(short buzhou){
		return newPlayerPrizeTemplate.get(buzhou);
	}
	/**
	 * 取得该等级的所有在线礼包
	 */
	public List<NewPlayerPrizeTemplate> getPrizeTemplateByLevel(int level){
		return newPlayerPrizeTemplateMap.get(level);
	}
	
	public double getLevelAtk(int level){
		if(!this.growLevelPropMap.containsKey(level)){
			return 0;
		}
		return this.growLevelPropMap.get(level).getAtk();
	}
	
	public double getLevelDef(int level){
		if(!this.growLevelPropMap.containsKey(level)){
			return 0;
		}
		return this.growLevelPropMap.get(level).getDef();
	}
	
	public double getLevelMaxHp(int level){
		if(!this.growLevelPropMap.containsKey(level)){
			return 0;
		}
		return this.growLevelPropMap.get(level).getMaxHp();
	}
}
