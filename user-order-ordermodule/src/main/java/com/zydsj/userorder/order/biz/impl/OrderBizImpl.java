package com.zydsj.userorder.order.biz.impl;

import com.zydsj.userorder.order.biz.OrderBiz;
import com.zydsj.userorder.order.mapper.OrderMapper;
import com.zydsj.userorder.po.Order;
import com.zydsj.userorder.po.OrderDetail;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderBizImpl implements OrderBiz {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public Integer saveOrder(Order order) {
        return orderMapper.insert(order);
    }

    @Override
    @Cacheable(value = "order", key = "#p0")
    public List<Order> findAll(Integer userId) {
        System.out.println("调用了select方法做查询order。。。。。。。。。。");
        return orderMapper.select(userId);
    }

    @Override
    public Integer saveOrderDetail(List<OrderDetail> ods) {
        return orderMapper.insertBatch(ods);
    }
}
