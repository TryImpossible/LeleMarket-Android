package com.bynn.lib_basic.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bynn.lib_basic.R;
import com.bynn.lib_basic.interfaces.IBaseView;
import com.bynn.lib_basic.qmui.QMUIStatusBarHelper;
import com.bynn.lib_basic.utils.DensityHelp;
import com.bynn.lib_basic.utils.ToastUtils;
import com.bynn.lib_basic.view.ProgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseActivity extends AppCompatActivity implements IBaseView {

    protected final String TAG = this.getClass().getSimpleName();

    protected Unbinder       mUnbinder;
    private   ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DensityHelp.setOrientationWidth(this, false);
        ARouter.getInstance().inject(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setStatusBarLightMode();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        setStatusBarLightMode();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBarLightMode();
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
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

    protected void setStatusBarLightMode() {
        if (!QMUIStatusBarHelper.setStatusBarLightMode(this)) {
            compatStatusBar(true);
        }
    }

    protected void setStatusBarDarkMode() {
        if (!QMUIStatusBarHelper.setStatusBarDarkMode(this)) {
            compatStatusBar(false);
        }
    }

    /**
     * 兼容StatusBar
     */
    protected void compatStatusBar(boolean isLight) {
        int colorResId = R.color.basic_light_status_background;
        if (!isLight) {
            colorResId = R.color.basic_dark_status_background;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, colorResId));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
            View statusBarView = new View(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, QMUIStatusBarHelper.getStatusbarHeight(this));
            statusBarView.setBackgroundColor(ContextCompat.getColor(this, colorResId));
            contentView.addView(statusBarView, layoutParams);
        }
    }

    @Override
    public void showLoading() {
        showLoading(R.string.basic_label_progress_message);
    }

    @Override
    public void showLoading(@StringRes int resId) {
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
    public void hideLoading() {
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
        hideLoading();
    }

    @Override
    public void onFailure(Throwable e) {
        hideLoading();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (isFinishing()) {
            return;
        }
    }


    @Override
    public void onFinish() {
        hideLoading();
    }

}
