package com.bynn.marketll.module_home;

import com.bynn.common.bean.GoodsResult;
import com.bynn.common.bean.RecommendGoodsResult;
import com.bynn.lib_basic.interfaces.IBaseView;
import com.bynn.lib_basic.network.ResponseException;
import com.bynn.lib_basic.network.ResponseObserver;
import com.bynn.lib_basic.network.ResponseResult;
import com.bynn.lib_basic.presenter.BasePresenter;
import com.bynn.lib_basic.utils.RxJavaUtils;
import com.bynn.marketll.module_home.bean.AppModuleResult;
import com.bynn.marketll.module_home.bean.ChartParamResult;
import com.bynn.marketll.module_home.bean.ChoicenessResult;
import com.bynn.marketll.module_home.bean.GoodsPropertyResult;
import com.bynn.marketll.module_home.bean.GoodsTypeResult;
import com.bynn.marketll.module_home.bean.NavInfoByPageResult;
import com.bynn.marketll.module_home.bean.NavInfoResult;
import com.bynn.marketll.module_home.bean.SpecialInfoResult;
import com.bynn.marketll.module_home.bean.TopNavResult;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends BasePresenter {
    public static final int PAGE_SIZE = 10;

    private HomeModel mHomeModel;

    @Inject
    public HomePresenter(IBaseView iBaseView, HomeModel homeModel) {
        this.mIBaseView = iBaseView;
        this.mHomeModel = homeModel;
    }

    /**
     * 获取首页滚动导航栏标签
     */
    public void getTopNav() {
        mHomeModel.getTopNav()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TopNavResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(TopNavResult topNavResult) {
                        if (topNavResult.isSuccess()) {
                            mIBaseView.onSuccess(topNavResult);
                        } else {
                            mIBaseView.onFailure(new ResponseException(topNavResult));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mIBaseView.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取精选Tab
     *
     * @return
     */
    public void getHomeChoiceness() {
        mHomeModel.getHomeChoiceness()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChoicenessResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ChoicenessResult choicenessResult) {
                        if (choicenessResult.isSuccess()) {
                            mIBaseView.onSuccess(choicenessResult);
                        } else {
                            mIBaseView.onFailure(new ResponseException(choicenessResult));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mIBaseView.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取其它Tab
     *
     * @param id 类型
     */
    public void getHomeNavInfoByPage(int id, int page) {
        Observable observable = null;
        if (page == 0) {
            observable = mHomeModel.getHomeNavInfo(id);
        } else {
            observable = mHomeModel.getHomeNavInfoByPage(id, page);
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Object result) {
                        ResponseResult networkResult = (ResponseResult) result;
                        if (networkResult.isSuccess()) {
                            if (result instanceof NavInfoResult) {
                                mIBaseView.onSuccess(NavInfoByPageResult.build((NavInfoResult) result));
                            } else {
                                mIBaseView.onSuccess((NavInfoByPageResult) result);
                            }
                        } else {
                            mIBaseView.onFailure(new ResponseException(networkResult));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mIBaseView.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取手机壳定制
     *
     * @param moduleId
     * @param page
     */
    public void getAppModuleById(int moduleId, int page) {
        mHomeModel.getAppModuleById(moduleId, page)
                .compose(RxJavaUtils.applySchedulers(this))
                .subscribe(new ResponseObserver<AppModuleResult>() {
                    @Override
                    public void onSuccess(AppModuleResult data) {
                        mIBaseView.onSuccess(data);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        mIBaseView.onFailure(e);
                    }
                });
    }

    /**
     * 其它定制
     *
     * @param id
     * @param type
     */
    public void getSpecialInfo(int id, int type) {
        mHomeModel.getSpecialInfo(id, type)
                .compose(RxJavaUtils.applySchedulers(this))
                .subscribe(new ResponseObserver<SpecialInfoResult>() {
                    @Override
                    public void onSuccess(SpecialInfoResult data) {
                        mIBaseView.onSuccess(data);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        mIBaseView.onFailure(e);
                    }
                });
    }

    /**
     * 发现好物，获取好物类型
     */
    public void getGoodsType() {
        mHomeModel.getGoodsType()
                .compose(RxJavaUtils.applySchedulers(this))
                .subscribe(new ResponseObserver<GoodsTypeResult>() {
                    @Override
                    public void onSuccess(GoodsTypeResult data) {
                        mIBaseView.onSuccess(data);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        mIBaseView.onFailure(e);
                    }
                });
    }

    /**
     * 发现好物，根据类型获取物品数据
     *
     * @param id
     * @param page
     */
    public void getGoods(int id, int page) {
        mHomeModel.getGoods(id, page)
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

    /**
     * 获取指定的商品
     *
     * @param id
     * @param type
     * @param userId
     */
    public void getPointGood(int id, int type, int userId) {
        mHomeModel.getPointGood(id, type, userId)
                .compose(RxJavaUtils.applySchedulers(this))
                .subscribe(new ResponseObserver<GoodsResult>() {
                    @Override
                    public void onSuccess(GoodsResult data) {
                        mIBaseView.onSuccess(data);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        mIBaseView.onFailure(e);
                    }
                });
    }

    /**
     * 产品Banner、分享标题和分享logo、商品H5、详情H5、评价H5
     *
     * @param type
     * @param id
     */
    public void chartParam2(int type, int id) {
        mHomeModel.chartParam2(type, id)
                .compose(RxJavaUtils.applySchedulers(this))
                .subscribe(new ResponseObserver<ChartParamResult>() {
                    @Override
                    public void onSuccess(ChartParamResult data) {
                        mIBaseView.onSuccess(data);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        mIBaseView.onFailure(e);
                    }
                });
    }

    /**
     * 产品属性，规格
     *
     * @param id
     */
    public void goodsProperty(int id) {
        mHomeModel.goodsProperty(id)
                .compose(RxJavaUtils.applySchedulers(this))
                .subscribe(new ResponseObserver<GoodsPropertyResult>() {
                    @Override
                    public void onSuccess(GoodsPropertyResult data) {
                        mIBaseView.onSuccess(data);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        mIBaseView.onFailure(e);
                    }
                });
    }
}