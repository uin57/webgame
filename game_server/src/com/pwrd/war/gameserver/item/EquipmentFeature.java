package com.pwrd.war.gameserver.item;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.ItemDef.AttrGroup;
import com.pwrd.war.gameserver.item.ItemDef.EquipProps;
import com.pwrd.war.gameserver.item.ItemDef.XinghunProps;
import com.pwrd.war.gameserver.item.template.EquipItemTemplate;
import com.pwrd.war.gameserver.item.template.ItemTemplate;


/**
 * 装备实例的属性定义
 * 
 * 
 */
public class EquipmentFeature implements ItemFeature{
	
	/** 此feature所属的item */
	Item item;
	  
	EquipItemTemplate template;
	/** 强化等级 */
	private int enhanceLevel;
	
	/** 该强化等级的名称 **/
	private String enhanceName;
	/** 绑定状态 */
	private int freezeState = ItemDef.FreezeState.NOMAL.index;
	
	/** 取消绑定的时间 */
	private long cancelTime = 0L;
	
	/** 附加的属性 **/
	private Map<ItemDef.EquipProps, Double> addonProps;
	
	
	/** 下一强化等级的属性 **/
	private Map<ItemDef.EquipProps, Double> nextLevelProps;
	/** 下一强化等级需要钱币  **/
	private int nextLevelMoney;
	
	private Integer[] xinghun = {-1,-1,-1,-1,-1,-1};
	
	private Map<String, String> xinghunProps; 
	
	public EquipmentFeature(Item item) {
		this.item = item;
		this.template = (EquipItemTemplate) item.getTemplate();
		addonProps = new HashMap<ItemDef.EquipProps, Double>();
		nextLevelProps = new HashMap<ItemDef.EquipProps, Double>();
//		//初始化，默认0
//		for(ItemDef.EquipProps p : ItemDef.EquipProps.values()){
//			addonProps.put(p, 0D);
//		}
		//默认0级
		this.enhanceLevel = ItemDef.INIT_ENHANCE_LEVEL;
		xinghunProps = new HashMap<String, String>();
		
		this.updateXinghunIndex();
	}
	
	
	@Override
	public void bindItem(Item item) {
		this.item = item;
	}
	 
	public int getEnhanceLevel() {
		return enhanceLevel;
	}
	
	public void setEnhanceLevel(int enhanceLevel) {
		if (enhanceLevel < ItemDef.INIT_ENHANCE_LEVEL) {
			enhanceLevel = ItemDef.INIT_ENHANCE_LEVEL;
		}
		this.enhanceLevel = enhanceLevel;
		item.setModified();
	}
	
	public int getFreezeState() {
		return freezeState;
	}

