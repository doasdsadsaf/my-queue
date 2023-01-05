package com.duang.handler.flowcontrol;

import com.duang.cloudcommons.domain.TaskInfo;

/**
 * @author 3y
 * 流量控制服务
 */
public interface FlowControlService {


    /**
     * 根据渠道进行流量控制
     *
     * @param taskInfo
     * @param flowControlParam
     * @return 耗费的时间
     */
    Double flowControl(TaskInfo taskInfo, FlowControlParam flowControlParam);

}
