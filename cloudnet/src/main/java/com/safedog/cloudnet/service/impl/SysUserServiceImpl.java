package com.safedog.cloudnet.service.impl;

import com.safedog.cloudnet.entity.phoenix.SysUser;
import com.safedog.cloudnet.mapper.mysql.SysUserMapper;
import com.safedog.cloudnet.service.SysUserService;
import com.safedog.common.mybatis.service.Impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public List<SysUser> test(){
        List<SysUser> sysUsers = selectAll();
        return sysUsers;
    }

}
