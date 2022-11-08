package com.duang.cloudcommons.entity;

import com.duang.cloudcommons.enums.ResponseCodeMessage;
import lombok.Data;

import java.io.Serializable;

@Data
public class MessageResponse<T> implements Serializable {

    private T data;

    private ResponseCodeMessage response;

    public MessageResponse(T data, ResponseCodeMessage codeMessage) {
        this.data = data;
        this.response = response;
    }

    public MessageResponse(ResponseCodeMessage response) {
        this.response = response;
    }

    public static MessageResponse success(){
        return new MessageResponse(ResponseCodeMessage.success);
    }
}
