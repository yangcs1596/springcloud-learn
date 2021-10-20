package com.safedog.cloudnet.entity.mysql;

import com.baomidou.mybatisplus.annotation.TableName;
import com.safedog.common.mybatis.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ycs
 * @description
 * @date 2021/10/16 11:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict")
public class SysDict extends AbstractEntity {
    //TODO 目前是测试，表与实体有出入，记得更正
    private String dictCode;
    private String dictName;
    private Boolean enable;
}
