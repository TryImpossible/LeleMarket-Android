package com.bynn.common.base;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bynn.common.R;
import com.bynn.common.R2;
import com.bynn.common.qmui.QMUIDisplayHelper;
import com.bynn.common.qmui.webview.QMUIWebView;
import com.bynn.common.qmui.webview.QMUIWebViewClient;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BaseWebActivity extends BaseActivity {

    @BindView(R2.id.tv_title)    TextView    mTvTitle;
    @BindView(R2.id.webview)     QMUIWebView mWebView;
    @BindView(R2.id.progressBar) ProgressBar mProgressBar;

    private int        mCurrentProgress = 0;
    private int        mTargetProgress  = 0;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_base_web);
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
        //设置true,才能让Webivew支持<meta>标签的viewport属性
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);

        mWebView.setWebViewClient(new QMUIWebViewClient(false, false) {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
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
                mTvTitle.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mTargetProgress = Math.max(30, newProgress);
            }
        });
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
}
