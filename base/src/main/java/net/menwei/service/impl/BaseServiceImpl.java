package net.menwei.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import net.menwei.ResultSet;
import net.menwei.mapper.IMapper;
import net.menwei.service.BaseService;
import net.menwei.utils.StringUtil;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseServiceImpl<T> implements BaseService<T> {
    public abstract IMapper<T> getMapper();

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ResultSet add(T instance) {
        try {
            Class clazz = instance.getClass();
            Method[] methods = clazz.getDeclaredMethods();
            for(Method method : methods){
                Type[] types = method.getGenericParameterTypes();
                if(types.length == 1 && String.class == types[0] && "setUuid".equals(method.getName())){
                    method.invoke(instance, StringUtil.buildUUID());
                }
            }
            int affectedRowCount = this.getMapper().add(instance);
            if(1 == affectedRowCount){
                return ResultSet.createSuccess("新增数据成功", instance);
            } else {
                return ResultSet.createError("新增数据失败");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return ResultSet.createError("新增数据失败");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ResultSet edit(T instance) {
        int affectedRowCount = this.getMapper().edit(instance);
        if(1 == affectedRowCount){
            return ResultSet.createSuccess("更新数据成功", instance);
        } else {
            return ResultSet.createError("更新数据失败");
        }
    }

    @Override
    public ResultSet delete(T instance) {
        int affectedRowCount = this.getMapper().del(instance);
        if(1 == affectedRowCount){
            return ResultSet.createSuccess("更新删除成功", instance);
        } else {
            return ResultSet.createError("更新删除失败");
        }
    }

    @Override
    public ResultSet count(Map<String, Object> paramsMap) {
        return  ResultSet.createSuccess("查询数量成功", this.getMapper().count(paramsMap));
    }

    @Override
    public ResultSet get(Map<String, Object> paramMap) {
        T instance = this.getMapper().selInfo(paramMap);
        if(instance != null) {
            return ResultSet.createSuccess("查询数据成功", instance);
        } else {
            return ResultSet.createError("查询数据为空");
        }
    }

    @Override
    public ResultSet list(Map<String, Object> paramMap) {
        List<T> list = this.getMapper().list(paramMap);
        if(!list.isEmpty()){
            return ResultSet.createSuccess("查询数据成功", list);
        }
        return ResultSet.createError("查询数据为空");
    }

    @Override
    public ResultSet findOne(Map<String, Object> paramMap) {
        List<T> list = this.getMapper().list(paramMap);
        if(!list.isEmpty() && list.size() == 1){
            return ResultSet.createSuccess("查询数据成功", list.get(0));
        }
        return ResultSet.createError("查询数据有误");
    }

    @Override
    public ResultSet queryPage(Map<String, Object> paramMap) {
        PageBounds pageBounds;
        if(paramMap.containsKey("sortField")){//排序
            pageBounds = new PageBounds((Integer) paramMap.get("_pageNum"), (Integer) paramMap.get("_pageSize"), Order.create(paramMap.get("sortField").toString(),paramMap.get("sortType").toString()));
        }else {
            pageBounds = new PageBounds((Integer) paramMap.get("_pageNum"), (Integer) paramMap.get("_pageSize"));
        }
        PageList<T> pageList = this.getMapper().queryPage(paramMap, pageBounds);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("aaData", pageList);
        resultMap.put("iTotalRecords", pageList.getPaginator() != null ? pageList.getPaginator().getEndRow() : 0);
        resultMap.put("iTotalDisplayRecords", pageList.getPaginator() != null ? pageList.getPaginator().getTotalCount() : 0);
        resultMap.put("sEcho", paramMap.get("_sEcho"));
        return ResultSet.createSuccess("", resultMap);
    }
}
