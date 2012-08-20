package com.pwrd.war.gameserver.item;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.math.RandomUtils;

import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.item.template.XinghunItemTemplate;
import com.pwrd.war.gameserver.item.template.XinghunParaProTemplate;
import com.pwrd.war.gameserver.item.template.XinghunParaTemplate;
import com.pwrd.war.gameserver.item.xinghun.XinghunAttributeVo;


/**
 * 装备实例的属性定义
 * 
 * 
 */
public class XinghunFeature implements ItemFeature{
	
	private static int RANDOMPARA = 21;
	
	/** 此feature所属的item */
	Item item;
	  
	XinghunItemTemplate template;
	
	private String xinghunUuid; 
	
	private String xinghunSn;
	
	private int xingzuoId;
	
	private String name;
	
	private String quality;
	
	private int hunshiPrice;
	
	private int price;
	
	private String prop1;//属性1的名称
	
	private String prop2;
	
	private String prop3;
	
	private int propQualtity1;//属性1的品质
	
	private int propQualtity2;
	
	private int propQualtity3;
	
	private int propNum;//激活属性的条数
	
	private String equipmentUuid;//镶嵌的装备uuid
	
	/** 星魂的属性 **/
	private Map<ItemDef.XinghunProps, Double> addonProps;
	
	/** 星魂的属性字段集合*/
	Map<ItemDef.XinghunProps, Double> props = new HashMap<ItemDef.XinghunProps, Double>();

	public XinghunFeature(Item item) {
		this.item = item;
		this.template = (XinghunItemTemplate) item.getTemplate();
		xinghunSn = template.getItemSn();
		xinghunUuid = item.getUUID();
		equipmentUuid = null;
		addonProps = new HashMap<ItemDef.XinghunProps, Double>();
		
		//把属性字段初始化
		props.clear();
		ItemDef.XinghunProps[] propArray = ItemDef.XinghunProps.values();
		for(int i = 0; i< propArray.length; i++){
			props.put(propArray[i], 0.0);
		}
			
		this.init();
	}
	
	/**
	 * 生成一个星魂对象
	 */
	public void init(){
		
		//获得星魂属性模板
		XinghunParaTemplate paraTemplate = Globals.getXinghunParaService()
				.getXinghunParaMap().get(xinghunSn);
		
		if(paraTemplate == null){
			return;
		}
		
		xingzuoId = paraTemplate.getXingzuoId();
		
		//获得星魂的第一个初始属性
		ItemDef.XinghunProps prop = this.getAttribute();
		this.getPara(paraTemplate, prop);
		//放到属性集合
		this.setAddOnProps( 1, prop, this.getPara(paraTemplate, prop));
		this.propNum = addonProps.size();
	}
	
