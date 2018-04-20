package com.tfkj.framework.core.utils.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface Excel {
	/**
	 * 列名
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * 宽度
	 * 
	 * @return
	 */
	int width() default 20;

	/**
	 * 忽略该字段
	 * 
	 * @return
	 */
	boolean skip() default false;

	/**
	 * 添加注释 <br>
	 * exp："{0:开启,1:关闭}"
	 * 
	 * @return
	 */
	String content() default "";
	
	/**
	 * 序号
	 * @return
	 */
	boolean number() default false;
	
	/**
	 * 数据字典码值
	 * @return
	 */
	String dictCode() default "";
}
