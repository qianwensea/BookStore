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
 * @create 2020-04-19 18:33
 */
@Deprecated
public class RegistServlet extends HttpServlet {

    private UserService userService = new UserServicImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        //2.检查验证码是否正确  --- 暂时写死,要求为:abcde
        if (!"abcde".equalsIgnoreCase(code)){
            //把回显信息保存在request域中
            req.setAttribute("msg","验证码错误");
            req.setAttribute("username",username);
            req.setAttribute("email",email);

            //不正确,跳回注册页面
            System.out.println("验证码[" + code + "]错误");
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
        }else{
            //3.检查用户名是否正确
            if (userService.exitsUsername(username)){
                //把回显信息保存在request域中
                req.setAttribute("msg","用户名已存在");
                req.setAttribute("username",username);
                req.setAttribute("email",email);

                //不可用,跳回注册页面
                System.out.println("用户名[" + username + "]已存在!");
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
            }else{
                //可用,将信息保存到数据库
                userService.registUser(new User(null,username,password,email));
                // 跳到注册成功页面 regist_success.jsp
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
            }
        }
    }
}
