package com.bynn.lib_basic.dagger;

import android.app.Application;
import android.util.Log;

import com.bynn.lib_basic.constants.BasicConstants;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 主要对retrofit的二次封装
 * 早期版本使用
 */
@Deprecated
@Module
public class NetworkModule {

    // http缓存，15Mib
    private static final int DISK_CACHE_SIZE = 15 * 1024 * 1024;
    // 统一超时时间，单位秒
    private static final int DEFAULT_TIMEOUT = 60;

    private Retrofit             mRetrofit;
    private OkHttpClient.Builder mBuilder;
    private OkHttpClient         mOkHttpClient;

    public NetworkModule(Application application) {

        File httpCacheDir = new File(application.getCacheDir(), "http-cache");
        Cache cache = new Cache(httpCacheDir, DISK_CACHE_SIZE);

        // 创建OKHttpClient，设置超时时间
        mBuilder = new OkHttpClient.Builder();
        mBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        mBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        mBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        mOkHttpClient = mBuilder
                // 设置Loggger拦截器
                .addInterceptor(getHttpLoggingInterceptor())
                // 禁止网络差，重复请求
                .retryOnConnectionFailure(false)
                // 设置缓存
                .cache(cache)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
//                        TODO: 待处理
//                        if ("youhostname".equals(hostname)) {
//                            return true;
//                        } else {
//                            HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
//                            return hv.verify(hostname, session);
//                        }
                        return true;
                    }
                }).build();

        mRetrofit = new Retrofit.Builder().client(mOkHttpClient)
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    private String getBaseUrl() {
        return BasicConstants.BASE_RUL;
    }

    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    Log.e("OKHttp-----", URLDecoder.decode(message, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e("OKHttp-----", message);
                }
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    String provideBaseUrl() {
        return getBaseUrl();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOKHttpClientBuilder() {
        return mBuilder;
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit() {
        return mRetrofit;
    }

}