package com.ciuciu.footballhighlight.di;

import android.content.Context;

import com.ciuciu.footballhighlight.FootballHighlightApplication;
import com.ciuciu.footballhighlight.data.disk.DiskHelper;
import com.ciuciu.footballhighlight.data.disk.DiskHelperImpl;
import com.ciuciu.footballhighlight.data.preferences.PreferencesHelper;
import com.ciuciu.footballhighlight.data.preferences.PreferencesHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private FootballHighlightApplication mApplication;

    public ApplicationModule(FootballHighlightApplication application) {
        this.mApplication = application;
    }

    @Provides
    Context applicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    DiskHelper provideDiskHelper(DiskHelperImpl diskHelper) {
        return diskHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(PreferencesHelperImpl preferencesHelper) {
        return preferencesHelper;
    }

}
