package com.safedog.cloudnet.generate.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: JDBCAnnotation
 * @Description: jdbc注解
 * @Author: xiaojl
 * @CreateDate: 2020-12-28 14:31
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JDBCAnnotation {
  //驱动名称
  String driver();
  //URL地址
  String url();
  //用户名
  String name();
  //密码
  String password();
}