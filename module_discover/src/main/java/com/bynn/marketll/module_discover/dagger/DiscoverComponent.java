package com.bynn.marketll.module_discover.dagger;

import com.bynn.lib_basic.dagger.ActivityScope;
import com.bynn.lib_basic.dagger.AppComponent;
import com.bynn.marketll.module_discover.DiscoverPresenter;
import com.bynn.marketll.module_discover.network.DiscoverApi;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {DiscoverModule.class})
public interface DiscoverComponent {
    DiscoverApi getDiscoverApi();

    DiscoverPresenter getDiscoverPresenter();
}
