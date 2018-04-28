package com.ciuciu.footballhighlight.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.ciuciu.footballhighlight.ApplicationException;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * NetworkBoundResource abstract class to persist data offline
 * Prefer call API before get data from Database
 */

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private final MediatorLiveData<Response<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource() {
        // set State LOADING
        result.setValue(Response.<ResultType>loading(null));
        // init data
        fetchFromNetwork();
    }

    private void fetchFromNetwork() {

        createCall().enqueue(new Callback<RequestType>() {

            @Override
            public void onResponse(Call<RequestType> call, retrofit2.Response<RequestType> response) {
                result.setValue(Response.success(processResult(response.body())));
            }

            @Override
            public void onFailure(Call<RequestType> call, Throwable t) {
                result.setValue(Response.error(new ApplicationException(t), null));
            }
        });
    }

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract Call<RequestType> createCall();

    protected abstract ResultType processResult(RequestType requestType);

    public final LiveData<Response<ResultType>> getAsLiveData() {
        return result;
    }

}