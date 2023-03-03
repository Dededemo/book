package test.com.hrj.dao.impl;

import com.hrj.bean.Order;
import com.hrj.dao.OrderDao;
import com.hrj.dao.impl.OrderDaoImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.junit.Assert.*;

public class OrderDaoImplTest {

    @Test
    public void save() {
        Order order = new Order(""+System.currentTimeMillis(),new Timestamp(System.currentTimeMillis()),new BigDecimal(10),0,1);
        OrderDao orderDao = new OrderDaoImpl();
        try {
            orderDao.save(order);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}