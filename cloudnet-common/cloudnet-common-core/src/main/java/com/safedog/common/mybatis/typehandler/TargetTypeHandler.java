package com.safedog.common.mybatis.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.Assert;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MyBatis 系统已经创建好的 typeHandler。在大部分的情况下无须显式地声明 jdbcType 和 javaType，或者用 typeHandler 去指定对应的 typeHandler 来实现数据类型转换，因为 MyBatis 系统会自己探测。有时候需要修改一些转换规则，比如枚举类往往需要自己去编写规则。
 *
 * 在 MyBatis 中 typeHandler 都要实现接口 org.apache.ibatis.type.TypeHandler
 */
public class TargetTypeHandler extends BaseTypeHandler<Object> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        Assert.notNull(jdbcType, "jdbc type not allow null");
        if (jdbcType == JdbcType.BOOLEAN) {
            // boolean -> int;
            jdbcType = JdbcType.INTEGER;
            parameter = booleanToInt(parameter);
        }
        ps.setObject(i, parameter, jdbcType.TYPE_CODE);
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getObject(columnIndex);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getObject(columnIndex);
    }

    private int booleanToInt(Object param) {
        if (param == null) {
            return 0;
        }
        String paramString = param.toString();
        return Boolean.parseBoolean(paramString) || "1".equals(param) ? 1 : 0;
    }

}
