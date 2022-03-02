package com.safedog.common.util;

import com.alibaba.fastjson.JSONObject;
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



    /**
     * 比对资产和规则的版本字符串
     * 版本号比较要用代码实现的原因，入库的版本号不规范
     *
     * @param assetVersionStr 资产应用的版本
     * @param jsonObject 漏洞库规则版本
     * @return
     */
    public static boolean isHitVersionRule(String assetVersionStr, JSONObject jsonObject) {
        String ruleVersion = jsonObject.getString("version");
        String ruleVersionStartIncluding = jsonObject.getString("ruleVersionStartIncluding");
        String ruleVersionStartExcluding = jsonObject.getString("ruleVersionStartExcluding");
        String ruleVersionEndIncluding = jsonObject.getString("ruleVersionEndIncluding");
        String ruleVersionEndExcluding = jsonObject.getString("ruleVersionEndExcluding");
        String naStr = "NA";
        String anyStr = "ANY";
        if(naStr.equals(ruleVersion)){
            return false;
        }
        if (assetVersionStr.equals(ruleVersion)) {
            return true;
        }
        if (anyStr.equals(ruleVersion)) {
            boolean startIncludingBool = StringUtils.isBlank(ruleVersionStartIncluding) || compareVersion(assetVersionStr, ruleVersionStartIncluding) >= 0;
            boolean startExcludingBool = StringUtils.isBlank(ruleVersionStartExcluding) || compareVersion(assetVersionStr, ruleVersionStartExcluding) > 0;
            boolean endIncludingBool = StringUtils.isBlank(ruleVersionEndIncluding) || compareVersion(assetVersionStr, ruleVersionEndIncluding) <= 0;
            boolean endExcludingBool = StringUtils.isBlank(ruleVersionEndExcluding) || compareVersion(assetVersionStr, ruleVersionEndExcluding) < 0;
            return startIncludingBool && startExcludingBool && endIncludingBool && endExcludingBool;
        }
        return false;
    }

    /**
     * 版本号比较
     *
     * @param version1
     * @param version2
     * @return version1 > version2 返回正数 反之负数 等于为0
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        int index = 0;
        //获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        //循环判断每位的大小
        while (index < minLen && (diff = Integer.parseInt(version1Array[index]) - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            //如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

}
