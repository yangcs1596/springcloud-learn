package com.safedog.cloudnet.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ycs
 * @description 动态定时任务测试类
 * @date 2021/12/3 11:19
 */
@Component
@Slf4j
public class DynamicScheduleTask implements SchedulingConfigurer {
    /**
     *  在需要动态修改定时任务cron参数的地方调用，此处只是示例
     *  dynamicScheduleTaskSecond .setCron("0/10 * * * * ?");
     */
    // 配置文件读取
    @Value("${audit.schedule.cron:  */5 * * * * ?}")
    private String cron;

    public String getCron() {
        return cron;
    }
    public void setCron(String cron) {
        this.cron = cron;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                try {
                    //写自己定时要执行的任务
                    log.info(cron + "：==========执行");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Trigger() {

            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                if ("".equals(cron) || cron == null) {
                    return null;
                }
                // 定时任务触发，可修改定时任务的执行周期
                CronTrigger trigger = new CronTrigger(cron);
                Date nextExecDate = trigger.nextExecutionTime(triggerContext);
                return nextExecDate;
            }
        });
    }
}
