package com.bynn.marketll.module_mine.dagger;

import com.bynn.lib_basic.dagger.ActivityScope;
import com.bynn.lib_basic.dagger.AppComponent;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {MineModule.class})
public interface MineComponent {
}