	public void setFreezeState(int freezeState) {
		if(ItemDef.FreezeState.valueOf(freezeState) == null){
			freezeState = ItemDef.FreezeState.NOMAL.index;
		}
		this.freezeState = freezeState;
		item.setModified();
	}
	public long getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(long cancelTime) {
		this.cancelTime = cancelTime;
		item.setModified();
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
 
	public String toFeatureString(){
		JSONObject json = new JSONObject();
		json.put("enhanceLevel", this.enhanceLevel);
		json.put("enhanceName", this.enhanceName);
		json.put("addonProps", this.getAddonProps(true));
		json.put("nextLevelMoney", this.nextLevelMoney);
		json.put("nextLevelProps", this.getNextAddonProps(true));
		
		JSONObject json1 = new JSONObject();
		json1.put("enhance", json);
		
		JSONObject json2 = new JSONObject();
		if(this.getXinghunAtk() > 0){
			json2.put("atk", (int)Math.round(this.getXinghunAtk()));
		}
		if(this.getXinghunDef() > 0){
			json2.put("def", (int)Math.round(this.getXinghunDef()));
		}
		if(this.getXinghunCri() > 0){
			json2.put("cri", (int)Math.round(this.getXinghunCri()));
		}
		if(this.getXinghunMingzhong() > 0){
			json2.put("mingzhong", (int)Math.round(this.getXinghunMingzhong()));
		}
		if(this.getXinghunShanbi() > 0){
			json2.put("shanbi", (int)Math.round(this.getXinghunShanbi()));
		}
		if(this.getXinghunSpd() > 0){
			json2.put("spd", (int)Math.round(this.getXinghunSpd()));
		}
		
		
		json1.put("star", json2);
		
		json1.put("xinghunProps", xinghunProps);
		json1.put("xinghun", xinghun);

		return json1.toString();
	}

	@Override
	public void recoverEndure() {		
	}
	@Override
	public int getCurEndure() {
		return 1;
	}
	@Override
	public void setCurEndure(int curEndure) { 		
	}
	@Override
	public boolean isFullEndure() { 
		return true;
	}
	@Override
	public int getCurMaxEndure() { 
		return 1;
	}
 
	/**
	 * 设置附加属性值
	 * @author xf
	 */
	public void setAddOnProps(EquipProps prop, double value){
		this.addonProps.put(prop, value);
		item.setModified();
	}
	
	private double getMapValue(Map<ItemDef.EquipProps, Double> map, EquipProps props){
		if(map.containsKey(props))return map.get(props);
		return 0;
	}
	
	public double getSpd() {
		return this.template.getSpd() + this.getMapValue(addonProps, EquipProps.SPD);
	}
	public double getAtk() {
		return this.template.getAtk() + this.getMapValue(addonProps, EquipProps.ATK) + this.getXinghunAtk();
	} 
	public double getDef() {
		return this.template.getDef() + this.getMapValue(addonProps, EquipProps.DEF) + this.getXinghunDef();
	} 
	public double getCri() {
		return this.template.getCri() + this.getMapValue(addonProps, EquipProps.CRI) + this.getXinghunCri();
	}
	public double getFanji() {
		return this.template.getFanji() + this.getMapValue(addonProps, EquipProps.FANJI);
	} 
	public double getMingzhong() {
		return this.template.getMingzhong() + this.getMapValue(addonProps, EquipProps.MINGZHONG) + this.getXinghunMingzhong();
	} 
	public double getShanbi() {
		return this.template.getShanbi() + this.getMapValue(addonProps, EquipProps.SHANBI) + this.getXinghunShanbi();
	}
	public double getLianji() {
		return this.template.getLianji() + this.getMapValue(addonProps, EquipProps.LIANJI);
	} 
	public double getShanghai() {
		return this.template.getShanghai() + this.getMapValue(addonProps, EquipProps.SHANGHAI);
	}
	public double getMianshang() {
		return this.template.getMianshang() + this.getMapValue(addonProps, EquipProps.MIANSHANG);
	} 
	public double getZhandouli() {
		//战斗力计算公式
		//生命/3.5+防御+攻击
		return this.getMaxHp()/3.5 + this.getDef() + this.getAtk();
//		return this.template.getZhandouli() + this.getMapValue(addonProps, EquipProps.ZHANDOULI);
	}
	public double getMaxHp(){
		return this.template.getMaxHp() + this.getMapValue(addonProps, EquipProps.MAXHP);
	}


	public String getEnhanceName() {
		return enhanceName;
	}


	public void setEnhanceName(String enhanceName) {
		this.enhanceName = enhanceName;
	}

 
	/**
	 * 是否四舍五入
	 * @author xf
	 */
	public Map<String, Double> getAddonProps(boolean bRound) { 
		Map<String, Double> map1 = new HashMap<String, Double>();
		for(Map.Entry<EquipProps, Double> e : addonProps.entrySet()){
			if(bRound){
				map1.put(e.getKey().getValue(), (double) Math.round(e.getValue()));
			}else{
				map1.put(e.getKey().getValue(), e.getValue());
			}
		}
		return map1;
	}
 
	public void initAddonProps(JSONObject json){
		@SuppressWarnings("unchecked")
		Iterator<String> it = (Iterator<String> ) json.keys();
		while(it.hasNext()){
			String key = it.next();
			addonProps.put(EquipProps.valueBy(key), json.optDouble(key));
			
		} 
	}
	/**
	 * 是否四舍五入
	 * @author xf
	 */
	public Map<String, Double> getNextAddonProps(boolean bRound) { 
		Map<String, Double> map1 = new HashMap<String, Double>();
		for(Map.Entry<EquipProps, Double> e : nextLevelProps.entrySet()){
			if(bRound){
				double d = e.getValue() - this.addonProps.get(e.getKey());
				map1.put(e.getKey().getValue(), 
						(double)(Math.round(this.getAtk()+d) - Math.round(this.getAtk())));
			}else{
				map1.put(e.getKey().getValue(), e.getValue() - this.addonProps.get(e.getKey()));
			}
//			if(bRound){
//				map1.put(e.getKey().getValue(), (double) Math.round(e.getValue()));
//			}else{
//				map1.put(e.getKey().getValue(), e.getValue());
//			}
		}
		return map1;
	}
 
	public void initNextAddonProps(JSONObject json){
		@SuppressWarnings("unchecked")
		Iterator<String> it = (Iterator<String> ) json.keys();
		while(it.hasNext()){
			String key = it.next();
			nextLevelProps.put(EquipProps.valueBy(key), json.optDouble(key)); 
		} 
	}

	@Override
	public ItemFeature cloneTo(ItemFeature to) {
		EquipmentFeature f = (EquipmentFeature) to;
		f.addonProps = new HashMap<ItemDef.EquipProps, Double>(this.addonProps);
		f.cancelTime = this.cancelTime;
		f.enhanceLevel = this.enhanceLevel;
		f.enhanceName = this.enhanceName;
		f.freezeState = this.freezeState; 
		f.nextLevelMoney = this.nextLevelMoney;
		f.nextLevelProps = new HashMap<ItemDef.EquipProps, Double>(this.nextLevelProps);
		f.xinghunProps = new HashMap<String, String>(this.xinghunProps);
		return f;
	}


	public Map<ItemDef.EquipProps, Double> getNextLevelProps() {
		return nextLevelProps;
	}

 
	/**
	 * 设置下一级附加属性值
	 * @author xf
	 */
	public void setNextAddOnProps(EquipProps prop, double value){
		this.nextLevelProps.put(prop, value);
		item.setModified();
	}
	
	public int getNextLevelMoney() {
		return nextLevelMoney;
	}
	public void setNextLevelMoney(int nextLevelMoney) {
		this.nextLevelMoney = nextLevelMoney;
	}
	
	/**
	 * 取得所有战斗属性
	 * @author xf
	 */
	public Map<String, Integer> getBattleProps(){
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put(EquipProps.ATK.getValue(), (int) Math.round(this.getAtk()));
		map.put(EquipProps.DEF.getValue(), (int) Math.round(this.getDef()));
		map.put(EquipProps.SPD.getValue(), (int) Math.round(this.getSpd()));
		map.put(EquipProps.CRI.getValue(), (int) Math.round(this.getCri()));
		map.put(EquipProps.MAXHP.getValue(), (int) Math.round(this.getMaxHp()));
		map.put(EquipProps.FANJI.getValue(), (int) Math.round(this.getFanji()));
		map.put(EquipProps.MINGZHONG.getValue(), (int) Math.round(this.getMingzhong()));
		map.put(EquipProps.SHANBI.getValue(), (int) Math.round(this.getShanbi()));
		map.put(EquipProps.LIANJI.getValue(), (int) Math.round(this.getLianji()));
		map.put(EquipProps.SHANGHAI.getValue(), (int) Math.round(this.getShanghai()));
		map.put(EquipProps.MIANSHANG.getValue(), (int) Math.round(this.getMianshang()));
		map.put(EquipProps.ZHANDOULI.getValue(), (int) Math.round(this.getZhandouli()));
		
		Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
		while(it.hasNext()){
			if(it.next().getValue() ==0)it.remove();
		}
		return map;
	}


	@Override
	public String toPropertyString() {
		JSONObject jsonObj = new JSONObject();
		//收集强化属性
		int enhLevel = this.getEnhanceLevel();
		jsonObj.put("enhanceLevel", enhLevel);		
		jsonObj.put("enhanceName", this.getEnhanceName()); 
		jsonObj.put("addOns", this.getAddonProps(false)); 
		jsonObj.put("nextLevelMoney", this.getNextLevelMoney()); 
		jsonObj.put("nextLevelProps", this.getNextAddonProps(false));
		//收集物品的绑定状态
		int freezeState = this.getFreezeState();
		jsonObj.put(ItemDef.AttrGroup.FREEZESTATE.index, freezeState);
		//收集物品的取消绑定的时间
		long cancelTime = this.getCancelTime();
		jsonObj.put(ItemDef.AttrGroup.CANCELTIME.index, cancelTime);
		
		jsonObj.put("xinghun",xinghun);
		jsonObj.put("xinghunProps", xinghunProps);
		
		return jsonObj.toString();
	}


	@Override
	public void fromPropertyString(String propStr) {
		if (StringUtils.isEmpty(propStr)) {
			return;
		}
		JSONObject jsnobj = JSONObject.fromObject(propStr);
		if (jsnobj.isEmpty()) {
			return;
		}
		// 强化等级
		String key = "enhanceLevel";
		this.setEnhanceLevel(jsnobj.has(key) ? jsnobj.getInt(key) : ItemDef.INIT_ENHANCE_LEVEL);
		this.setEnhanceName(jsnobj.optString("enhanceName"));
		JSONObject addon = jsnobj.optJSONObject("addOns");
		this.initAddonProps(addon);
		this.setNextLevelMoney(jsnobj.optInt("nextLevelMoney"));
		this.initNextAddonProps(jsnobj.optJSONObject("nextLevelProps"));
		
		// 绑定状态
		String freezeStateKey = String.valueOf(AttrGroup.FREEZESTATE.index);
		this.setFreezeState(jsnobj.has(freezeStateKey) ? jsnobj.getInt(freezeStateKey) : ItemDef.FreezeState.NOMAL.index);
		// 取消绑定的时间
		String cancelTimeKey = String.valueOf(AttrGroup.CANCELTIME.index);
		if(jsnobj.get(cancelTimeKey) == null){
			this.setCancelTime(0);
		}
		else{
			try{
				Long cancelTime = new Long(jsnobj.get(cancelTimeKey).toString().trim());
				this.setCancelTime(cancelTime);
			}
			catch(NumberFormatException e){
				this.setCancelTime(0);
			}
		}
		
		
//		Object[] fff = jsnobj.getJSONArray("xinghun").toArray();
//		this.xinghun =  (int[])jsnobj.get("xinghun");
		
		jsnobj.getJSONArray("xinghun").toArray(xinghun);
		
		JSONObject xinghunProps = jsnobj.optJSONObject("xinghunProps");
		this.initXinghunProps(xinghunProps);

	}
	
	public double getXinghunSpd() {
		Human human = item.getOwner();
		double result = 0.0;
		for(int i = 0; i<5; i++){
			int index = xinghun[i];
			if(index < 0){
				continue;
			}

			Item item = human.getInventory().getItemByIndex(BagType.XIANGQIAN,
					"", index);
			if (item == null) {
				continue;
			}

			ItemTemplate itemTmpl = item.getTemplate();
			if (itemTmpl == null) {
				continue;
			}

//			XinghunFeature feature = (XinghunFeature) item.getFeature();
//			if (feature != null && feature.getAddonProps().get(XinghunProps.ATK) != null) {
//				result += feature.getAddonProps().get(XinghunProps.ATK);
//			}
		}
		return result;
	}
	public double getXinghunAtk() {
		Human human = item.getOwner();
		double result = 0.0;
		for(int i = 0; i<5; i++){
			int index = xinghun[i];
			if(index < 0){
				continue;
			}

			Item item = human.getInventory().getItemByIndex(BagType.XIANGQIAN,
					"", index);
			if (item == null) {
				continue;
			}

			ItemTemplate itemTmpl = item.getTemplate();
			if (itemTmpl == null) {
				continue;
			}

			XinghunFeature feature = (XinghunFeature) item.getFeature();
			if (feature != null && feature.getAddonProps().get(XinghunProps.ATK) != null) {
				result += feature.getAddonProps().get(XinghunProps.ATK);
			}
		}
		return result;		
	} 
	public double getXinghunDef() {
		Human human = item.getOwner();
		double result = 0.0;
		for(int i = 0; i<5; i++){
			int index = xinghun[i];
			if(index < 0){
				continue;
			}

			Item item = human.getInventory().getItemByIndex(BagType.XIANGQIAN,
					"", index);
			if (item == null) {
				continue;
			}

			ItemTemplate itemTmpl = item.getTemplate();
			if (itemTmpl == null) {
				continue;
			}

			XinghunFeature feature = (XinghunFeature) item.getFeature();
			if (feature != null && feature.getAddonProps().get(XinghunProps.DEF) != null) {
				result += feature.getAddonProps().get(XinghunProps.DEF);
			}
		}
		return result;	
	} 
	public double getXinghunCri() {
		Human human = item.getOwner();
		double result = 0.0;
		for(int i = 0; i<5; i++){
			int index = xinghun[i];
			if(index < 0){
				continue;
			}

			Item item = human.getInventory().getItemByIndex(BagType.XIANGQIAN,
					"", index);
			if (item == null) {
				continue;
			}

			ItemTemplate itemTmpl = item.getTemplate();
			if (itemTmpl == null) {
				continue;
			}

			XinghunFeature feature = (XinghunFeature) item.getFeature();
			if (feature != null && feature.getAddonProps().get(XinghunProps.CRI) != null) {
				result += feature.getAddonProps().get(XinghunProps.CRI);
			}
		}
		return result;	
	}
//	public double getXinghunFanji() {
//		
//	} 
	public double getXinghunMingzhong() {
		Human human = item.getOwner();
		double result = 0.0;
		for(int i = 0; i<5; i++){
			int index = xinghun[i];
			if(index < 0){
				continue;
			}

			Item item = human.getInventory().getItemByIndex(BagType.XIANGQIAN,
					"", index);
			if (item == null) {
				continue;
			}

			ItemTemplate itemTmpl = item.getTemplate();
			if (itemTmpl == null) {
				continue;
			}

			XinghunFeature feature = (XinghunFeature) item.getFeature();
			if (feature != null && feature.getAddonProps().get(XinghunProps.MINGZHONG) != null) {
				result += feature.getAddonProps().get(XinghunProps.MINGZHONG);
			}
		}
		return result;	
	} 
	public double getXinghunShanbi() {
		Human human = item.getOwner();
		double result = 0.0;
		for(int i = 0; i<5; i++){
			int index = xinghun[i];
			if(index < 0){
				continue;
			}

			Item item = human.getInventory().getItemByIndex(BagType.XIANGQIAN,
					"", index);
			if (item == null) {
				continue;
			}

			ItemTemplate itemTmpl = item.getTemplate();
			if (itemTmpl == null) {
				continue;
			}

			XinghunFeature feature = (XinghunFeature) item.getFeature();
			if (feature != null && feature.getAddonProps().get(XinghunProps.SHANBI) != null) {
				result += feature.getAddonProps().get(XinghunProps.SHANBI);
			}
		}
		return result;	
	}
//	public double getXinghunLianji() {
//	} 
//	public double getShanghai() {
//		
//	}
//	public double getMianshang() {
//		
//	} 
	
	public void setXinghunIndex(int xingzuoId, int index){
		if(xingzuoId < 1 || xingzuoId > 6){
			return;
		}
		xinghun[xingzuoId -1 ] = index;
		
		this.updateXinghunIndex();
	}
	
	public static void main(String[] args) {
		final int[] xinghun11= {-1,-1,-1,-1,-1,-1};
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("starIndex", xinghun11);
		
		Integer[] array = new Integer[xinghun11.length];
		
		jsonObj.getJSONArray("starIndex").toArray(array);
		
		for(Integer a : array) {
			System.out.println(a);
		}
	}
	
	public void updateXinghunIndex(){
		xinghunProps.clear();
		Human human = item.getOwner();
		for(int i = 0; i < 6; i++){
			if(xinghun[i] != -1){
				Item xingHunItem = human.getInventory().getItemByIndex(BagType.XIANGQIAN,
						"", xinghun[i]);
				if (xingHunItem == null) {
					continue;
				}

				ItemTemplate itemTmpl = xingHunItem.getTemplate();
				if (itemTmpl == null) {
					continue;
				}

				XinghunFeature feature = (XinghunFeature) xingHunItem.getFeature();
				
				xinghunProps.put( xinghun[i]+"", feature.toFeatureString());
			}
		}
		item.setModified();
	}
	
	public void initXinghunProps(JSONObject json){
		if(json == null){
			return;
		}
		@SuppressWarnings("unchecked")
		Iterator<String> it = (Iterator<String> ) json.keys();
		while(it.hasNext()){
			String key = it.next();
			xinghunProps.put(key, json.optString(key));
			
		} 
	}
}
