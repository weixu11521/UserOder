package com.zydsj.userorder.repo.producer;

import com.zydsj.userorder.po.Order;
import com.zydsj.userorder.po.OrderDetail;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class RepoProducer {

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



    public void sendMap(Map<Order,List<OrderDetail>> maps){
        //消息的唯一ID
        CorrelationData correlationDate = new CorrelationData(UUID.randomUUID().toString());
        //给消息生产者设置消息确认、返回的监听
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.convertAndSend("order_product_exchange","order_product.create",maps,correlationDate);

    }

    

}
