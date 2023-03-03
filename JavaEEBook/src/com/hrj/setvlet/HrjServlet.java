package com.hrj.setvlet;

import com.hrj.utils.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "HrjServlet", value = "/HrjServlet")
public class HrjServlet extends BaseServlet {
    protected void myTest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("我是HrjServlet.myTest方法执行完成");

    }
}
