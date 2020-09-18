package com.shi.web;

import com.google.gson.Gson;
import com.shi.pojo.User;
import com.shi.service.UserService;
import com.shi.service.impl.UserServicImpl;
import com.shi.utils.WebUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * @author 千文sea
 * @create 2020-04-23 20:22
 */
public class UserServlet extends BaseServlet {

    private UserService userService = new UserServicImpl();

    /**
     * 处理注册的功能
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取session中的验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        //删除session中的验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        //1.获取请请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        User user = WebUtils.copyParamBean(req.getParameterMap(), new User());

        //2.检查验证码是否正确  --- 使用谷歌验证码
        if (token == null || !token.equalsIgnoreCase(code)) {
            //把回显信息保存在request域中
            req.setAttribute("msg", "验证码错误");
            req.setAttribute("username", username);
            req.setAttribute("email", email);

            //不正确,跳回注册页面
            System.out.println("验证码[" + code + "]错误");
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
        } else {
            //3.检查用户名是否正确
            if (userService.exitsUsername(username)) {
                //把回显信息保存在request域中
                req.setAttribute("msg", "用户名已存在");
                req.setAttribute("username", username);
                req.setAttribute("email", email);

                //不可用,跳回注册页面
                System.out.println("用户名[" + username + "]已存在!");
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
            } else {
                //可用,将信息保存到数据库
                userService.registUser(new User(null, username, password, email));
                // 跳到注册成功页面 regist_success.jsp
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
            }
        }
    }

    /**
     * 处理登录的功能
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取输入信息
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User loginUser = userService.login(new User(null, username, password, null));
        if (loginUser == null) {
            //用户名或密码错误
//            System.out.println("用户名或密码错误!");
            //把错误信息,和回显表单项信息,保存到request域中
            req.setAttribute("msg", "用户名或密码错误");
            req.setAttribute("username", username);
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        } else {
            //保存用户登录之后的信息到session中
            req.getSession().setAttribute("user", loginUser);
            //登录成功,跳转到登录成功页面
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req, resp);
        }
    }

    /**
     * 注销登录
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.销毁sessio中的用户登录信息(或销毁session)
        req.getSession().invalidate();
        //2.重定向到首页
        resp.sendRedirect(req.getContextPath());
    }

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxExistsUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数username
        String username = req.getParameter("username");
        //调用UserService.exitsUsername
        boolean exitsUsername = userService.exitsUsername(username);
        //把返回的结果封装成为map对象
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("exitsUsername",exitsUsername);
        Gson gson = new Gson();
        String json = gson.toJson(resultMap);
        resp.getWriter().write(json);
    }
}
