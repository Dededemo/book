package com.hrj.setvlet;

import com.hrj.bean.CartItem;
import com.hrj.bean.TBook;
import com.hrj.service.BookService;
import com.hrj.service.Cart;
import com.hrj.service.impl.BookServiceImpl;
import com.hrj.utils.BaseServlet;
import com.hrj.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CartServlet", value = "/CartServlet")
public class CartServlet extends BaseServlet {
    /**
     ● 加入购物车
     ● @param request
     ● @param response
     ● @throws ServletException
     ● @throws IOException
     */
    protected void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        HttpSession session = request.getSession();

        //获取请求的参数 商品编号
        int id = WebUtils.parseInt(request.getParameter("id"),0);
        try {
            //通过主键id获取图书对象:book
            TBook book = bookService.queryBookById(id);
            // 把图书信息，转换成为CartItem 商品项
            CartItem cartItem = new CartItem(book.getId(),book.getName(),1,book.getPrice(),book.getPrice());
            //从session会话作用域中去除cart,如果为null则表示购物车中无商品信息,否则有
            Cart cart = (Cart) session.getAttribute("cart");
            if(cart==null){
                cart = new Cart();//创建cart对象
                session.setAttribute("cart",cart);
            }
            //添加商品项
            cart.addItem(cartItem);
            System.out.println("cart = " + cart);
            System.out.println("请求头 Referer 的值： = " + request.getHeader("Referer"));//发完请求回到原页面
            session.setAttribute("lastName",book.getName());
            response.sendRedirect(request.getHeader("Referer"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     ● 删除商品项
     ● @param request
     ● @param response
     ● @throws ServletException
     ● @throws IOException
     */
    protected void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int id = WebUtils.parseInt(request.getParameter("id"),0);
        HttpSession session = request.getSession();
        //获取购物车对象
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart!=null){
            // 删除购物车商品项
            cart.deleteItem(id);
            // 重定向回原来购物车展示页面
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    /**
     ● 清空购物车
     ● @param request
     ● @param response
     ● @throws ServletException
     ● @throws IOException
     */
    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        //获取购物车对象
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart != null){
            // 调用clear方法, 清空购物车
            cart.clear();
            // 重定向回原来购物车展示页面
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    protected void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // 获取请求的参数 商品编号、商品数量
        int id = WebUtils.parseInt(request.getParameter("id"),0);
        int count = WebUtils.parseInt(request.getParameter("count"), 1);
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart != null){
            cart.updateCount(id,count);
        }
        // 重定向回原来购物车展示页面
        response.sendRedirect(request.getHeader("Referer"));
    }


}
