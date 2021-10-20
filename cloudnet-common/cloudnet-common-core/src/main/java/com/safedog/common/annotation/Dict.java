package com.safedog.common.annotation;

import com.safedog.common.constants.SysDictEnum;

import java.lang.annotation.*;

/**
 * @author ycs
 * @description 数据字典的注解
 * @date 2021/10/16 14:37
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Dict {
    /**
     * 所属业务的命名， 1代表基础配置
     */
    String appId() default "1";

    /**
     * 字典类型
     */
    String dictCode() default "";
    /**
     * 字典编码
     */
    String targetField() default "";

    /**
     * 字典枚举类型，如果指定了枚举，则dictCode无效
     */
    SysDictEnum dictEnum() default SysDictEnum.DEFAUlT;

}
