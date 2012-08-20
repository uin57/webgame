package com.pwrd.war.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标识变化的属性定义,如模板对象中的属性一部分是不可变的(只随着模板变化而变化),另一部分的属性是与玩家绑定的,需要单独保留
 * 
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MutableProp {
}
