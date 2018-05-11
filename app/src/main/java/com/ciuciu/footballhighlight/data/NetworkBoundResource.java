package com.ciuciu.footballhighlight.data;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * NetworkBoundResource abstract class to persist data offline
 * Prefer call API before get data from Database
 */

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private Observable<Response<ResultType>> result;

    @MainThread
    public NetworkBoundResource() {
        result = createCall()
                .subscribeOn(Schedulers.io())
                .flatMap(apiResponse -> Observable.just(Response.success(transformData(apiResponse))))
                .doOnError(t -> {
                    onFetchFailed();
                })
                .onErrorResumeNext(t -> {
                    return Observable.just(Response.error(t, null));
                })
                .observeOn(AndroidSchedulers.mainThread())
                //.startWith(Response.loading(null))
        ;

    }

    public Observable<Response<ResultType>> asObservable() {
        return result;
    }

    @NonNull
    @MainThread
    protected abstract Observable<RequestType> createCall();

    @NonNull
    @MainThread
    protected abstract ResultType transformData(RequestType response);

    protected void onFetchFailed() {

    }

}