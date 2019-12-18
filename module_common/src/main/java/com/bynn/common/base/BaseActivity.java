package com.bynn.common.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bynn.common.R;
import com.bynn.common.exception.NetworkResultException;
import com.bynn.common.qmui.QMUIStatusBarHelper;
import com.bynn.common.utils.DensityHelp;
import com.bynn.common.utils.ToastUtils;
import com.bynn.common.view.ProgressDialog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class BaseActivity extends AppCompatActivity implements IBaseView {

    protected final String TAG = this.getClass().getSimpleName();

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DensityHelp.setOrientationWidth(this, false);
        ARouter.getInstance().inject(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        if (!QMUIStatusBarHelper.setStatusBarLightMode(this)) {
            compatStatusBar();
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        if (!QMUIStatusBarHelper.setStatusBarLightMode(this)) {
            compatStatusBar();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (!QMUIStatusBarHelper.setStatusBarLightMode(this)) {
            compatStatusBar();
        }
    }

    /**
     * 透明状态栏
     * 必须在setContentView下调用
     */
    protected void translucentStatusBar() {
        QMUIStatusBarHelper.translucent(this);
//        if (!QMUIStatusBarHelper.setStatusBarLightMode(this)) {
//            compatStatusBar();
//        }
    }

    /**
     * 兼容StatusBar
     */
    protected void compatStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.common_light_status_backgroud));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
            View statusBarView = new View(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, QMUIStatusBarHelper.getStatusbarHeight(this));
            statusBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.common_light_status_backgroud));
            contentView.addView(statusBarView, layoutParams);
        }
    }

    @Override
    public void showProgress() {
        showProgress(R.string.common_label_progress_message);
    }

    @Override
    public void showProgress(@StringRes int resId) {
        if (null == mProgressDialog) {
            synchronized (this) {
                if (null == mProgressDialog) {
                    mProgressDialog = new ProgressDialog(this, getString(resId));
                }
            }
        } else {
            mProgressDialog.setText(getString(resId));
        }
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (null != mProgressDialog) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    @Override
    public void showToast(String message) {
        ToastUtils.showShort(this, message);
    }

    @Override
    public void showToast(@StringRes int resId) {
        showToast(getString(resId));
    }

    @Override
    public void onSuccess(Object successObj) {
        hideProgress();
    }

    @Override
    public void onFailure(NetworkResultException e) {
        hideProgress();
        showToast(e.getMessage());
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (isFinishing()) {
            return;
        }
        hideProgress();
        if (e instanceof UnknownHostException) {
            showToast("请检查您的网络设置");
            noNetwork();
        } else if (e instanceof SocketTimeoutException) {
            showToast("连接超时");
        } else if (e instanceof ConnectException) {
            showToast("连接出错");
        } else {
            showToast("访问出错");
        }
    }

    /**
     * 网络不可用
     */
    protected void noNetwork() {

    }
}
