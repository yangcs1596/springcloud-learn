package com.safedog.cloudnet.phoenix.impl;

import com.safedog.cloudnet.entity.SysUser;
import com.safedog.cloudnet.mapper.phoenix.TestMapper;
import com.safedog.cloudnet.phoenix.HBaseService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HBaseServiceImpl implements HBaseService {
    @Resource(name = "phoenixSqlSessionTemplate")
    private SqlSessionTemplate sqlsession;

    @Override
    public List<SysUser> test(){
        TestMapper mapper = sqlsession.getMapper(TestMapper.class);
        List<SysUser> sysUsers = mapper.queryAll();
       return sysUsers;
    }
}
