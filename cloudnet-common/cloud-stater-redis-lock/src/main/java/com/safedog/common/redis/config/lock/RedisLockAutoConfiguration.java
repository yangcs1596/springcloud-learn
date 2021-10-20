package com.safedog.common.redis.config.lock;

import com.safedog.common.redis.lock.ExecutableLockRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * @author wuxin
 */
@ConditionalOnClass({RedisLockRegistry.class, RedisConnectionFactory.class})
@AutoConfigureAfter(RedisAutoConfiguration.class)
@RequiredArgsConstructor
@EnableConfigurationProperties(RedisLockProperties.class)
public class RedisLockAutoConfiguration {

    private final RedisLockProperties redisLockProperties;

    @Bean
    @ConditionalOnBean(RedisConnectionFactory.class)
    @ConditionalOnMissingBean(ExecutableLockRegistry.class)
    public ExecutableLockRegistry lockRegistry(RedisConnectionFactory redisConnectionFactory) {
        String registryKey = redisLockProperties.getRegistryKey();
        long expireAfter = redisLockProperties.getExpireAfter().toMillis();
        RedisLockRegistry lockRegistry = new RedisLockRegistry(redisConnectionFactory, registryKey, expireAfter);
        return new ExecutableLockRegistry(lockRegistry);
    }

}
