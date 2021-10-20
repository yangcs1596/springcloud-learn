package com.safedog.common.redis.config;

import com.safedog.common.redis.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Author: 杨春生
 * @Date: 2020/3/30 09:25
 * @Description:
 */
@RequiredArgsConstructor
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {
    /**
     * redis的封装工具
     * @param connectionFactory
     * @return
     */
    @Bean("redisUtils")
    public RedisUtils<String> createRedisUtils(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        RedisUtils<String> redisUtils = new RedisUtils(redisTemplate);
        return redisUtils;
    }

}
