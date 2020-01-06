package com.ff.community.exception;

public enum  CustomizeExcepCode implements ICustomizeExcepCode{
    QUESTION_NOT_FOUND("服务器炸了");

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeExcepCode(String message) {
        this.message = message;
    }
}
