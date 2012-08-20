package com.pwrd.war.core.converter;

/**
 * 数据转换器,用于将对象在两种类型之间<tt>FROM</tt>和<tt>TO</tt>的相互转换
 * 
  *
 * 
 * @param <FROM>
 *            原数据类型
 * @param <TO>
 *            目标数据类型
 */
public interface Converter<FROM, TO> {
	/**
	 * 将指定的对象<tt>src</tt>的属性对应设置到<tt>dest</tt>中,即从<tt>FROM</tt>转换为<tt>TO</tt>
	 * 
	 * @param src
	 *            转换的源对象
	 * @param dest
	 *            转换的目标对象
	 * 
	 */
	public TO convert(FROM src);

	/**
	 * 将指定的对象<tt>src</tt>的属性对应设置到<tt>dest</tt>中,进行反向转换,即从<tt>TO</tt>转换为<tt>FROM</tt>类型
	 * 
	 * @param src
	 * @param dest
	 * 
	 */
	public FROM reverseConvert(TO src);

}
