package com.safedog.common.autoconfigure.idgenerator;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.safedog.common.identity.IdGenerator;
import com.safedog.common.mybatis.ModelMetaObjectHandler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuxin
 */
@Configuration
@AutoConfigureAfter(IdGeneratorAutoConfiguration.class)
@ConditionalOnClass({IKeyGenerator.class, MetaObjectHandler.class})
public class MyBatisPlusKeyGeneratorAutoConfiguration {

    @Bean
    @ConditionalOnBean(IdGenerator.class)
    @ConditionalOnProperty(prefix = "notary.mybatis", name = "enable", havingValue = "true", matchIfMissing = true)
    public MetaObjectHandler metaObjectHandler(IdGenerator idGenerator) {
        return new ModelMetaObjectHandler(idGenerator);
    }

}
