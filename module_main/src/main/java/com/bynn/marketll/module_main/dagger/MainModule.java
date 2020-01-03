package com.bynn.marketll.module_main.dagger;

import android.content.Context;

import com.bynn.common.base.IBaseView;
import com.bynn.common.dagger.ActivityScope;
import com.bynn.marketll.module_main.MainModel;
import com.bynn.marketll.module_main.network.MainApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {
    private Context   mContext;
    private IBaseView mIBaseView;

    public MainModule(IBaseView iBaseView) {
        this.mIBaseView = iBaseView;
    }

    public MainModule(Context context, IBaseView iBaseView) {
        this.mContext = context;
        this.mIBaseView = iBaseView;
    }

    @ActivityScope
    @Provides
    MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
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
    MainModel provideMainModel(Retrofit retrofit) {
        MainApi api = retrofit.create(MainApi.class);
        return new MainModel(api);
    }
}
