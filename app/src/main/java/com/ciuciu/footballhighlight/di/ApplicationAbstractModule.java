package com.ciuciu.footballhighlight.di;

import android.arch.lifecycle.ViewModelProvider;

import com.ciuciu.footballhighlight.common.viewmodel.MyViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ApplicationAbstractModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(MyViewModelFactory factory);
}
