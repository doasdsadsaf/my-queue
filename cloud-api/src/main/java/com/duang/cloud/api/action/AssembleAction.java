package com.duang.cloud.api.action;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duang.cloud.api.domain.MessageParam;
import com.duang.cloud.api.domain.SendTaskModel;
import com.duang.cloud.api.enums.BusinessCode;
import com.duang.cloudcommons.constant.AustinConstant;
import com.duang.cloudcommons.domain.TaskInfo;
import com.duang.cloudcommons.dto.model.ContentModel;
import com.duang.cloudcommons.enums.ChannelType;
import com.duang.cloudcommons.enums.RespStatusEnum;
import com.duang.cloudcommons.vo.BasicResultVO;
import com.duang.support.support.domain.MessageTemplate;
import com.duang.support.support.mapper.MessageTemplateMapper;
import com.duang.support.support.pipeline.BusinessProcess;
import com.duang.support.support.pipeline.ProcessContext;
import com.duang.support.support.utils.ContentHolderUtil;
import com.duang.support.support.utils.TaskInfoUtils;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author 3y
 * @date 2021/11/22
 * @description 拼装参数
 */
@Slf4j
@Service
public class AssembleAction implements BusinessProcess<SendTaskModel> {

    @Autowired
    private MessageTemplateMapper messageTemplateMapper;

    /**
     * 发送消息第二步
     * @param context
     */
    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        SendTaskModel sendTaskModel = context.getProcessModel();
        Long messageTemplateId = sendTaskModel.getMessageTemplateId();

        try {
            // 查询消息模板
            MessageTemplate messageTemplate = messageTemplateMapper.selectById(messageTemplateId);
            if (Objects.isNull(messageTemplate) || AustinConstant.TRUE.equals(messageTemplate.getIsDeleted())) {
                context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.TEMPLATE_NOT_FOUND));
                return;
            }
            // 消息code走不同的逻辑
            if (BusinessCode.COMMON_SEND.getCode().equals(context.getCode())) {
                List<TaskInfo> taskInfos = assembleTaskInfo(sendTaskModel, messageTemplate);
                sendTaskModel.setTaskInfo(taskInfos);
            } else if (BusinessCode.RECALL.getCode().equals(context.getCode())) {
                sendTaskModel.setMessageTemplate(messageTemplate);
            }
        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
            log.error("assemble task fail! templateId:{}, e:{}", messageTemplateId, Throwables.getStackTraceAsString(e));
        }

    }

    /**
     * 组装 TaskInfo 任务消息
     *
     * @param sendTaskModel  发送信息对象
     * @param messageTemplate  消息模板对象
     */
    private List<TaskInfo> assembleTaskInfo(SendTaskModel sendTaskModel, MessageTemplate messageTemplate) {
        List<MessageParam> messageParamList = sendTaskModel.getMessageParamList();
        List<TaskInfo> taskInfoList = new ArrayList<>();
        // 遍历每个接收者 准备消息
        for (MessageParam messageParam : messageParamList) {

            TaskInfo taskInfo = TaskInfo.builder()
                    .messageTemplateId(messageTemplate.getId())
                    .businessId(TaskInfoUtils.generateBusinessId(messageTemplate.getId(), messageTemplate.getTemplateType()))
                    .receiver(new HashSet<>(Arrays.asList(messageParam.getReceiver().split(String.valueOf(StrUtil.C_COMMA)))))
                    .idType(messageTemplate.getIdType())
                    .sendChannel(messageTemplate.getSendChannel())
                    .templateType(messageTemplate.getTemplateType())
                    .msgType(messageTemplate.getMsgType())
                    .shieldType(messageTemplate.getShieldType())
                    .sendAccount(messageTemplate.getSendAccount())
                    .contentModel(getContentModelValue(messageTemplate, messageParam)).build();

            taskInfoList.add(taskInfo);
        }

        return taskInfoList;

    }


    /**
     * 获取 contentModel，替换模板msgContent中占位符信息
     */
    private static ContentModel getContentModelValue(MessageTemplate messageTemplate, MessageParam messageParam) {

        // 得到真正的ContentModel 类型
        Integer sendChannel = messageTemplate.getSendChannel();
        Class contentModelClass = ChannelType.getChanelModelClassByCode(sendChannel);


        // 得到模板的 msgContent 和 入参
        Map<String, String> variables = messageParam.getVariables();
        // 拿到模板里的消息内容
        JSONObject jsonObject = JSON.parseObject(messageTemplate.getMsgContent());


        // 通过反射 组装出 contentModel
        // 获得类里对象
        Field[] fields = ReflectUtil.getFields(contentModelClass);
        // 获得对象
        ContentModel contentModel = (ContentModel) ReflectUtil.newInstance(contentModelClass);
        for (Field field : fields) {
            String originValue = jsonObject.getString(field.getName());
            // 当字段名跟json里的key一样 替换占位符
            if (StrUtil.isNotBlank(originValue)) {
                String resultValue = ContentHolderUtil.replacePlaceHolder(originValue, variables);
                Object resultObj = JSONUtil.isJsonObj(resultValue) ? JSONUtil.toBean(resultValue, field.getType()) : resultValue;
                ReflectUtil.setFieldValue(contentModel, field, resultObj);
            }
        }

        // 如果 url 字段存在，则在url拼接对应的埋点参数
        String url = (String) ReflectUtil.getFieldValue(contentModel, "url");
        if (StrUtil.isNotBlank(url)) {
            String resultUrl = TaskInfoUtils.generateUrl(url, messageTemplate.getId(), messageTemplate.getTemplateType());
            ReflectUtil.setFieldValue(contentModel, "url", resultUrl);
        }
        return contentModel;
    }
}
