package com.bynn.marketll.module_mine.dagger;

import android.content.Context;

import com.bynn.lib_basic.interfaces.IBaseView;
import com.bynn.lib_basic.dagger.ActivityScope;
import com.bynn.marketll.module_mine.MineModel;
import com.bynn.marketll.module_mine.network.MineApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MineModule {

    private Context   mContext;
    private IBaseView mIBaseView;

    public MineModule(Context context, IBaseView IBaseView) {
        mContext = context;
        mIBaseView = IBaseView;
    }

    public MineModule(IBaseView IBaseView) {
        mIBaseView = IBaseView;
    }

    @ActivityScope
    @Provides
    MineApi provideMineApi(Retrofit retrofit) {
        return retrofit.create(MineApi.class);
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
    MineModel provideMineModel(Retrofit retrofit) {
        MineApi api = retrofit.create(MineApi.class);
        return new MineModel(api);
    }
}
