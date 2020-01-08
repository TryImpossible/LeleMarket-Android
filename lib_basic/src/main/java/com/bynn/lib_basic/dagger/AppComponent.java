package com.bynn.lib_basic.dagger;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bynn.lib_basic.BaseApplication;
import com.google.gson.Gson;
import com.hwangjr.rxbus.Bus;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    BaseApplication getApp();

    String getBaseUrl();

    OkHttpClient.Builder getOKHttpClientBuilder();

    Retrofit.Builder getRetrofitBuilder();

    Retrofit getRetrofit();

    Gson getGson();

    ARouter getARoute();

    Bus getRxBus();
}
