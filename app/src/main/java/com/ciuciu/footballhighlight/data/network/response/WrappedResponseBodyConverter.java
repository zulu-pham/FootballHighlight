package com.ciuciu.footballhighlight.data.network.response;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class WrappedResponseBodyConverter<T> implements Converter<ResponseBody, ApiResponseBody<T>> {

    private Gson gson;
    private Type type;

    public WrappedResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public ApiResponseBody convert(ResponseBody value) throws IOException {
        String response = value.string();
        MatchStatus httpStatus;

        try {
            httpStatus = gson.fromJson(response, MatchStatus.class);
        } catch (JsonSyntaxException ex) {
            httpStatus = new MatchStatus();
        }

        T t = null;
        if (httpStatus.getCode() != ApiResponseBody.STATUS_CODE_NO_CONTENT) {
            t = gson.fromJson(response, type);
        }

        return new ApiResponseBody(httpStatus.getCode(), t);
    }
}
