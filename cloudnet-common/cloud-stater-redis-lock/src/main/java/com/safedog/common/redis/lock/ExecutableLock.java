package com.safedog.common.redis.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

/**
 * @author ycs
 */
@Slf4j
public class ExecutableLock implements Lock {

    private final Lock delegate;

    public ExecutableLock(Lock lock) {
        this.delegate = lock;
    }

    public <R> R execute(Supplier<R> bizFun) {
        return execute(bizFun, false);
    }

    public void execute(Runnable runnable) {
        execute(runnable, false);
    }

    public void execute(Runnable runnable, boolean ignoreReleaseLockError) {
        Supplier<Void> supplier = () -> {
            runnable.run();
            return null;
        };
        execute(supplier, ignoreReleaseLockError);
    }

    public <R> R execute(Supplier<R> bizFun, boolean ignoreReleaseLockError) {
        Throwable error = null;
        try {
            delegate.lock();
            return bizFun.get();
        } catch (Throwable e) {
            log.warn("execute with lock has some exception.", e);
            error = e;
            throw e;
        } finally {
            try {
                delegate.unlock();
            } catch (Exception releaseLockError) {
                if (ignoreReleaseLockError) {
                    log.debug("release lock failed", releaseLockError);
                } else {
                    rethrowReleaseLockException(releaseLockError, error);
                }
            }
        }
    }

    @Override
    public void lock() {
        delegate.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        delegate.lockInterruptibly();
    }

    @Override
    public boolean tryLock() {
        return delegate.tryLock();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return delegate.tryLock(time, unit);
    }

    @Override
    public void unlock() {
        delegate.unlock();
    }

    @Override
    public Condition newCondition() {
        return delegate.newCondition();
    }

    private void rethrowReleaseLockException(Throwable releaseLockError, Throwable lockError) {
        ReflectionUtils.rethrowRuntimeException(new ReleaseLockException(releaseLockError, lockError));
    }

    private static class ReleaseLockException extends Exception {

        protected ReleaseLockException(Throwable releaseLockError, Throwable lockError) {
            super(releaseLockError.getMessage(), lockError == null ? releaseLockError : lockError);
        }

    }

}
