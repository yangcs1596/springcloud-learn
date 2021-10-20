package com.safedog.cloudnet.service.impl;

import com.safedog.cloudnet.entity.mysql.SysDict;
import com.safedog.cloudnet.mapper.mysql.SysDictMapper;
import com.safedog.cloudnet.service.SysDictService;
import com.safedog.common.annotation.Dict;
import com.safedog.common.constants.SysDictEnum;
import com.safedog.common.mybatis.service.Impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 * @author ycs
 * @description
 * @date 2021/10/16 14:13
 */
@Slf4j
@Service
public class SysDictServiceImpl extends BaseServiceImpl<SysDictMapper, SysDict> implements SysDictService {
    @Override
    public void fillTextFieldDueAnnotation(Object obj) {
        if (Objects.isNull(obj)) {
//            Assert.notNull(obj, "字典填充对象不能为空");
            return;
        }
        Class<?> clazz = obj.getClass();
        do {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                try {
                    //非静态属性
                    if (Modifier.isStatic(field.getModifiers()) && field.isAnnotationPresent(Dict.class)) {
                        Dict dict = field.getDeclaredAnnotation(Dict.class);
                        //需要填充的目标属性
                        Field targetField = clazz.getDeclaredField(dict.targetField());
                        field.setAccessible(true);
                        //获取该field的属性对应值
                        String dictCode = Objects.nonNull(field.get(obj)) ? field.get(obj).toString() : "";
                        if(Objects.nonNull(targetField) && StringUtils.isNotBlank(dictCode)){
                            //优先使用枚举查询
                            if(!SysDictEnum.DEFAUlT.getCode().equals(dict.dictEnum().getCode())){
                                //执行查询数据库的方法，或者读取缓存
                            } else{
                                //执行查询数据库的方法，或者读取缓存，两者方法差不多
                            }
                        }
                    }

                } catch (Exception e) {
                    log.info("数据字典填充失败===={}", field);
                }
            }
            clazz = clazz.getSuperclass();
        } while (null != clazz);
    }


}
