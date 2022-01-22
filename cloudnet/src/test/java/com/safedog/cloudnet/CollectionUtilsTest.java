package com.safedog.cloudnet;

import com.safedog.common.util.VersionUtil;
import org.apache.commons.collections.CollectionUtils;
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
    }

    @Test
    public void test_versionUtil(){
        //格式化
        String ver = VersionUtil.processVersionStr("20213.04.9A-1");
        //左填充补 fillChar
        String fillStr = VersionUtil.flushLeftThree('0', 3, ver);
        System.out.println(fillStr);
        System.out.println(VersionUtil.removeFillChar('0', fillStr));

    }
}
