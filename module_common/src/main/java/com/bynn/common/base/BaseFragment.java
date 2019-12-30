package com.bynn.common.base;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bynn.common.R;
import com.bynn.common.exception.NetworkResultException;
import com.bynn.common.qmui.QMUIStatusBarHelper;
import com.bynn.common.utils.ToastUtils;
import com.bynn.common.view.loadstate.EmptyView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class BaseFragment extends Fragment implements IBaseView {

    protected final String TAG = getClass().getSimpleName();

//    private ProgressDialog mProgressDialog;

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
        ((BaseActivity) getActivity()).showProgress(resId);
//        if (null == mProgressDialog) {
//            synchronized (this) {
//                if (null == mProgressDialog) {
//                    mProgressDialog = new ProgressDialog(getContext(), getString(resId));
//                }
//            }
//        } else {
//            mProgressDialog.setText(getString(resId));
//        }
//        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        ((BaseActivity) getActivity()).hideProgress();
//        if (null != mProgressDialog) {
//            if (mProgressDialog.isShowing()) {
//                mProgressDialog.dismiss();
//            }
//        }
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
        if (getActivity().isFinishing()) {
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
        EmptyView emptyView = new EmptyView(getContext());

        ViewGroup root = getActivity().findViewById(android.R.id.content);
        root.addView(emptyView);
    }
}
