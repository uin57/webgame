package com.pwrd.war.gameserver.common.i18n;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pwrd.war.common.constants.Loggers;

/**
 * 全局事物名字临时查找表，在加载各种事物配置信息时建立，用于多语言名字引用标记的翻译，待翻译完后曾后销毁此查找表
 * 
 * 
 */
public class NameLookupTable {

	/** 表结构，名字类型对应一个map，map的key为此事物id，value为对应的名字信息 */
	private EnumMap<NameRefMark, Map<Integer, List<NameInfo>>> table;

	public NameLookupTable() {
		this.table = new EnumMap<NameRefMark, Map<Integer,List<NameInfo>>>(NameRefMark.class);
	}

	/**
	 * 向查找表中添加一项
	 * 
	 * @param type
	 * @param id
	 * @param info
	 */
	public void put(NameRefMark type, NameInfo info) {
		Map<Integer, List<NameInfo>> infoMap = table.get(type);
		if (infoMap == null) {
			infoMap = new HashMap<Integer, List<NameInfo>>();
			table.put(type, infoMap);
		}
		List<NameInfo> infoList = infoMap.get(info.getId());
		if (infoList == null) {
			infoList = new ArrayList<NameInfo>();
			infoMap.put(info.getId(), infoList);
		}
		infoList.add(info);
	}

	/**
	 * 查找type和id对应的名字信息，不区分地图
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	public NameInfo get(NameRefMark type, int id) {
		List<NameInfo> infoList = getInfoList(type, id);
		if (infoList == null || infoList.isEmpty()) {
			return null;
		}
		NameInfo result = infoList.get(0);	
		return result;
	}

	/**
	 * 按类型返回不可变的名字map
	 * 
	 * @param type
	 * @return
	 */
	public Map<Integer, List<NameInfo>> getNameInfoMap(NameRefMark type) {
		return Collections.unmodifiableMap(table.get(type));
	}
	
	private List<NameInfo> getInfoList(NameRefMark type, int id) {
		if (!table.containsKey(type)) {
			String msg = "找不到所需的名字类型，请检查相关配置表格: 类型：" + type.name();
			Loggers.gameLogger.warn(msg);
			return null;
		}
		Map<Integer, List<NameInfo>> infoMap = table.get(type);
		if (!infoMap.containsKey(id)) {
			String msg = "找不到所需的名字: 类型：" + type.name() + " id=" + id;
			Loggers.gameLogger.warn(msg);
			return null;
		}
		return infoMap.get(id);
	}
}
