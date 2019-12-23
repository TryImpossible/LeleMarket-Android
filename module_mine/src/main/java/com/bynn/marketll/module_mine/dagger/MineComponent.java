package com.bynn.marketll.module_mine.dagger;

import com.bynn.common.dagger.ActivityScope;
import com.bynn.common.dagger.AppComponent;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {MineModule.class})
public interface MineComponent {
}
