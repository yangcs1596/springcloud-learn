package com.safedog.cloudnet.function;

import com.safedog.common.function.ConsumerTest;
import com.safedog.common.function.TestUtils;
import com.safedog.common.thread.ThreadPoolManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

/**
 * @author ycs
 * @description
 * @date 2021/12/8 9:04
 */
@SpringBootTest
@Slf4j
public class FunctionTest {

    @Test
    public void test1() {
        TestUtils.test("帅哥", (msg) -> {
            System.out.println(Thread.currentThread().getName() + "====" + msg);
        });
    }

    @Test
    public void biFunctionTest() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("a", "Hello world");
        TestUtils.biFunctionTest(map, Integer.class, "a", (aMap, key) -> {
            String obj = (String) map.get(key);
            return obj;
        });
    }

    @Test
    public void consumer() {
        ConsumerTest.create()
                .test(builder -> builder.setName("哈哈哈哈"))
                .printInfo();
//        TestUtils.testConsumer();
    }


    @Test
    public void futureTest() throws Exception {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        //异步任务
        CompletableFuture<String> future = CompletableFuture.supplyAsync(
                () -> {
                    objectObjectHashMap.put("a", "a");
                    return "我是runAsync";
                }, ThreadPoolManager.getThreadPool()
        ).whenComplete((a, throwable) -> {
            objectObjectHashMap.put("b", "b");
            System.out.println(objectObjectHashMap);
        });
        ThreadPoolManager.getThreadPool().execute(() -> {
            System.out.println("我的青春结束了");
        });
        System.out.println(objectObjectHashMap);
    }
}
