package com.hrj.setvlet;

import com.hrj.bean.Order;
import com.hrj.bean.OrderItem;
import com.hrj.bean.User;
import com.hrj.service.Cart;
import com.hrj.service.OrderService;
import com.hrj.service.impl.OrderServiceImpl;
import com.hrj.utils.BaseServlet;
import com.hrj.utils.Page;
import com.hrj.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "OrderServlet", value = "/OrderServlet")
public class OrderServlet extends BaseServlet {

    //生成订单
    protected void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        if(user == null){
            request.setAttribute("msg","登录超时,请重新登录!");
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request,response);
        }
        OrderService orderService = new OrderServiceImpl();
        try {
            //生成订单, 并且返回此订单号
            String orderId = orderService.createOrder(cart, user.getId());

            //为什么选择重定向,而没有选择服务器转发--订单重复提交
            //为了防止表单重复提交
//            request.getRequestDispatcher("/pages/cart/checkout.jsp").forward(request,response);
            request.getSession().setAttribute("orderId",orderId);
            response.sendRedirect(request.getContextPath()+"/pages/cart/checkout.jsp");
            //重定向跳转前不加斜杠
            //response.sendRedirect("pages/cart/checkout.jsp");

        } catch (SQLException throwables) {
            throw new RuntimeException();
        }

    }

    protected void listOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderServiceImpl();
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), 4);
        try {
            Page<Order> page = orderService.page(pageNo, pageSize);
            request.setAttribute("page",page);
            page.setUrl("OrderServlet?action=listOrder&");
            request.getRequestDispatcher("/pages/order/order.jsp").forward(request,response);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    protected void orderItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderServiceImpl();
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), 4);
        String orderId = request.getParameter("orderId");
        System.out.println("orderId = " + orderId);
        try {
            Page<OrderItem> page = orderService.page(orderId,pageNo, pageSize);
            request.setAttribute("page",page);
            page.setUrl("OrderServlet?action=orderItem&orderId="+orderId+"&");
            request.getRequestDispatcher("/pages/order/orderItem.jsp").forward(request,response);

        } catch (SQLException throwables) {
            throw new RuntimeException();
//            throwables.printStackTrace();
        }
    }
}
