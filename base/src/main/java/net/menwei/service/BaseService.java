package net.menwei.service;

import net.menwei.ResultSet;

import java.util.Map;

public interface BaseService<T> {
    ResultSet add(T instance);

    ResultSet edit(T instance);

    ResultSet delete(T instance);

    ResultSet count(Map<String, Object> paramsMap);

    ResultSet get(Map<String, Object> paramsMap);

    ResultSet list(Map<String, Object> paramsMap);

    ResultSet findOne(Map<String, Object> paramsMap);

    ResultSet queryPage(Map<String, Object> paramsMap);
}
