package com.bynn.marketll.module_discover.dagger;

import com.bynn.common.dagger.ActivityScope;
import com.bynn.common.dagger.AppComponent;
import com.bynn.marketll.module_discover.network.DiscoverApi;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {DiscoverModule.class})
public interface DiscoverComponent {
    DiscoverApi getCustomApi();

    DiscoverApi getCustomPresenter();
}
