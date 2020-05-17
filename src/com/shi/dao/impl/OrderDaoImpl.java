package com.shi.dao.impl;

import com.shi.dao.OrderDao;
import com.shi.pojo.Order;

/**
 * @author 千文sea
 * @create 2020-04-30 17:20
 */
public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public int saveOrder(Order order) {
        String sql = "insert into t_order(order_id,create_time,price,status,user_id) values(?,?,?,?,?)";
        return update(sql,order.getOrderId(),order.getCreateTime(),order.getPrice(),order.getStatus(),order.getUserId());
    }
}
