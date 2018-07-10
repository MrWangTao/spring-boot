package com.bear.house.mybatis.handler;


import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

/**
 * @author WangTao
 *         Created at 18/7/10 下午4:41.
 *         TODO 待琢磨
 */
public class BaseEnumTypeHandler<E extends Enum<E> & Indentifiable<K>, K> extends BaseTypeHandler<E> {

    private Class<E> type;
    private E[] enums;

    /**
     * 设置配置文件设置的转换类以及枚举类的内容，共其他方法更便捷高效的实现
     *
     * @param type
     */
    public BaseEnumTypeHandler(Class<E> type) {
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
    public void setNonNullParameter(PreparedStatement ps, int i, E e, JdbcType jdbcType) throws SQLException {
        if (jdbcType == null) {
            K id = e.getId();
            if(id instanceof Integer || id instanceof Short || id instanceof Character || id instanceof Byte){
                ps.setInt(i, (Integer)id);
            }else if(id instanceof String){
                ps.setString(i, (String)id);
            }else if(id instanceof Boolean){
                ps.setBoolean(i, (Boolean)id);
            }else if(id instanceof Long){
                ps.setLong(i, (Long)id);
            }else if(id instanceof Double){
                ps.setDouble(i, (Double)id);
            }else if(id instanceof Float){
                ps.setFloat(i, (Float)id);
            }else{
                throw new RuntimeException("unsupported [id] type of enum");
            }
        } else {
            ps.setObject(i, e.getId(), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public E getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String ss = resultSet.getString(s);
        return toEnum(ss);
    }

    @Override
    public E getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String s = resultSet.getString(i);
        return toEnum(s);
    }

    @Override
    public E getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String s = callableStatement.getString(i);
        return toEnum(s);
    }

    private E toEnum(String id){
        EnumSet<E> set = EnumSet.allOf(type);
        if (set == null || set.size() <= 0) {
            return null;
        }
        for (E e : set) {
            K k = e.getId();
            if(k != null){
                if(k.toString().equals(id)){
                    return e;
                }
            }
        }
        return null;
    }

}
