package com.shi.junit;

import com.shi.dao.OrderItemDao;
import com.shi.dao.impl.OrderItemDaoImpl;
import com.shi.pojo.OrderItem;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author 千文sea
 * @create 2020-04-30 17:30
 */
public class OrderItemDaoTest {

    OrderItemDao orderItemDao = new OrderItemDaoImpl();

    @Test
    public void saveOrderItem() {
        orderItemDao.saveOrderItem(new OrderItem(null,"数据结构",2,new BigDecimal(80),new BigDecimal(160),"1000-1"));
    }
}