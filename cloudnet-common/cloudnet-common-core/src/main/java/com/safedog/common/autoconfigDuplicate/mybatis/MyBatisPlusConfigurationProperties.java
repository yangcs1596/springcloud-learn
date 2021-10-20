package com.safedog.common.autoconfigDuplicate.mybatis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wuxin
 */
@ConfigurationProperties(prefix = "notary.mybatis")
public class MyBatisPlusConfigurationProperties {

    private String logPrefix = "mybatis.mapper.";

    private OptimisticLock optimisticLock = new OptimisticLock();

    private Pageable pageable = new Pageable();

    public static class OptimisticLock {

        private boolean enabled = false;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }


    public static class Pageable {
        private boolean enabled = true;
        private Long pageSize = 500L;
        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public Long getPageSize() {
            return pageSize;
        }

        public void setPageSize(Long pageSize) {
            this.pageSize = pageSize;
        }
    }

    public String getLogPrefix() {
        return logPrefix;
    }

    public void setLogPrefix(String logPrefix) {
        this.logPrefix = logPrefix;
    }

    public OptimisticLock getOptimisticLock() {
        return optimisticLock;
    }

    public void setOptimisticLock(OptimisticLock optimisticLock) {
        this.optimisticLock = optimisticLock;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}
