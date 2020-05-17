package com.shi.web;

import com.shi.pojo.User;
import com.shi.service.UserService;
import com.shi.service.impl.UserServicImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 千文sea
 * @create 2020-04-19 19:31
 */
@Deprecated
public class LoginServlet extends HttpServlet {

    private UserService userService = new UserServicImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取输入信息
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (userService.login(new User(null,username,password,null)) == null){
            //用户名或密码错误
//            System.out.println("用户名或密码错误!");
            //把错误信息,和回显表单项信息,保存到request域中
            request.setAttribute("msg","用户名或密码错误");
            request.setAttribute("username",username);
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request,response);
        }else {
            //登录成功,跳转到登录成功页面
            request.getRequestDispatcher("/pages/user/login_success.jsp").forward(request,response);
        }
    }

//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
}
