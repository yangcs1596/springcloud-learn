package com.safedog.common.redis;

import lombok.Data;

/**
 * jidis配置
 * @author ymz
 * @date 2020/12/12 14:20
 */
@Data
public class JedisPoolProperty {
    private String ip;
    private String port;
    private String timeout;
    private String maxIdle;
    private String maxTotal;
    private String testOnBorrow;
    private String maxWaitMillis;
    private String masterName;
    private String host;
    private String password;
    private String minIdle;
    private String testWhileIdle;
    private String timeBetweenEvictionRunsMillis;
    private String numTestsPerEvictionRun;
    private String minEvictableIdleTimeMillis;
}
