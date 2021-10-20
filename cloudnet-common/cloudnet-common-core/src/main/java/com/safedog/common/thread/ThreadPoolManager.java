package com.safedog.common.thread;

import org.springframework.beans.factory.DisposableBean;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 统一线程池管理
 *
 */
public final class ThreadPoolManager implements DisposableBean {
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
    private static ThreadPoolExecutor sysLogthreadPool = null;

    private static final Object OBJECT = new Object();
    private static final Object OBJECT1 = new Object();

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
                            new ArrayBlockingQueue<>(2),
                            // 线程命名
                            new NameTheadFactory("Console ThreadPoolManager"),
                            // 队列满了，加线程到超过最大线程，被拒绝同时抛出异常
                            new ThreadPoolExecutor.AbortPolicy());
                }
            }
        }
        return threadPool;
    }

    /**
     * syslog thread pool
     * @return
     */
    public static ThreadPoolExecutor getSysLogThreadPool() {
        if (sysLogthreadPool == null) {
            synchronized (OBJECT1) {
                if (sysLogthreadPool == null) {
                    sysLogthreadPool = new ThreadPoolExecutor(
                            16,
                            16,
                            KEEP_ALIVE_TIME,
                            TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(),
                            new NameTheadFactory("syslogSend-pool"),
                            new ThreadPoolExecutor.AbortPolicy());
                }
            }
        }
        return sysLogthreadPool;
    }

    @Override
    public void destroy() throws Exception {
        if(threadPool!=null){
            threadPool.shutdown();
        }
        if(sysLogthreadPool!=null){
            sysLogthreadPool.shutdown();
        }
    }
}

