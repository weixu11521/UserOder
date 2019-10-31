package com.zydsj.userorder.order.biz;

import com.zydsj.userorder.po.Order;
import com.zydsj.userorder.po.OrderDetail;

import java.util.List;

public interface OrderBiz {

    Integer saveOrder(Order order);

    List<Order> findAll(Integer userId);

    Integer saveOrderDetail(List<OrderDetail> ods);
}
