package com.ciuciu.footballhighlight.data.network.response;


import android.support.annotation.Nullable;

public class ApiResponseBody<T> {

    public static final int STATUS_CODE_SUCCESS = 0;
    public static final int STATUS_CODE_NO_CONTENT = 404;
    public static final int STATUS_CODE_BAD_REQUEST = 400;

    private int statusCode;
    private T data;

    public ApiResponseBody() {

    }

    public ApiResponseBody(int statusCode, @Nullable T data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public static <T> ApiResponseBody<T> success(int statusCode, T data) {
        return new ApiResponseBody(statusCode, data);
    }

    public static <T> ApiResponseBody<T> error() {
        return new ApiResponseBody(STATUS_CODE_BAD_REQUEST, null);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getData() {
        return data;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setData(T data) {
        this.data = data;
    }
}
