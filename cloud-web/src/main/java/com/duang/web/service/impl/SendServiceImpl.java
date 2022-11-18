package com.duang.web.service.impl;

import cn.monitor4all.logRecord.annotation.OperationLog;
import com.duang.cloud.api.domain.BatchSendRequest;
import com.duang.cloud.api.domain.SendRequest;
import com.duang.cloud.api.domain.SendResponse;
import com.duang.cloud.api.domain.SendTaskModel;
import com.duang.cloudcommons.vo.BasicResultVO;
import com.duang.support.support.pipeline.ProcessContext;
import com.duang.support.support.pipeline.ProcessController;
import com.duang.web.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 发送接口
 *
 * @author 3y
 */
@Service
public class SendServiceImpl implements SendService {

    @Autowired
    private ProcessController processController;

    /**
     * 通过 @Bean注解,项目启动时就按发送消息类型和撤销消息类型 把对应的实现子类注入到Map<Stirng,List>里去,再循环这个list
     * 按顺序去执行他的每一个方法,这就是责任链 preParamCheckAction, assembleAction,
     *                 afterParamCheckAction, sendMqAction
     * @param sendRequest
     * @return
     */
    @Override
    @OperationLog(bizType = "SendService#send", bizId = "#sendRequest.messageTemplateId", msg = "#sendRequest")
    public SendResponse send(SendRequest sendRequest) {
        // 把消息相关参数set出来 模板id 收消息人等等
        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(sendRequest.getMessageTemplateId())
                .messageParamList(Collections.singletonList(sendRequest.getMessageParam()))
                .build();
        // 把消息参数放到责任链上下文里
        ProcessContext context = ProcessContext.builder()
                .code(sendRequest.getCode())
                .processModel(sendTaskModel)
                .needBreak(false)
                .response(BasicResultVO.success()).build();

        ProcessContext process = processController.process(context);

        return new SendResponse(process.getResponse().getStatus(), process.getResponse().getMsg());
    }

    @Override
    @OperationLog(bizType = "SendService#batchSend", bizId = "#batchSendRequest.messageTemplateId", msg = "#batchSendRequest")
    public SendResponse batchSend(BatchSendRequest batchSendRequest) {
        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(batchSendRequest.getMessageTemplateId())
                .messageParamList(batchSendRequest.getMessageParamList())
                .build();
        ProcessContext context = ProcessContext.builder()
                .code(batchSendRequest.getCode())
                .processModel(sendTaskModel)
                .needBreak(false)
                .response(BasicResultVO.success()).build();

        ProcessContext process = processController.process(context);

        return new SendResponse(process.getResponse().getStatus(), process.getResponse().getMsg());
    }


}
