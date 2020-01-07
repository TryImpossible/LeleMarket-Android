package com.bynn.marketll.module_main;

import com.bynn.common.bean.RecommendGoodsResult;
import com.bynn.common.constants.NetApiConstants;
import com.bynn.lib_basic.database.HttpDao;
import com.bynn.lib_basic.network.ResponseObserver;
import com.bynn.lib_basic.presenter.BasePresenter;
import com.bynn.lib_basic.interfaces.IBaseView;
import com.bynn.lib_basic.network.ResponseException;
import com.bynn.lib_basic.utils.RxJavaUtils;
import com.bynn.marketll.module_main.bean.KeywordBean;
import com.bynn.marketll.module_main.bean.KeywordResult;
import com.bynn.marketll.module_main.database.HistorySearchDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BasePresenter {
    public static final int PAGE_SIZE = 10;

    private MainModel mMainModel;

    @Inject
    public MainPresenter(IBaseView iBaseView, MainModel mainModel) {
        this.mIBaseView = iBaseView;
        this.mMainModel = mainModel;
    }

    /**
     * 获取热门推荐
     */
    public void getRecommand() {
        mMainModel.getRecommand()
                .compose(RxJavaUtils.applySchedulers(this))
                .subscribe(new ResponseObserver<KeywordResult>() {
                    @Override
                    public void onSuccess(KeywordResult data) {
                        mIBaseView.onSuccess(data);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        mIBaseView.onFailure(e);
                    }
                });
    }

    /**
     * 获取最近搜索
     */
    public void getHistorySearch() {
        HistorySearchDao.findAll()
                .compose(RxJavaUtils.applySchedulers(this))
                .doOnNext(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        mIBaseView.onSuccess(strings);
                    }
                });
    }

    /**
     * 获取关键字
     *
     * @param name
     */
    public void getKeyword(String name) {
        mMainModel.getKeyword(name)
                .compose(RxJavaUtils.applySchedulers(this))
                .subscribe(new ResponseObserver<KeywordResult>() {
                    @Override
                    public void onSuccess(KeywordResult data) {
                        mIBaseView.onSuccess(data);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        mIBaseView.onFailure(e);
                    }
                });
    }

    /**
     * 搜索结果
     *
     * @param page 页码
     * @param name 关键字
     */
    public void getGoodsInfo(int page, String name) {
        mMainModel.getGoodsInfo(page, name)
                .compose(RxJavaUtils.applySchedulers(this))
                .subscribe(new ResponseObserver<RecommendGoodsResult>() {
                    @Override
                    public void onSuccess(RecommendGoodsResult data) {
                        mIBaseView.onSuccess(data);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        mIBaseView.onFailure(e);
                    }
                });
    }
}