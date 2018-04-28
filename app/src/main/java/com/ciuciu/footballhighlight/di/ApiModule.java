package com.ciuciu.footballhighlight.di;

import com.ciuciu.footballhighlight.data.network.LiveScoreApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {

    @Provides
    LiveScoreApi provideLiveScoreApi(Retrofit retrofit) {
        return retrofit.create(LiveScoreApi.class);
    }
}
