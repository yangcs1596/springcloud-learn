package com.safedog.common.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ycs
 * @description
 * @date 2022/1/22 10:40
 */
//@AutoConfigureAfter(RedisConfig.class)
public class RedisCacheConfig {
    @Value("${redis.globalSessionTimeout:3600}")
    private long globalSessionTimeout;
    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory)
                //默认的缓存配置(没有配置键的key均使用此配置)
                .cacheDefaults(getDefaultCacheConfiguration())
                .withInitialCacheConfigurations(getCacheConfigurations())
                //在spring事务正常提交时才缓存数据
                .transactionAware()
                .build();
    }

    /**
     * 配置缓存键值对的过期时间
     *
     * @return
     */
    private Map<String, RedisCacheConfiguration> getCacheConfigurations() {
        Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
        //缓存键,且3600秒后过期,3600秒后再次调用方法时需要重新缓存
        configurationMap.put("AllMenuList", this.getDefaultCacheConfiguration(globalSessionTimeout));
        configurationMap.put("Menus", this.getDefaultCacheConfiguration(globalSessionTimeout));
        configurationMap.put("NotButtonList", this.getDefaultCacheConfiguration(globalSessionTimeout));
        configurationMap.put("UserMenuList", this.getDefaultCacheConfiguration(globalSessionTimeout));
        return configurationMap;
    }

    /**
     * 获取redis的缓存配置(针对于键)
     *
     * @param seconds 键过期时间
     * @return
     */
    private RedisCacheConfiguration getDefaultCacheConfiguration(long seconds) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(jackson2JsonRedisSerializer)
        ).entryTtl(Duration.ofSeconds(seconds));
        return redisCacheConfiguration;
    }

    /**
     * 获取Redis缓存配置,此处获取的为默认配置
     * 如对键值序列化方式,是否缓存null值,是否使用前缀等有特殊要求
     * 可另行调用 RedisCacheConfiguration 的构造方法
     *
     * @return
     */
    private RedisCacheConfiguration getDefaultCacheConfiguration() {
        // 注意此构造函数为 spring-data-redis-2.1.9 及以上拥有,经试验 已知spring-data-redis-2.0.9及以下版本没有此构造函数
        // 但观察源码可得核心不过是在值序列化器(valueSerializationPair)的构造中注入 ClassLoader 即可
        return RedisCacheConfiguration.defaultCacheConfig(resourceLoader.getClassLoader());
    }
}
