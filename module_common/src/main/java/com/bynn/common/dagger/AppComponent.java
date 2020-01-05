package com.bynn.common.dagger;

import com.bynn.common.base.BaseApplication;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    String getBaseUrl();

    BaseApplication getApp();

    Retrofit getRetrofit();

    OkHttpClient.Builder getOKHttpClientBuilder();

    Gson getGson();
}
