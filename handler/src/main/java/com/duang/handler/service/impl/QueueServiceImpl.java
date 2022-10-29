package com.duang.handler.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duang.cloudcommons.entity.User;
import com.duang.handler.mapper.QueueMapper;
import com.duang.handler.service.QueueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QueueServiceImpl extends ServiceImpl<QueueMapper,User> implements QueueService {

    @Resource
    private QueueMapper queueMapper;
    @Override
    public User get(Long id) {
        return queueMapper.get(id);
    }
}
