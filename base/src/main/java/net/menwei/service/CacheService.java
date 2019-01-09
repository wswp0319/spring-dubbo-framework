package net.menwei.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CacheService {
    /**
     *
     * @param key 键
     * @param value 值
     * @param timeout 超时时间（分）
     */
    public void set(String key, Object value, long timeout);

    public Object get(String key);

    public void setList(String key, List<?> value);

    public Object getList(String key);

    public void setSet(String key, Set<?> value);

    public Object getSet(String key);

    public void setHash(String key, Map<String, ?> value);

    public Object getHash(String key);

    public void delete(String key);
}
