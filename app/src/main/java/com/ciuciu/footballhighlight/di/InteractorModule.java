package com.ciuciu.footballhighlight.di;

import com.ciuciu.footballhighlight.feature.league.interactor.CurrentMatchInteractor;
import com.ciuciu.footballhighlight.feature.league.interactor.CurrentMatchInteractorImpl;
import com.ciuciu.footballhighlight.feature.league.interactor.PlayedMatchInteractor;
import com.ciuciu.footballhighlight.feature.league.interactor.PlayedMatchInteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorModule {

    @Provides
    @Singleton
    CurrentMatchInteractor provideCurrentMatchInteractor(CurrentMatchInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    @Singleton
    PlayedMatchInteractor providePlayedMatchInteractor(PlayedMatchInteractorImpl interactor) {
        return interactor;
    }

}
