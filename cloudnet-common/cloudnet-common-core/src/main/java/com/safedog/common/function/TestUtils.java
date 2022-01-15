package com.safedog.common.function;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author ycs
 * @description
 * @date 2021/12/7 21:02
 */

public class TestUtils {

    public static void test(String msg, FunctionInterfaceTest functionInterfaceTest){
        FunctionInterfaceTest.hello();
        functionInterfaceTest.printMsg(msg);
    }

    /**
     * BiFunction 三个参数 T,U,R
     * @param
     * @param
     */
    public static <QueryType, keyType> void biFunctionTest(Map map, Class<QueryType> query, keyType key,  BiFunction<Map, keyType, String> function){
        String result = function.apply(map, key);
        TestUtils.test(result, (a)->{
            System.out.println("a======" + a);
            System.out.println(Thread.currentThread().getName() + "====" + result);
        });
    }


    public static void testConsumer(){
        ConsumerTest consumerTest = new ConsumerTest(new ConsumerModel());
        consumerTest.test(builder -> builder.setName("哈哈哈哈"));
    }
}
