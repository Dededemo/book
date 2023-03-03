package com.hrj.setvlet;

import com.hrj.bean.Student;
import com.hrj.bean.User;
import com.hrj.service.UserService;
import com.hrj.service.impl.UserServiceImpl;
import com.hrj.utils.BaseServlet;
import com.hrj.utils.CookieUtils;
import com.hrj.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//静态导入
import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;


@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends BaseServlet {

    protected void stutest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        List<Student> students = new ArrayList<Student>();
        for (int i = 0; i < 10; i++) {
            int t = i + 1;
            students.add(new Student(i,"小王","123456",i+10,"123456789"));
        }

        req.setAttribute("studentList",students);

        req.getRequestDispatcher("showStudent.jsp").forward(req,resp);

    }

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.setCharacterEncoding("UTF-8");//解决post乱码问题

        //不用自己手动一个个获取用户提交上来的参数了
        //String username = request.getParameter("username");
        //String password = request.getParameter("password");
        /*for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue()[0]);
        }*/
        Map<String, String[]> parameterMap = request.getParameterMap();

        User user = new User();
        WebUtils.copyParamToBean(parameterMap,user);

        UserService userService = new UserServiceImpl();
        //User user = new User(null,username,password,null);
        try {
//        System.out.println(userService.login(user));
            User myuser = userService.login(user);
            if(myuser!=null){
                Cookie namecookie = new Cookie("username",myuser.getUsername());
                Cookie passcookie = new Cookie("password",myuser.getPassword());
                namecookie.setMaxAge(60*60*24*7);
                passcookie.setMaxAge(60*60*24*7);

                response.addCookie(namecookie);
                response.addCookie(passcookie);

                HttpSession session = request.getSession();//会话作用域
                session.setAttribute("user",myuser);//用户登录成功后的个人信息保存在sessio会话作用域中
                request.setAttribute("username",user.getUsername());
                request.setAttribute("msg","欢迎回来");
                if(request.getParameter("hrjurl")!=null && !request.getParameter("hrjurl").equals("")){
                    request.getRequestDispatcher(request.getParameter("hrjurl")).forward(request,response);
                }else {
                    request.getRequestDispatcher("/pages/user/success.jsp").forward(request,response);

                }

            }else {
                request.setAttribute("msg","账号名或登陆密码不正确!");
                request.setAttribute("username",user.getUsername());
                request.setAttribute("password",user.getPassword());
                request.getRequestDispatcher("/pages/user/login.jsp").forward(request,response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");


        request.setAttribute("username",username);
        request.setAttribute("password",password);
        request.setAttribute("email",email);
        request.setAttribute("code",code);
        */
        HttpSession session = request.getSession();
        // 获取Session 中的验证码
        String token = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        // 删除 Session 中的验证码
        request.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        String code = request.getParameter("code");//验证码

        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        WebUtils.copyParamToBean(parameterMap,user);
        request.setAttribute("user",user);

        UserService userService = new UserServiceImpl();
        try {
            if (token.equalsIgnoreCase(code)) {
                if (!userService.existsUsername(user.getUsername())) {
                    //User user = new User(null, username, password, email);
                    userService.registUser(user);
                    session = request.getSession();
                    session.setAttribute("user",user);//session请求域与request请求域同名的话在jsp界面加上requestScope可以解决登陆后注册界面出现信息,或者两个域使用不同名
                    //request.setAttribute("username",username);
                    request.setAttribute("msg","注册成功!");
                    request.getRequestDispatcher("/pages/user/success.jsp").forward(request, response);
                } else {
                    request.setAttribute("msg","用户名已存在请更换");
                    request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("msg","验证码不正确");
                request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
//                response.sendRedirect("pages/user/regist.jsp");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();//session立刻失效

        Cookie nameCookie = CookieUtils.findCookie("username", request.getCookies());
        Cookie passCookie = CookieUtils.findCookie("password", request.getCookies());

        if(nameCookie!=null){
            nameCookie.setMaxAge(0);//立刻失效
            response.addCookie(nameCookie);
        }
        if(passCookie!=null){
            passCookie.setMaxAge(0);//立刻失效
            response.addCookie(passCookie);
        }

        response.sendRedirect("index.jsp");
    }

}
