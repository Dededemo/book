package com.hrj.dao;

import com.hrj.bean.User;
import com.hrj.utils.BaseInterface;

import java.sql.SQLException;

//UserDao接口继承BaseInterface
public interface UserDao extends BaseInterface<User> {
    /**
     ● 根据用户名查询用户信息
     ● @param username 用户名
     ● @return 如果返回null,说明没有这个用户。反之亦然
     */
    public User queryUserByUsername(String username) throws SQLException;

    /**
     ● 根据 用户名和密码查询用户信息
     ● @param username
     ● @param password
     ● @return 如果返回null,说明用户名或密码错误,反之亦然
     */
    public User queryUserByUsernameAndPassword(String username,String password) throws SQLException;
}
