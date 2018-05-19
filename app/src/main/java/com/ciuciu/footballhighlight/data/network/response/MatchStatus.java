package com.ciuciu.footballhighlight.data.network.response;

public class MatchStatus {

    private int error;
    private String message;

    public int getCode() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public boolean isNotContent() {
        return error == 404;
    }
}
