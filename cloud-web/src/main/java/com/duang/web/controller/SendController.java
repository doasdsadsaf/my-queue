package com.duang.web.controller;


import com.duang.cloud.api.domain.SendRequest;
import com.duang.cloud.api.domain.SendResponse;
import com.duang.web.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 三歪
 */

@RestController
public class SendController {

    @Autowired
    private SendService sendService;


    /**
     * 发送消息接口
     * 入参完整示例：curl -XPOST "127.0.0.1:8080/send"  -H 'Content-Type: application/json'  -d '{"code":"send","messageParam":{"receiver":"13788888888","variables":{"title":"yyyyyy","contentValue":"6666164180"}},"messageTemplateId":1}'
     * @return
     */
//    @ApiOperation(value = "下发接口",notes = "多渠道多类型下发消息，目前支持邮件和短信，类型支持：验证码、通知类、营销类。")
    @PostMapping("/send")
    public SendResponse send(@RequestBody SendRequest sendRequest) {
        return sendService.send(sendRequest);
    }
}
