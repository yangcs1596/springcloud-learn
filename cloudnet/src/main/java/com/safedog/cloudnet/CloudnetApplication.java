package com.safedog.cloudnet;

import com.safedog.spark.annotation.EnableSpark;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableSpark
@Import({com.safedog.common.controller.encrypt.EncryptController.class})
@EnableScheduling
public class CloudnetApplication {

    public static void main(String[] args) {
        //windows启动需要知道
        System.setProperty("hadoop.home.dir", "D:\\hadoop\\hadoop-2.7.0");
        //同时整合了redis和es需要设置这个
        System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(CloudnetApplication.class, args);

        System.out.println("///////////////////////////////////////////////////////////////////////////");
        System.out.println("//                      cloud net started successfully                   //");
        System.out.println("///////////////////////////////////////////////////////////////////////////");
    }

}
