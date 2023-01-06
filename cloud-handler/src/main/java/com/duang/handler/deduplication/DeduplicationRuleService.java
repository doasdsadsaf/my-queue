package com.duang.handler.deduplication;


import com.duang.cloudcommons.domain.TaskInfo;
import com.duang.cloudcommons.enums.DeduplicationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 3y.
 * @date 2021/12/12
 * 去重服务
 */
@Service
public class DeduplicationRuleService {

    public static final String DEDUPLICATION_RULE_KEY = "deduplicationRule";

//    @Autowired
//    private ConfigService config;

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    public void duplication(TaskInfo taskInfo) {
        // 配置样例：
        // String deduplicationConfig = config.getProperty(DEDUPLICATION_RULE_KEY, CommonConstant.EMPTY_JSON_OBJECT);
        String deduplicationConfig ="{\"deduplication_10\":{\"num\":1,\"time\":300},\"deduplication_20\":{\"num\":5}}";
        // 去重
        // 获取去重的类型code
        List<Integer> deduplicationList = DeduplicationType.getDeduplicationList();
        // 循环去重类型
        for (Integer deduplicationType : deduplicationList) {
            DeduplicationParam deduplicationParam = deduplicationHolder.selectBuilder(deduplicationType).build(deduplicationConfig, taskInfo);
            if (deduplicationParam != null) {
                deduplicationHolder.selectService(deduplicationType).deduplication(deduplicationParam);
            }
        }
    }


}
