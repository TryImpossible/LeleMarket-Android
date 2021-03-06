package com.bynn.lib_basic;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bynn.lib_basic.config.ModuleConfig;
import com.bynn.lib_basic.dagger.AppComponent;
import com.bynn.lib_basic.dagger.AppModule;
import com.bynn.lib_basic.dagger.DaggerAppComponent;
import com.bynn.lib_basic.interfaces.IModuleApplication;
import com.bynn.lib_basic.utils.DensityHelp;
import com.bynn.lib_basic.utils.SpanUtils;
import com.bynn.lib_basic.utils.Utils;
import com.google.gson.Gson;
import com.hwangjr.rxbus.Bus;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

public class BaseApplication extends Application {

    private static BaseApplication sApplication;

    private static Context sContext;

    private static AppComponent sAppComponent;

    public static BaseApplication getInstance() {
        return sApplication;
    }

    public static Context getContext() {
        return sContext;
    }

    public static AppComponent getAppComponent() {
        if (null == sAppComponent) {
            sAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(sApplication))
//                    .networkModule(new NetworkModule(sApplication))
                    .build();
        }
        return sAppComponent;
    }


    public static Gson getGson() {
        return getAppComponent().getGson();
    }

    public static ARouter getARouter() {
        return getAppComponent().getARoute();
    }

    public static Bus getRxBus() {
        return getAppComponent().getRxBus();
    }

    //static 代码段可以防止内存泄露
    static {
        ClassicsFooter.REFRESH_FOOTER_PULLING = "上拉加载更多";
        ClassicsFooter.REFRESH_FOOTER_RELEASE = "释放立即加载";
        ClassicsFooter.REFRESH_FOOTER_LOADING = "正在加载...";
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = "正在刷新...";
        ClassicsFooter.REFRESH_FOOTER_FINISH = "加载完成";
        ClassicsFooter.REFRESH_FOOTER_FAILED = "加载失败";
        ClassicsFooter.REFRESH_FOOTER_NOTHING = "到底啦~";
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new WaterDropHeader(context);
//                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
//                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setTextSizeTitle(13)
                        .setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        sContext = getApplicationContext();
        // 初始化屏幕密度
        DensityHelp.setDensity(sApplication);
        // 初始化SpanUtils
        SpanUtils.init(this);
        // 初始化Utils
        Utils.init(this);
        // 初始化Module类的Application
        initModulesApplication();
        // 初始化ARouter
        initARouter();
        // 解决(# fields: 72756 > 65536)
        MultiDex.install(this);
        // 初始化Dagger AppComonpent
        initInjector();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //7.0严格模式
            StrictMode.VmPolicy.Builder builder =
                    new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
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
//                .networkModule(new NetworkModule(this))
                .build();
    }
}
