package com.ciuciu.footballhighlight.di;

import com.ciuciu.footballhighlight.feature.worldcup.interactor.WorldCupInteractor;
import com.ciuciu.footballhighlight.feature.worldcup.interactor.WorldCupInteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorModule {

    @Provides
    @Singleton
    WorldCupInteractor provideWorldCupInteractor(WorldCupInteractorImpl interactor) {
        return interactor;
    }
}
