package com.duang.handler.deduplication.build;

import cn.hutool.core.date.DateUtil;
import com.duang.cloudcommons.domain.TaskInfo;
import com.duang.cloudcommons.enums.AnchorState;
import com.duang.cloudcommons.enums.DeduplicationType;
import com.duang.handler.deduplication.DeduplicationParam;
import org.springframework.stereotype.Service;

import java.util.Date;

/** 频率重复消除
 * @author huskey
 * @date 2022/1/18
 */

@Service
public class FrequencyDeduplicationBuilder extends AbstractDeduplicationBuilder implements Builder {
    public FrequencyDeduplicationBuilder() {
        deduplicationType = DeduplicationType.FREQUENCY.getCode();
    }

    @Override
    public DeduplicationParam build(String deduplication, TaskInfo taskInfo) {
        DeduplicationParam deduplicationParam = getParamsFromConfig(deduplicationType, deduplication, taskInfo);
        if (deduplicationParam == null) {
            return null;
        }
        deduplicationParam.setDeduplicationTime((DateUtil.endOfDay(new Date()).getTime() - DateUtil.current()) / 1000);
        deduplicationParam.setAnchorState(AnchorState.RULE_DEDUPLICATION);
        return deduplicationParam;
    }
}
