package com.zydsj.userorder.user.biz.impl;

import com.zydsj.userorder.po.User;
import com.zydsj.userorder.user.biz.UserBiz;
import com.zydsj.userorder.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserBizImpl implements UserBiz {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findUser(Integer id) {
        return userMapper.select(id);
    }
}
