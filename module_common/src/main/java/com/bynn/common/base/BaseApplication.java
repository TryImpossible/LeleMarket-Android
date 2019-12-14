package com.bynn.common.base;

import android.app.Application;
import android.content.Context;

import com.bynn.common.config.ModuleConfig;

public class BaseApplication extends Application {

    private static BaseApplication sApplication;

    private static Context sContext;

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

}
