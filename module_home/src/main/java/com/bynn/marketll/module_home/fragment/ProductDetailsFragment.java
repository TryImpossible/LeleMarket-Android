package com.bynn.marketll.module_home.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bynn.lib_basic.fragment.BaseFragment;
import com.bynn.lib_basic.qmui.QMUIStatusBarHelper;
import com.bynn.lib_basic.qmui.webview.QMUIWebView;
import com.bynn.lib_basic.qmui.webview.QMUIWebViewClient;
import com.bynn.marketll.module_home.R;
import com.bynn.marketll.module_home.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends BaseFragment {

    @BindView(R2.id.header)
    LinearLayout mHeader;
    @BindView(R2.id.webview)
    QMUIWebView mWebView;

    public static ProductDetailsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment_product_details, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initNotch();
        initView();
        return view;
    }

    public void initNotch() {
        int top = QMUIStatusBarHelper.getStatusbarHeight(getContext());
        mHeader.setPadding(0, top,0,0);
    }

    private void initView() {
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
        });
        mWebView.setWebChromeClient(new WebChromeClient());

        mWebView.loadUrl("https://api.51app.cn/diyMall/v3.0.0/diyDetails.html?id=456&type=4");
    }

    private void handleWebUrl(WebView view, String url) {
        if (url.startsWith("mailto:")) {
            //Handle mail Urls
            startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(url)));
        } else if (url.startsWith("tel:")) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                //Handle telephony Urls
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        } else {
            view.loadUrl(url);
        }
    }
}
