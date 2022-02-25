package com.safedog.cloudnet.generate.start;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.safedog.cloudnet.generate.processor.backend.EntityInfoProcessor;
import com.safedog.cloudnet.generate.processor.base.BaseProcessor;

import java.util.Collections;

/**
 * @author ycs
 * @description
 * @date 2021/11/8 20:47
 */
public class MainStart {

    public static void main(String[] args) {
        try {
//            backend("sys_user", "base", "api");
//            frontend("api_page_model", "api", "api");

            //mybatis代码生成器，可看官网地址学习
            FastAutoGenerator.create("jdbc:mysql://localhost:3306/cloud_system?userUnicode=true&characterEncoding=utf-8&useSSL=false",
                    "root",
                    "root")
                    .globalConfig(builder -> {
                        builder.author("baomidou") // 设置作者
                                .enableSwagger() // 开启 swagger 模式
                                .fileOverride() // 覆盖已生成文件
                                .outputDir("D://"); // 指定输出目录
                    })
                    .packageConfig(builder -> {
                        builder.parent("com.baomidou.mybatisplus.samples.generator") // 设置父包名
                                .moduleName("system") // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://com//baomidou//")); // 设置mapperXml生成路径
                    })
                    .strategyConfig(builder -> {
                        builder.addInclude("sys_user") // 设置需要生成的表名
                                .addTablePrefix("t_", "c_") // 设置过滤表前缀
                                //mapper策略集配置，可去掉使用默认
//                                .mapperBuilder()
//                                .superClass(BaseMapper.class)
//                                .enableMapperAnnotation()
//                                .enableBaseResultMap()
//                                .enableBaseColumnList()
////                               .cache(MyMapperCache.class)
//                                .formatMapperFileName("%sDao")
//                                .formatXmlFileName("%sXml")
                                ;
                    })
                    .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成后台代码
     * @param tableName
     * @param modelName
     * @param tablePrefix
     * @throws Exception
     */
    private static void backend(String tableName, String modelName, String tablePrefix) throws Exception {
        entityInfo(tableName, modelName, tablePrefix);
    }

    /**
     * 生成前台代码
     */
    public static void frontend(String tableName, String modelName, String tablePrefix) throws Exception {

    }

    /**
     * 后台实体
     * @param tableName
     * @param modelName
     * @param tablePrefix
     * @throws Exception
     */
    public static void entityInfo(String tableName, String modelName, String tablePrefix) throws Exception {
        //组装输出路径，模块名等
        BaseProcessor consumerController = new EntityInfoProcessor(tableName, modelName, tablePrefix);
        //执行模板替换过程
        consumerController.process();
    }
}
