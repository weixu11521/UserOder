package com.zydsj.userorder.order.consumer;

import com.rabbitmq.client.Channel;
import com.zydsj.userorder.order.biz.OrderBiz;
import com.zydsj.userorder.order.producer.OrderProducer;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderConsumer {

    //注入rabbitTemplage对象，用来操作rabbitmq
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private OrderBiz orderBiz;

    @RabbitListener(bindings = @QueueBinding(
            value=@Queue(value="user_order_queue"),
            exchange=@Exchange(value="user_order_exchange",type = "topic"),
            key = "user_order.#"
    ))
    @RabbitHandler
    public void userConsumer(@Payload Map<String,Object> msg, Channel channel, @Headers Map<String,Object> headers){
        //1 获取user模块传来的消息map
        Order order = (Order) msg.get("order");
        List<OrderDetail> ods = (List<OrderDetail>) msg.get("ods");
        order.setDescription("订单已提交");
        System.out.println("order消费端================================");
        System.out.println("order:"+order);
        System.out.println("ods:"+ods);
        //2 封装成相应的order和orderdetail对象
        Integer orderId = orderBiz.saveOrder(order);
        System.out.println("存储之后的order对象~~~~~~~~~~~~~~~~~~~~~："+order);
        //3 往Order以及OrderDetail表中添加新订单
        for (OrderDetail od: ods) {
            od.setOrder_id(orderId);
        }
        orderBiz.saveOrderDetail(ods);
        //4 给库存子系统发送该订单消息
        Map<Order,List<OrderDetail>> map = new HashMap<>();
        map.put(order,ods);
        orderProducer.sendMap(map);


        //5 返回ACK
        Long delivery_tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            channel.basicAck(delivery_tag,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

}
