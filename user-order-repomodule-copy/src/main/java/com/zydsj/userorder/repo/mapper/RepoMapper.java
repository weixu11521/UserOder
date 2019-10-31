package com.zydsj.userorder.repo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RepoMapper {

   Integer update(@Param("id") Integer id, @Param("number")Integer number);

   Double selectPrice(Integer id);
}
