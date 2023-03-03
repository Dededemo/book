package com.hrj.dao;

import com.hrj.bean.Order;
import com.hrj.bean.OrderItem;
import com.hrj.bean.TBook;
import com.hrj.utils.BaseInterface;

import java.sql.SQLException;
import java.util.List;

public interface OrderItemDao extends BaseInterface<OrderItem> {
    public Integer queryForPageTotalCount(String orderId) throws SQLException;
    public List<OrderItem> queryForPageItems(String orderId,int begin, int pageSize) throws SQLException;
}
