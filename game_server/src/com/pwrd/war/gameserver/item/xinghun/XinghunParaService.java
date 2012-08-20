package com.pwrd.war.gameserver.item.xinghun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.item.template.XinghunParaProTemplate;
import com.pwrd.war.gameserver.item.template.XinghunParaTemplate;
import com.pwrd.war.gameserver.item.template.XinghunXilianTemplate;


/**
 * 强化装备概率变化
 * 
 * @author xf
 * @param <XinghunParaTemplate>
 */
public class XinghunParaService{
	
	private Map<String, XinghunParaTemplate> xinghunParaMap = new HashMap<String, XinghunParaTemplate>();
	
	private Map<Integer, XinghunXilianTemplate> xilianMap = new HashMap<Integer, XinghunXilianTemplate>();
	 
	private Map<Integer, List<XinghunParaProTemplate>> xinghunParaProMap = new HashMap<Integer, List<XinghunParaProTemplate>>();
	
	public XinghunParaService() {
		
		Map<Integer, XinghunParaTemplate> map = Globals.getTemplateService().getAll(XinghunParaTemplate.class);
		for(Map.Entry<Integer, XinghunParaTemplate> m : map.entrySet() ){
			xinghunParaMap.put(m.getValue().getXinghunSn(), m.getValue());
		}
		
		xilianMap = Globals.getTemplateService().getAll(XinghunXilianTemplate.class);
		
		//构造随机属性的一些坑爹模板
		Map<Integer, XinghunParaProTemplate> map1 = Globals.getTemplateService().getAll(XinghunParaProTemplate.class);
		for(Map.Entry<Integer, XinghunParaProTemplate> m : map1.entrySet()){
			if(!xinghunParaProMap.containsKey(m.getValue().getQualityNum())){
				xinghunParaProMap.put(m.getValue().getQualityNum(), new ArrayList<XinghunParaProTemplate>());
			}		
			xinghunParaProMap.get(m.getValue().getQualityNum()).add(m.getValue());
		}
	}

		
	
	
	public Map<String, XinghunParaTemplate> getXinghunParaMap() {
		return xinghunParaMap;
	}

	public void setXinghunParaMap(Map<String, XinghunParaTemplate> xinghunParaMap) {
		this.xinghunParaMap = xinghunParaMap;
	}
	
	/**
	 * 获得星魂洗练费用模板
	 * @param prop1
	 * @param prop2
	 * @param prop3
	 * @param quantity
	 * @return
	 */
	public XinghunXilianTemplate getXilianTemp(int prop1, int prop2, int prop3, int qualtity){
		int lock = 0;
		int xilian = 0;
		
		if(prop1 == 0){
			lock++;
		}
		if(prop1 == 1){
			xilian++;
		}
		if(prop2 == 0){
			lock++;
		}
		if(prop2 == 1){
			xilian++;
		}
		if(prop3 == 0){
			lock++;
		}
		if(prop3 == 1){
			xilian++;
		}
		
		XinghunXilianTemplate result = new XinghunXilianTemplate();
		for(Map.Entry<Integer, XinghunXilianTemplate> t : xilianMap.entrySet()){
			XinghunXilianTemplate x = t.getValue();
			if(x.getQualtityNum() == qualtity && x.getLock() == lock && x.getRefresh() == xilian){
				result = x;
				break;
			}
		}
		
		return result;
	}




	public Map<Integer, List<XinghunParaProTemplate>> getXinghunParaProMap() {
		return xinghunParaProMap;
	}

	public void setXinghunParaProMap(
			Map<Integer, List<XinghunParaProTemplate>> xinghunParaProMap) {
		this.xinghunParaProMap = xinghunParaProMap;
	}
	
}
