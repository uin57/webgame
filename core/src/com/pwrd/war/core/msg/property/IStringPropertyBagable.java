package com.pwrd.war.core.msg.property;

public interface IStringPropertyBagable extends IPropertyBagable{
	public void readFromString (String str); //由一个String构建一个IPropertyBagable对象
}
