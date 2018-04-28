package com.ciuciu.footballhighlight;

import android.support.v7.app.AppCompatDelegate;

import com.ciuciu.footballhighlight.di.ApplicationModule;
import com.ciuciu.footballhighlight.di.DaggerMyApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class FootballHighlightApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerMyApplicationComponent.builder()
                .application(this)
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}
