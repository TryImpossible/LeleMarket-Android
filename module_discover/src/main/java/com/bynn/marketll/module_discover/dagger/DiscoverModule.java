package com.bynn.marketll.module_discover.dagger;

import android.content.Context;

import com.bynn.common.base.IBaseView;
import com.bynn.common.dagger.ActivityScope;
import com.bynn.marketll.module_discover.DiscoverModel;
import com.bynn.marketll.module_discover.network.DiscoverApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class DiscoverModule {
    private Context   mContext;
    private IBaseView mIBaseView;

    public DiscoverModule(IBaseView mIBaseView) {
        this.mIBaseView = mIBaseView;
    }

    public DiscoverModule(Context mContext, IBaseView mIBaseView) {
        this.mContext = mContext;
        this.mIBaseView = mIBaseView;
    }

    @ActivityScope
    @Provides
    DiscoverApi provideHomeApi(Retrofit retrofit) {
        return retrofit.create(DiscoverApi.class);
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
    DiscoverModel provideCustomModel(Retrofit retrofit) {
        DiscoverApi discoverApi = retrofit.create(DiscoverApi.class);
        DiscoverModel model = new DiscoverModel(discoverApi);
        return model;
    }
}
