package com.safedog.cloudnet;

import com.safedog.common.thread.ThreadPoolManager;
import com.safedog.common.util.ZLibUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

/**
 * @author ycs
 * @description
 * @date 2021/9/23 10:01
 */
@SpringBootTest
@Slf4j
public class NotRunTest {

    @Test
    public void xx(){
        System.out.println(ZLibUtil.compress("12354989"));
        System.out.println(ZLibUtil.decode("eJw1jcEKwjAQRP9lzsGgaIVeFc+C3koPyyaRaNjIphGl9N8NiKfhwZuZYQbpraCfwVkmiuI1usYDdiHs/bbbrIPrMBpMn6dHD0oJBuScNqgS37219kVqtYp1mR9eV6UFFoNQhZt1zHzVfCe5MMkp6+H/1IY4USk/pzXPmhnL+AWUWjJn"));
    }


    @Test
    public void futureTest() throws Exception {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        //异步任务
        CompletableFuture.runAsync(() -> {
            objectObjectHashMap.put("a", "a");
        }, ThreadPoolManager.getThreadPool()).whenComplete((a, throwable) -> {
            objectObjectHashMap.put("b", "b");
            System.out.println(objectObjectHashMap);
        });
        System.out.println(objectObjectHashMap);
    }
}
