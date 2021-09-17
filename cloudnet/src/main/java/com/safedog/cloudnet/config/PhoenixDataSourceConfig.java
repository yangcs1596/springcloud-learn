package com.safedog.cloudnet.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.safedog.cloudnet.mapper.phoenix", sqlSessionFactoryRef = "phoenixSqlSessionFactory")
public class PhoenixDataSourceConfig {

    @Bean(name = "phoenixDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.phoenix")
    public DataSource getPhoenixDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("phoenixSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("phoenixDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 设置mybatis的xml所在位置
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/phoenix/*.xml"));
        return bean.getObject();
    }

    @Bean("phoenixSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("phoenixSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
