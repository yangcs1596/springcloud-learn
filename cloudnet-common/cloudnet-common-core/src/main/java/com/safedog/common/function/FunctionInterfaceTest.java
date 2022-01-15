package com.safedog.common.function;

/**
 * @FunctionInterface注释的约束
 * 1、接口有且只能有一个抽象方法，只有方法定义，没有方法体
 * 2、在接口中覆写Object类中的public方法，不算是函数式接口的方法
 */
@FunctionalInterface
public interface FunctionInterfaceTest {
    void printMsg(String msg);

    /**
     * 可以定义多个静态方法
     */
    static void hello(){
        System.out.println("Hello World! ====  static");
    }
}
