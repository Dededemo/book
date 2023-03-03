package com.hrj.service;

import com.hrj.bean.Admin;
import com.hrj.bean.User;

import java.sql.SQLException;

public interface AdminService {
    /**
     ● 登录
     ● @param admin
     ● @return 如果返回null，说明登录失败，返回有值，是登录成功
     */
    public Admin login(Admin admin) throws SQLException;
}
