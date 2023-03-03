package com.hrj.utils;

import java.sql.SQLException;
import java.util.List;

/**
 * 优化接口
 * @param <T>
 */
public interface BaseInterface<T> {
    /**
     * 查询表中所有数据
     * @return list集合
     */
    public List<T> findAll() throws SQLException;

    /**
     * 添加数据
     * @param t
     */
    public void save(T t) throws SQLException;

    /**
     * 根据主键ud进行数据的修改
     * @param t
     */
    public void updateById(T t) throws SQLException;

    /**
     * 根据主键id删除数据
     * @param id
     */
    public void deleteById(Integer id) throws SQLException;

    /**
     * 根据主键id查询
     * @param id
     * @return
     */
    public T findById(Integer id) throws SQLException;
    /**
     * 无条件分页查询
     * @return pageNumber页码
     */
    public List<T> page(Integer pageNumber) throws SQLException;

    /**
     * 求表中的记录数
     * @return
     */
    public Integer pageRecord() throws SQLException;
}
