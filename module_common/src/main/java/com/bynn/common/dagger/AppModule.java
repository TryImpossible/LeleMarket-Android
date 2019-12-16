package com.bynn.common.dagger;

import com.bynn.common.base.BaseApplication;

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
}
