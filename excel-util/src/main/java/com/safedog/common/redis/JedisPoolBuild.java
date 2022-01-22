package com.safedog.common.redis;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author ymz
 * @date 2020/12/15 11:39
 */
public class JedisPoolBuild {

    /**
     * 读取配置
     *
     * @param sentinel
     * @return
     */
    private JedisPoolProperty loadJedisProperty(Properties properties, boolean sentinel) {
        // 哨兵模式
        if (sentinel) {
            return loadJedisPoolPropertySentinel(properties);
        }
        return loadJedisPoolPropertySingle(properties);
    }

    /**
     * 单机模式读取配置
     */
    private JedisPoolProperty loadJedisPoolPropertySingle(Properties properties) {
        String ip = getProperty("redis.ip", properties);
        if (StringUtils.isBlank(ip)) {
            return null;
        }
        String maxIdle = getProperty("redis.pool.maxIdle", properties);
        String maxTotal = getProperty("redis.pool.maxTotal", properties);
        String testOnBorrow = getProperty("redis.pool.testOnBorrow", properties);
        String maxWaitMillis = getProperty("redis.maxWaitMillis", properties);
        String port = getProperty("redis.port", properties);
        String timeout = getProperty("redis.timeout", properties);
        String password = getProperty("redis.pass", properties);
        JedisPoolProperty jedisPoolProperty = new JedisPoolProperty();
        jedisPoolProperty.setIp(ip);
        jedisPoolProperty.setPort(port);
        jedisPoolProperty.setPassword(password);

        jedisPoolProperty.setTimeout(timeout);
        jedisPoolProperty.setMaxIdle(maxIdle);
        jedisPoolProperty.setMaxTotal(maxTotal);
        jedisPoolProperty.setMaxWaitMillis(maxWaitMillis);
        jedisPoolProperty.setTestOnBorrow(testOnBorrow);
        return jedisPoolProperty;
    }

    /**
     * 哨兵模式读取配置
     */
    private JedisPoolProperty loadJedisPoolPropertySentinel(Properties properties) {
        String sentinelHosts = getProperty("redis.sentinel.host", properties);
        if (StringUtils.isBlank(sentinelHosts)) {
            return null;
        }
        String password = getProperty("redis.password", properties);
        String masterName = getProperty("redis.masterName", properties);
        String maxTotal = getProperty("redis.pool.maxTotal", properties);
        String maxIdle = getProperty("redis.pool.maxIdle", properties);
        String minIdle = getProperty("redis.pool.minIdle", properties);
        String maxWaitMillis = getProperty("redis.pool.maxWaitMillis", properties);
        String testWhileIdle = getProperty("redis.pool.testWhileIdle", properties);
        String timeBetweenEvictionRunsMillis = getProperty("redis.pool.timeBetweenEvictionRunsMillis", properties);
        String numTestsPerEvictionRun = getProperty("redis.pool.numTestsPerEvictionRun", properties);
        String minEvictableIdleTimeMillis = getProperty("redis.pool.minEvictableIdleTimeMillis", properties);
        String timeout = getProperty("redis.timeout", properties);

        JedisPoolProperty jedisPoolProperty = new JedisPoolProperty();
        jedisPoolProperty.setHost(sentinelHosts);
        jedisPoolProperty.setPassword(password);
        jedisPoolProperty.setTimeout(timeout);
        jedisPoolProperty.setMasterName(masterName);
        jedisPoolProperty.setMaxIdle(maxIdle);
        jedisPoolProperty.setMaxTotal(maxTotal);
        jedisPoolProperty.setMaxWaitMillis(maxWaitMillis);
        jedisPoolProperty.setMinIdle(minIdle);
        jedisPoolProperty.setTestWhileIdle(testWhileIdle);
        jedisPoolProperty.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        jedisPoolProperty.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        jedisPoolProperty.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        return jedisPoolProperty;
    }

    private String getProperty(String key, Properties properties) {
        return properties.getProperty(key);
    }

    /**
     * 初始化JedisPool
     */
    public JedisPool initJedisPool(Properties properties) {
        // 读取配置
        JedisPoolProperty property = loadJedisProperty(properties, false);
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(Integer.parseInt(property.getMaxIdle()));
        config.setMaxTotal(Integer.parseInt(property.getMaxTotal()));
        String testOnBorrow = property.getTestOnBorrow();
        if(StringUtils.isBlank(testOnBorrow)){
            config.setTestOnBorrow(false);
        } else {
            config.setTestOnBorrow(Boolean.parseBoolean(testOnBorrow));
        }
        config.setMaxWaitMillis(Long.parseLong(property.getMaxWaitMillis()));
        JedisPool jedisPool = new JedisPool(config, property.getIp(), Integer.parseInt(property.getPort()),
                Integer.parseInt(property.getTimeout()),
                property.getPassword()
        );
        return jedisPool;
    }

    /**
     * 初始化JedisSentinelPool
     */
    public JedisSentinelPool initJedisSentinelPool(Properties properties) {
        // 读取配置
        JedisPoolProperty property = loadJedisProperty(properties, true);
        // 参数
        String masterName = property.getMasterName();
        String sentinelHosts = property.getHost();
        Set<String> sentinels = new TreeSet<>(Arrays.asList(sentinelHosts.split(",")));
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(Integer.parseInt(property.getMaxTotal()));
        poolConfig.setMaxIdle(Integer.parseInt(property.getMaxIdle()));
        poolConfig.setMinIdle(Integer.parseInt(property.getMinIdle()));
        poolConfig.setMaxWaitMillis(Long.parseLong(property.getMaxWaitMillis()));
        poolConfig.setTestWhileIdle(Boolean.parseBoolean(property.getTestWhileIdle()));
        poolConfig.setTimeBetweenEvictionRunsMillis(Long.parseLong(property.getTimeBetweenEvictionRunsMillis()));
        poolConfig.setNumTestsPerEvictionRun(Integer.parseInt(property.getNumTestsPerEvictionRun()));
        poolConfig.setMinEvictableIdleTimeMillis(Long.parseLong(property.getMinEvictableIdleTimeMillis()));
        String testOnBorrow = property.getTestOnBorrow();
        if(StringUtils.isBlank(testOnBorrow)){
            poolConfig.setTestOnBorrow(false);
        } else {
            poolConfig.setTestOnBorrow(Boolean.parseBoolean(testOnBorrow));
        }
        String clientName = getProperty("redis.client.name", properties);
        clientName = StringUtils.isBlank(clientName) ? null : clientName;
        int connectionTimeout = Integer.parseInt(property.getTimeout());
        int soTimeout = connectionTimeout;
        String password = property.getPassword();
        // 默认
        int database = 0;
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName, sentinels, poolConfig,
                connectionTimeout, soTimeout, password, database, clientName);
        return jedisSentinelPool;
    }
}
