package com.zydsj.userorder.order.mapper;

import com.zydsj.userorder.po.Order;
import com.zydsj.userorder.po.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    List<Order> select(Integer id);

    Integer insert(Order order);

    Integer insertBatch(List<OrderDetail> ods);
}
