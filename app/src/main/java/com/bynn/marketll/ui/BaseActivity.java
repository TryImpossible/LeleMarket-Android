package com.bynn.marketll.ui;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import com.bynn.marketll.R;
import com.bynn.marketll.qmui.QMUIDisplayHelper;
import com.bynn.marketll.qmui.QMUIStatusBarHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class BaseActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();

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
        };
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
}
