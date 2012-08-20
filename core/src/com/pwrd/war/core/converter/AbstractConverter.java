package com.pwrd.war.core.converter;

/**
 * 
 * 转换器的抽象实现
 * 
 * @param <FROM>
 * @param <TO>
 */
public class AbstractConverter<FROM,TO> implements Converter<FROM,TO>{

	@Override
	public TO convert(FROM src) {
		return null;
	}

	@Override
	public FROM reverseConvert(TO src) {
		return null;
	}


}
