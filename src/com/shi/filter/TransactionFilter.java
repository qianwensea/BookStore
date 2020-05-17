package com.shi.filter;

import com.shi.utils.JdbcUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author 千文sea
 * @create 2020-05-02 19:24
 */
public class TransactionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest,servletResponse);
            JdbcUtils.commitAndClose();//提交事务
        } catch (Exception e){
            JdbcUtils.rollbackAndClose();//回滚事务
            e.printStackTrace();
            throw new RuntimeException(e);//把异常统一的抛给Tomcat服务器管理展示友好的错误界面
        }
    }
}
