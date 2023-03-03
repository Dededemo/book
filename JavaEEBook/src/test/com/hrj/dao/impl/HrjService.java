package test.com.hrj.dao.impl;

import com.hrj.bean.Order;
import com.hrj.bean.TBook;
import com.hrj.bean.User;
import com.hrj.dao.OrderDao;
import com.hrj.dao.TBookDao;
import com.hrj.dao.UserDao;
import com.hrj.dao.impl.OrderDaoImpl;
import com.hrj.dao.impl.TBoookDaoImpl;
import com.hrj.dao.impl.UserDaoImpl;

import java.sql.SQLException;

public class HrjService {
    private TBookDao bookDao = new TBoookDaoImpl();
    private OrderDao orderDao = new OrderDaoImpl();
    private UserDao userDao = new UserDaoImpl();


    /*public void hrjTest(){
        try {
            bookDao.updateById(new TBook());//成功

            orderDao.updateById(new Order());//成功

            userDao.save(new User());//异常

            //事务提交

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //事务回滚
        }
    }*/
}
