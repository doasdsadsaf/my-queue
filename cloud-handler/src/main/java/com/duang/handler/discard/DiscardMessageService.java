package com.duang.handler.discard;

import com.alibaba.fastjson.JSONArray;
import com.duang.cloudcommons.constant.SendAccountConstant;
import com.duang.cloudcommons.domain.AnchorInfo;
import com.duang.cloudcommons.domain.TaskInfo;
import com.duang.cloudcommons.enums.AnchorState;
import com.duang.support.support.domain.MessageTemplate;
import com.duang.support.support.mapper.MessageTemplateMapper;
import com.duang.support.support.utils.LogUtils;
import com.duang.support.support.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 丢弃模板消息
 * @author 3y.
 */
@Service
public class DiscardMessageService {
    private static final String DISCARD_MESSAGE_KEY = "discardMsgIds";

    @Autowired
    private LogUtils logUtils;

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private MessageTemplateMapper messageTemplateMapper;
    

    /**
     * 丢弃消息，配置在apollo  改成从redis里拿 如果redis没有去数据库里查
     * @param taskInfo
     * @return
     */
    public boolean isDiscard(TaskInfo taskInfo) {
        // 配置示例:	["1","2"]
        // JSONArray array = JSON.parseArray(config.getProperty(DISCARD_MESSAGE_KEY, CommonConstant.EMPTY_VALUE_JSON_ARRAY));

        String template = redisUtils.get(SendAccountConstant.MESSAGE_TEMPLATE);
        if(StringUtils.isNotEmpty(template)){
            List<Long> ids = JSONArray.parseArray(template, Long.class);
            if(ids.contains(taskInfo.getMessageTemplateId())){
                logUtils.print(AnchorInfo.builder().businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).state(AnchorState.DISCARD.getCode()).build());
                return true;
            }
        }else{
            List<MessageTemplate> list = messageTemplateMapper.findAllByList();
            Set<Long> ids = list.stream().map(MessageTemplate::getId).collect(Collectors.toSet());
            redisUtils.set(SendAccountConstant.MESSAGE_TEMPLATE,ids,1000*60*24);
            if(ids.contains(taskInfo.getMessageTemplateId())){
               logUtils.print(AnchorInfo.builder().businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).state(AnchorState.DISCARD.getCode()).build());

                return true;
            }
        }
//        if (array.contains(String.valueOf(taskInfo.getMessageTemplateId()))) {
//            logUtils.print(AnchorInfo.builder().businessId(taskInfo.getBusinessId()).ids(taskInfo.getReceiver()).state(AnchorState.DISCARD.getCode()).build());
//            return true;
//        }
        return false;
    }

}
