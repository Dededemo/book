package com.hrj.service.impl;

import com.hrj.bean.CartItem;
import com.hrj.bean.Order;
import com.hrj.bean.OrderItem;
import com.hrj.bean.TBook;
import com.hrj.dao.OrderDao;
import com.hrj.dao.OrderItemDao;
import com.hrj.dao.TBookDao;
import com.hrj.dao.impl.OrderDaoImpl;
import com.hrj.dao.impl.OrderItemDaoImpl;
import com.hrj.dao.impl.TBoookDaoImpl;
import com.hrj.service.Cart;
import com.hrj.service.OrderService;
import com.hrj.utils.Page;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    //订单dao
    private OrderDao orderDao = new OrderDaoImpl();
    //订单项dao
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    //图书dao
    private TBookDao bookDao = new TBoookDaoImpl();

    /**
     * 生成一个订单
     * 1. 添加 一个订单数据到数据库中的order表
     * 2. 此订单中至少有一个单项, 至多会有n个..多以要将订单项目都添加到OrderItem表中
     * 3. 清空掉购物车中的数据
     *
     * @param cart   购物车对象
     * @param userId 用户id
     * @return 返回此订单的id
     */
    @Override
    public String createOrder(Cart cart, Integer userId) throws SQLException {
        //1. 添加 一个订单数据到数据库中的order表
        String orderId = "" + System.currentTimeMillis() + userId;
        Order order = new Order();
        order.setOrderId(orderId);//生成的订单编号
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));//当前的系统时间
        order.setPrice(cart.getTotalPrice());//订单的总价格
        order.setStatus(0);//设置订单状态 0未发货
        order.setUserId(userId);//设置用户编号, 因为这个订单要知道是谁下的单
        orderDao.save(order);

//        int i= 10/0;

        //2. 此订单中至少有一个单项, 至多会有n个..所以要将订单项目都添加到OrderItem表中
        Map<Integer, CartItem> items = cart.getItems();
        for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
            OrderItem item = new OrderItem();
            item.setName(entry.getValue().getName());//设置订单项的名字
            item.setCount(entry.getValue().getCount());//设置订单项的数量
            item.setPrice(entry.getValue().getPrice());//设置订单项的单价
            item.setTotalPrice(entry.getValue().getTotalPrice());//设置订单项的总价
            item.setOrderId(orderId);//设置订单的外键id 订单编号
            orderItemDao.save(item);

            //更新库存和销量
            TBook book = bookDao.findById(entry.getValue().getId());//通过图书的主键id返回一个图书对象:book
            book.setSales(book.getSales()+entry.getValue().getCount());//设置销量
            book.setStock(book.getStock()- entry.getValue().getCount());//设置库存
            bookDao.updateById(book);//修改book的销量与库存
        }

        //3. 清空掉购物车中的数据
        cart.clear();

        return orderId;
    }

    @Override
    public Page<Order> page(Integer pageNo,Integer pageSize) throws SQLException {
        //需要调用两个方法
        Page<Order> page = new Page<>();
        Integer totalCount = orderDao.pageRecord();//获取总记录数
        page.setPageTotalCount(totalCount);//设置总记录数

        page.setPageTotal((totalCount + pageSize -1)/pageSize);//设置总页数,效果同上
        page.setPageNo(pageNo);//设置当前页
        page.setItems(orderDao.page(page.getPageNo()));//设置分页查询结果集
        return page;
    }

    @Override
    public Page<OrderItem> page(String orderId, int pageNo, int pageSize) throws SQLException {
        //需要调用两个方法
        Page<OrderItem> page = new Page<>();
        Integer totalCount = orderItemDao.queryForPageTotalCount(orderId);//获取总记录数
        page.setPageTotalCount(totalCount);//设置总记录数
        page.setPageTotal((totalCount + pageSize -1)/pageSize);//设置总页数,效果同上
        page.setPageNo(pageNo);//设置当前页
        page.setItems(orderItemDao.queryForPageItems(orderId,(page.getPageNo()-1)*pageSize,pageSize));//设置分页查询结果集
        return page;
    }

}