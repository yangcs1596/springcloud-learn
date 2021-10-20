package com.safedog.spark.annotation;

import com.safedog.spark.configure.SparkConfiguration;
import com.safedog.spark.service.WordCountService;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ycs
 * @description
 * @date 2021/10/15 9:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({SparkConfiguration.class, WordCountService.class})
public @interface EnableSpark {
}
