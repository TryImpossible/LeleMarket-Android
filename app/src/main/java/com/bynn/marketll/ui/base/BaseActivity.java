package com.bynn.marketll.ui.base;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import com.bynn.common.qmui.QMUIStatusBarHelper;
import com.bynn.common.view.ProgressDialog;
import com.bynn.marketll.R;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class BaseActivity extends AppCompatActivity implements IBaseView {

    protected final String TAG = this.getClass().getSimpleName();

    private ProgressDialog mProgressDialog;

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
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.light_status_backgroud));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
            View statusBarView = new View(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, QMUIStatusBarHelper.getStatusbarHeight(this));
            statusBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.light_status_backgroud));
            contentView.addView(statusBarView, layoutParams);
        }
    }

    @Override
    public void showProgress() {
        showProgress(R.string.label_progress_text);
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
    public void showToast(String text) {

    }

    @Override
    public void showToast(@StringRes int resId) {

    }
}
