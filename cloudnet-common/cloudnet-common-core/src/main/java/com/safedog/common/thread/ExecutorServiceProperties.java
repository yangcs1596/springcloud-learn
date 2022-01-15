package com.safedog.common.thread;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@ConfigurationProperties(prefix = "notary.executor-service")
public class ExecutorServiceProperties {

    private boolean enabled = false;
    private String threadNamePrefix = "NotaryCloud-";
    /**
     * 核心线程数
     */
    private int corePoolSize = 50;
    /**
     * 任务拒绝处理器
     */
    private RejectPolicy rejectPolicy = RejectPolicy.CALLER_RUNS;
    /**
     * 线程空闲时间
     */
    private int keepAliveSeconds = 60;
    /**
     * 最大线程数
     */
    private int maxPoolSize = Integer.MAX_VALUE;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public RejectPolicy getRejectPolicy() {
        return rejectPolicy;
    }

    public void setRejectPolicy(RejectPolicy rejectPolicy) {
        this.rejectPolicy = rejectPolicy;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public enum RejectPolicy {
        /**
         * 处理程序遭到拒绝将抛出RejectedExecutionException
         */
        ABORT(new ThreadPoolExecutor.AbortPolicy()),
        /**
         * 放弃当前任务
         */
        DISCARD(new ThreadPoolExecutor.DiscardPolicy()),
        /**
         * 如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重试执行程序（如果再次失败，则重复此过程）
         */
        DISCARD_OLDEST(new ThreadPoolExecutor.DiscardOldestPolicy()),
        /**
         * 由主线程来直接执行
         */
        CALLER_RUNS(new ThreadPoolExecutor.CallerRunsPolicy());

        private RejectedExecutionHandler rejectHandler;

        RejectPolicy(RejectedExecutionHandler rejectHandler) {
            this.rejectHandler = rejectHandler;
        }

        public RejectedExecutionHandler getRejectHandler() {
            return this.rejectHandler;
        }

    }

}
