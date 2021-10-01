package com.scms.supplychainmanagementsystem.exceptions;

public class AppException extends RuntimeException {
    public AppException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public AppException(String exMessage) {
        super(exMessage);
    }
}
