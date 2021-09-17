package com.safedog.cloudnet.service;

import com.safedog.cloudnet.entity.SysUser;
import com.safedog.common.mybatis.service.IBaseService;

import java.util.List;

public interface SysUserService extends IBaseService<SysUser> {

    public List<SysUser> test();
}
