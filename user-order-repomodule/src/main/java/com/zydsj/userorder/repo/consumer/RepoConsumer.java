package com.zydsj.userorder.repo.consumer;

import com.rabbitmq.client.Channel;
import com.zydsj.userorder.order.biz.RepoBiz;
import com.zydsj.userorder.po.Order;
import com.zydsj.userorder.po.OrderDetail;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class RepoConsumer {

    //注入rabbitTemplage对象，用来操作rabbitmq
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RepoBiz repoBiz;


    @RabbitListener(bindings = @QueueBinding(
            value=@Queue(value="order_product_queue"),
            exchange=@Exchange(value="order_product_exchange",type = "topic"),
            key = "order_product.#"
    ))
    @RabbitHandler
    public void repoConsumer(@Payload Map<Order,List<OrderDetail>> msg, Channel channel, @Headers Map<String,Object> headers){
        //1 获取order模块传来的消息map
        Order order = null;
        List<OrderDetail> ods = null;
        for (Map.Entry<Order, List<OrderDetail>> entry : msg.entrySet()) {
            order = entry.getKey();
            ods = entry.getValue();
        }

        System.out.println("repo模块接收到的order："+order);
        System.out.println("repo模块接收到的ods："+ods);

        //2 修改本订单相关产品的数量
        repoBiz.modifyProductNumber(ods);

        //3 修改order状态为：status为1  description 商品已出库 ？？？？？？？？

        //5 返回ACK
        Long delivery_tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            channel.basicAck(delivery_tag,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

}
