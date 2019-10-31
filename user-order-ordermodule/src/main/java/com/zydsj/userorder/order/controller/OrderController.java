package com.zydsj.userorder.order.controller;

import com.zydsj.userorder.order.biz.OrderBiz;
import com.zydsj.userorder.po.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderBiz orderBiz;


    @GetMapping("/findOrders/{id}")
    public List<Order> findUser(@PathVariable(value="id") Integer userId){
        List<Order> all = orderBiz.findAll(userId);
        return all;
    }



}
