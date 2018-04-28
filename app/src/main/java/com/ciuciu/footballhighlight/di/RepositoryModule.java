package com.ciuciu.footballhighlight.di;

import com.ciuciu.footballhighlight.data.network.LiveScoreApi;
import com.ciuciu.footballhighlight.repository.ScoreHighlightRepository;
import com.ciuciu.footballhighlight.repository.WorldCupRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    ScoreHighlightRepository provideScoreHighlightRepository(LiveScoreApi liveScoreApi) {
        return new ScoreHighlightRepository(liveScoreApi);
    }


    @Provides
    @Singleton
    WorldCupRepository provideWorldCupRepository(LiveScoreApi liveScoreApi) {
        return new WorldCupRepository(liveScoreApi);
    }
}
