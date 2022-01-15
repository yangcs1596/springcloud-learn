package com.safedog.cloudnet.service.impl;

import com.safedog.cloudnet.entity.mysql.SysUser;
import com.safedog.cloudnet.mapper.mysql.SysUserMapper;
import com.safedog.cloudnet.service.SysUserService;
import com.safedog.common.mybatis.entity.AbstractEntity;
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

    @Override
    public List<SysUser> testSelect(){
        List<SysUser> list = super.lambdaQuery().select(AbstractEntity::getId)
                .list();
        return list;
    }

}
