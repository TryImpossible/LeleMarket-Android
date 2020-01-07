package com.bynn.marketll.module_home.dagger;

import com.bynn.lib_basic.dagger.ActivityScope;
import com.bynn.lib_basic.dagger.AppComponent;
import com.bynn.marketll.module_home.HomePresenter;
import com.bynn.marketll.module_home.network.HomeApi;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {HomeModule.class})
public interface HomeComponent {
    HomeApi getHomeApi();
    
    HomePresenter getHomePresenter();
}
