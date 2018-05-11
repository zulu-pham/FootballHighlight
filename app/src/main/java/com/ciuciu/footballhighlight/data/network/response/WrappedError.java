package com.ciuciu.footballhighlight.data.network.response;

import java.io.IOException;

public class WrappedError extends IOException {

    private int code;
    private String message;

    public WrappedError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