	@Override
	public void bindItem(Item item) {
		this.item = item;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
 
	public String toFeatureString(){
		JSONObject json = new JSONObject();
		json.put("xinghunSn", this.xinghunSn);
		json.put("name", this.name);
		json.put("prop1", this.prop1);
		json.put("prop2", this.prop2);
		json.put("prop3", this.prop3);
		json.put("propNum", this.propNum);
		json.put("addonProps", this.getAddonProps(addonProps, false));
		json.put("quality", this.quality);
		json.put("hunshiPrice", this.hunshiPrice);
		json.put("price", this.price);
		json.put("equipmentUuid", this.equipmentUuid);
		json.put("xinzuoId", this.xingzuoId);
		json.put("propQualtity1", this.propQualtity1);
		json.put("propQualtity2", this.propQualtity2);
		json.put("propQualtity3", this.propQualtity3);
		return json.toString();
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
	 * 存储星魂的属性字段和值
	 * @param index 代表三个属性的位置
	 * @param prop  属性名称
	 * @param value 属性值
	 */
	public void setAddOnProps(int index, ItemDef.XinghunProps prop, XinghunAttributeVo value){
		
		switch(index){
		case 1:
			prop1 = prop.getValue();
			propQualtity1 = value.getQualtityNum();
			break;
		case 2:
			prop2 = prop.getValue();
			propQualtity2 = value.getQualtityNum();
			break;
		case 3:
			prop3 = prop.getValue();
			propQualtity3 = value.getQualtityNum();
			break;
		default:
			break;
		}
		this.addonProps.put(prop, value.getValue());
		item.setModified();
	}
	
	/**
	 * 获取星魂属性
	 * @param map
	 * @param props
	 * @return
	 */
	public double getMapValue(Map<ItemDef.XinghunProps, Double> map, ItemDef.XinghunProps props){
		if(map.containsKey(props))return map.get(props);
		return 0;
	}


 
	/**
	 * 是否四舍五入
	 * @author xf
	 */
	public Map<String, Double> getAddonProps(Map<ItemDef.XinghunProps, Double> props, boolean bRound) { 
		Map<String, Double> map1 = new HashMap<String, Double>();
		for(Map.Entry<ItemDef.XinghunProps, Double> e : props.entrySet()){
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
			addonProps.put(ItemDef.XinghunProps.valueBy(key), json.optDouble(key));
			
		} 
	}
	
	public void initProps(JSONObject json){
		@SuppressWarnings("unchecked")
		Iterator<String> it = (Iterator<String> ) json.keys();
		while(it.hasNext()){
			String key = it.next();
			props.put(ItemDef.XinghunProps.valueBy(key), json.optDouble(key));
			
		} 
	}

	@Override
	public ItemFeature cloneTo(ItemFeature to) {
		XinghunFeature f = (XinghunFeature) to;
		f.xinghunUuid = this.xinghunUuid;
		f.addonProps = new HashMap<ItemDef.XinghunProps, Double>(this.addonProps);
		f.xinghunSn = this.xinghunSn;
		f.name = this.name;
		f.prop1 = this.prop1;
		f.prop2 = this.prop2;
		f.prop3 = this.prop3;
		f.propNum = this.propNum;
		f.addonProps = this.addonProps;
		f.quality = this.quality; 
		f.hunshiPrice = this.hunshiPrice;
		f.price = this.price;
		f.equipmentUuid = this.equipmentUuid;
		f.xingzuoId = this.xingzuoId;
		f.propQualtity1 = this.propQualtity1;
		f.propQualtity2 = this.propQualtity2;
		f.propQualtity3 = this.propQualtity3;

		return f;
	}
	
	/**
	 * 获得星魂的随机属性字段
	 * @return
	 */
	public ItemDef.XinghunProps getAttribute(){
		
		ItemDef.XinghunProps result = null;
		double random = RandomUtils.nextDouble();
		double rate = 1.0/props.size();
		for(Map.Entry<ItemDef.XinghunProps, Double> t : props.entrySet()){
			random = random - rate;
			
			//失败
			if(random > 0){
				continue;
			}
			result = t.getKey();
			props.remove(t.getKey());
			break;
		}
		return result;
	}
	
	/**
	 * 根据星魂的属性字段获得随机属性值
	 * @param template
	 * @param attribute
	 * @return
	 */
	public XinghunAttributeVo getPara(XinghunParaTemplate template, ItemDef.XinghunProps attribute){
		
		XinghunAttributeVo result = new XinghunAttributeVo();
		
		int qualityNum = template.getQualityNum();
//		double result = 0.0;
		
		switch (attribute){
		case ATK:
			result = getRandomPara(qualityNum, template.getMinAtk(), template.getMaxAtk());
			break;
		case DEF:
			result = getRandomPara(qualityNum, template.getMinDef(), template.getMaxDef());
			break;
//		case SKILLATK:
//			result = getRandomPara(template.getMinPetSkillAtk(), template.getMaxPetSkillAtk());
//			break;
//		case SKILLDEF:
//			result = getRandomPara(template.getMinPetSkillDef(), template.getMaxPetSkillDef());
//			break;
		case HP:
			result = getRandomPara(qualityNum, template.getMinHp(), template.getMaxHp());
			break;
		case SHANBI:
			result = getRandomPara(qualityNum, template.getMinShanbi(), template.getMaxShanbi());
			break;
		case MINGZHONG:
			result = getRandomPara(qualityNum, template.getMinMingzhong(), template.getMaxMingzhong());
			break;
		case CRI:
			result = getRandomPara(qualityNum, template.getMinCri(), template.getMaxCri());
			break;
		case RENXING:
			result = getRandomPara(qualityNum, template.getMinRenxing(), template.getMaxRenxing());
			break;
		case BISHA:
			result = getRandomPara(qualityNum, template.getMinBisha(), template.getMaxBisha());
			break;
//		case ADDJITUI:
//			result = getRandomPara(template.getMinAddJitui(), template.getMaxAddJitui());
//			break;
//		case REDJITUI:
//			result = getRandomPara(template.getMinRedJitui(), template.getMaxRedJitui());
//			break;
		default:
			break;
		
		}
		
		return result;
	}
	
	private XinghunAttributeVo getRandomPara(int qualtityNum, int min, int max){
		
		XinghunAttributeVo result = new XinghunAttributeVo();
		
		//先根据品质取出可能出现的模板
		List<XinghunParaProTemplate> paraProList = Globals.getXinghunParaService().getXinghunParaProMap().get(qualtityNum);
		
		//随机数字获得模板
		double templateRandom = RandomUtils.nextDouble();
		
		XinghunParaProTemplate template = new XinghunParaProTemplate();
		for(XinghunParaProTemplate x : paraProList){
			templateRandom = templateRandom - x.getProbability();
			if(templateRandom > 0){
				continue;
			}else{
				template = x;
				break;
			}
		}
		
		//产生0-20之间的整数 
		int random = RandomUtils.nextInt(RANDOMPARA);
		
		//计算加权值
		double add = template.getMinVaule() + (template.getMaxValue() - template.getMinVaule())/(RANDOMPARA-1)* random;
		
		
		result.setQualtityNum(template.getAttributeNum());
		
		int value = min + (int)((max - min) * add);
		
		result.setValue(value);
		
		return result;	
	}
	
	private XinghunAttributeVo getRandomPara(int qualtityNum, double min, double max){
		
		XinghunAttributeVo result = new XinghunAttributeVo();
		
		//先根据品质取出可能出现的模板
		List<XinghunParaProTemplate> paraProList = Globals.getXinghunParaService().getXinghunParaProMap().get(qualtityNum);
		
		//随机数字获得模板
		double templateRandom = RandomUtils.nextDouble();
		
		XinghunParaProTemplate template = new XinghunParaProTemplate();
		for(XinghunParaProTemplate x : paraProList){
			if(templateRandom - x.getProbability() > 0){
				templateRandom = templateRandom - x.getProbability();
				continue;
			}else{
				template = x;
				break;
			}
		}
		
		//产生0-20之间的整数 
		int random = RandomUtils.nextInt(RANDOMPARA);
		
		//计算加权值
		double add = template.getMinVaule() + (template.getMaxValue() - template.getMinVaule())/(RANDOMPARA-1)* random;
		
		
		result.setQualtityNum(template.getAttributeNum());
		
		double value = min + (max - min) * add;
		
		result.setValue(value);
		
		return result;
	}

	@Override
	public String toPropertyString() {
		JSONObject jsonObj = new JSONObject();
		//收集属性
		jsonObj.put("xinghunSn", this.xinghunSn);
		jsonObj.put("name", this.name);
		jsonObj.put("prop1", this.prop1);
		jsonObj.put("prop2", this.prop2);
		jsonObj.put("prop3", this.prop3);
		jsonObj.put("propNum", this.propNum);
		jsonObj.put("addonProps", this.getAddonProps(addonProps, false));
		jsonObj.put("props", this.getAddonProps(props, false));	
		jsonObj.put("quality", this.quality);
		jsonObj.put("hunshiPrice", this.hunshiPrice);
		jsonObj.put("price", this.price);
		jsonObj.put("equipmentUuid", this.equipmentUuid);
		jsonObj.put("xingzuoId", this.xingzuoId);
		jsonObj.put("propQualtity1", this.propQualtity1);
		jsonObj.put("propQualtity2", this.propQualtity2);
		jsonObj.put("propQualtity3", this.propQualtity3);
		
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
		
		this.setXinghunSn(jsnobj.optString("xinghunSn"));
		this.setName(jsnobj.optString("name"));
		this.setProp1(jsnobj.optString("prop1"));
		this.setProp2(jsnobj.optString("prop2"));
		this.setProp3(jsnobj.optString("prop3"));
		this.setPropNum(jsnobj.optInt("propNum"));

		JSONObject addonProps = jsnobj.optJSONObject("addonProps");
		this.initAddonProps(addonProps);
		
		JSONObject props = jsnobj.optJSONObject("props");
		this.initAddonProps(props);
		
		this.setQuality(jsnobj.optString("quality"));
		this.setHunshiPrice(jsnobj.optInt("hunshiPrice"));
		this.setPrice(jsnobj.optInt("price"));
		this.setEquipmentUuid(jsnobj.optString("equipmentUuid"));
		this.setXingzuoId(jsnobj.optInt("xingzuoId"));
		
		this.setPropQualtity1(jsnobj.optInt("propQualtity1"));
		this.setPropQualtity2(jsnobj.optInt("propQualtity2"));
		this.setPropQualtity3(jsnobj.optInt("propQualtity3"));
	}
	
	public int getIndex(String prop){
		if(prop.equals(prop1)){
			return 1;
		}
		if(prop.equals(prop2)){
			return 2;
		}
		if(prop.equals(prop3)){
			return 3;
		}
		return -1;
	}
	
	/**
	 * 随机属性前的预处理
	 * @param value
	 */
	public void changeProp(String value){
		ItemDef.XinghunProps prop = ItemDef.XinghunProps.valueBy(value);
		addonProps.remove(prop);
		props.put(prop, 0.0);
	}

	public XinghunItemTemplate getTemplate() {
		return template;
	}

	public void setTemplate(XinghunItemTemplate template) {
		this.template = template;
	}

	public String getXinghunUuid() {
		return xinghunUuid;
	}

	public void setXinghunUuid(String xinghunUuid) {
		this.xinghunUuid = xinghunUuid;
	}

	public String getXinghunSn() {
		return xinghunSn;
	}

	public void setXinghunSn(String xinghunSn) {
		this.xinghunSn = xinghunSn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public int getHunshiPrice() {
		return hunshiPrice;
	}

	public void setHunshiPrice(int hunshiPrice) {
		this.hunshiPrice = hunshiPrice;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getProp1() {
		return prop1;
	}

	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}

	public String getProp2() {
		return prop2;
	}

	public void setProp2(String prop2) {
		this.prop2 = prop2;
	}

	public String getProp3() {
		return prop3;
	}

	public void setProp3(String prop3) {
		this.prop3 = prop3;
	}

	public int getPropNum() {
		return propNum;
	}

	public void setPropNum(int propNum) {
		this.propNum = propNum;
		item.setModified();
	}

	public Map<ItemDef.XinghunProps, Double> getAddonProps() {
		return addonProps;
	}

	public void setAddonProps(Map<ItemDef.XinghunProps, Double> addonProps) {
		this.addonProps = addonProps;
	}

	public Map<ItemDef.XinghunProps, Double> getProps() {
		return props;
	}

	public void setProps(Map<ItemDef.XinghunProps, Double> props) {
		this.props = props;
	}

	public String getEquipmentUuid() {
		return equipmentUuid;
	}

	public void setEquipmentUuid(String equipmentUuid) {
		this.equipmentUuid = equipmentUuid;
	}

	public int getXingzuoId() {
		return xingzuoId;
	}

	public void setXingzuoId(int xingzuoId) {
		this.xingzuoId = xingzuoId;
	}

	public int getPropQualtity1() {
		return propQualtity1;
	}

	public void setPropQualtity1(int propQualtity1) {
		this.propQualtity1 = propQualtity1;
	}

	public int getPropQualtity2() {
		return propQualtity2;
	}

	public void setPropQualtity2(int propQualtity2) {
		this.propQualtity2 = propQualtity2;
	}

	public int getPropQualtity3() {
		return propQualtity3;
	}

	public void setPropQualtity3(int propQualtity3) {
		this.propQualtity3 = propQualtity3;
	}
	
}
