package com.duang.cloud.api.service;


import com.duang.cloud.api.domain.SendRequest;
import com.duang.cloud.api.domain.SendResponse;

/**
 * 撤回接口
 *
 * @author 3y
 */
public interface RecallService {


    /**
     * 根据模板ID撤回消息
     *
     * @param sendRequest
     * @return
     */
    SendResponse recall(SendRequest sendRequest);
}
