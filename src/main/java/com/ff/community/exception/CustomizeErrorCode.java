package com.ff.community.exception;

public enum CustomizeErrorCode implements ICustomizeExcepCode{
    QUESTION_NOT_FOUND(2001,"提问内容不存在，服务器炸了"),
    TAGET_PARENT_NOT_FOUND(2002,"未选中问题或评论进行回复"),
    NOT_LOGIN_IN(2003,"为进行登录"),
    SYS_ERROR(2004,"服务器炸了"),
    TYPE_PARENT_WRONG(2005,"没有找到相应标签"),
    COMMET_NOT_FOUND(2006,"评论未找到");

    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
