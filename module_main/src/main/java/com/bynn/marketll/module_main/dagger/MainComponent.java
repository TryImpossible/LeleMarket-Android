package com.bynn.marketll.module_main.dagger;

import com.bynn.common.dagger.ActivityScope;
import com.bynn.common.dagger.AppComponent;
import com.bynn.marketll.module_main.MainPresenter;
import com.bynn.marketll.module_main.network.MainApi;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {MainModule.class})
public interface MainComponent {
    MainApi getMainApi();

    MainPresenter getMainPresenter();
}
