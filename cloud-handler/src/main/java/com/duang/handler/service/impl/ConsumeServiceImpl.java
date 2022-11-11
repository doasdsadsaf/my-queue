package com.duang.handler.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.duang.cloudcommons.domain.AnchorInfo;
import com.duang.cloudcommons.domain.LogParam;
import com.duang.cloudcommons.domain.TaskInfo;
import com.duang.cloudcommons.enums.AnchorState;
import com.duang.handler.handler.HandlerHolder;
import com.duang.handler.pending.Task;
import com.duang.handler.pending.TaskPendingHolder;
import com.duang.handler.service.ConsumeService;
import com.duang.handler.utils.GroupIdMappingUtils;
import com.duang.support.support.domain.MessageTemplate;
import com.duang.support.support.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 3y
 */
@Service
public class ConsumeServiceImpl implements ConsumeService {
    private static final String LOG_BIZ_TYPE = "Receiver#consumer";
    private static final String LOG_BIZ_RECALL_TYPE = "Receiver#recall";
    @Autowired
    private ApplicationContext context;

    @Autowired
    private TaskPendingHolder taskPendingHolder;

    @Autowired
    private LogUtils logUtils;

    @Autowired
    private HandlerHolder handlerHolder;

    @Override
    public void consume2Send(List<TaskInfo> taskInfoLists) {
        String topicGroupId = GroupIdMappingUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfoLists.iterator()));
        for (TaskInfo taskInfo : taskInfoLists) {
            logUtils.print(LogParam.builder().bizType(LOG_BIZ_TYPE).object(taskInfo).build(), AnchorInfo.builder().ids(taskInfo.getReceiver()).businessId(taskInfo.getBusinessId()).state(AnchorState.RECEIVE.getCode()).build());
            Task task = context.getBean(Task.class).setTaskInfo(taskInfo);
            taskPendingHolder.route(topicGroupId).execute(task);
        }
    }

    @Override
    public void consume2recall(MessageTemplate messageTemplate) {
        logUtils.print(LogParam.builder().bizType(LOG_BIZ_RECALL_TYPE).object(messageTemplate).build());
        handlerHolder.route(messageTemplate.getSendChannel()).recall(messageTemplate);
    }
}
