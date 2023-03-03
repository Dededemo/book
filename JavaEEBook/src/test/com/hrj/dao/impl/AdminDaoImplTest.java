package test.com.hrj.dao.impl;

import com.hrj.bean.Admin;
import com.hrj.dao.AdminDao;
import com.hrj.dao.impl.AdminDaoImpl;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class AdminDaoImplTest {

    @Test
    public void queryUserByUsernameAndPassword() throws SQLException {
        AdminDao adminDao = new AdminDaoImpl();
        Admin admin = adminDao.queryUserByUsernameAndPassword("xiaoming", "123");
        System.out.println("admin = " + admin);
    }
}