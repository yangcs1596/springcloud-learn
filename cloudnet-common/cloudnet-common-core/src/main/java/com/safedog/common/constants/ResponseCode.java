package com.safedog.common.constants;

public interface ResponseCode {

    int __DATA_NOT_FOUND_CODE = 10_005;

    int __DATA_EXISTS_CODE = 10_013;

    int getCode();

    String getMessage();

}
