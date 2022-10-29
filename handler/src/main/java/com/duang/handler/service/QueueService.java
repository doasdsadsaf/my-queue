package com.duang.handler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duang.cloudcommons.entity.User;

public interface QueueService extends IService<User> {
    User get(Long id);
}
