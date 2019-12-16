package com.bynn.common.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bynn.common.R;
import com.bynn.common.qmui.QMUIStatusBarHelper;
import com.bynn.common.utils.ToastUtils;
import com.bynn.common.view.ProgressDialog;

public class BaseFragment extends Fragment implements IBaseView {

    protected final String TAG = getClass().getSimpleName();

    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
    }

    /**
     * 透明状态栏
     * 必须在setContentView下调用
     */
    protected void translucentStatusBar() {
        QMUIStatusBarHelper.translucent(getActivity());
//        if (!QMUIStatusBarHelper.setStatusBarLightMode(this)) {
//            compatStatusBar();
//        }
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
                    mProgressDialog = new ProgressDialog(getContext(), getString(resId));
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
        ToastUtils.showShort(getContext(), message);
    }

    @Override
    public void showToast(@StringRes int resId) {
        showToast(getString(resId));
    }
}
