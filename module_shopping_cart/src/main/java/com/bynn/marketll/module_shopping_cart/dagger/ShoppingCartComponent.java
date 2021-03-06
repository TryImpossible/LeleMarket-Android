package com.bynn.marketll.module_shopping_cart.dagger;

import com.bynn.lib_basic.dagger.ActivityScope;
import com.bynn.lib_basic.dagger.AppComponent;
import com.bynn.marketll.module_shopping_cart.ShoppingCartPresenter;
import com.bynn.marketll.module_shopping_cart.network.ShoppingCartApi;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ShoppingCartModule.class})
public interface ShoppingCartComponent {
    ShoppingCartApi getShoppingCartApi();

    ShoppingCartPresenter getShoppingCartPresenter();
}
