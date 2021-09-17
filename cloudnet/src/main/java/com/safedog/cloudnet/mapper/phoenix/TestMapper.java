package com.safedog.cloudnet.mapper.phoenix;

import com.safedog.cloudnet.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestMapper {
    List<SysUser> queryAll();
}
