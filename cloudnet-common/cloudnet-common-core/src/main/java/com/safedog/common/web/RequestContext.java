package com.safedog.common.web;


import com.safedog.common.BaseException;
import com.safedog.common.constants.ErrorCode;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.function.Supplier;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

/**
 * 获取当前请求的用户等信息工具
 *
 * @author wuxin
 */
public class RequestContext {

    private static final String REQUEST_INFO_ATTR_NAME = RequestContext.class.getName() + ".request_info";

    public static String getAuthcAppId() {
        return getAuthcRequestInfo(() -> new BaseException(ErrorCode.UNAUTHORIZED)).getAppId();
    }

    public static String getAuthcUserId() {
        return getAuthcUserId(() -> new BaseException(ErrorCode.UNAUTHORIZED));
    }

    public static <X extends Throwable> String getAuthcUserId(Supplier<? extends X> exceptionSupplier) throws X {
        return getAuthcRequestInfo(exceptionSupplier).getUserId();
    }

    public static <X extends Throwable> RequestInfo getAuthcRequestInfo(Supplier<? extends X> exceptionSupplier) throws X {
        RequestInfo info = getRequestInfo();
        if (info.isAnonymous()) {
            throw exceptionSupplier.get();
        }
        return info;
    }

    public static RequestInfo getRequestInfo() {
        RequestAttributes reqAttr = RequestContextHolder.getRequestAttributes();
        if (reqAttr == null) {
            return RequestInfoUtils.anonymous();
        }
        RequestInfo requestInfo = (RequestInfo) reqAttr.getAttribute(REQUEST_INFO_ATTR_NAME, SCOPE_REQUEST);
        return requestInfo == null ? RequestInfoUtils.anonymous() : requestInfo;
    }

    static void setRequestInfo(RequestInfo requestInfo) {
        RequestAttributes reqAttr = RequestContextHolder.getRequestAttributes();
        if (reqAttr != null) {
            if (requestInfo != null) {
                reqAttr.setAttribute(REQUEST_INFO_ATTR_NAME, requestInfo, SCOPE_REQUEST);
            } else {
                reqAttr.removeAttribute(REQUEST_INFO_ATTR_NAME, SCOPE_REQUEST);
            }
        }
    }

}
