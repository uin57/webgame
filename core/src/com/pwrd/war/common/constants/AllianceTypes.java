//package com.pwrd.war.common.constants;
//
//import java.util.List;
//
//import com.pwrd.war.core.enums.IndexedEnum;
//import com.pwrd.war.core.util.EnumUtil;
//import com.pwrd.war.core.util.MathUtils;
//
///**
// * 
// * 玩家阵营
// * 
// */
//public enum AllianceTypes implements IndexedEnum {
//	/** 无阵营 */
//	LESS(0, CommonLangConstants_10000.ALLIANCE_LESS),
//	/** 同盟 */
//	TONGMENG(1, LangConstants.ALLIANCE_TONGMENG),
//	/** 轴心 */
//	ZHOUXIN(2, LangConstants.ALLIANCE_ZHOUXIN),
//	/** 共产国际 */
//	GONGCHANGUOJI(4, LangConstants.ALLIANCE_GONGCHANGUOJI),
//
//	
//	;
//	
//	public static final int MAX_JOB_MASK = 1 | 2 | 4 | 8;
//	
//	private final int index;
//	
//	private final Integer nameLangKey;
//	
//	private AllianceTypes(int index, Integer nameLangKey) {
//		this.index = index;
//		this.nameLangKey = nameLangKey;
//	}
//
//	@Override
//	public int getIndex() {
//		return index;
//	}
//	
//	public Integer getNameLangKey() {
//		return nameLangKey;
//	}
//
//	private static final List<AllianceTypes> values = IndexedEnumUtil
//			.toIndexes(AllianceTypes.values());
//
//	public static AllianceTypes valueOf(int index) {
//		return EnumUtil.valueOf(values, index);
//	}
//	
//	/**
//	 * 随机一个职业，不包括{@link #LESS}
//	 * 
//	 * @return
//	 */
//	public static AllianceTypes randomJobExceptLess() {
//		AllianceTypes[] jobs = AllianceTypes.values();
//		return jobs[MathUtils.random(1, jobs.length - 1)];
//	}
//	
//}