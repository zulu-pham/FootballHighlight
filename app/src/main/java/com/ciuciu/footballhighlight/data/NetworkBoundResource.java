package com.ciuciu.footballhighlight.data;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ciuciu.footballhighlight.data.network.response.ApiResponseBody;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * NetworkBoundResource abstract class to persist data offline
 * Prefer call API before get data from Database
 */

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private Observable<ApiResponseBody<ResultType>> result;

    @MainThread
    public NetworkBoundResource() {
        result = createCall()
                .subscribeOn(Schedulers.io())
                .flatMap(apiResponse -> Observable.just(transformData(apiResponse)))
                .onErrorResumeNext(throwable -> {
                    Log.d("NetworkBoundResource", throwable.getMessage());
                    onFetchFailed();
                    return Observable.just(ApiResponseBody.error());
                });
    }

    public Observable<ApiResponseBody<ResultType>> asObservable() {
        return result;
    }

    @NonNull
    @MainThread
    protected abstract Observable<RequestType> createCall();

    @NonNull
    @MainThread
    protected abstract ApiResponseBody<ResultType> transformData(RequestType response);

    protected void onFetchFailed() {

    }
}