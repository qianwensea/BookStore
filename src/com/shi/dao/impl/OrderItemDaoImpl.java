package com.shi.dao.impl;

import com.shi.dao.OrderItemDao;
import com.shi.pojo.OrderItem;

/**
 * @author 千文sea
 * @create 2020-04-30 17:25
 */
public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {

    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql = "insert into t_order_item(name,count,price,total_price,order_id) values(?,?,?,?,?)";
        return update(sql,orderItem.getName(),orderItem.getCount(),orderItem.getPrice(),orderItem.getTotalPrice(),orderItem.getOrderId());
    }
}
