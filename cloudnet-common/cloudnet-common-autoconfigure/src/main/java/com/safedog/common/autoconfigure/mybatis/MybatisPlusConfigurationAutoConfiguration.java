package com.safedog.common.autoconfigure.mybatis;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.Update;
import com.baomidou.mybatisplus.core.injector.methods.UpdateById;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.safedog.common.mybatis.typehandler.BooleanToIntTypeHandler;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
@RequiredArgsConstructor
@Import({MybatisPlusConfigurationAutoConfiguration.SqlInjectorConfiguration.class})
@ConditionalOnClass({ConfigurationCustomizer.class, MybatisConfiguration.class})
@EnableConfigurationProperties(MyBatisPlusConfigurationProperties.class)
public class MybatisPlusConfigurationAutoConfiguration {

    private final MyBatisPlusConfigurationProperties myBatisPlusConfigurationProperties;

    @Bean
    public ConfigurationCustomizer loggingPrefixCustomizer() {
        return configuration -> {
            configuration.setLogPrefix(myBatisPlusConfigurationProperties.getLogPrefix());

            // custom boolean to int(数据库中的字段类型是)
            BooleanToIntTypeHandler typeHandler = new BooleanToIntTypeHandler();
            configuration.getTypeHandlerRegistry().register(Boolean.class, typeHandler);
            configuration.getTypeHandlerRegistry().register(Boolean.TYPE, typeHandler);

            if (myBatisPlusConfigurationProperties.getOptimisticLock().isEnabled()) {
                configuration.addInterceptor(new OptimisticLockerInterceptor());
            }

            /**
             * 分页功能启动
             */
            if (myBatisPlusConfigurationProperties.getPageable().isEnabled()) {
                PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
                // 最大单页限制数量 默认500 小于0如-1  不受限制
                paginationInterceptor.setLimit(myBatisPlusConfigurationProperties.getPageable().getPageSize());
                configuration.addInterceptor(paginationInterceptor);
            }

        };
    }

    @ConditionalOnClass({ISqlInjector.class, DefaultSqlInjector.class})
    public static class SqlInjectorConfiguration {

        @Bean
        @ConditionalOnMissingBean(ISqlInjector.class)
        public ISqlInjector sqlInjector() {
            return new CustomSqlInjector();
        }

    }

    private static class CustomSqlInjector extends DefaultSqlInjector {
        @Override
        public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
            List<AbstractMethod> result = super.getMethodList(mapperClass);
            List<AbstractMethod> resultCopy = new ArrayList<>();
            for (AbstractMethod m : result) {
                if (m instanceof UpdateById) {
                    resultCopy.add(new OverrideUpdateById());
                    continue;
                }

                if (m instanceof Update) {
                    resultCopy.add(new OverrideUpdate());
                    continue;
                }

                resultCopy.add(m);
            }
            return resultCopy;
        }

    }

    private static class OverrideUpdate extends AbstractMethod {

        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            SqlMethod sqlMethod = SqlMethod.UPDATE;
            String sql = String.format(
                    sqlMethod.getSql(),
                    tableInfo.getTableName(),
                    sqlSet(false, true, tableInfo, true, ENTITY, ENTITY_DOT),
                    sqlWhereEntityWrapper(true, tableInfo),
                    sqlComment()
            );
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
            return this.addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
        }

    }

    private static class OverrideUpdateById extends AbstractMethod {

        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {

            // boolean logicDelete = tableInfo.isLogicDelete();
            SqlMethod sqlMethod = SqlMethod.UPDATE_BY_ID;
            final String additional = optlockVersion() + tableInfo.getLogicDeleteSql(true, false);

            String sql = String.format(
                    sqlMethod.getSql(),
                    tableInfo.getTableName(),
                    sqlSet(false, false, tableInfo, false, ENTITY, ENTITY_DOT),
                    tableInfo.getKeyColumn(),
                    ENTITY_DOT + tableInfo.getKeyProperty(),
                    additional
            );

            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
            return addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
        }
    }

}
