package com.bynn.marketll.module_custom.dagger;

import com.bynn.common.dagger.ActivityScope;
import com.bynn.common.dagger.AppComponent;
import com.bynn.marketll.module_custom.CustomPresenter;
import com.bynn.marketll.module_custom.network.CustomApi;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {CustomModule.class})
public interface CustomComponent {
    CustomApi getCustomApi();

    CustomPresenter getCustomPresenter();
}
