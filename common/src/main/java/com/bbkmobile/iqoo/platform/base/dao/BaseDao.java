package com.bbkmobile.iqoo.platform.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseDao<T, ID extends Serializable> {

    /**
     * 保存实体
     * 
     * @param t
     */
    public void save(T t);

    /**
     * 保存或更新实体
     * 
     * @param t
     */
    public void saveOrUpdate(T t);

    /**
     * 加载实体的load方法
     * 
     * @param id
     * @return
     */
    public T load(ID id);

    /**
     * 查找的get方法
     * 
     * @param id
     * @return
     */
    public T get(ID id);

    /**
     * contains
     * 
     * @param t
     * @return
     */
    public boolean contains(T t);

    /**
     * 删除表中的t数据
     * 
     * @param t
     */
    public void delete(T t);

    /**
     * 根据ID删除数据
     * 
     * @param Id
     * @return
     */
    public boolean deleteById(ID Id);

    /**
     * 删除所有
     * 
     * @param entities
     */
    public void deleteAll(Collection<T> entities);

    /**
     * refresh实体
     * 
     * @param t
     */
    public void refresh(T t);

    /**
     * update实体
     * 
     * @param t
     */
    public void update(T t);

    /**
     * 按bean中不为null的属性更新
     * 
     * @param id
     * @param bean
     */
    public void update(ID id, Object bean);

    /**
     * 根据一个属性查询
     * 
     * @param pName
     * @param pValue
     * @return
     */
    public List<T> listByProperty(String pName, Object pValue);

    /**
     * 根据多个属性查询
     * 
     * @param properties
     * @return
     */
    public List<T> listByProperties(Map<String, Object> properties);

    /**
     * 批量查询
     * 
     * @param property
     * @param inValues
     * @return
     */
    public List<T> listInProperty(String property, List<Object> inValues);

    /**
     * 按T中不为null属性的注解类型进行匹配查询
     * 
     * @param t
     * @return
     */
    public List<T> listByBean(T t);

    /**
     * 按T中不为null属性的注解类型进行匹配查询(带分页)
     * 
     * @param pageNo
     * @param pageSize
     * @param t
     * @return
     */
    public List<T> listByBean(Integer pageNo, Integer pageSize, T t);

}
