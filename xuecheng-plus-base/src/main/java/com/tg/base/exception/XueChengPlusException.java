package com.tg.base.exception;

public class XueChengPlusException extends RuntimeException{

    private String errMessage;
    public XueChengPlusException() {
        super();
    }

    public XueChengPlusException(String message) {
        super(message);
        this.errMessage = message;
    }

    public static void cast(CommonError error){

        throw new XueChengPlusException(error.getErrMessage());

    }

    public static void cast(String error){

        throw new XueChengPlusException(error);

    }

}
