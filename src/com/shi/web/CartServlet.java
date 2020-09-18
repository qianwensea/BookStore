package com.shi.web;

import com.google.gson.Gson;
import com.shi.pojo.Book;
import com.shi.pojo.Cart;
import com.shi.pojo.CartItems;
import com.shi.service.BookService;
import com.shi.service.impl.BookServiceImpl;
import com.shi.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 千文sea
 * @create 2020-04-30 14:58
 */
public class CartServlet extends BaseServlet {

    private BookService bookService = new BookServiceImpl();

    /**
     * 加入购物车
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Deprecated
    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取请求的参数商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //调用bookService.queryBookById(id) :book
        Book book = bookService.queryBookById(id);
        // 把图书信息,转换成cartItem商品项
        CartItems cartItem = new CartItems(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        //调用Cart.addItem(cartItem);添加商品项
        //将购物车信息保存到session中
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            req.getSession().setAttribute("cart", cart);
        }
        cart.addItem(cartItem);

        req.getSession().setAttribute("lastName",cartItem.getName());

        //重定向回商品列表页面
        //在http协议中有一个请求头,叫Refer,它可以把浏览器的地址栏的地址发给服务器
        resp.sendRedirect(req.getHeader("Referer"));
    }

    /**
     * 使用ajax处理加入购物车
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxAddItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //调用bookService.queryBookById(id) :book
        Book book = bookService.queryBookById(id);
        // 把图书信息,转换成cartItem商品项
        CartItems cartItem = new CartItems(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        //调用Cart.addItem(cartItem);添加商品项
        //将购物车信息保存到session中
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            req.getSession().setAttribute("cart", cart);
        }
        cart.addItem(cartItem);

        req.getSession().setAttribute("lastName",cartItem.getName());

        //6、返回购物车总的商品数量和最后一个添加的商品名称
        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("totalCount",cart.getTotalCount());
        resultMap.put("lastName",cartItem.getName());
        Gson gson = new Gson();
        String resultMapToJson = gson.toJson(resultMap);
        resp.getWriter().write(resultMapToJson);

    }

    /**
     * 删除商品项
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数id
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        //获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null) {
            //删除了购物车商品项
            cart.deleteItem(id);
            //重定向回原来的页面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    /**
     * 清空购物车
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void cleanCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null) {
            // 清空购物车
            cart.clear();
            // 重定向回原来购物车展示页面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    protected void updateCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数,商品数量和编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        int count = WebUtils.parseInt(req.getParameter("count"), 0);
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null) {
            cart.updateCount(id,count);
            // 重定向回原来购物车展示页面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

}
