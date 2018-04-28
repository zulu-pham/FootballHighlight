package com.ciuciu.footballhighlight.di;

import com.ciuciu.footballhighlight.feature.events.current.interactor.CurrentEventsInteractor;
import com.ciuciu.footballhighlight.feature.events.current.interactor.CurrentEventsInteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorModule {

    @Provides
    @Singleton
    CurrentEventsInteractor provideCurrentEventsInteractor(CurrentEventsInteractorImpl interactor) {
        return interactor;
    }
}
