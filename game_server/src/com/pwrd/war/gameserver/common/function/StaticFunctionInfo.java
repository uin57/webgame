package com.pwrd.war.gameserver.common.function;

import java.util.ArrayList;
import java.util.List;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.BeanFieldNumber;
import com.pwrd.war.core.util.StringUtils;

/**
 * 场景静态功能配置信息
 *
 * @author haijiang.jin
 * 
 */
@ExcelRowBinding
public class StaticFunctionInfo {
	
	/** 功能标题多语言Id */
	@BeanFieldNumber(number = 3)
	private int titleLangId;
	/** 功能标题 */
	@BeanFieldNumber(number = 4)
	private String title;
	
	/** 功能类型 */
	@BeanFieldNumber(number = 5)
	private int type;

	/** 功能描述多语言Id */
	@BeanFieldNumber(number = 6)
	private int descLangId;
	
	/** 功能描述 */
	@BeanFieldNumber(number = 7)
	private String desc;
	
	/** 功能参数1 */
	@BeanFieldNumber(number = 8)
	private String arg1;
	
	/** 功能参数2 */
	@BeanFieldNumber(number = 9)
	private String arg2;
	
	/** 功能参数3 */
	@BeanFieldNumber(number = 10)
	private String arg3;
	
	/** 功能参数4 */
	@BeanFieldNumber(number = 11)
	private String arg4;
	
	private String[] args;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getArg1() {
		return arg1;
	}

	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}
	
	public String getArg2() {
		return arg2;
	}

	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}

	public String getArg3() {
		return arg3;
	}

	public void setArg3(String arg3) {
		this.arg3 = arg3;
	}

	public String getArg4() {
		return arg4;
	}

	public void setArg4(String arg4) {
		this.arg4 = arg4;
	}

	public int getTitleLangId() {
		return titleLangId;
	}

	public void setTitleLangId(int titleLangId) {
		this.titleLangId = titleLangId;
	}

	public int getDescLangId() {
		return descLangId;
	}

	public void setDescLangId(int descLangId) {
		this.descLangId = descLangId;
	}

	public String[] getArgs() {
		if (args == null) {
			List<String> _l = new ArrayList<String>();
			if (!StringUtils.isEmpty(arg1))
				_l.add(arg1);
			if (!StringUtils.isEmpty(arg2))
				_l.add(arg2);
			if (!StringUtils.isEmpty(arg3))
				_l.add(arg3);
			if (!StringUtils.isEmpty(arg4))
				_l.add(arg4);
			this.args = _l.toArray(new String[_l.size()]);
		}
		return args;
	}

	@Override
	public String toString() {
		return "标题=" + title + ", 类型=" + type + ", a="
				+ arg1 + ", b=" + arg2 + ", c=" + arg3 + ", d=" + arg4;
	}

}
