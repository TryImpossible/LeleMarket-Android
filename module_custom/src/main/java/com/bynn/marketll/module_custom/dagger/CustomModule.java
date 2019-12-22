package com.bynn.marketll.module_custom.dagger;

import android.content.Context;

import com.bynn.common.base.IBaseView;
import com.bynn.common.dagger.ActivityScope;
import com.bynn.marketll.module_custom.CustomModel;
import com.bynn.marketll.module_custom.network.CustomApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class CustomModule {
    private Context mContext;
    private IBaseView mIBaseView;

    public CustomModule(IBaseView mIBaseView) {
        this.mIBaseView = mIBaseView;
    }

    public CustomModule(Context mContext, IBaseView mIBaseView) {
        this.mContext = mContext;
        this.mIBaseView = mIBaseView;
    }

    @ActivityScope
    @Provides
    CustomApi provideHomeApi(Retrofit retrofit) {
        return retrofit.create(CustomApi.class);
    }

    @ActivityScope
    @Provides
    Context provideActivityContext() {
        return this.mContext;
    }

    @ActivityScope
    @Provides
    IBaseView provideBaseView() {
        return this.mIBaseView;
    }

    @ActivityScope
    @Provides
    CustomModel provideCustomModel(Retrofit retrofit) {
        CustomApi customApi = retrofit.create(CustomApi.class);
        CustomModel model = new CustomModel(customApi);
        return model;
    }
}
