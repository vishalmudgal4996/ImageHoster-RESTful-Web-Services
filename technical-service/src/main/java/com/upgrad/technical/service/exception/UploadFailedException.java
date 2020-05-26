package com.upgrad.technical.service.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class UploadFailedException extends Exception {
    //Define two private and final attributes of String type named "code" and "errorMessage"
    //Also define the constructor for both the attributes
    //Also define getters for both the attributes
    //Write code here//
    private final String code;
    private final String errorMessage;

    public UploadFailedException(final String code, final String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    public String getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}