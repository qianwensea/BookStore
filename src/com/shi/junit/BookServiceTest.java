package com.shi.junit;

import com.shi.pojo.Book;
import com.shi.service.BookService;
import com.shi.service.impl.BookServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author 千文sea
 * @create 2020-04-26 15:23
 */
public class BookServiceTest {

    private BookService bookService = new BookServiceImpl();

    @Test
    public void addBook() {
        bookService.addBook(new Book(null,"数值分析","Wang",new BigDecimal(444.9),888,999,null));
    }

    @Test
    public void deleteBookById() {
        bookService.deleteBookById(22);
    }

    @Test
    public void updateBook() {
        bookService.updateBook(new Book(20,"数值分析","Wang",new BigDecimal(444.9),888,999,null));
    }

    @Test
    public void queryBookById() {
        System.out.println(bookService.queryBookById(20));
    }

    @Test
    public void queryBooks() {
        for (Book queryBook : bookService.queryBooks()) {
            System.out.println(queryBook);
        }
    }

    @Test
    public void page(){
        System.out.println(bookService.page(1, 4));
    }

    @Test
    public void pageByPrice(){
        System.out.println(bookService.pageByPrice(0,4,10,50));
    }
}