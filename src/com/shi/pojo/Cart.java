package com.shi.pojo;

import java.math.BigDecimal;
import java.util.*;

/**
 * 购物车对象
 * @author 千文sea
 * @create 2020-04-30 14:19
 */
public class Cart {
//    private Integer totalCount;
//    private BigDecimal totalPrice;
    /**
     * key 是商品编号
     * value 是商品信息
     */
    private Map<Integer,CartItems> items = new LinkedHashMap<>();

    /**
     * 添加商品项
     * @param cartItem
     */
    public void addItem(CartItems cartItem){
        //先查看购物车中是否添加过此商品,如果已添加,则数量累加,总金额更新,如果没有添加过,直接放到集合中即可
        CartItems item = this.items.get(cartItem.getId());
        if (item == null){
            //之前没有添加过此商品
            items.put(cartItem.getId(),cartItem);
        } else {
            //已经添加过
            item.setCount(item.getCount() + 1);
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }

    }

    /**
     * 删除商品项
     * @param id
     */
    public void deleteItem(Integer id){
        items.remove(id);
    }

    /**
     * 清空购物车
     */
    public void clear(){
        items.clear();
    }

    /**
     * 修改商品数量
     * @param id
     * @param count
     */
    public void updateCount(Integer id,Integer count){
        //先查看购物车中是否有此商品,如果有更新数量和总金额
        CartItems cartItem = this.items.get(id);
        if (cartItem != null){
            //已经添加过
            cartItem.setCount(count);
            cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())));
        }
    }



    public Integer getTotalCount() {
        Integer totalCount = 0;
        for (Map.Entry<Integer,CartItems> item : items.entrySet()){
            totalCount += item.getValue().getCount();
        }
        return totalCount;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<Integer,CartItems> item : items.entrySet()){
            totalPrice = totalPrice.add(item.getValue().getTotalPrice());
        }
        return totalPrice;
    }

    public Map<Integer, CartItems> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItems> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }
}
