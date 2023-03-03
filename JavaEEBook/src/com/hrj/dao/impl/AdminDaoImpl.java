package com.hrj.dao.impl;

import com.hrj.bean.Admin;
import com.hrj.dao.AdminDao;
import com.hrj.utils.BaseDao;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class AdminDaoImpl extends BaseDao implements AdminDao {

    @Override
    public Admin queryUserByUsernameAndPassword(String username, String password) throws SQLException {
        String sql = "select * from t_admin where username = ? and password = ? ";
        return queryRunner.query(sql, new BeanHandler<>(Admin.class),username,password);
    }

    @Override
    public Admin queryUserByUsernameAndPassword(Admin admin) throws SQLException {
        String sql = "select * from t_admin where username = ? and password = ? ";
        return queryRunner.query(sql, new BeanHandler<>(Admin.class),admin.getUsername(),admin.getPassword());
    }

    @Override
    public void save(Admin admin) throws SQLException {
        String sql = "insert into t_admin value(null,?,?)";
        //添加成功后获取主键id
        Long id = queryRunner.insert(sql, new ScalarHandler<Long>(), admin.getUsername(), admin.getPassword());
        admin.setId(id.intValue());
    }

    @Override
    public List<Admin> findAll() throws SQLException {
        return null;
    }

    @Override
    public void updateById(Admin admin) throws SQLException {

    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }

    @Override
    public Admin findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List<Admin> page(Integer pageNumber) throws SQLException {
        return null;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        return null;
    }
}
