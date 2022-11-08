package com.duang.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duang.cloudcommons.entity.Order;
import com.duang.cloudcommons.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    Order get(@Param("id") Long id);
}
