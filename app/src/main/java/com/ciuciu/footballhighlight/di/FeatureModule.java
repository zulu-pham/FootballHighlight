package com.ciuciu.footballhighlight.di;

import android.arch.lifecycle.ViewModel;

import com.ciuciu.footballhighlight.common.viewmodel.ViewModelKey;
import com.ciuciu.footballhighlight.feature.league.multi.MultiLeaguesFragment;
import com.ciuciu.footballhighlight.feature.league.multi.MultiLeaguesViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module()
public abstract class FeatureModule {

    /**
     * Multi League module
     */
    @ContributesAndroidInjector
    abstract MultiLeaguesFragment multiLeaguesFragment();

    @Binds
    @IntoMap
    @ViewModelKey(MultiLeaguesViewModel.class)
    abstract ViewModel bindMultiLeaguesViewModel(MultiLeaguesViewModel multiLeaguesViewModel);

}
