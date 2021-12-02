package com.safedog.common.autoconfigure.idgenerator;


import com.safedog.common.identity.IdGenerator;
import com.safedog.common.identity.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

/**
 * @author wuxin
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(IdGeneratorProperties.class)
@ConditionalOnClass(SnowflakeIdGenerator.class)
public class IdGeneratorAutoConfiguration {

    private static final Random RANDOM = new Random();

    private final IdGeneratorProperties idGeneratorProperties;

    @Bean
    @ConditionalOnMissingBean(IdGenerator.class)
    public IdGenerator idGenerator() {
        Long workerId = idGeneratorProperties.getWorkerId();
        Long datacenterId = idGeneratorProperties.getDatacenterId();
        if (workerId == null) {
            workerId = random(SnowflakeIdGenerator.maxWorkerId);
            log.warn("snowflake worker id not set, generate random worker id: {}", workerId);
        }
        if (datacenterId == null) {
            datacenterId = random(SnowflakeIdGenerator.maxDataCenterId);
            log.warn("snowflake datacenter id not set, generate random datacenter id {}", datacenterId);
        }
        return new SnowflakeIdGenerator(workerId, datacenterId);
    }

    private long random(long max) {
        return RANDOM.nextInt((int) max);
    }

}
