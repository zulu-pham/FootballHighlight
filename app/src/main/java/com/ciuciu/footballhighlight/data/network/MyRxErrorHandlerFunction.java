package com.ciuciu.footballhighlight.data.network;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.HttpException;

public class MyRxErrorHandlerFunction<T> implements Function<Throwable, ObservableSource<? extends T>> {
    @Override
    public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            if (httpException.response().isSuccessful()) {
                return Observable.error(new ServiceException("Parsing error occurred"));
            } else {
                return Observable.error(new ServiceApiException(httpException.response()));
            }
        }
        return Observable.error(new ServiceException("Connection Error - " + throwable.getMessage()));
    }
}
