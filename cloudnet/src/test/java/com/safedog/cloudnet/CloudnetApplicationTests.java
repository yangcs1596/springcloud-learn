package com.safedog.cloudnet;

import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class CloudnetApplicationTests {

//    @Autowired
    private Environment env;

    @Test
    public void contextLoads() {
        ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .sources(CloudnetApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run();
        ConfigurableEnvironment environment = context.getEnvironment();
        System.out.println("-----------xxx----------");
        System.out.println(environment.getProperty("JAVA_HOME"));
    }

}
