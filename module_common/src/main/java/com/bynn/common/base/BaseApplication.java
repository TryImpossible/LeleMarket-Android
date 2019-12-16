package com.bynn.common.base;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bynn.common.BuildConfig;
import com.bynn.common.config.ModuleConfig;
import com.bynn.common.dagger.AppComponent;
import com.bynn.common.dagger.AppModule;
import com.bynn.common.dagger.DaggerAppComponent;

public class BaseApplication extends Application {

    private static BaseApplication sApplication;

    private static Context sContext;

    private static AppComponent sAppComponent;

    public static synchronized BaseApplication getInstance() {
        return sApplication;
    }

    public static synchronized Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        sContext = getApplicationContext();
        // 初始化Module类的Application
        initModulesApplication();
        // 初始化ARouter
        initARouter();
        // 解决(# fields: 72756 > 65536)
        MultiDex.install(this);
        // 初始化Dagger AppComonpent
        initInjector();
    }

    private void initModulesApplication() {
        for (String moduleImpl : ModuleConfig.APPLICATION_LIST) {
            try {
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof IModuleApplication) {
                    ((IModuleApplication) obj).onCreate(BaseApplication.getInstance());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(sApplication);
    }

    private void initInjector() {
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }
}
