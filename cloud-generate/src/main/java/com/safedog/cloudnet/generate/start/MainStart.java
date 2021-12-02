package com.safedog.cloudnet.generate.start;

import com.safedog.cloudnet.generate.processor.backend.EntityInfoProcessor;
import com.safedog.cloudnet.generate.processor.base.BaseProcessor;

/**
 * @author ycs
 * @description
 * @date 2021/11/8 20:47
 */
public class MainStart {

    public static void main(String[] args) {
        try {
            backend("sys_user", "base", "api");
//            frontend("api_page_model", "api", "api");
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
