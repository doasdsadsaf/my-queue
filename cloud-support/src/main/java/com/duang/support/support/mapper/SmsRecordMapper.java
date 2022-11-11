package com.duang.support.support.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duang.support.support.domain.SmsRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 短信记录的Dao
 *
 * @author 3y
 */
@Mapper
public interface SmsRecordMapper extends BaseMapper<SmsRecord> {

    /**
     * 通过日期和手机号找到发送记录
     *
     * @param phone
     * @param sendDate
     * @return
     */
    List<SmsRecord> findByPhoneAndSendDate(Long phone, Integer sendDate);
}
