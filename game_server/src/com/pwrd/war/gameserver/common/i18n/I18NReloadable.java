package com.pwrd.war.gameserver.common.i18n;

import com.pwrd.war.common.Reloadable.IParameter;
import com.pwrd.war.common.Reloadable.IResult;

/**
 * 定义多语言相关的重新加载
 * 
  *
 * 
 */
public class I18NReloadable {

	/**
	 * 任务重新加载的参数
	 * 
	  *
	 * 
	 */
	public static class Parameter implements IParameter {
		/** 多语言名称 */
		public final String langName;

		public Parameter(String langName) {
			super();
			this.langName = langName;
		}

	}

	/**
	 * 任务重新加载的结果
	 * 
	  *
	 * 
	 */
	public static class Result implements IResult {
		/** 重新加载的结果 */
		public final Object dict;
		/** 该次加载的参数对象 */
		public final Parameter param;

		public Result(Object dict, Parameter param) {
			this.dict = dict;
			this.param = param;
		}
	}
}
