package com.safedog.cloudnet.generate.processor.backend;

import com.safedog.cloudnet.generate.processor.base.BaseProcessor;
import com.safedog.cloudnet.generate.utils.Constant;
import com.safedog.cloudnet.generate.utils.PropertiesUtil;

/**
 * @author ycs
 * @description
 * @date 2021/11/10 15:12
 */
public class EntityInfoProcessor extends BaseProcessor {
    private String modelName;

    /**
     * 实体输出
     * @param tableName
     * @param modelName
     * @param tablePrefix
     */
    public EntityInfoProcessor(String tableName, String modelName, String tablePrefix) {
        super(tableName, tablePrefix, "EntityInfo.ftl");
        //模块名 = #java路径 + #基础路径 + modelName  //包名 实际要为 #基础路径.replace("/", ".")
        //例如： src/main/java/com/notary/framework/ + modelName
        setCloudName(PropertiesUtil.getValue(Constant.JAVA_SOURCE_PATH) + PropertiesUtil.getValue(Constant.BASE_PACKAGE) + "/" + modelName);
        //输出文件名 根据不同，后缀不同
        setOutputFileName(getTableNameFU() + "Info.java");
        //输出文件夹路径 = #后台项目路径 + 模块名
        setOutputPath(PropertiesUtil.getValue(Constant.OUTPUT_PROJECT_PATH) + getCloudName());
        this.modelName = modelName;
    }
    @Override
    public void bindData(){
        super.bindData();
        getDataMap().put("modelName", modelName);
    }


}
