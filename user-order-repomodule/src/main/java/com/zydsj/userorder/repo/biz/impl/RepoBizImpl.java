package com.zydsj.userorder.order.biz.impl;

import com.zydsj.userorder.order.biz.RepoBiz;
import com.zydsj.userorder.po.OrderDetail;
import com.zydsj.userorder.repo.mapper.RepoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RepoBizImpl implements RepoBiz {

    @Resource
    private RepoMapper orderMapper;

    @Override
    public Double findPrice(Integer id) {
        return orderMapper.selectPrice(id);
    }

    @Override
    public Integer modifyProductNumber(List<OrderDetail> ods) {
        for (OrderDetail od : ods) {
            orderMapper.update(od.getProduct_id(),od.getNumber());
        }
        return null;
    }
}
