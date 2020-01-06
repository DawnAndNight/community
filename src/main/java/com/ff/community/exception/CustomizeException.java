package com.ff.community.exception;

public class CustomizeException extends RuntimeException{
    private String message;

    public CustomizeException(ICustomizeExcepCode errorCode){
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
