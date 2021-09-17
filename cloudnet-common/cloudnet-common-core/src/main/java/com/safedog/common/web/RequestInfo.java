package com.safedog.common.web;

import lombok.*;
import lombok.experimental.FieldDefaults;


@ToString
@Builder
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestInfo  {

    public static final String X_REQUEST_INFO_HEADER = "X-NotaryCloud-Request-Info";
    private static final String ANONYMOUS_STRING = "anonymous";

    private static final RequestInfo.RequestInfoBuilder ANONYMOUS_BUILDER =
            RequestInfo.builder()
                    .userId(ANONYMOUS_STRING)
                    .username(ANONYMOUS_STRING)
                    .notaryOfficeId(ANONYMOUS_STRING)
                    .notaryOfficeName(ANONYMOUS_STRING)
                    .appId(ANONYMOUS_STRING)
                    .appName(ANONYMOUS_STRING)
                    .requestId(ANONYMOUS_STRING)
                    .userType(ANONYMOUS_STRING)
                    .partnerId(ANONYMOUS_STRING)
                    .appClientId(ANONYMOUS_STRING)
                    .applicationId(ANONYMOUS_STRING);

    public static RequestInfo anonymous(String remoteAddress) {
        return ANONYMOUS_BUILDER
                .remoteAddress(remoteAddress)
                .anonymous(true)
                .build();
    }

    public static final String APP_ID = "aid";
    public static final String APP_NAME = "ane";
    public static final String USER_ID = "uid";
    public static final String USER_NAME = "une";
    public static final String NOTARY_OFFICE_ID = "nid";
    public static final String NOTARY_OFFICE_NAME = "nne";
    public static final String REMOTE_ADDRESS = "ip";
    public static final String REQUEST_ID = "rid";
    public static final String USER_TYPE = "utype";
    public static final String PARTNER_ID = "pid";
    public static final String APP_CLIENT_ID = "cid";
    public static final String APPLICATION_ID = "aaid";

    @Deprecated
    String appId;

    @Deprecated
    String appName;

    @Deprecated
    String notaryOfficeId;

    @Deprecated
    String notaryOfficeName;

    String userId;

    String username;

    String remoteAddress;

    String requestId;

    String userType;

    String partnerId;

    String appClientId;

    String applicationId;

    boolean anonymous;

    public boolean isAnonymous() {
        return anonymous;
    }


}
