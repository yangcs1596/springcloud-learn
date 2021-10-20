package com.safedog.cloudnet.service;

import com.safedog.cloudnet.entity.mysql.SysDict;
import com.safedog.common.mybatis.service.IBaseService;

/**
 * @author ycs
 * @description
 * @date 2021/10/16 14:12
 */
public interface SysDictService extends IBaseService<SysDict> {

    /**
     * 根据属性注解@Dict填充 对象Object的值
     * @param object
     */
    void fillTextFieldDueAnnotation(Object object);
}
