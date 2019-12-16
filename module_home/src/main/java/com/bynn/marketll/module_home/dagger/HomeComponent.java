package com.bynn.marketll.module_home.dagger;

import com.bynn.common.dagger.ActivityScope;
import com.bynn.common.dagger.AppModule;
import com.bynn.marketll.module_home.activity.HomeActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppModule.class, modules = {HomeModule.class})
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
}
