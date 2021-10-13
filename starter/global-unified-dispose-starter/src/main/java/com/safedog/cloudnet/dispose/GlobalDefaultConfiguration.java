package com.safedog.cloudnet.dispose;

import com.safedog.cloudnet.dispose.advice.CommonResponseAdvice;
import com.safedog.cloudnet.dispose.advice.GlobalDefaultExceptionHandler;
import com.safedog.cloudnet.dispose.properties.GlobalDefaultProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * {@link GlobalDefaultExceptionHandler} {@link CommonResponseAdvice} bean配置加载
 *
 */
@Configuration
@EnableConfigurationProperties(GlobalDefaultProperties.class)
@PropertySource(value = "classpath:dispose.properties", encoding = "UTF-8")
public class GlobalDefaultConfiguration {

//    @Bean
//    public GlobalDefaultExceptionHandler globalDefaultExceptionHandler() {
//        return new GlobalDefaultExceptionHandler();
//    }
//
//    @Bean
//    public CommonResponseAdvice commonResponseDataAdvice(GlobalDefaultProperties globalDefaultProperties) {
//        return new CommonResponseAdvice(globalDefaultProperties);
//    }

}
