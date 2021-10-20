package com.safedog.common.redis.config.lock;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * @author wuxin
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@ConfigurationProperties("notary.lock.redis")
public class RedisLockProperties {

    String registryKey = "spring-lock-registry";

    Duration expireAfter = Duration.ofSeconds(30);

}
