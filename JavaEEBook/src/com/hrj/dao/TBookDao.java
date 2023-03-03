package com.hrj.dao;

import com.hrj.bean.TBook;
import com.hrj.utils.BaseInterface;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface TBookDao extends BaseInterface<TBook> {
    public Integer queryForPageTotalCount() throws SQLException;
    public List<TBook> queryForPageItems(int begin, int pageSize) throws SQLException;
    public Integer queryForPageTotalCount(String name, String author, BigDecimal min, BigDecimal max) throws SQLException;
    public List<TBook> queryForPageItems(int begin, int pageSize,String name, String author, BigDecimal min, BigDecimal max) throws SQLException;
    }
