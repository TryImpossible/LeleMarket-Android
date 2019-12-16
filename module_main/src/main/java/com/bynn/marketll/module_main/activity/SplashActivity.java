package com.bynn.marketll.module_main.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bynn.common.arouter.MainRoutePath;
import com.bynn.common.base.BaseActivity;
import com.bynn.marketll.module_main.R;
import com.bynn.marketll.module_main.R2;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

@Route(path = MainRoutePath.SPLASH_ACTIVITY)
public class SplashActivity extends BaseActivity {

    @BindView(R2.id.btn_skip)
    Button mBtnSkip;

    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_splash);
        ButterKnife.bind(this);

        mBtnSkip.setText(String.format(getString(R.string.main_label_splash_skip), 5));
        mDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mBtnSkip.setText(String.format(getString(R.string.main_label_splash_skip), 5 - aLong));
                        if (aLong == 5) {
                            ARouter.getInstance().build(MainRoutePath.MAIN_ACTIVITY).navigation();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mDisposable) {
            if (mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
            mDisposable = null;
        }
    }
}
