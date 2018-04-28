package com.ciuciu.footballhighlight.di;

import android.arch.lifecycle.ViewModel;

import com.ciuciu.footballhighlight.common.viewmodel.ViewModelKey;
import com.ciuciu.footballhighlight.feature.events.current.CurrentEventsFragment;
import com.ciuciu.footballhighlight.feature.events.current.viewmodel.CurrentEventsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module()
public abstract class FeatureModule {

    /**
     * Events Current module
     */
    @ContributesAndroidInjector
    abstract CurrentEventsFragment currentEventsFragment();

    @Binds
    @IntoMap
    @ViewModelKey(CurrentEventsViewModel.class)
    abstract ViewModel bindCurrentEventsViewModel(CurrentEventsViewModel currentEventsViewModel);
}
