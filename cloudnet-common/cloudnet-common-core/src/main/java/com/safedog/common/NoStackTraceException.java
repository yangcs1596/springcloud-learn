package com.safedog.common;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class NoStackTraceException extends RuntimeException {

    protected NoStackTraceException(String message) {
        this(message, null);
    }

    protected NoStackTraceException(String message, Throwable cause) {
        super(message, cause, false, false);
    }

}
