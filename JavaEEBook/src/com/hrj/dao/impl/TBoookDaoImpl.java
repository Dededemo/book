package com.hrj.dao.impl;

import com.hrj.bean.TBook;
import com.hrj.dao.TBookDao;
import com.hrj.utils.BaseDao;
import com.hrj.utils.JdbcUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TBoookDaoImpl extends BaseDao implements TBookDao {
    @Override
    public List<TBook> findAll() throws SQLException {
        //开启下划线->驼峰转换所用 - start
        BeanProcessor bean = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(bean);
        //开启下划线->驼峰转换所用 - end
        BeanListHandler<TBook> handler = new BeanListHandler<>(TBook.class, processor);
        List<TBook> tBookList = queryRunner.query("select * from t_book order by id desc", handler);
        return tBookList;
    }

    @Override
    public void save(TBook tBook) throws SQLException {
        String sql = "insert into t_book values(null,?,?,?,?,?,?)";
        queryRunner.update(sql, tBook.getName(), tBook.getPrice(), tBook.getAuthor(), tBook.getSales(), tBook.getStock(), tBook.getImgpath());
    }

    @Override
    public void updateById(TBook tBook) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "update t_book set name=?,price=?,author=?,sales=?,stock=?,img_path=? where id=?";
        queryRunner.update(connection,sql, tBook.getName(), tBook.getPrice(), tBook.getAuthor(), tBook.getSales(), tBook.getStock(), tBook.getImgpath(), tBook.getId());
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        String sql = "delete from t_book where id = ?";
        queryRunner.update(sql, id);
    }

    @Override
    public TBook findById(Integer id) throws SQLException {
        //开启下划线->驼峰转换所用 - start
        BeanProcessor bean = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(bean);
        //开启下划线->驼峰转换所用 - end
        BeanHandler<TBook> handler = new BeanHandler<>(TBook.class, processor);
        String sql = "select * from t_book where id=?";
        TBook tBook = queryRunner.query(sql, handler, id);
        return tBook;
    }

    @Override
    public List<TBook> page(Integer pageNumber) throws SQLException {
        String sql = "select * from t_book limit ?,?";
        BeanListHandler<TBook> handler = new BeanListHandler<>(TBook.class);
        List<TBook> tBooks = queryRunner.query(sql, handler, (pageNumber - 1) * pageSize, pageSize);
        return tBooks;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        String sql = "select count(*) from t_book";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long query = queryRunner.query(sql, handler);
        return query.intValue();
    }

    /**
     * 得到book的总记录数
     *
     * @return
     * @throws SQLException
     */

    @Override
    public Integer queryForPageTotalCount() throws SQLException {
        String sql = "select count(*) from t_book";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long query = queryRunner.query(sql, handler);
        return query.intValue();
    }

    /**
     * 分页查询
     *
     * @param begin    起始值
     * @param pageSize 每次查询数据量
     * @return
     * @throws SQLException
     */
    @Override
    public List<TBook> queryForPageItems(int begin, int pageSize) throws SQLException {
        //开启下划线->驼峰转换所用 - start
        BeanProcessor bean = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(bean);
        //开启下划线->驼峰转换所用 - end
        String sql = "select * from t_book order by id desc limit ?,?";
        List<TBook> list = queryRunner.query(sql, new BeanListHandler<TBook>(TBook.class, processor), begin, pageSize);
        return list;
    }

    @Override
    public Integer queryForPageTotalCount(String name, String author, BigDecimal min, BigDecimal max) throws SQLException {
        StringBuilder sql = new StringBuilder("select count(*) from t_book where 1 = 1 ");
        ArrayList list = new ArrayList();
        if (name != null && !"".equals(name)) {
            sql.append(" and name like ? ");
            list.add("%" + name + "%");
        }
        if (author != null && !"".equals(author)) {
            sql.append(" and author like ? ");
            list.add("%" + author + "%");
        }
        if ((min != null && min.signum() == 1) && (max != null && max.signum() == 1)) {
            //min>max
            if (min.compareTo(max) == 1) {
                BigDecimal temp = min;
                min = max;
                max = temp;
            }
            sql.append(" and price BETWEEN ? AND ? ");
            list.add(min);
            list.add(max);
        } else if (min != null && min.signum() == 1) {
            sql.append(" and price > ? ");
            list.add(min);
        } else if (max != null && max.signum() == 1) {
            sql.append(" and price < ? ");
            list.add(max);
        }


        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long query = queryRunner.query(sql.toString(), handler, list.toArray());
        return query.intValue();
    }

    @Override
    public List<TBook> queryForPageItems(int begin, int pageSize, String name, String author, BigDecimal min, BigDecimal max) throws SQLException {
        StringBuilder sql = new StringBuilder("select * from t_book where 1=1 ");
        ArrayList list = new ArrayList();
        if (name != null && !"".equals(name)) {
            sql.append(" and name like ? ");
            list.add("%" + name + "%");
        }
        if (author != null && !"".equals(author)) {
            sql.append(" and author like ? ");
            list.add("%" + author + "%");
        }
        if ((min != null && min.signum() == 1) && (max != null && max.signum() == 1)) {
            //min>max
            if (min.compareTo(max) == 1) {
                BigDecimal temp = min;
                min = max;
                max = temp;
            }
            sql.append(" and price BETWEEN ? AND ? ");
            list.add(min);
            list.add(max);
        } else if (min != null && min.signum() == 1) {
            sql.append(" and price > ? ");
            list.add(min);
        } else if (max != null && max.signum() == 1) {
            sql.append(" and price < ? ");
            list.add(max);
        }
        String end = " order by id desc limit ?,? ";
        sql.append(end);
        list.add(begin);
        list.add(pageSize);

        //开启下划线->驼峰转换所用 - start
        BeanProcessor bean = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(bean);
        //开启下划线->驼峰转换所用 - end

        return queryRunner.query(sql.toString(), new BeanListHandler<TBook>(TBook.class, processor), list.toArray());

    }

}
