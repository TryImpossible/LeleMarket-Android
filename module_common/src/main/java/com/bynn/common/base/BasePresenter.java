package com.bynn.common.base;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter implements IPresenter {
    protected IBaseView           mIBaseView;
    private   CompositeDisposable mCompositeDisposable;

    @Override
    public void onDestroy(LifecycleOwner owner) {
        Log.e("BasePresenter", "onDestroy" + getClass().getSimpleName());
        disposeAll();
    }

    /**
     * 添加当前View的Disposable
     *
     * @param disposable
     */
    protected void addDisposable(Disposable disposable) {
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 释放所有Disposable
     */
    protected void disposeAll() {
        if (null == mCompositeDisposable) {
            return;
        }
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
        mCompositeDisposable = null;
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     *
     * @return
     */
    public boolean isViewAttached() {
        return this.mIBaseView != null;
    }
}
