package com.bynn.lib_basic.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.bynn.lib_basic.R;
import com.bynn.lib_basic.R2;
import com.bynn.lib_basic.qmui.webview.QMUIWebView;
import com.bynn.lib_basic.qmui.webview.QMUIWebViewClient;
import com.bynn.lib_basic.utils.LogUtils;
import com.bynn.lib_basic.view.HeaderView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BaseWebActivity extends BaseActivity {

    @BindView(R2.id.header_view) HeaderView  mHeaderView;
    @BindView(R2.id.webview)
    protected                    QMUIWebView mWebView;
    @BindView(R2.id.progressBar) ProgressBar mProgressBar;

    private int        mCurrentProgress = 0;
    private int        mTargetProgress  = 0;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_activity_base_web);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        dismissProgressAnimation();
        super.onDestroy();
    }

    private void initView() {
        mHeaderView.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        WebSettings webSettings = mWebView.getSettings();
        //设置true,才能让Webivew支持<meta>标签的viewport属性
        webSettings.setUseWideViewPort(true);
        //设置webview加载的页面的模式
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //隐藏webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        // 设置支持javascript脚本
        webSettings.setJavaScriptEnabled(true);
        // 允许访问文件
        webSettings.setAllowFileAccess(true);
        // 设置显示缩放按钮
        webSettings.setBuiltInZoomControls(true);
        // 支持缩放
        webSettings.setSupportZoom(true);
        // 允许本地在存储
        webSettings.setDomStorageEnabled(true);

        //主要用于平板，针对特定屏幕代码调整分辨率
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        LogUtils.i("densityDpi = " + mDensity);
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }
        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型：
         * 1、LayoutAlgorithm.NARROW_COLUMNS ：适应内容大小
         * 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        mWebView.setWebViewClient(new QMUIWebViewClient(false, false) {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                handleWebUrl(view, url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.toString();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    url = request.getUrl().toString();
                }
                handleWebUrl(view, url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);
                dismissProgressAnimation();
            }

            @Override
            public void onPageStarted(WebView view, String url, @Nullable Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
                showProgressAnimation();
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mHeaderView.setTitleText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mTargetProgress = Math.max(30, newProgress);
            }
        });
    }

    private void handleWebUrl(WebView view, String url) {
        if (url.startsWith("mailto:")) {
            //Handle mail Urls
            startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(url)));
        } else if (url.startsWith("tel:")) {
            if (ActivityCompat.checkSelfPermission(BaseWebActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                //Handle telephony Urls
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
            } else {
                ActivityCompat.requestPermissions(BaseWebActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        } else {
            view.loadUrl(url);
        }
    }

    private void showProgressAnimation() {
        mDisposable = Observable.interval(15, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (mCurrentProgress < mTargetProgress) {
                            mCurrentProgress += 1;
                            mProgressBar.setProgress(mCurrentProgress);
                        }
                    }
                });
    }

    private void dismissProgressAnimation() {
        if (null != mDisposable) {
            if (mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
            mDisposable = null;
        }
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    public void loadData(String data, String mimeType, String encoding) {
        mWebView.loadData(data, mimeType, encoding);
    }

    public void setTitle(String title) {
        mHeaderView.setTitleText(title);
    }
}
