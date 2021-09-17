package com.safedog.common.autoconfig.property;

import com.safedog.common.util.encryptor.DefaultEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "encrypt.config", name = "enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(EncryptablePropertyResolverProperties.class)
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
    @ConditionalOnBean(EncryptablePropertyResolver.class)
    public EnableEncryptablePropertiesBeanFactoryPostProcessor enableEncryptablePropertiesBeanFactoryPostProcessor(
            final ConfigurableEnvironment environment, EncryptablePropertyResolver encryptablePropertyResolver){
        return new EnableEncryptablePropertiesBeanFactoryPostProcessor(environment, encryptablePropertyResolver);
    }
}
