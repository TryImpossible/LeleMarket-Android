package com.bynn.marketll.module_main.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bynn.common.arouter.HomeRoutePath;
import com.bynn.common.arouter.MainRoutePath;
import com.bynn.common.base.BaseActivity;
import com.bynn.marketll.module_main.R;
import com.bynn.marketll.module_main.R2;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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

        translucentStatusBar();

        final int count = 5;
        Observable.interval(1, 1, TimeUnit.SECONDS)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        // 禁用按钮
                        mDisposable = disposable;
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (mBtnSkip.getVisibility() == View.GONE) {
                            mBtnSkip.setVisibility(View.VISIBLE);
                        }
                        mBtnSkip.setText(String.format(getString(R.string.main_label_splash_skip), aLong));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        ARouter.getInstance().build(MainRoutePath.MAIN_ACTIVITY).navigation();
                        // 开启按钮
                    }
                });
    }

    @OnClick(R2.id.btn_skip)
    public void onSkipClicked() {
        ARouter.getInstance().build(MainRoutePath.MAIN_ACTIVITY).navigation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mDisposable) {
            if (!mDisposable.isDisposed()) {
                mDisposable.dispose();
                mDisposable = null;
            }
        }
    }
}
