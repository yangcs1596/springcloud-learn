//package com.safedog.cloudnet.task;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//
///**
// * @author ycs
// * @description
// * @date 2021/12/3 11:30
// **
// * 利用线程池实现任务调度
// * Task任务调度器可以实现任务的调度和删除
// * 原理:
// * 实现一个类：ThreadPoolTaskScheduler线程池任务调度器，能够开启线程池进行任务调度
// * ThreadPoolTaskScheduler.schedule（）方法会创建一个定时计划ScheduleFuture,
// * 在这个方法中添加两个参数一个是Runable:线程接口类，和CronTrigger(定时任务触发器)
// * 在ScheduleFuture中有一个cancel可以停止定时任务
// * @author Admin
// *
// * Scheduled Task是一种轻量级的任务定时调度器，相比于Quartz,减少了很多的配置信息，但是Scheduled Task
// * 不适用于服务器集群，引文在服务器集群下会出现任务被多次调度执行的情况，因为集群的节点之间是不会共享任务信息的
// * 每个节点的定时任务都会定时执行
// *
// * 解决方法是：将定时任务单独成一个服务，有跨服务调用的方式，去调用集群的服务。
// *
// */
//@Configuration
//@EnableScheduling
//public class StaticSchduleTask {
//
////    /**
////     * 每一秒中执行以下改方法
////     * cron是用来指定执行的 秒，分钟，日期等
////     */
////    @Scheduled(cron="0/10 * * * * *")
////    public void test1(){
////        System.out.println("静态执行定时任务输出 test1");
////    }
//}
