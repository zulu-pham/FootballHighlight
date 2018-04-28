package com.ciuciu.footballhighlight.di;

import android.arch.lifecycle.ViewModel;

import com.ciuciu.footballhighlight.common.viewmodel.ViewModelKey;
import com.ciuciu.footballhighlight.feature.worldcup.WorldCupFragment;
import com.ciuciu.footballhighlight.feature.worldcup.viewmodel.WorldCupViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module()
public abstract class FeatureModule {

    /**
     * WorldCup module
     */
    @ContributesAndroidInjector
    abstract WorldCupFragment worldCupFragment();

    @Binds
    @IntoMap
    @ViewModelKey(WorldCupViewModel.class)
    abstract ViewModel bindWorldCupViewModel(WorldCupViewModel worldCupViewModel);
}
