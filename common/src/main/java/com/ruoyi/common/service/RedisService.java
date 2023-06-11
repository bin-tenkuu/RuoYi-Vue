package com.ruoyi.common.service;

import java.time.Duration;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.exception.ResultException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

/**
 * spring redis 工具类
 *
 * @author ruoyi
 **/
@Component
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     */
    public void expire(final String key, final Duration timeout) {
        redisTemplate.expire(key, timeout);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void setValue(final String key, final T value) {
        redisTemplate.opsForValue().set(key, toJson(value));
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     */
    public <T> void setValue(final String key, final T value, final Duration timeout) {
        redisTemplate.opsForValue().set(key, toJson(value), timeout);
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getValue(final String key, Class<T> clazz) {
        val value = redisTemplate.opsForValue().get(key);
        return fromJson(value, clazz);
    }

    public long increment(String key) {
        val increment = redisTemplate.opsForValue().increment(key);
        return increment == null ? 0 : increment;
    }

    /**
     * 删除单个对象
     */
    public void delete(final String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     */
    public void delete(final Collection<String> collection) {
        redisTemplate.delete(collection);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Set<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    private String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new ResultException("序列化失败", e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T fromJson(String json, Class<T> clazz) {
        if (clazz == String.class) {
            return (T) json;
        }
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new ResultException("反序列化失败:" + clazz.getName(), e);
        }
    }

}
