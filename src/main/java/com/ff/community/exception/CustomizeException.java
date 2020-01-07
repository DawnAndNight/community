package com.ff.community.exception;

public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;

    public CustomizeException(ICustomizeExcepCode errorCode){
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }
}
