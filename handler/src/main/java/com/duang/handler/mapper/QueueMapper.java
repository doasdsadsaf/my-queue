package com.duang.handler.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duang.cloudcommons.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;



@Mapper
public interface QueueMapper extends BaseMapper<User> {

    User get(@Param("id") Long id);
}
