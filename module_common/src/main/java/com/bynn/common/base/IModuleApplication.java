package com.bynn.common.base;

import android.app.Application;

/**
 * 模块 Application接口
 */
public interface IModuleApplication {

    void onCreate(BaseApplication application);

    Application getAppliaction();
}
