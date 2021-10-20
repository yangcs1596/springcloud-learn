package com.safedog.common.autoconfigure.property;

import com.safedog.common.util.encryptor.DefaultEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 加密工具
 * @author ycs
 */
@RequiredArgsConstructor
@ConditionalOnClass({ConfigurableEnvironment.class, EncryptablePropertyResolver.class, DefaultEncryptor.class})
@EnableConfigurationProperties(EncryptablePropertyResolverProperties.class)
@ConditionalOnProperty(prefix = "encrypt.config", name = "enable", havingValue = "true")
public class LocalEncryptablePropertiesBeanFactoryPostProcessorAutoConfig {

    private final EncryptablePropertyResolverProperties encryptablePropertyResolverProperties;

    @Bean
    @ConditionalOnMissingBean
    public EncryptablePropertyResolver encryptablePropertyResolver(){
        return new EncryptablePropertyResolver(new DefaultEncryptor(encryptablePropertyResolverProperties.getKey()),
                encryptablePropertyResolverProperties.getPrefix(),
                encryptablePropertyResolverProperties.getSuffix());
    }

    @Bean
    public static EnableEncryptPostProcessor enableEncryptPostProcessor(
           final ConfigurableEnvironment environment,
           EncryptablePropertyResolver encryptablePropertyResolver){
        return new EnableEncryptPostProcessor(environment, encryptablePropertyResolver);
    }
}
