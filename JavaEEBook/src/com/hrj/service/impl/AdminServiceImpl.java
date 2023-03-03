package com.hrj.service.impl;

import com.hrj.bean.Admin;
import com.hrj.dao.AdminDao;
import com.hrj.dao.impl.AdminDaoImpl;
import com.hrj.service.AdminService;

import java.sql.SQLException;

public class AdminServiceImpl implements AdminService {

    private AdminDao adminDao = new AdminDaoImpl();

    @Override
    public Admin login(Admin admin) throws SQLException {
        return adminDao.queryUserByUsernameAndPassword(admin);
    }
}
