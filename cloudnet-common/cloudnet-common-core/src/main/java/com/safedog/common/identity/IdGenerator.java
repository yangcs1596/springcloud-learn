package com.safedog.common.identity;

/**
 * @author wuxin
 */
public interface IdGenerator {

    long nextId();

    default String nextStringId() {
        return String.valueOf(nextId());
    }

}
