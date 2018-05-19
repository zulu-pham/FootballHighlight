package com.ciuciu.footballhighlight.data.network.response;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UnwrapConverterFactory extends Converter.Factory {

    private GsonConverterFactory factory;
    private Gson gson;

    public UnwrapConverterFactory(Gson gson, GsonConverterFactory factory) {
        this.gson = gson;
        this.factory = factory;
    }

    @Override
    public Converter<ResponseBody, ApiResponseBody> responseBodyConverter(final Type type, Annotation[] annotations, Retrofit retrofit) {

        if (type instanceof ParameterizedType && ((ParameterizedType) type).getRawType().equals(ApiResponseBody.class)) {
            Type[] typeArr = ((ParameterizedType) type).getActualTypeArguments();

            if (typeArr.length > 0) {
                return new WrappedResponseBodyConverter(gson, typeArr[0]);
            }
        }

        return new WrappedResponseBodyConverter(gson, null);
    }
}
