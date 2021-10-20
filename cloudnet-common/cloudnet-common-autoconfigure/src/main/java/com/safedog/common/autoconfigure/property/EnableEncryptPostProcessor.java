package com.safedog.common.autoconfigure.property;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * 注入解密
 * @author ycs
 */
public class EnableEncryptPostProcessor implements BeanPostProcessor, Ordered {

    private final ConfigurableEnvironment environment;
    private final EncryptablePropertyResolver encryptablePropertyResolver;

    public EnableEncryptPostProcessor(ConfigurableEnvironment environment, EncryptablePropertyResolver encryptablePropertyResolver) {
        this.environment = environment;
        this.encryptablePropertyResolver = encryptablePropertyResolver;
    }

    /**
     * 只对String类型的加解密
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //这里如何让代码只执行一次
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.forEach(pv -> {
            PropertySource propertySource = propertySources.get(pv.getName());
            if(propertySource instanceof MapPropertySource){
                Map<String, Object> convertPropertySource = new HashMap<String, Object>();
                MapPropertySource newMapPropertySource = new MapPropertySource(pv.getName(), convertPropertySource);
                String[] propertyNames = ((MapPropertySource) propertySource).getPropertyNames();
                for (String propertyName : propertyNames) {
                    Object value = propertySource.getProperty(propertyName);
                    if(value instanceof  String){
                        convertPropertySource.put(propertyName, encryptablePropertyResolver.resolvePropertyValue((String)value));
                    }else{
                        convertPropertySource.put(propertyName, value);
                    }
                }
                propertySources.replace(pv.getName(), newMapPropertySource);
            }
        });
        return bean;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
