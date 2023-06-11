package com.ruoyi.common.config.handlers;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.exception.ResultException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mybatis-Plus Jackson类型处理器基类
 *
 * @author bin
 * @since 2023/02/10
 */
public abstract class BaseJacksonTypeHandler<T> extends TypeReference<T> implements TypeHandler<T> {
    private static ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        if (null == objectMapper) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    public static void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "ObjectMapper should not be null");
        BaseJacksonTypeHandler.objectMapper = objectMapper;
    }

    @Override
    public void setParameter(final PreparedStatement ps, final int i, final T parameter, final JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, toJson(parameter));
    }

    @Override
    public T getResult(final ResultSet rs, final String columnName) throws SQLException {
        final String json = rs.getString(columnName);
        return StringUtils.isBlank(json) ? null : parse(json);
    }

    @Override
    public T getResult(final ResultSet rs, final int columnIndex) throws SQLException {
        final String json = rs.getString(columnIndex);
        return StringUtils.isBlank(json) ? null : parse(json);
    }

    @Override
    public T getResult(final CallableStatement cs, final int columnIndex) throws SQLException {
        final String json = cs.getString(columnIndex);
        return StringUtils.isBlank(json) ? null : parse(json);
    }

    protected T parse(String json) {
        try {
            return getObjectMapper().readValue(json, this);
        } catch (IOException e) {
            throw new ResultException("反序列化失败", e);
        }
    }

    protected String toJson(T obj) {
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new ResultException("序列化失败", e);
        }
    }
}
