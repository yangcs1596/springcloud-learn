package com.safedog.common.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

/**
 * @author wuxin
 */
@Slf4j
@Configuration
@ConditionalOnClass(ThreadPoolExecutorFactoryBean.class)
@EnableConfigurationProperties({ExecutorServiceProperties.class})
public class ExecutorServiceAutoConfiguration {

    private final ExecutorServiceProperties executorServiceProperties;

    public ExecutorServiceAutoConfiguration(ExecutorServiceProperties executorServiceProperties) {
        this.executorServiceProperties = executorServiceProperties;
    }

    @Bean
    @ConditionalOnProperty(prefix = "notary.executor-service", name = "enabled", havingValue = "true")
    public ThreadPoolExecutorFactoryBean executorService() {
        ThreadPoolExecutorFactoryBean factoryBean = new ThreadPoolExecutorFactoryBean();
        factoryBean.setThreadNamePrefix(executorServiceProperties.getThreadNamePrefix());
        factoryBean.setCorePoolSize(executorServiceProperties.getCorePoolSize());
        factoryBean.setRejectedExecutionHandler(executorServiceProperties.getRejectPolicy().getRejectHandler());
        factoryBean.setKeepAliveSeconds(executorServiceProperties.getKeepAliveSeconds());
        factoryBean.setMaxPoolSize(executorServiceProperties.getMaxPoolSize());
        return factoryBean;
    }

}
