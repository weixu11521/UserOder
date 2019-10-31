package com.zydsj.userorder.user.mapper;

import com.zydsj.userorder.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User select(Integer id);
}
