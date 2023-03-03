package com.hrj.service;

import com.hrj.bean.TBook;
import com.hrj.utils.Page;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface BookService {

    public void addBook(TBook book) throws SQLException;

    public void deleteBookById(Integer id)throws SQLException;


    public void updateBook(TBook book)throws SQLException;
    public TBook queryBookById(Integer id)throws SQLException;
    public List<TBook> queryBooks()throws SQLException;
    public Page<TBook> page(int pageNo, int pageSize) throws SQLException;
    public Page<TBook> page(int pageNo, int pageSize, String name, String author,BigDecimal min, BigDecimal max) throws SQLException;
}
