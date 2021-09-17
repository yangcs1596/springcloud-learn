package com.safedog.common.web;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 用于加解密request info的工具
 *
 * @author wuxin
 */
public class RequestInfoUtils {

    @Deprecated
    public static String encodeRequestInfo(RequestInfo requestInfo) {
        String requestInfoString = JSON.toJSONString(requestInfo);
        return Base64.getEncoder().encodeToString(requestInfoString.getBytes(StandardCharsets.UTF_8));
    }

    @Deprecated
    public static RequestInfo decodeRequestInfo(String encodedRequestInfoString) {
        byte[] buf = Base64.getDecoder().decode(encodedRequestInfoString.getBytes(StandardCharsets.UTF_8));
        String requestInfoJsonString = new String(buf, StandardCharsets.UTF_8);
        return JSON.parseObject(requestInfoJsonString, RequestInfo.class);
    }

    public static RequestInfo anonymous() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (requestAttributes instanceof ServletRequestAttributes) {
            request = ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return anonymous(request);
    }

    public static RequestInfo anonymous(HttpServletRequest request) {
        return RequestInfo.anonymous(remoteAddress(request));
    }

    private static String remoteAddress(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String remoteAddress = request.getHeader("X-Real-IP");
        if (!StringUtils.hasText(remoteAddress)) {
            remoteAddress = request.getHeader("X-Forwarded-For");
        }
        if (!StringUtils.hasText(remoteAddress)) {
            remoteAddress = request.getHeader("Proxy-Client-IP");
        }
        if (!StringUtils.hasText(remoteAddress)) {
            remoteAddress = request.getRemoteAddr();
        }
        if (!StringUtils.hasText(remoteAddress)) {
            remoteAddress = "";
        }
        return remoteAddress.split(",")[0].trim();
    }

}
