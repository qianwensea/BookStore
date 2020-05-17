package com.shi.web;

import com.shi.pojo.Book;
import com.shi.pojo.Page;
import com.shi.service.BookService;
import com.shi.service.impl.BookServiceImpl;
import com.shi.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 千文sea
 * @create 2020-04-27 17:34
 */
public class ClientBookServlet extends BaseServlet{

    BookService bookService = new BookServiceImpl();

    /**
     * 处理分页的功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取请求的参数 pageNo 和 PageSize
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);

        //2.调用bookService.page(pageNo,pageSize) : 返回page对象
        Page<Book> page = bookService.page(pageNo,pageSize);
        page.setUrl("client/bookServlet?action=page");

        //3.保存page对象到request域中
        request.setAttribute("page",page);

        //4.跳到图书列表页面  /pages/manager/book_manager.jsp页面
        request.getRequestDispatcher("/pages/client/index.jsp").forward(request,response);
    }

    /**
     * 根据价格区间处理分页的功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void pageByPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取请求的参数 pageNo 和 PageSize
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        int min = WebUtils.parseInt(request.getParameter("min"),0);
        int max = WebUtils.parseInt(request.getParameter("max"),Integer.MAX_VALUE);

        //2.调用bookService.pageByPrice(pageNo,pageSize) : 返回page对象
        Page<Book> page = bookService.pageByPrice(pageNo,pageSize,min,max);

        StringBuilder str = new StringBuilder("client/bookServlet?action=pageByPrice");
        //如果有最小价格参数,追加到分页条参数中
        if (request.getParameter("min") != null){
            str.append("&min=").append(request.getParameter("min"));
        }
        //如果有最大价格参数,追加到分页条参数中
        if (request.getParameter("max") != null){
            str.append("&max=").append(request.getParameter("max"));
        }
        page.setUrl(str.toString());

        //3.保存page对象到request域中
        request.setAttribute("page",page);

        //4.跳到图书列表页面  /pages/manager/book_manager.jsp页面
        request.getRequestDispatcher("/pages/client/index.jsp").forward(request,response);
    }
}
