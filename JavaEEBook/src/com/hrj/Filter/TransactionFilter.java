package com.hrj.Filter;

import com.hrj.utils.JdbcUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "TransactionFilter",urlPatterns = "/*")
public class TransactionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            System.out.println("进入TransactionFilter");
            filterChain.doFilter(servletRequest, servletResponse);
            System.out.println("回到TransactionFilter");
            JdbcUtils.commitAndClose();// 提交事务
        } catch (Exception e) {
            System.out.println("回到TransactionFilter因为发生了异常");
            JdbcUtils.rollbackAndClose();//回滚事务
            e.printStackTrace();//打印异常信息
        }
    }

    @Override
    public void destroy() {

    }


}