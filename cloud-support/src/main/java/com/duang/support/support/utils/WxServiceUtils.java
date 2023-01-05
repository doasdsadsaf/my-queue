package com.duang.support.support.utils;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaSubscribeService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.api.impl.WxMaSubscribeServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.duang.cloudcommons.account.WeChatMiniProgramAccount;
import com.duang.cloudcommons.account.WeChatOfficialAccount;
import com.duang.cloudcommons.constant.CommonConstant;
import com.duang.cloudcommons.enums.ChannelType;
import com.duang.support.support.domain.ChannelAccount;
import com.duang.support.support.mapper.ChannelAccountMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 微信服务号/微信小程序 服务初始化工具类
 *
 * @author 3y
 */
@Component
@Slf4j
@Data
public class WxServiceUtils {

    /**
     * 推送消息的小程序/微信服务号 账号
     */
    private Map<Long, WxMpService> officialAccountServiceMap = new ConcurrentHashMap<>();
    private Map<Long, WxMaSubscribeService> miniProgramServiceMap = new ConcurrentHashMap<>();

    @Autowired
    private ChannelAccountMapper channelAccountMapper;

    @PostConstruct
    public void init() {
        initOfficialAccount();
        initMiniProgram();
    }


    /**
     * 当账号存在变更/新增时，刷新Map
     */
    public void fresh() {
        init();
    }

    /**
     * 得到所有的小程序账号
     */
    private void initMiniProgram() {
        List<ChannelAccount> miniProgram = channelAccountMapper.findAllByIsDeletedEqualsAndSendChannelEquals(CommonConstant.FALSE, ChannelType.MINI_PROGRAM.getCode());
        if (CollectionUtil.isNotEmpty(miniProgram)) {
            for (ChannelAccount channelAccount : miniProgram) {
                WeChatMiniProgramAccount weChatMiniProgramAccount = JSON.parseObject(channelAccount.getAccountConfig(), WeChatMiniProgramAccount.class);
                miniProgramServiceMap.put(channelAccount.getId(), initMiniProgramService(weChatMiniProgramAccount));
            }
        }
    }

    /**
     * 得到所有的微信服务号账号
     */
    private void initOfficialAccount() {
        List<ChannelAccount> officialAccountList = channelAccountMapper.findAllByIsDeletedEqualsAndSendChannelEquals(CommonConstant.FALSE, ChannelType.OFFICIAL_ACCOUNT.getCode());
        if (CollectionUtil.isNotEmpty(officialAccountList)) {
            for (ChannelAccount channelAccount : officialAccountList) {
                WeChatOfficialAccount weChatOfficialAccount = JSON.parseObject(channelAccount.getAccountConfig(), WeChatOfficialAccount.class);
                officialAccountServiceMap.put(channelAccount.getId(), initOfficialAccountService(weChatOfficialAccount));
            }
        }
    }

    /**
     * 初始化微信服务号
     *
     * @return
     */
    public WxMpService initOfficialAccountService(WeChatOfficialAccount officialAccount) {
        WxMpService wxMpService = new WxMpServiceImpl();
        WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
        config.setAppId(officialAccount.getAppId());
        config.setSecret(officialAccount.getSecret());
        config.setToken(officialAccount.getToken());
        wxMpService.setWxMpConfigStorage(config);
        return wxMpService;
    }

    /**
     * 初始化微信小程序
     *
     * @return
     */
    private WxMaSubscribeServiceImpl initMiniProgramService(WeChatMiniProgramAccount miniProgramAccount) {
        WxMaService wxMaService = new WxMaServiceImpl();
        WxMaDefaultConfigImpl wxMaConfig = new WxMaDefaultConfigImpl();
        wxMaConfig.setAppid(miniProgramAccount.getAppId());
        wxMaConfig.setSecret(miniProgramAccount.getAppSecret());
        wxMaService.setWxMaConfig(wxMaConfig);
        return new WxMaSubscribeServiceImpl(wxMaService);
    }
}
