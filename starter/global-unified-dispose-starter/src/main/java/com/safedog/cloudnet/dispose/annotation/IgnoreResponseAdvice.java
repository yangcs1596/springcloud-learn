package com.safedog.cloudnet.dispose.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author ycs
 * @description
 * @date 2021/10/11 16:21
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseAdvice {
    /**
     * 是否忽略全局封装返回
     * @return true:进行处理;  false:不进行异常处理
     */
    boolean errorDispose() default true;
}
