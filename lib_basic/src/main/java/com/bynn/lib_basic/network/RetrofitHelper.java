package com.bynn.lib_basic.network;

import android.util.Log;

import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.constants.BasicConstants;
import com.bynn.lib_basic.utils.LogUtils;
import com.bynn.lib_basic.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    // http缓存，15Mib
    private static final int    DISK_CACHE_SIZE = 15 * 1024 * 1024;
    // 统一超时时间，单位秒
    private static final int    DEFAULT_TIMEOUT = 60;
    // 接口缓存天数， 14天
    public static final  int    CACHE_TIME      = 14 * 24 * 60 * 60;
    /**
     * 获取缓存，
     * 添加了only-if-cached， 没有缓存时，会显示504错误，  不加就有缓存显示缓存，没有就网络请求
     *
     * @see RetrofitHelper#CACHE_TIME
     */
    public final static  String CHACHE          = "public,only-if-cached, max-stale=" + RetrofitHelper.CACHE_TIME;

    /**
     * 获取网络
     */
    public final static String NO_CACHE = "max-age=0";

    private OkHttpClient.Builder mOkHttpClientBuilder;

    private Retrofit.Builder mRetrofitBuilder;

    public RetrofitHelper() {
        // 缓存
        File httpCacheDir = new File(BaseApplication.getContext().getCacheDir(), "http-cache");
        Cache cache = new Cache(httpCacheDir, DISK_CACHE_SIZE);

        // Logger拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    Log.i("OKHttp-----", URLDecoder.decode(message, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e("OKHttp-----", message);
                }
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 请求头拦截器，一般用于自行处理签名
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //  配置请求头
                String accessToken = "token";
                String tokenType = "tokenType";
                Request request = chain.request().newBuilder()
                        .header("app_key", "appId")
                        .header("Authorization", tokenType + " " + accessToken)
                        .header("Content-Type", "application/json")
                        .addHeader("Connection", "close")
                        .addHeader("Accept-Encoding", "identity")
                        .build();
                return chain.proceed(request);
            }
        };

        // Response拦截器
        Interceptor responseInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                return response;
            }
        };

        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isConnected()) {  //没网强制从缓存读取
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                    LogUtils.d("Okhttp", "no network");
                }

                Response originalResponse = chain.proceed(request);
                if (NetworkUtils.isConnected()) {
                    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                    String cacheControl = request.cacheControl().toString();

                    return originalResponse.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_TIME)
                            .removeHeader("Pragma")
                            .build();
                }
            }
        };

        // IP验证
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
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
        };

        mOkHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                // 设置Logger拦截器
                .addInterceptor(httpLoggingInterceptor)
                // 设置Response拦截器
                .addInterceptor(responseInterceptor)
                // 设置Cache拦截器
                .addInterceptor(cacheInterceptor)
                // 禁止网络差，重复请求
                .retryOnConnectionFailure(false)
                // https认证 如果要使用https且为自定义证书 可以去掉这两行注释，并自行配制证书
                // .sslSocketFactory(SslContextFactory.getSSLSocketFactoryForTwoWay())
                .hostnameVerifier(hostnameVerifier)
                // 设置缓存
                .cache(cache);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        OkHttpClient okHttpClient = mOkHttpClientBuilder.build();
        mRetrofitBuilder = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public String getBaseUrl() {
        return BasicConstants.BASE_RUL;
    }

    public OkHttpClient.Builder getOkHttpClientBuilder() {
        return mOkHttpClientBuilder;
    }

    public Retrofit.Builder getRetrofitBuilder() {
        return mRetrofitBuilder;
    }
}
