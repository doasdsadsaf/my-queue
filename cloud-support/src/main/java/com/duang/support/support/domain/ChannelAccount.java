package com.duang.support.support.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 3y
 * 渠道账号信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("channel_account")
public class ChannelAccount {

    private Long id;

    /**
     * 账号名称
     */
    private String name;

    /**
     * 发送渠道
     * 枚举值：com.java3y.austin.common.enums.ChannelType
     */
    private Integer sendChannel;

    /**
     * 账号配置
     */
    private String accountConfig;

    /**
     * 是否删除
     * 0：未删除
     * 1：已删除
     */
    private Integer isDeleted;

    /**
     * 创建时间 单位 s
     */
    private Integer created;

    /**
     * 更新时间 单位s
     */
    private Integer updated;

}
