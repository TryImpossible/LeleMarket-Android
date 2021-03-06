package com.bynn.marketll.module_shopping_cart.dagger;

import android.content.Context;

import com.bynn.lib_basic.interfaces.IBaseView;
import com.bynn.lib_basic.dagger.ActivityScope;
import com.bynn.marketll.module_shopping_cart.ShoppingCartModel;
import com.bynn.marketll.module_shopping_cart.network.ShoppingCartApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ShoppingCartModule {
    private Context   mContext;
    private IBaseView mIBaseView;

    public ShoppingCartModule(IBaseView mIBaseView) {
        this.mIBaseView = mIBaseView;
    }

    public ShoppingCartModule(Context mContext, IBaseView mIBaseView) {
        this.mContext = mContext;
        this.mIBaseView = mIBaseView;
    }

    @ActivityScope
    @Provides
    ShoppingCartApi provideShoppingCartApi(Retrofit retrofit) {
        return retrofit.create(ShoppingCartApi.class);
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
    ShoppingCartModel provideCustomModel(Retrofit retrofit) {
        ShoppingCartApi shoppingCartApi = retrofit.create(ShoppingCartApi.class);
        ShoppingCartModel model = new ShoppingCartModel(shoppingCartApi);
        return model;
    }
}
