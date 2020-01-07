package com.bynn.marketll.module_home.dagger;

import android.content.Context;

import com.bynn.lib_basic.interfaces.IBaseView;
import com.bynn.lib_basic.dagger.ActivityScope;
import com.bynn.marketll.module_home.HomeModel;
import com.bynn.marketll.module_home.network.HomeApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class HomeModule {
    private Context   mContext;
    private IBaseView mIBaseView;

    public HomeModule(IBaseView iBaseView) {
        this.mIBaseView = iBaseView;
    }

    public HomeModule(Context context, IBaseView iBaseView) {
        this.mContext = context;
        this.mIBaseView = iBaseView;
    }

    @ActivityScope
    @Provides
    HomeApi provideHomeApi(Retrofit retrofit) {
        return retrofit.create(HomeApi.class);
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
    HomeModel provideHomeModel(Retrofit retrofit) {
        HomeApi api = retrofit.create(HomeApi.class);
        return new HomeModel(api);
    }
}
