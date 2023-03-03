package com.hrj.service;

import com.hrj.bean.Order;
import com.hrj.bean.OrderItem;
import com.hrj.bean.TBook;
import com.hrj.utils.Page;

import java.sql.SQLException;

public interface OrderService {
    public String createOrder(Cart cart, Integer userId) throws SQLException;
    public Page<Order> page(Integer pageNo,Integer pageSize) throws SQLException;
    public Page<OrderItem> page(String orderId,int pageNo, int pageSize) throws SQLException;
}