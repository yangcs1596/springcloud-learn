package com.safedog.cloudnet.function;

import com.safedog.common.function.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

/**
 * @author ycs
 * @description
 * @date 2021/12/8 9:04
 */
@SpringBootTest
@Slf4j
public class FunctionTest {

    @Test
    public void test1(){
        TestUtils.test("帅哥", (msg)->{
            System.out.println(Thread.currentThread().getName() + "====" + msg);
        });
    }

    @Test
    public void biFunctionTest(){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("a", "Hello world");
        TestUtils.biFunctionTest(map, Integer.class, "a", (aMap, key)->{
            String obj = (String)map.get(key);
            return obj;
        });
    }

    @Test
    public void consumer(){
        TestUtils.testConsumer();
    }
}
