package com.zydsj.userorder.repo.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.zydsj.userorder.order.biz.RepoBiz;
import com.zydsj.userorder.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class RepoController {

    @Autowired
    private RepoBiz repoBiz;

    @GetMapping("/findPrice/{id}")
    public Double findPrice(@PathVariable(value="id") Integer id, HttpSession session){
        System.out.println("repo-copy======================");
        Double price = repoBiz.findPrice(id);
        return price;
    }



    private User getUser(HttpSession session){
        User user = (User) session.getAttribute("user");
        return user;
    }
}
