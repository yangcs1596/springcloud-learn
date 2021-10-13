package com.safedog.cloudnet.entity.mysql;

import com.baomidou.mybatisplus.annotation.TableName;
import com.safedog.common.mybatis.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ycs
 * @description
 * @date 2021/9/18 17:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends AbstractEntity {
    private String name;
}
