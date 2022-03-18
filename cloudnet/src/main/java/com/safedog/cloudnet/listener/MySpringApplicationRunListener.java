package com.safedog.cloudnet.listener;

/**
 * @author ycs
 * @description
 * @date 2022/3/4 17:17
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class MySpringApplicationRunListener implements SpringApplicationRunListener {

    public MySpringApplicationRunListener(SpringApplication application, String[] args) {
    }
    @Override
    public void starting() {
        System.out.println("==============starting==============");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment configurableEnvironment) {
        System.out.println("==============environmentPrepared==============");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("==============contextPrepared==============");

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("==============contextLoaded==============");
    }

    @Override
    public void started(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("==============started==============");
    }

    @Override
    public void running(ConfigurableApplicationContext configurableApplicationContext) {

        System.out.println("==============running==============");
    }

    @Override
    public void failed(ConfigurableApplicationContext configurableApplicationContext, Throwable throwable) {
        System.out.println("==============failed==============");

    }
}