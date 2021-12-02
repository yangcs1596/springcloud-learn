package com.safedog.cloudnet;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.safedog.common.thread.ThreadPoolManager;
import com.safedog.common.util.ZLibUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

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
    public void test_foreach(){
        System.out.println(System.getProperty("user.dir"));
        HashMap<String, String> map = new HashMap<>();
        map.put("A", "G");
        map.put("B", "F");
        map.put("V", "T");
        map.forEach((a, b) ->{
            System.out.printf("key:%s==========value:%s", a, b);
            System.out.println();
        });
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>(5);
        System.out.println(objectObjectHashMap.size() == 0);
    }
    @Test
    public void xx() throws Exception{
        String path = ResourceUtils.getURL("classpath:").getPath();
        System.out.println(path);
        System.out.println(ZLibUtil.encode("12354989"));
        System.out.println(ZLibUtil.decode(ZLibUtil.encode("12354989")));
        System.out.println(ZLibUtil.decode("eJw1jcEKwjAQRP9lzsGgaIVeFc+C3koPyyaRaNjIphGl9N8NiKfhwZuZYQbpraCfwVkmiuI1usYDdiHs/bbbrIPrMBpMn6dHD0oJBuScNqgS37219kVqtYp1mR9eV6UFFoNQhZt1zHzVfCe5MMkp6+H/1IY4USk/pzXPmhnL+AWUWjJn"));
    }

    @Test
    public void testMap(){


    }
    @Test
    public void testTable(){
        Table<String, String, Object> basedTable = HashBasedTable.create();
        basedTable.put("A", "A", "A");
        basedTable.put("A", "A", "B");
        basedTable.put("A", "B", "B");
        basedTable.put("B", "B", "B");
        for (Table.Cell<String, String, Object> stringStringObjectCell : basedTable.cellSet()) {

        }
        System.out.println(basedTable);

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
