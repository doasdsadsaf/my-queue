package com.duang.handler.shield;

import com.duang.cloudcommons.domain.TaskInfo;

/**
 * 屏蔽服务
 *
 * @author 3y
 */
public interface ShieldService {


    /**
     * 屏蔽消息
     * @param taskInfo
     */
    void shield(TaskInfo taskInfo);
}
