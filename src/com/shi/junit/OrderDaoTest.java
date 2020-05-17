package com.shi.junit;

import com.shi.dao.OrderDao;
import com.shi.dao.impl.OrderDaoImpl;
import com.shi.pojo.Order;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author 千文sea
 * @create 2020-04-30 17:28
 */
public class OrderDaoTest {

    OrderDao orderDao = new OrderDaoImpl();

    @Test
    public void saveOrder() {
        orderDao.saveOrder(new Order("1000-1",new Date(),new BigDecimal(100),0,1));
    }
}