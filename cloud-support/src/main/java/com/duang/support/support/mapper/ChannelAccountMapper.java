package com.duang.support.support.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duang.support.support.domain.ChannelAccount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 渠道账号信息 Dao
 *
 * @author 3y
 */
@Mapper
public interface ChannelAccountMapper extends BaseMapper<ChannelAccount> {


    /**
     * 查询 列表（分页)
     *
     * @param deleted     0：未删除 1：删除
     * @param channelType 渠道值
     * @return
     */
    List<ChannelAccount> findAllByIsDeletedEqualsAndSendChannelEquals(Integer deleted, Integer channelType);


    /**
     * 统计未删除的条数
     *
     * @param deleted
     * @return
     */
    Long countByIsDeletedEquals(Integer deleted);
}
