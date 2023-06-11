package com.ruoyi.common.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.config.handlers.BaseJacksonTypeHandler;
import com.ruoyi.common.exception.ResultException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author bin
 * @since 2023/05/30
 */
@Component
@RequiredArgsConstructor
public class GlobalInstance {

    // region ObjectMapper

    public static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        BaseJacksonTypeHandler.setObjectMapper(objectMapper);
        GlobalInstance.objectMapper = objectMapper;
    }

    public static String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new ResultException("序列化失败", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new ResultException("反序列化失败:" + clazz.getName(), e);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new ResultException("反序列化失败:" + clazz.getType(), e);
        }
    }

    public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
        try {
            val type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new ResultException("反序列化失败:" + clazz.getName(), e);
        }
    }
    // endregion

}
