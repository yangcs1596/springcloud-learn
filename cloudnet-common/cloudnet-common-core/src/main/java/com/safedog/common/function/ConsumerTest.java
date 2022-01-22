package com.safedog.common.function;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * @author ycs
 * @description
 * @date 2022/1/15 14:27
 */
@Slf4j
public class ConsumerTest {
    private final ConsumerModel consumerModel;

    public static ConsumerTest create(){
        return new ConsumerTest(new ConsumerModel());
    }

    public ConsumerTest(ConsumerModel consumerModel) {
        this.consumerModel = consumerModel;
    }


    public ConsumerTest test(Consumer<ConsumerModel> consumer){
        consumer.accept(this.consumerModel);
        return this;
    }

    public ConsumerTest printInfo(){
        log.info("============consumerModel============{}", consumerModel);
        return this;
    }

}
