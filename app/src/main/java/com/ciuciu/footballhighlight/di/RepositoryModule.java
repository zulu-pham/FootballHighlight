package com.ciuciu.footballhighlight.di;

import com.ciuciu.footballhighlight.data.network.LiveScoreApi;
import com.ciuciu.footballhighlight.repository.CurrentEventsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    CurrentEventsRepository provideCurrentEventsRepository(LiveScoreApi liveScoreApi) {
        return new CurrentEventsRepository(liveScoreApi);
    }
}
