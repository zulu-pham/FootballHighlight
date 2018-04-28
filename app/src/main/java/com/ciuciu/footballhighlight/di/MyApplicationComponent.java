package com.ciuciu.footballhighlight.di;

import android.app.Application;

import com.ciuciu.footballhighlight.FootballHighlightApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        ApplicationAbstractModule.class,
        NetworkModule.class,
        InteractorModule.class,
        RepositoryModule.class,
        FeatureModule.class
})
public interface MyApplicationComponent extends AndroidInjector<FootballHighlightApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyApplicationComponent.Builder application(Application application);

        Builder applicationModule(ApplicationModule applicationModule);

        MyApplicationComponent build();
    }
}
