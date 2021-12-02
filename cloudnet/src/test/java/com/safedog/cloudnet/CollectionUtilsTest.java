package com.safedog.cloudnet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author ycs
 * @description
 * @date 2021/11/17 13:54
 */
@SpringBootTest
public class CollectionUtilsTest {

    @Test
    public void test(){
        ArrayList<String> a = new ArrayList<String>(){{add("1");add("2");add("3");add("3");add("4");}};
        ArrayList<String> b = new ArrayList<String>(){{add("3");add("4");}};

        //并集
        Collection<String> union = CollectionUtils.union(a, b);
        //交集
        Collection<String> intersection = CollectionUtils.intersection(a, b);
        //交集的补集
        Collection<String> disjunction = CollectionUtils.disjunction(a, b);
        //集合相减
        Collection<String> subtract = CollectionUtils.subtract(a, b);

        System.out.println("=====union" + union);
        System.out.println("=====intersection" + intersection);
        System.out.println("=====disjunction" + disjunction);
        System.out.println("=====subtract" + subtract);

        System.out.println(this.processVersionStr("17.06.2-ce"));
    }

    private String processVersionStr(String versionStr) {
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
