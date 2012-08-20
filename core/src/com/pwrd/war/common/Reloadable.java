package com.pwrd.war.common;

/**
 * 可重新加载的对象接口
 * 
 * 
 */
public interface Reloadable {
	/**
	 * 根据指定的参数重新加载配置
	 * 
	 * @param iParameter
	 *            重新加载的参数
	 * @return 重新加载后的结果
	 */
	public IResult reload(IParameter iParameter);

	/**
	 * 处理重新加载的结果,该结果通常是由{@link #reload(IParameter)}执行后得到
	 * 
	 * @param iResult
	 * @return true,处理成功;false,处理失败
	 */
	public boolean afterReload(IResult iResult);

	/**
	 * 重新加载的参数
	 * 
	  *
	 * 
	 */
	public interface IParameter {

	}

	/**
	 * 重新加载的结果
	 * 
	  *
	 * 
	 */
	public interface IResult {

	}
}
