package com.ztesoft.mobile.v2.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 过滤器标记
 * @author heisonyee
 *
 */
@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface FilterTag {
	public String value() default "";
	/** 是否跳过REST服务日志记录的拦截处理，默认：true */
	public boolean skipServLog() default true;
	/** 是否跳过REST服务的拦截处理，默认：true */
	public boolean skipRestServ() default true;
	/** 是否跳过用户控制的拦截处理，默认：true */
	public boolean skipStaffControls() default true;
	/** 是否跳过流量控制的拦截处理，默认：true */
	public boolean skipFlowControls() default true;
	
}