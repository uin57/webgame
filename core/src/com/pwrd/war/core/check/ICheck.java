package com.pwrd.war.core.check;

/**
 * 检查接口,用于进行某一项验证的工作
 * 
 * 
 */
public interface ICheck {
	/**
	 * 进行检查工作
	 * 
	 * @return true,校验通过;false,校验未通过
	 */
	public boolean check();
}
