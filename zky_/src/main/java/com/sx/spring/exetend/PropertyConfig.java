package com.sx.spring.exetend;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 
 * <p>
 * Title: PropertyConfig
 * </p>
 * <p>
 * Description: 注解，直接在java中注解，获得propeties中的属性值
 * </p>
 * <p>
 * Company: bksx
 * </p>
 * 
 * @author 殷龙飞
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PropertyConfig {
	String value() default "";

	boolean required() default true;
}
