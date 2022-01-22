/**
 * Copyright©2012 Xiamen Meiah Pico IT CO., Ltd.
 * All rights reserved.
 */
package com.safedog.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 功能/模块：<br/>
 * 描述：<br/>
 * 修订历史：<br/>
 * 日期 作者 参考 描述：<br/>
 *
 * @author wangwh
 * @version 1.0 2012-12-31
 * @see
 */
public class ActionUtils {
    /**
     * 判断是否是firefox浏览器
     *
     * @param request
     * @return
     */
    public static boolean isFirefox(HttpServletRequest request) {
        String userAgent = request.getHeader("USER-AGENT");
        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            return userAgent.contains("firefox");
        }
        return false;
    }

    /**
     * 设置下载文件时response头部信息
     *
     * @param request
     * @param response
     * @param fileName
     * @param suffix
     */
    public static void setResponseHeader(HttpServletRequest request, HttpServletResponse response,
                                         String fileName, String suffix) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(fileName)) {
            String ext = suffix;
            if (suffix == null || "".equals(suffix)) {
                ext = "xlsx";
            }
            int index = fileName.indexOf(".");
            if (index != -1) {
                ext = fileName.substring(index + 1);
                fileName = fileName.substring(0, index);
            }
            response.setContentType("multipart/form-data");
            String filenameDisplay = URLEncoder.encode(fileName, "UTF-8") + "." + ext;
            if (isFirefox(request)) {
                filenameDisplay = new String(fileName.getBytes("UTF-8"), "iso-8859-1") + "." + ext;
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + filenameDisplay);
        }
    }
}
