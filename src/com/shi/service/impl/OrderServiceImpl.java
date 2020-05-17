package com.shi.service.impl;

import com.shi.dao.BookDao;
import com.shi.dao.OrderDao;
import com.shi.dao.OrderItemDao;
import com.shi.dao.impl.BookDaoImpl;
import com.shi.dao.impl.OrderDaoImpl;
import com.shi.dao.impl.OrderItemDaoImpl;
import com.shi.pojo.*;
import com.shi.service.OrderService;

import java.util.Date;
import java.util.Map;

/**
 * @author 千文sea
 * @create 2020-04-30 17:36
 */
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookdao = new BookDaoImpl();

    @Override
    public String createOder(Cart cart, Integer userId) {
        // 订单号 === 要有唯一性
        String orderId = System.currentTimeMillis() + "-" + userId;
        //创建一个订单对象
        Order order = new Order(orderId,new Date(),cart.getTotalPrice(),0,userId);
        //保存订单
        orderDao.saveOrder(order);

        //遍历购物车中的每一个商品项转换为订单项保存到数据库
        for (Map.Entry<Integer, CartItems> entry : cart.getItems().entrySet()){
            //获取每一个购物车中的商品项
            CartItems cartItem = entry.getValue();
            //转换为每一个订单项
            OrderItem orderItem = new OrderItem(null,cartItem.getName(),cartItem.getCount(),cartItem.getPrice(),cartItem.getTotalPrice(),orderId);
            //保存订单项到数据库
            orderItemDao.saveOrderItem(orderItem);

            int i = 10/0;

            //更新book的销量和库存
            Book book = bookdao.queryBookById(cartItem.getId());
            book.setSales(book.getSales() + cartItem.getCount());
            book.setStock(book.getStock() - cartItem.getCount());
            bookdao.updateBook(book);
        }
        cart.clear();
        return orderId;
    }
}
