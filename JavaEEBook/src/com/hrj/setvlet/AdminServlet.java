package com.hrj.setvlet;

import com.hrj.bean.Admin;
import com.hrj.bean.User;
import com.hrj.service.AdminService;
import com.hrj.service.UserService;
import com.hrj.service.impl.AdminServiceImpl;
import com.hrj.service.impl.UserServiceImpl;
import com.hrj.utils.BaseServlet;
import com.hrj.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "AdminServlet", value = "/AdminServlet")
public class AdminServlet extends BaseServlet {
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();

        AdminService adminService = new AdminServiceImpl();
        Admin admin = new Admin();
        WebUtils.copyParamToBean(parameterMap, admin);
        try {
            Admin myadmin = adminService.login(admin);
            if (myadmin != null) {
                request.getSession().setAttribute("admin",myadmin);
                request.getRequestDispatcher("/pages/manager/manager.jsp").forward(request,response);
            } else {
                request.setAttribute("msg", "账号名或登陆密码不正确!");
                request.setAttribute("username", admin.getUsername());
                request.setAttribute("password", admin.getPassword());
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

