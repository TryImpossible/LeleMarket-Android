package com.bynn.marketll.module_mine.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bynn.common.arouter.MineRoutePath;
import com.bynn.common.base.BaseActivity;
import com.bynn.common.view.loadstate.LoadStateLayout;
import com.bynn.marketll.module_mine.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Route(path = MineRoutePath.SEE_MORE_ACTIVITY)
public class SeeMoreActivity extends BaseActivity {

    @BindView(R.id.loadStateLayout) LoadStateLayout mLoadStateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_see_more);
        ButterKnife.bind(this);

        Observable.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick({R.id.btn_loading, R.id.btn_failure, R.id.btn_empty, R.id.btn_no_network})
    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_loading:
                mLoadStateLayout.showLoading();
                break;
            case R.id.btn_failure:
                mLoadStateLayout.showFailure();
                break;
            case R.id.btn_empty:
                mLoadStateLayout.showEmpty();
                break;
            case R.id.btn_no_network:
                mLoadStateLayout.showNoNetwork();
                break;
            default:
                break;
        }
    }
}
