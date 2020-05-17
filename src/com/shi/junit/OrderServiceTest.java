package com.shi.junit;

import com.shi.pojo.Cart;
import com.shi.pojo.CartItems;
import com.shi.service.OrderService;
import com.shi.service.impl.OrderServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author 千文sea
 * @create 2020-04-30 17:47
 */
public class OrderServiceTest {

    OrderService orderService = new OrderServiceImpl();

    @Test
    public void createOder() {
        Cart cart = new Cart();
        cart.addItem(new CartItems(1,"java核心技术",1,new BigDecimal(89),new BigDecimal(89)));
        cart.addItem(new CartItems(1,"java核心技术",1,new BigDecimal(89),new BigDecimal(89)));
        cart.addItem(new CartItems(2,"数据结构",1,new BigDecimal(19),new BigDecimal(19)));

        System.out.println(orderService.createOder(cart, 1));
    }
}