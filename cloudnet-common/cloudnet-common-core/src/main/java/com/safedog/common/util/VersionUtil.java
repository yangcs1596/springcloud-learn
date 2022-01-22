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
     *
     * @param versionStr
     * @return
     */
    public static String processVersionStr(String versionStr) {
        if (StringUtils.isBlank(versionStr)) {
            return null;
        }
        String anyStr = "ANY";
        String naStr = "NA";
        if (anyStr.equals(versionStr) || naStr.equals(versionStr)) {
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
            //遇到非数字类型的，则直接退出循环
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

    /**
     * 输入3.4.9  返回03.04.09
     * 字符填充函数
     *
     * @param fillChar 要填充的字符
     * @param len      填充长度
     * @param string   要格式化的字符串
     * @return
     */
    public static String flushLeftThree(char fillChar, int len, String string) {
        StringBuilder result = new StringBuilder();
        String[] strArray = string.split("\\.");
        if (strArray.length >= len) {
            for (int i = 0; i < len; i++) {
                int length = strArray[i].length();
                if (length < len) {
                    for (int j = 0; j < len - length; j++) {
                        strArray[i] = fillChar + strArray[i];
                    }
                }
                if (length > len) {
                    strArray[i] = strArray[i].substring(0, len);
                }
                result.append(strArray[i]);
                if (i < len - 1) {
                    result.append(".");
                }
            }
        } else {
            for (int i = 0; i < len - strArray.length; i++) {
                string = string + "." + "0";
            }
            return flushLeftThree(fillChar, len, string);
        }
        return result.toString();
    }

    /**
     * 去除填充数据
     * @param fillChar
     * @param string
     * @return
     */
    public static String removeFillChar(char fillChar, String string){
        String[] strArray = string.split("\\.");
        for (int i = 0; i < strArray.length; i++) {
            for (int j = 0; j < strArray[i].length(); j++) {
                if(strArray[i].charAt(j) != fillChar){
                    strArray[i] = strArray[i].substring(j);
                    break;
                }
            }

        }
        return StringUtils.join(strArray, ".");
    }
}
