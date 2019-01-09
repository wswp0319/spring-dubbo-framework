package net.menwei.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class CacheServiceImpl implements CacheService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value, long timeout) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, timeout, TimeUnit.MINUTES);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setList(String key, List<?> value) {
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        listOperations.leftPush(key, value);
    }

    public Object getList(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public void setSet(String key, Set<?> value) {
        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        setOperations.add(key, value);
    }

    public Object getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public void setHash(String key, Map<String, ?> value) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key, value);
    }

    public Object getHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}