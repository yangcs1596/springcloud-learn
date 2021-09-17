package com.safedog.common;


import com.safedog.common.constants.ErrorCode;
import com.safedog.common.constants.ResponseCode;

/**
 * @author wuxin
 */
public class BaseException extends NoStackTraceException implements ResponseCode {

    private int code;
    private String detailMessage;

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
        this.detailMessage = message;
    }

    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.detailMessage = message;
    }

    public BaseException(Throwable cause) {
        this(ErrorCode.ERROR, cause);
    }

    public BaseException(String message, Throwable cause) {
        this(ErrorCode.ERROR, message, cause);
    }

    public BaseException(ResponseCode respCode) {
        this(respCode, respCode.getMessage(), null);
    }

    public BaseException(ResponseCode respCode, String detailMessage) {
        this(respCode, detailMessage, null);
    }

    public BaseException(ResponseCode respCode, Throwable cause) {
        this(respCode, null, cause);
    }

    public BaseException(ResponseCode respCode, String detailMessage, Throwable cause) {
        super(respCode.getMessage(), cause);
        this.code = respCode.getCode();
        this.detailMessage = detailMessage;
    }

    @Override
    public int getCode() {
        return code;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    @Override
    public String getMessage() {
        StringBuilder o = new StringBuilder();
        o.append(super.getMessage());
        if (this.detailMessage != null) {
            o.append(":").append(this.detailMessage);
        }
        return o.toString();
    }

}
