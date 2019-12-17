package com.bynn.marketll.module_home.dagger;

import com.bynn.common.dagger.ActivityScope;
import com.bynn.common.dagger.AppComponent;
import com.bynn.common.dagger.AppModule;
import com.bynn.marketll.module_home.HomeModel;
import com.bynn.marketll.module_home.HomePresenter;
import com.bynn.marketll.module_home.activity.HomeActivity;
import com.bynn.marketll.module_home.network.HomeApi;

import dagger.Component;
import retrofit2.Retrofit;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {HomeModule.class})
public interface HomeComponent {
    HomeApi getHomeApi();
    
    HomePresenter getHomePresenter();
}
