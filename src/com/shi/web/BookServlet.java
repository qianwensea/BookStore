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
import java.util.List;

/**
 * @author 千文sea
 * @create 2020-04-26 15:30
 */
public class BookServlet extends BaseServlet {

    private BookService bookService = new BookServiceImpl();

    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"),0) + 1;
        //1.获取请求的参数,封装成Book对象
        Book book = WebUtils.copyParamBean(request.getParameterMap(), new Book());
        //2.调用bookService.addBook()保存图书
        bookService.addBook(book);
        //3.跳到图书列表页面  /manager/bookServlet?action=list
//        request.getRequestDispatcher("/manager/bookServlet?action=list").forward(request,response);
//        当用户提交完请求，浏览器会记录下最后一次请求的全部信息。当用户按下功能键 F5，就会发起浏览器记录的最后一次请求。
        System.out.println(request.getParameter("pageNo"));
        response.sendRedirect(request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + pageNo);
    }

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
        page.setUrl("manager/bookServlet?action=page");

        //3.保存page对象到request域中
        request.setAttribute("page",page);

        //4.跳到图书列表页面  /pages/manager/book_manager.jsp页面
        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request,response);
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求参数id
        int id = WebUtils.parseInt(request.getParameter("id"),0);
        //2.调用bookService.deleteBookById()  删除图书
        bookService.deleteBookById(id);
        //3.重定向回 /manager/bookServlet?action=list
        response.sendRedirect(request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + request.getParameter("pageNo"));
    }

    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求的参数,封装成book对象
        Book book = WebUtils.copyParamBean(request.getParameterMap(), new Book());
        //2.调用bookService.updateBook() 修改图书
        bookService.updateBook(book);
        //3.重定向回 /manager/bookServlet?action=list
        response.sendRedirect(request.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + request.getParameter("pageNo"));
    }

    protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.通过bookService查询图书信息
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        Book book = bookService.queryBookById(id);
        //2.将图书信息保存到request域中
        request.setAttribute("book",book);
        //3.请求转发的/pages/manager/book_edit.jsp页面
        request.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(request,response);
    }

    @Deprecated
    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.通过bookService查询全部图书
        List<Book> books = bookService.queryBooks();
        //2.把全部图书保存到request域中
        request.setAttribute("books",books);
        //3.请求转发到/pages/manager/book_manager.jsp页面
        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request,response);
    }
}
