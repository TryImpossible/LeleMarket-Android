package com.bynn.lib_basic.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bynn.lib_basic.R;
import com.bynn.lib_basic.activity.BaseActivity;
import com.bynn.lib_basic.interfaces.IBaseView;
import com.bynn.lib_basic.network.ResponseException;
import com.bynn.lib_basic.utils.ToastUtils;
import com.bynn.lib_basic.view.ProgressDialog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import butterknife.Unbinder;

public class BaseFragment extends Fragment implements IBaseView {

    protected final String TAG = getClass().getSimpleName();

    private   ProgressDialog mProgressDialog;
    protected Unbinder       mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
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
                    mProgressDialog = new ProgressDialog(getContext(), getString(resId));
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
        ToastUtils.showShort(getContext(), message);
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
        if (getActivity().isFinishing()) {
            return;
        }
    }
}
