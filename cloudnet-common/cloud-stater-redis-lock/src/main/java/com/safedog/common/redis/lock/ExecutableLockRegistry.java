package com.safedog.common.redis.lock;

import org.springframework.integration.support.locks.LockRegistry;

import java.util.concurrent.locks.Lock;

/**
 * @author
 */
public class ExecutableLockRegistry implements LockRegistry {

    private LockRegistry lockRegistry;

    public ExecutableLockRegistry(LockRegistry lockRegistry) {
        this.lockRegistry = lockRegistry;
    }

    @Override
    public Lock obtain(Object o) {
        return lockRegistry.obtain(o);
    }

    public ExecutableLock obtainExecutableLock(Object o) {
        return new ExecutableLock(obtain(o));
    }

}
