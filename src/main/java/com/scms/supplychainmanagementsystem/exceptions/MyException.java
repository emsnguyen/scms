package com.scms.supplychainmanagementsystem.exceptions;

public class MyException extends RuntimeException {
    public MyException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public MyException(String exMessage) {
        super(exMessage);
    }
}
