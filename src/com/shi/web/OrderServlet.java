package com.shi.web;

import com.shi.pojo.Cart;
import com.shi.pojo.User;
import com.shi.service.OrderService;
import com.shi.service.impl.OrderServiceImpl;
import com.shi.utils.JdbcUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 千文sea
 * @create 2020-04-30 17:51
 */
public class OrderServlet extends BaseServlet{

    OrderService orderService = new OrderServiceImpl();

    /**
     * 生成订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //先获取cart购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        //获取用户id;
        User loginUser = (User) req.getSession().getAttribute("user");

        if (loginUser == null){
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
            return;
        }
        Integer id = loginUser.getId();

        //生成订单
        String orderId = orderService.createOder(cart, id);

        req.getSession().setAttribute("orderId",orderId);

        //重定向到/pages/cart/checkout.jsp
//        req.getRequestDispatcher("/pages/cart/checkout.jsp").forward(req,resp);
        resp.sendRedirect(req.getContextPath() + "/pages/cart/checkout.jsp");
    }
}
