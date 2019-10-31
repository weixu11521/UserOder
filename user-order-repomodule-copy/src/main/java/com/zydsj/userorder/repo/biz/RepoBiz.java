package com.zydsj.userorder.order.biz;

import com.zydsj.userorder.po.OrderDetail;

import java.util.List;

public interface RepoBiz {

    Double findPrice(Integer id);
    Integer modifyProductNumber(List<OrderDetail> ods);
}
