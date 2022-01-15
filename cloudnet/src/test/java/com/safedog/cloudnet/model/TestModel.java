package com.safedog.cloudnet.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author ycs
 * @description
 * @date 2022/1/13 15:33
 */
@Data
public class TestModel {
    private String name;
    /**
     * JsonIgnore 忽略序列化和反序列 作用：在json序列化时将pojo中的一些属性忽略掉，标记在属性或者方法上，返回的json数据即不包含该属性。
     * @JSONField(serialize = false)，JSONField来自com.alibaba.fastjson.annotation包的，返回的json数据即不包含该属性。
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private Integer age;
}
