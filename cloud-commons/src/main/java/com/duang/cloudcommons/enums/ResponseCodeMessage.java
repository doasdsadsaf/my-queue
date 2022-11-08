package com.duang.cloudcommons.enums;

public enum ResponseCodeMessage {

    success(0,"成功");

    private Integer code;
    private String message;

    ResponseCodeMessage(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
