package com.safedog.cloudnet.mapper.mysql;

import com.safedog.cloudnet.entity.mysql.SysUser;
import com.safedog.common.mybatis.mapper.SuperMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper extends SuperMapper<SysUser> {
}
