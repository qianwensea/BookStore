package com.shi.dao;

import com.shi.dao.impl.BaseDao;
import com.shi.pojo.Book;

import java.util.List;

/**
 * @author 千文sea
 * @create 2020-04-25 17:34
 */
public interface BookDao{

    int addBook(Book book);

    int deleteBookById(Integer id);

    int updateBook(Book book);

    Book queryBookById(Integer id);

    List<Book> queryBooks();

    Integer queryForPageTotalCount();

    List<Book> queryForPageItems(int begin, int pageSize);

    Integer queryForPageTotalCountByPrice(int min, int max);

    List<Book> queryForPageItemsByPrice(int begin, int pageSize, int min, int max);
}
