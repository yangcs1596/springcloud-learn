package com.safedog.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ycs
 * @description
 * @date 2022/1/11 11:44
 */
public class VersionUtil {
    /**
     * 拆接版本号
     * @param versionStr
     * @return
     */
    public static String processVersionStr(String versionStr) {
        if (StringUtils.isBlank(versionStr)) {
            return null;
        }
        String anyStr = "ANY";
        String naStr = "NA";
        if(anyStr.equals(versionStr) || naStr.equals(versionStr)){
            return versionStr;
        }
        boolean start = false;
        StringBuffer str = new StringBuffer();
        boolean lastCharIsPoint = false;
        for (int i = 0; i < versionStr.length(); i++) {
            char c = versionStr.charAt(i);
            boolean isNumber = c >= '0' && c <= '9';
            boolean isPoint = c == '.';
            lastCharIsPoint = isPoint;
            if (!start && isNumber) {
                start = true;
            }
            if (start && !isNumber && !isPoint) {
                break;
            }
            boolean isVersionChar = isNumber || isPoint;
            if (start && isVersionChar) {
                str.append(c);
            }
        }
        return start && lastCharIsPoint ? str.substring(0, str.length() - 1) : str.toString();
    }
}
