package com.hrj.setvlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "HelloWorldServlet", value = "/HelloWorldServlet")
public class HelloWorldServlet extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("HelloWorldServlet 被访问了!");
    }


    @Override
    public void destroy() {
        System.out.println("HelloWorldServlet.destroy销毁方法访问");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("HelloWorldServlet.init方法访问");
    }

}
