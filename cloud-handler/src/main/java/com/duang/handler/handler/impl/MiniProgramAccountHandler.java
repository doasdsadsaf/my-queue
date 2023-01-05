package com.duang.handler.handler.impl;

import cn.binarywang.wx.miniapp.api.WxMaSubscribeService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import com.alibaba.fastjson.JSON;
import com.duang.cloudcommons.domain.TaskInfo;
import com.duang.cloudcommons.dto.model.MiniProgramContentModel;
import com.duang.cloudcommons.enums.ChannelType;
import com.duang.handler.handler.BaseHandler;
import com.duang.handler.handler.Handler;
import com.duang.support.support.domain.MessageTemplate;
import com.duang.support.support.utils.WxServiceUtils;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author sunql
 * 微信小程序发送订阅消息
 */
@Component
@Slf4j
public class MiniProgramAccountHandler extends BaseHandler implements Handler {
    @Autowired
    private WxServiceUtils wxServiceUtils;

    public MiniProgramAccountHandler() {
        channelCode = ChannelType.MINI_PROGRAM.getCode();
    }

    @Override
    public boolean handler(TaskInfo taskInfo) {
        MiniProgramContentModel contentModel = (MiniProgramContentModel) taskInfo.getContentModel();
        WxMaSubscribeService wxMaSubscribeService = wxServiceUtils.getMiniProgramServiceMap().get(taskInfo.getSendAccount().longValue());
        List<WxMaSubscribeMessage> wxMaSubscribeMessages = assembleReq(taskInfo.getReceiver(), contentModel);
        for (WxMaSubscribeMessage message : wxMaSubscribeMessages) {
            try {
                wxMaSubscribeService.sendSubscribeMsg(message);
            } catch (Exception e) {
                log.info("MiniProgramAccountHandler#handler fail! param:{},e:{}", JSON.toJSONString(taskInfo), Throwables.getStackTraceAsString(e));
            }
        }
        return true;
    }

    /**
     * 组装发送模板信息参数
     */
    private List<WxMaSubscribeMessage> assembleReq(Set<String> receiver, MiniProgramContentModel contentModel) {
        List<WxMaSubscribeMessage> messageList = new ArrayList<>(receiver.size());
        for (String openId : receiver) {
            WxMaSubscribeMessage subscribeMessage = WxMaSubscribeMessage.builder()
                    .toUser(openId)
                    .data(getWxMaTemplateData(contentModel.getMiniProgramParam()))
                    .templateId(contentModel.getTemplateId())
                    .page(contentModel.getPage())
                    .build();
            messageList.add(subscribeMessage);
        }
        return messageList;
    }

    /**
     * 构建订阅消息参数
     *
     * @returnp
     */
    private List<WxMaSubscribeMessage.MsgData> getWxMaTemplateData(Map<String, String> data) {
        List<WxMaSubscribeMessage.MsgData> templateDataList = new ArrayList<>(data.size());
        data.forEach((k, v) -> templateDataList.add(new WxMaSubscribeMessage.MsgData(k, v)));
        return templateDataList;
    }

    @Override
    public void recall(MessageTemplate messageTemplate) {

    }
}

