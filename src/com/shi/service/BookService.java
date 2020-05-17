package com.shi.service;

import com.shi.pojo.Book;
import com.shi.pojo.Page;

import java.util.List;

/**
 * @author 千文sea
 * @create 2020-04-26 15:18
 */
public interface BookService {

    public void addBook(Book book);

    public void deleteBookById(Integer id);

    public void updateBook(Book book);

    public Book queryBookById(Integer id);

    public List<Book> queryBooks();

    public Page<Book> page(int pageNo, int pageSize);

    public Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max);
}
