package com.hrj.service.impl;

import com.hrj.bean.TBook;
import com.hrj.dao.TBookDao;
import com.hrj.dao.impl.TBoookDaoImpl;
import com.hrj.service.BookService;
import com.hrj.utils.Page;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class BookServiceImpl implements BookService {

    private TBookDao bookDao = new TBoookDaoImpl();

    @Override
    public void addBook(TBook book) throws SQLException {
        bookDao.save(book);
    }

    @Override
    public void deleteBookById(Integer id) throws SQLException {
        bookDao.deleteById(id);
    }

    @Override
    public void updateBook(TBook book) throws SQLException {
        bookDao.updateById(book);
    }

    @Override
    public TBook queryBookById(Integer id) throws SQLException {
        return bookDao.findById(id);
    }

    @Override
    public List<TBook> queryBooks() throws SQLException {
        return bookDao.findAll();
    }

    @Override
    public Page<TBook> page(int pageNo, int pageSize) throws SQLException {
        //需要调用两个方法
        Page<TBook> page = new Page<>();
        Integer totalCount = bookDao.queryForPageTotalCount();//获取总记录数
        page.setPageTotalCount(totalCount);//设置总记录数
        //22%4==0 22为当前数据库数据数量
        /*if (totalCount % pageSize == 0) {
            page.setPageTotal(totalCount / pageSize);//能整除
        } else {
            page.setPageTotal(totalCount / pageSize + 1);//不能整除
        }*/
        page.setPageTotal((totalCount + pageSize -1)/pageSize);//设置总页数,效果同上
        page.setPageNo(pageNo);//设置当前页
        page.setItems(bookDao.queryForPageItems((page.getPageNo()-1)*pageSize,pageSize));//设置分页查询结果集
        return page;
    }



    @Override
    public Page<TBook> page(int pageNo, int pageSize, String name, String author, BigDecimal min, BigDecimal max) throws SQLException {
        //需要调用两个方法
        Page<TBook> page = new Page<>();
        Integer totalCount = bookDao.queryForPageTotalCount(name,author,min,max);//获取总记录数
        page.setPageTotalCount(totalCount);//设置总记录数
        page.setPageTotal((totalCount + pageSize -1)/pageSize);//设置总页数,效果同上
        page.setPageNo(pageNo);//设置当前页
        page.setItems(bookDao.queryForPageItems((page.getPageNo()-1)*pageSize,pageSize,name,author,min,max));//设置分页查询结果集
        return page;
    }

    public static void main(String[] args) throws SQLException {
        BookService bookService = new BookServiceImpl();
        Page<TBook> page = bookService.page(1, 5);
        List<TBook> items = page.getItems();
        System.out.println("items = " + items);
    }

}
