package com.safedog.cloudnet;

import com.safedog.spark.service.WordCountService;
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

import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CloudnetApplicationTests {

    @Autowired
    private Environment env;

    @Autowired
    private WordCountService wordCountService;

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

    @Test
    public void testSpark() throws Exception{
        Map<String, Integer> run = wordCountService.run();
        System.out.println(run);
    }
}
