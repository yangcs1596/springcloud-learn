//package com.safedog.cloudnet.aspact;
//
//import com.alibaba.fastjson.JSONObject;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.safedog.cloudnet.dispose.model.Result;
//import com.safedog.cloudnet.service.SysDictService;
//import com.safedog.common.annotation.Dict;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Field;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author ycs
// * @description
// * @date 2021/10/16 15:18
// */
//@Aspect
//@Component
//@Slf4j
//public class SysDictAspect {
//    @Autowired
//    private SysDictService sysDictService;
//    //redis的缓存
//
//    @Pointcut("execution( * com.safedog.*.controller.*.*(..))")
//    private void dict(){
//    }
//
//    @Around("dict()")
//    public Object fillDictText(ProceedingJoinPoint pjp) throws Throwable {
//        long time1 = System.currentTimeMillis();
//        //执行结果
//        Object result = pjp.proceed();
//        long time2 = System.currentTimeMillis();
//        log.debug("获取JSON数据 耗时：" + (time2 - time1) + "ms");
//        long start = System.currentTimeMillis();
//        this.parseDictText(result);
//        long end = System.currentTimeMillis();
//        log.debug("解析注入JSON数据  耗时" + (end - start) + "ms");
//        return result;
//    }
//
//    private void parseDictText(Object result) {
//        if (result instanceof Result) {
//            List<JSONObject> items = new ArrayList<>();
//            Result rr = (Result) result;
//            if (rr.isOk()) {
//                List<?> list = (List<?>) rr.getData();
//                for (Object record : list) {
//                    ObjectMapper mapper = new ObjectMapper();
//                    String json = "{}";
//                    try {
//                        // 解决@JsonFormat注解解析不了的问题详见SysAnnouncement类的@JsonFormat
//                        json = mapper.writeValueAsString(record);
//                    } catch (JsonProcessingException e) {
//                        log.error("Json解析失败：" + e);
//                    }
//                    JSONObject item = JSONObject.parseObject(json);
//                    // 解决继承实体字段无法翻译问题
//                    for (Field field : this.getAllFields(record)) {
//                        //解决继承实体字段无法翻译问题
//                        // 如果该属性上面有@Dict注解，则进行翻译
//                        if (field.getAnnotation(Dict.class) != null) {
//                            // 拿到注解的dictDataSource属性的值
//                            String dictType = field.getAnnotation(Dict.class).dictCode();
//                            // 拿到注解的dictText属性的值
//                            String text = field.getAnnotation(Dict.class).targetField();
//                            //获取当前带翻译的值 field.getName() 为空时
//                            String key = String.valueOf(item.get(field.getName()));
//                            //翻译字典值对应的text值
//                            String textValue = translateDictValue(dictType, key);
//                            // DICT_TEXT_SUFFIX的值为，是默认值：
//                            // public static final String DICT_TEXT_SUFFIX = "_dictText";
//                            log.debug("字典Val: " + textValue);
//                            log.debug("翻译字典字段：" + field.getName() + DICT_TEXT_SUFFIX + "： " + textValue);
//                            //如果给了文本名
//                            if (!StringUtils.isBlank(text)) {
//                                item.put(text, textValue);
//                            } else {
//                                // 走默认策略
//                                item.put(field.getName() + DICT_TEXT_SUFFIX, textValue);
//                            }
//                        }
//                        // date类型默认转换string格式化日期
//                        if ("java.util.Date".equals(field.getType().getName())
//                                && field.getAnnotation(JsonFormat.class) == null
//                                && item.get(field.getName()) != null) {
//                            SimpleDateFormat aDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                            item.put(field.getName(), aDate.format(new Date((Long) item.get(field.getName()))));
//                        }
//                    }
//                    items.add(item);
//                }
//                rr.setData(items);
//            }
//        }
//    }
//
//
//    /**
//     * 获取所有属性，包括父类,可封装成工具
//     * @param object
//     * @return
//     */
//    private Field[] getAllFields(Object object) {
//        Class<?> clazz = object.getClass();
//        List<Field> fieldList = new ArrayList<>();
//        while (clazz != null) {
//            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
//            clazz = clazz.getSuperclass();
//        }
//        Field[] fields = new Field[fieldList.size()];
//        fieldList.toArray(fields);
//        return fields;
//    }
//}
