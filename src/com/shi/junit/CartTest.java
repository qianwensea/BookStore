package com.shi.junit;

import com.shi.pojo.Cart;
import com.shi.pojo.CartItems;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author 千文sea
 * @create 2020-04-30 14:40
 */
public class CartTest {

    Cart cart = new Cart();

    @Test
    public void addItem() {
        cart.addItem(new CartItems(1,"java核心技术",1,new BigDecimal(89),new BigDecimal(89)));
        cart.addItem(new CartItems(1,"java核心技术",1,new BigDecimal(89),new BigDecimal(89)));
        cart.addItem(new CartItems(2,"数据结构",1,new BigDecimal(19),new BigDecimal(19)));
        System.out.println(cart);
    }

    @Test
    public void deleteItem() {
        cart.addItem(new CartItems(1,"java核心技术",1,new BigDecimal(89),new BigDecimal(89)));
        cart.addItem(new CartItems(1,"java核心技术",1,new BigDecimal(89),new BigDecimal(89)));
        cart.addItem(new CartItems(2,"数据结构",1,new BigDecimal(19),new BigDecimal(19)));
        cart.deleteItem(1);
        System.out.println(cart);
    }

    @Test
    public void clear() {
        cart.addItem(new CartItems(1,"java核心技术",1,new BigDecimal(89),new BigDecimal(89)));
        cart.addItem(new CartItems(1,"java核心技术",1,new BigDecimal(89),new BigDecimal(89)));
        cart.addItem(new CartItems(2,"数据结构",1,new BigDecimal(19),new BigDecimal(19)));
        cart.clear();
        System.out.println(cart);
    }

    @Test
    public void updateCount() {
        cart.addItem(new CartItems(1,"java核心技术",1,new BigDecimal(89),new BigDecimal(89)));
        cart.addItem(new CartItems(1,"java核心技术",1,new BigDecimal(89),new BigDecimal(89)));
        cart.addItem(new CartItems(2,"数据结构",1,new BigDecimal(19),new BigDecimal(19)));
        System.out.println(cart);
        cart.updateCount(2,10);
        System.out.println(cart);
    }
}