package com.safedog.common.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 统一线程池管理
 *
 * @author wujf
 * @date 2020/10/20 15:18
 */
public final class ThreadPoolManager {
    /**
     * 线程池维护线程的最少数量
     */
    private static final int CORE_POOL_SIZE = 20;
    /**
     * 线程池维护线程的最大数量
     */
    private static final int MAX_POOL_SIZE = 200;
    /**
     * 终止空闲多长时间的线程。
     */
    private static final long KEEP_ALIVE_TIME = 60L;

    private static ThreadPoolExecutor threadPool = null;

    private static final Object OBJECT = new Object();

    private ThreadPoolManager() {
    }

    public static ThreadPoolExecutor getThreadPool() {
        if (threadPool == null) {
            synchronized (OBJECT) {
                if (threadPool == null) {
                    threadPool = new ThreadPoolExecutor(
                            CORE_POOL_SIZE,
                            MAX_POOL_SIZE,
                            KEEP_ALIVE_TIME,
                            TimeUnit.SECONDS,
                            // 有界缓存队列，满了才加线程处理
                            new ArrayBlockingQueue<>(20),
                            // 线程命名
                            new NameTheadFactory("easyExcel ThreadPoolManager"),
                            // 队列满了，加线程到超过最大线程，被拒绝同时抛出异常
                            new ThreadPoolExecutor.AbortPolicy());
                }
            }
        }
        return threadPool;
    }
}
