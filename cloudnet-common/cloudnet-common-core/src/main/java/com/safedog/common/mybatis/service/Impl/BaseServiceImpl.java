package com.safedog.common.mybatis.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.safedog.common.mybatis.mapper.SuperMapper;
import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ycs
 * @description 数据源切换
 * @date 2021/8/27 13:53
 */
public abstract class BaseServiceImpl<M extends SuperMapper<T>, T> extends ServiceImpl<M, T> {

    //自定义sql查询
    @Resource(name = "mysqlSqlSessionTemplate")
    public SqlSessionTemplate sqlSession;


    /**
     * @return
     */
    public List<T> selectAll(){
        return baseMapper.selectList(null);
    }

    /**
     * 获取mapper name
     * @return
     */
    public String getMapperName(){
        String mapperName = "";
        Class<? extends SuperMapper> cl = baseMapper.getClass();
        Class<?>[] interfaces = cl.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            mapperName = anInterface.getName();
        }
        return  mapperName + ".";
    }
}
