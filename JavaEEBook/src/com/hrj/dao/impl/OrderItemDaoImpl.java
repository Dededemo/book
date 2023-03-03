package com.hrj.dao.impl;

import com.hrj.bean.OrderItem;
import com.hrj.bean.TBook;
import com.hrj.dao.OrderItemDao;
import com.hrj.utils.BaseDao;
import com.hrj.utils.JdbcUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {
    @Override
    public List<OrderItem> findAll() throws SQLException {
        return null;
    }

    @Override
    public void save(OrderItem orderItem) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "insert into t_order_item(`name`,`count`,`price`,`total_price`,`order_id`) values(?,?,?,?,?)";
        queryRunner.update(connection,sql,orderItem.getName(),orderItem.getCount(),orderItem.getPrice(),orderItem.getTotalPrice(),orderItem.getOrderId());
    }

    @Override
    public void updateById(OrderItem orderItem) throws SQLException {

    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }

    @Override
    public OrderItem findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List<OrderItem> page(Integer pageNumber) throws SQLException {
        return null;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        return null;
    }

    @Override
    public Integer queryForPageTotalCount(String orderId) throws SQLException {
        String sql = "select count(*) from t_order_item where order_id=?";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long query = queryRunner.query(sql, handler,orderId);
        return query.intValue();
    }

    @Override
    public List<OrderItem> queryForPageItems(String orderId,int begin, int pageSize) throws SQLException {
        //开启下划线->驼峰转换所用 - start
        BeanProcessor bean = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(bean);
        //开启下划线->驼峰转换所用 - end
        String sql = "select * from t_order_item where order_id=? order by `count` desc limit ?,?";
        List<OrderItem> list = queryRunner.query(sql, new BeanListHandler<OrderItem>(OrderItem.class, processor),orderId, begin, pageSize);
        return list;
    }
}
