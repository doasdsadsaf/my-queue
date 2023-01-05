package com.duang.handler.flowcontrol.impl;

import com.duang.cloudcommons.domain.TaskInfo;
import com.duang.handler.enums.RateLimitStrategy;
import com.duang.handler.flowcontrol.FlowControlParam;
import com.duang.handler.flowcontrol.FlowControlService;
import com.duang.handler.flowcontrol.annotations.LocalRateLimit;
import com.google.common.util.concurrent.RateLimiter;


/**
 * Created by TOM
 * On 2022/7/21 17:05
 * @author TOM
 */
@LocalRateLimit(rateLimitStrategy = RateLimitStrategy.REQUEST_RATE_LIMIT)
public class RequestRateLimitServiceImpl implements FlowControlService {

  /**
   * 根据渠道进行流量控制
   *
   * @param taskInfo
   * @param flowControlParam
   */
  @Override
  public Double flowControl(TaskInfo taskInfo, FlowControlParam flowControlParam) {
    RateLimiter rateLimiter = flowControlParam.getRateLimiter();
    return rateLimiter.acquire(1);
  }
}
