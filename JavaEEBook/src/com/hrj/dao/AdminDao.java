package com.hrj.dao;

import com.hrj.bean.Admin;
import com.hrj.utils.BaseInterface;

import java.sql.SQLException;

public interface AdminDao extends BaseInterface<Admin> {

    /**
     ● 根据 用户名和密码查询用户信息
     ● @param username
     ● @param password
     ● @return 如果返回null,说明用户名或密码错误,反之亦然
     */
    public Admin queryUserByUsernameAndPassword(String username,String password) throws SQLException;
    public Admin queryUserByUsernameAndPassword(Admin admin) throws SQLException;
}
