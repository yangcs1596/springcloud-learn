package com.safedog.cloudnet.generate.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

public class GeratorManger {

    private Configuration cfg = null;

    private static class SingletonHolder {
        private static final GeratorManger INSTANCE = new GeratorManger();
    }

    private GeratorManger() {
        init();
    }

    public static final GeratorManger getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private void init() {

        try {
            // 初始化FreeMarker配置
            // 创建一个Configuration实例
            cfg = new Configuration(Configuration.VERSION_2_3_28);
            // 设置FreeMarker的模版文件夹位置
            cfg.setClassForTemplateLoading(FieldsExclude.class, "/template");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void process(String ftlName, Map map, Writer w) throws Exception {

        // 创建模版对象
        Template t = cfg.getTemplate(ftlName);
        // 在模版上执行插值操作，并输出到制定的输出流中
        t.process(map, w);
        /*
         * Configuration cfg; cfg = new Configuration(); //设置FreeMarker的模版文件夹位置
         * cfg.setDirectoryForTemplateLoading(new
         * File(PropertiesUtil.getValue(Constant.PROJECT_PATH) + "/"+
         * PropertiesUtil.getValue(Constant.TEMPLATE_PATH))); //创建模版对象 Template
         * t = cfg.getTemplate(ftlName); //在模版上执行插值操作，并输出到制定的输出流中 t.process(map,
         * w);
         */
    }

    public void process(String ftlName, Map map) throws Exception {
        Writer w = new OutputStreamWriter(System.out);
        process(ftlName, map, w);

    }
}
