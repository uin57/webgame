package com.pwrd.war.core.template;

import java.util.BitSet;
import java.util.Collection;

import com.pwrd.war.common.TemplateLevelRange;
import com.pwrd.war.common.constants.RoleConstants;
import com.pwrd.war.common.exception.TemplateConfigException;

/**
 * 检查配置表格中所填的等级段是否合法，对于一个sheet内的等级段，需要满足下面条件才是合法的<br/>
 * <ul>
 * <li>起始等级和终止等级必须是一个合法的角色等级</li>
 * <li>起始等级不能超过终止等级</li>
 * <li>所有等级段不能有相交的部分</li>
 * <li>所有等级段的并集应为所有等级的的集合，也就是不能存在一个级别不属于任何级别段</li>
 * </ul>
 * 检查不通过会抛出TemplateConfigException
 * 
 * 
 */
public class LevelRangeValidater {

	/**
	 * 检查一系列级别段是否合法
	 * 
	 * @param ranges
	 */
	public void checkLevelRanges(Collection<TemplateLevelRange> ranges) {
		for (TemplateLevelRange range : ranges) {
			checkLevelRange(range);
		}
		BitSet bs = new BitSet(RoleConstants.HUMAN_MAX_LEVEL_NUM + 1);
		String templateName = "";
		for (TemplateLevelRange range : ranges) {
			for (int i = range.getStartLevel(); i <= range.getEndLevel(); i++) {
				if (bs.get(i)) {
					// 有重叠的级别段
					throw new TemplateConfigException(range.getTemplateName(),
							range.getId(), String.format(
									"等级段[%d,%d]与其他等级段有重叠的部分，级别%d包括在两个级别段中",
									range.getStartLevel(), range.getEndLevel(),
									i));
				}
				bs.set(i);
				templateName = range.getTemplateName();
			}
		}
		for (int i = 1; i <= RoleConstants.HUMAN_MAX_LEVEL_NUM; i++) {
			if (!bs.get(i)) {
				throw new TemplateConfigException(templateName, -1,
						String.format("所有等级段合起来也不能覆盖所有级别，级别%d不包括在任何等级段中", i));
			}
		}
	}

	/**
	 * 检查一个级别段是否合法
	 * 
	 * @param range
	 */
	public void checkLevelRange(TemplateLevelRange range) {
		int start = range.getStartLevel();
		int end = range.getEndLevel();
		if (!checkLevel(start) || !checkLevel(end)) {
			throw new TemplateConfigException(range.getTemplateName(),
					range.getId(), String.format("等级段[%d,%d]非法", start, end));
		}
		if (start > end) {
			throw new TemplateConfigException(range.getTemplateName(),
					range.getId(), String.format("等级段[%d,%d]非法，起始等级不能超过终止等级",
							start, end));
		}
	}

	private boolean checkLevel(int level) {
		if (level > 0 && level <= RoleConstants.HUMAN_MAX_LEVEL_NUM) {
			return true;
		} else {
			return false;
		}
	}
}
