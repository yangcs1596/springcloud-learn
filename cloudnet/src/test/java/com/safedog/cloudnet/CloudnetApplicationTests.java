package com.safedog.cloudnet;

import com.safedog.cloudnet.entity.mysql.SysUser;
import com.safedog.cloudnet.service.SysUserService;
import com.safedog.spark.service.WordCountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@SpringBootTest(classes = CloudnetApplication.class)
@RunWith(SpringRunner.class)
public class CloudnetApplicationTests {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private WordCountService wordCountService;

    @Test
    public void testAssign(){
        SysUser sysUser = new SysUser();
        sysUser.setName("李四");
        sysUserService.save(sysUser);
    }

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
