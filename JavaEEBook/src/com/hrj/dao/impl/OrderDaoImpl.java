package com.hrj.dao.impl;

import com.hrj.bean.Order;
import com.hrj.bean.TBook;
import com.hrj.dao.OrderDao;
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

public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public List<Order> findAll() throws SQLException {
        return null;
    }

    @Override
    public void save(Order order) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "insert into t_order(`order_id`,`create_time`,`price`,`status`,`user_id`) values(?,?,?,?,?)";
        queryRunner.update(connection,sql,order.getOrderId(),order.getCreateTime(),order.getPrice(),order.getStatus(),order.getUserId());
    }

    @Override
    public void updateById(Order order) throws SQLException {

    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }

    @Override
    public Order findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List<Order> page(Integer pageNumber) throws SQLException {
        //开启下划线->驼峰转换所用 - start
        BeanProcessor bean = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(bean);
        //开启下划线->驼峰转换所用 - end
        String sql = "select * from t_order order by create_time desc limit ?,?";
        return queryRunner.query(sql,new BeanListHandler<Order>(Order.class,processor),(pageNumber-1)*pageSize,pageSize);

    }

    @Override
    public Integer pageRecord() throws SQLException {
        String sql = "select count(*) from t_order ";
        ScalarHandler<Long> handler = new ScalarHandler();
        Long i = queryRunner.query(sql, handler);
        return i.intValue();
    }
}
