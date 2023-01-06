package com.duang.handler.pending;


import com.duang.handler.config.HandlerThreadPoolConfig;
import com.duang.handler.utils.GroupIdMappingUtils;
import com.duang.support.support.utils.ThreadPoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 存储 每种消息类型 与 TaskPending 的关系
 *
 * @author 3y
 */
@Component
public class TaskPendingHolder {
    @Autowired
    private ThreadPoolUtils threadPoolUtils;

    private Map<String, ThreadPoolTaskExecutor> taskPendingHolder = new HashMap<>(32);

    /**
     * 获取得到所有的groupId
     */
    private static List<String> groupIds = GroupIdMappingUtils.getAllGroupIds();

    /**
     * 给每个渠道，每种消息类型初始化一个线程池
     */
    @PostConstruct
    public void init() {
        /**
         * example ThreadPoolName:austin.im.notice
         *
         * 可以通过apollo配置：dynamic-tp-apollo-dtp.yml  动态修改线程池的信息
         */
        for (String groupId : groupIds) {
            ThreadPoolTaskExecutor executor = HandlerThreadPoolConfig.getExecutor(groupId);
            threadPoolUtils.register(executor);

            taskPendingHolder.put(groupId, executor);
        }
    }

    /**
     * 得到对应的线程池
     *
     * @param groupId
     * @return
     */
    public ThreadPoolTaskExecutor route(String groupId) {
        return taskPendingHolder.get(groupId);
    }


}
