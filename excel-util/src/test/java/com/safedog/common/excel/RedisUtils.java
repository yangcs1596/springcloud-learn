/**
 * Copyright©2018 Xiamen Meiah Pico IT CO., Ltd.
 * All rights reserved.
 */
package com.safedog.common.excel;

import com.safedog.common.util.CommonRedisUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 功能/模块：<br/>
 * 描述：<br/>
 *
 * @author liwb2
 * @date 2020/12/9.
 */
public class RedisUtils extends CommonRedisUtils {
    private static volatile RedisUtils instance;
    public static ReentrantLock lock = new ReentrantLock();

    private RedisUtils(Properties properties,boolean sentinelMode){
        initJedis(properties,sentinelMode);
    }

    public static RedisUtils getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    boolean modeCache = initSentinelModeCache();
                    Properties properties = initProperties();
                    instance = new RedisUtils(properties,modeCache);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * 初始化redis部署模式
     */
    private static boolean initSentinelModeCache() {
        //if (sentinelModeCache == null) {
//            String property = StringUtils.defaultIfBlank(DbConfigUtil.getInstance().value().
//                    getConfig(DbConfigUtil.SYS_EYES, "redis.run.mode"),"0");
//            log.info("#init redis run mode:" + property + ".");
//            sentinelModeCache = Integer.valueOf(REDIS_RUN_MODE_SENTINEL).equals(Integer.parseInt(property.trim()));
        return false;
        //}
    }

    private static Properties initProperties() {
        Properties properties = null;
        if (initSentinelModeCache()) {
            try {
                properties = PropertiesLoaderUtils.loadAllProperties("properties/redis-sentinel.properties");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                properties = PropertiesLoaderUtils.loadAllProperties("redis-sentinel.properties");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //测试
        return properties;
    }
}
