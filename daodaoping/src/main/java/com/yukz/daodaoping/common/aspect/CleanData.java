package com.yukz.daodaoping.common.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 清空缓存标记
 * @author vipsw
 * @varsion V1.0
 * @date: 2020/6/28 12:02
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CleanData {
    String value() default "";
}
