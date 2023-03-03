package test.com.hrj.dao.impl;

import com.hrj.bean.OrderItem;
import com.hrj.dao.OrderItemDao;
import com.hrj.dao.impl.OrderItemDaoImpl;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class OrderItemDaoImplTest {

    @Test
    public void queryForPageTotalCount() throws SQLException {
        String id = "16599551771678";
        OrderItemDao orderItemDao =new OrderItemDaoImpl();
        System.out.println(orderItemDao.queryForPageTotalCount(id));
    }

    @Test
    public void queryForPageItems() throws SQLException {
        String id = "16599551771678";
        OrderItemDao orderItemDao =new OrderItemDaoImpl();
        List<OrderItem> orderItems = orderItemDao.queryForPageItems(id, 1, 4);
        for (OrderItem orderItem : orderItems) {
            System.out.println("orderItem = " + orderItem);
        }
    }
}