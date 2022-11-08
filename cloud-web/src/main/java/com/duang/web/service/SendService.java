package com.duang.web.service;

import com.duang.cloud.api.domain.BatchSendRequest;
import com.duang.cloud.api.domain.SendRequest;
import com.duang.cloud.api.domain.SendResponse;

public interface SendService {

    /**
     * 单文案发送接口
     * @param sendRequest
     * @return
     */
    SendResponse send(SendRequest sendRequest);


    /**
     * 多文案发送接口
     * @param batchSendRequest
     * @return
     */
    SendResponse batchSend(BatchSendRequest batchSendRequest);

}
