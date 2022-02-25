package com.safedog.common.util;

import com.safedog.common.BaseException;
import com.safedog.common.constants.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.support.CronExpression;

import java.time.LocalDateTime;

/**
 * @author ycs
 * @description
 * @date 2022/1/27 11:45
 */
@Slf4j
public class CornUtil {

    /**
     * 根据cron表达式计算下次的时间点
     * @param cron
     * @return
     */
    public static LocalDateTime calculatNextTime(String cron){
        if(CronExpression.isValidExpression(cron)) {
            System.out.println("表达式正确");
            CronExpression cronExpression = CronExpression.parse(cron);
            LocalDateTime now = LocalDateTime.now();
            //当前时间
            log.info("当前执行时间========={}", now);
            //下次运行时间
            log.info("下次执行时间========={}", cronExpression.next(now));
            return cronExpression.next(now);

        }else {
            log.info("cron表达式错误");
            throw new BaseException(ErrorCode.ERROR, "cron表达式错误");
        }
    }


    /**
     * 按天创建cron表达式
     */
    public String createCronExpression(){
        return "";
    }
}
