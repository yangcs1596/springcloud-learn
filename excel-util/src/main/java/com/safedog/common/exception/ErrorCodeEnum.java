package com.safedog.common.exception;

import java.util.Objects;

/**
 * 异常错误码枚举类
 *
 * @author wujf
 * @date Create in 2020/11/5 14:59
 */
public enum ErrorCodeEnum {
    /** 未知错误*/
    UNKNOWN_ERROR("-1", "未知错误"),
    PARAMS_ERROR("0", "参数错误"),
    DATA_ERROR("1", "数据为空"),
    CRISIS_ERROR("2", "导出总条数超过阈值");

    private String errorCode;
    private String errorMsg;

    ErrorCodeEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static String getErrorMsg(String errorCode) {
        for (ErrorCodeEnum errorCodeEnum : ErrorCodeEnum.values()) {
            if (Objects.equals(errorCodeEnum.errorCode, errorCode)) {
                return errorCodeEnum.errorMsg;
            }
        }
        return null;
    }
}
