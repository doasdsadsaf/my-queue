package com.duang.cloudcommons.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("order")
public class Order {
    private Long id;

    private String name;
}
