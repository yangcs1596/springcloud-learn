package com.safedog.spark.configure;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import java.io.FileNotFoundException;

/**
 * @author ycs
 * @description
 * @date 2021/10/14 20:29
 */
@Configuration
public class SparkConfiguration {
    @Autowired
    private Environment env;

    @Value("${spark.app.name:Spark shell}")
    private String appName;

    @Value("${spark.home:D:\\spark\\spark-2.3.0-bin-hadoop2.7\\bin\\}")
    private String sparkHome;

    @Value("${spark.master.uri:local[4]}")
    private String masterUri;

    @Bean
    public SparkConf sparkConf() throws FileNotFoundException {
//        File file = ResourceUtils.getFile("classpath:file/winutils.exe");
        System.out.println(appName);
        System.out.println(sparkHome);
        System.out.println(masterUri);
        SparkConf sparkConf = new SparkConf()
                .setAppName(appName)
                .setSparkHome(sparkHome)
                .setMaster(masterUri)
                .set("spark.cores.max", "1")
                .set("spark.eventLog.enabled", "true");

        return sparkConf;
    }

    @Bean
    public JavaSparkContext javaSparkContext() throws FileNotFoundException {
        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf());
        return javaSparkContext;
    }
//
//    @Bean
//    public SparkSession sparkSession() {
//        return SparkSession
//                .builder()
//                .sparkContext(javaSparkContext().sc())
//                .appName("Java Spark SQL basic example")
//                .getOrCreate();
//    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
