package com.ciuciu.footballhighlight.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.ciuciu.footballhighlight.ApplicationException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * NetworkBoundResource abstract class to persist data offline
 * Prefer call API before get data from Database
 */

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource() {
        // set State LOADING
        result.setValue(Resource.<ResultType>loading(null));
        // init data
        fetchFromNetwork();
    }

    private void fetchFromNetwork() {

        createCall().enqueue(new Callback<RequestType>() {
            @Override
            public void onResponse(Call<RequestType> call, Response<RequestType> response) {
                result.setValue(Resource.success(processResult(response.body())));
            }

            @Override
            public void onFailure(Call<RequestType> call, Throwable t) {
                result.setValue(Resource.error(new ApplicationException(t), null));
            }
        });
    }

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract Call<RequestType> createCall();

    protected abstract ResultType processResult(RequestType requestType);

    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return result;
    }

}