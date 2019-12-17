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

    protected void addDisposable(Disposable disposable) {
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected void disposeAll() {
        if (null == mCompositeDisposable) {
            return;
        }
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
        mCompositeDisposable = null;
    }
}
