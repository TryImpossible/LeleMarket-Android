package com.bynn.marketll.module_shopping_cart.dagger;

import com.bynn.common.dagger.ActivityScope;
import com.bynn.common.dagger.AppComponent;
import com.bynn.marketll.module_shopping_cart.network.ShoppingCartApi;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ShoppingCartModule.class})
public interface ShoppingCartComponent {
    ShoppingCartApi getCustomApi();

    ShoppingCartApi getCustomPresenter();
}
