package com.bynn.lib_basic.dagger;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.network.RetrofitHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hwangjr.rxbus.Bus;
import com.hwangjr.rxbus.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
public class AppModule {

    private BaseApplication mBaseApplication;
    private RetrofitHelper  mRetrofitHelper;

    public AppModule(BaseApplication baseApplication) {
        this.mBaseApplication = baseApplication;
        this.mRetrofitHelper = new RetrofitHelper();
    }

    @Singleton
    @Provides
    BaseApplication provideBaseApplication() {
        return mBaseApplication;
    }

    @Provides
    String provideBaseUrl() {
        return mRetrofitHelper.getBaseUrl();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOKHttpClientBuilder() {
        return mRetrofitHelper.getOkHttpClientBuilder();
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return mRetrofitHelper.getRetrofitBuilder();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit() {
        return mRetrofitHelper.getRetrofitBuilder().build();
    }

    @Singleton
    @Provides
    Gson provideGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
    }

    @Singleton
    @Provides
    ARouter provideARouter() {
        return ARouter.getInstance();
    }

    @Singleton
    @Provides
    Bus provideRxBus() {
        return RxBus.get();
    }

}
