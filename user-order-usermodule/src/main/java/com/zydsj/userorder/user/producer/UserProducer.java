package com.zydsj.userorder.user.producer;

import com.zydsj.userorder.po.Order;
import com.zydsj.userorder.po.OrderDetail;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserProducer {

    //注入rabbitTemplage对象，用来操作rabbitmq
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private RabbitTemplate.ConfirmCallback confirmCallback =new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.out.println("UserProducer ConfirmCallback+++++++++++++++++++++");
            System.out.println(correlationData.getId());
            //假如exchange返回的是ack是false，打印错误消息
            if(!ack){
                System.out.println("错误原因是："+cause);
            }
        }
    };

    private RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int errorCode, String errorCause, String exchangeName, String routingKey) {
            System.out.println("UserProducer returnCallBack==========================");
            System.out.println(message);
            System.out.println(errorCode);
            System.out.println(errorCause);
            System.out.println(exchangeName);
            System.out.println(routingKey);
        }
    };



    public void sendMap(Map<Integer,Integer> maps,Order order){
        //消息的唯一ID
        CorrelationData correlationDate = new CorrelationData(UUID.randomUUID().toString());
        //给消息生产者设置消息确认、返回的监听
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        //封装Order对象，以及该Order对象对应的OrderDetail对象
        order.setCreateTime(new Date());
        order.setStatus(0);
        List<OrderDetail> ods = new ArrayList<>();

        for (Map.Entry<Integer,Integer> entry : maps.entrySet()) {
            Integer key = entry.getKey();

            Integer value = entry.getValue();
            OrderDetail od = new OrderDetail();
            //还应该设置订单细节对象的 价格  ？？？？？？？？？？？？？

            od.setProduct_id(key);
            od.setNumber(value);
            ods.add(od);
        }
        //封装一个消息map：包含order对象和ods对象
        Map<String,Object> msg = new HashMap<>();
        msg.put("order",order);
        msg.put("ods",ods);

        rabbitTemplate.convertAndSend("user_order_exchange","user_order.create",msg,correlationDate);

    }

    

}
