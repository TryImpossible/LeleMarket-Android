package com.bynn.common.dagger;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bynn.common.base.BaseApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hwangjr.rxbus.Bus;
import com.hwangjr.rxbus.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private BaseApplication mBaseApplication;

    public AppModule(BaseApplication baseApplication) {
        this.mBaseApplication = baseApplication;
    }

    @Singleton
    @Provides
    BaseApplication provideBaseApplication() {
        return mBaseApplication;
    }

    @Singleton
    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }
}
