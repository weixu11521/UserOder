package com.zydsj.userorder.user.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zydsj.userorder.feign.RepoFeignClient;
import com.zydsj.userorder.po.Order;
import com.zydsj.userorder.po.User;
import com.zydsj.userorder.user.biz.UserBiz;
import com.zydsj.userorder.user.producer.UserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserBiz userBiz;

    @Autowired(required = false)
    private RepoFeignClient repoFeignClient;

    @Autowired
    private UserProducer userProducer;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/findUser/{id}")
    public User findUser(@PathVariable(value="id") Integer id, HttpSession session){

        User user = userBiz.findUser(id);
        //把该user对象放到session中
        session.setAttribute("user",user);
        return user;
    }

    @GetMapping("/addOrder")
    public String addOrder(Order order,Integer[] ids,Integer[] nums,HttpSession session){
        System.out.println("UserController的addOrder方法：==========================");
        System.out.println("order:"+order);
        User user = getUser(session);
        order.setUser_id(user.getId());
        //创建商品详情Map，发送到消息队列上
        Map<Integer,Integer> details = new HashMap<>();
        for (int i = 0; i < ids.length; i++) {
            details.put(ids[i],nums[i]);
        }


        this.userProducer.sendMap(details,order);




        return "发送给Order模块的消息已经完成。。。。";
    }

//    @HystrixCommand(fallbackMethod="getPriceCallBack")
    @GetMapping("/getPrice/{pid}")
    public Double getPrice(@PathVariable("pid") Integer pid){
        //调用外部服务提供的方法，进行价格查询
//        String url = "http://localhost:8087/findPrice1/{id}";
//        String url = "http://USER-ORDER-REPOMODULE/findPrice/{id}";
        //使用feign调用
        Double price = this.repoFeignClient.findPrice(pid);
       // Double price = this.restTemplate.getForObject(url,Double.class,pid);
        return price;

    }

    //getPrice服务降级方法
//    public Double getPriceCallBack(Integer id){
//        return 0.0;
//    }

    private User getUser(HttpSession session){
        User user = (User) session.getAttribute("user");
        return user;
    }
}
