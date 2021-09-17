package com.safedog.common.constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author wuxin
 */
public enum ErrorCode implements ResponseCode {

    // 通用异常--可以考虑配置在数据字典里
    OK(0, "success"),
    ERROR(-1, "error"),
    FEIGN_FAILED(10_001, "common_feign_failed"),
    UNAUTHORIZED(10_002, "common_unauthorized"),
    ACCESS_DENIED(10_003, "common_access_denied"),
    NOT_FOUND(10_004, "common_not_found"),
    DATA_NOT_FOUND(__DATA_NOT_FOUND_CODE, "common_data_not_found"),

    FILE_SIZE_OVERFLOW(10_006, "common_file_size_overflow"),
    PARAM_REQUIRED(10_007, "common_param_required"),
    ILLEGAL_PARAM(10_008, "common_illegal_param"),
    CALLBACK_FAILED(10_009, "common_callback_failed"),
    BAD_CREDENTIALS(10_010, "common_bad_credentials"),
    TARGET_SERVICE_UNAVAILABLE(10_011, "common_target_service_unavailable"),
    NON_UNIQUE_RESULT(10_012, "common_non_unique_result"),
    DATA_EXISTS(__DATA_EXISTS_CODE, "common_data_exists"),
    DUPLICATE_DATA(10_014, "common_duplicate_data"),
    FILE_DATA_ERROR(10_015, "common_file_data_error"),
    //
    ;

    public final int code;
    public final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static final List<ResponseCode> ALL_RESPONSE_CODE;

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("error");

    static {
        List<ResponseCode> responseCodes = new ArrayList<>();
        Collections.addAll(responseCodes, ErrorCode.values());
        //
        ALL_RESPONSE_CODE = Collections.unmodifiableList(responseCodes);
    }

    public static ResponseCode getResponseCode(int code) {
        return ALL_RESPONSE_CODE
                .stream()
                .filter(e -> code == e.getCode())
                .findFirst()
                .orElse(ERROR);
    }

    public static String getResponseCodeDesc(ResponseCode code) {
        if (!code.getClass().isEnum()) {
            code = getResponseCode(code.getCode());
        }
        return getText(code.getMessage());
    }

    private static String getText(String message) {
        return resourceBundle.containsKey(message) ? resourceBundle.getString(message) : message;
    }


}
