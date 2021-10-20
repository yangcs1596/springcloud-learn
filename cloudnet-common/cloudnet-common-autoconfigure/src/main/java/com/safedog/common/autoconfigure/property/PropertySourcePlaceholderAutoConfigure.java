package com.safedog.common.autoconfigure.property;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurablePropertyResolver;

/**
 * @author ycs
 * @description
 * @date 2021/10/19 9:37
 */
public class PropertySourcePlaceholderAutoConfigure extends PropertySourcesPlaceholderConfigurer {
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, ConfigurablePropertyResolver propertyResolver) throws BeansException {
        this.processProperties(beanFactoryToProcess, propertyResolver);
    }
}
