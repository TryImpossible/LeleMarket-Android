package com.bynn.marketll.module_home.dagger;

import com.bynn.common.dagger.ActivityScope;
import com.bynn.marketll.module_home.network.HomeApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class HomeModule {
    @ActivityScope
    @Provides
    HomeApi provideHomeApi(Retrofit retrofit) {
        return retrofit.create(HomeApi.class);
    }
}
