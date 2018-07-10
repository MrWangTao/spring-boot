package com.bear.house.mybatis.handler;

import com.bear.house.mybatis.enums.StatusEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author WangTao
 *         Created at 18/7/10 下午4:06.
 */
public class StatusTypeHandler extends BaseTypeHandler<StatusEnum> {

    private Class<StatusEnum> type;
    private StatusEnum[] enums;

    /**
     * 设置配置文件设置的转换类以及枚举类的内容，共其他方法更便捷高效的实现
     *
     * @param type
     */
    public StatusTypeHandler(Class<StatusEnum> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        this.enums = type.getEnumConstants();
        if (this.enums == null) {
            throw new IllegalArgumentException(type.getSimpleName() + "does not represent an enum type.");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, StatusEnum statusEnum, JdbcType jdbcType) throws SQLException {
        // baseTypeHandler已经帮我们做了parameter的null判断
        preparedStatement.setInt(i, statusEnum.getValue());
    }

    @Override
    public StatusEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        // 根据数据库存储的类型决定获取类型
        int anInt = resultSet.getInt(s);
        // 根据获取的value值找到对应的枚举类型
        return StatusEnum.getEnum(anInt);
    }

    @Override
    public StatusEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        // 根据数据库存储的类型决定获取类型
        int anInt = resultSet.getInt(i);
        // 根据获取的value值找到对应的枚举类型
        return StatusEnum.getEnum(anInt);
    }

    @Override
    public StatusEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        // 根据数据库存储的类型决定获取类型
        int anInt = callableStatement.getInt(i);
        // 根据获取的value值找到对应的枚举类型
        return StatusEnum.getEnum(anInt);
    }

}
