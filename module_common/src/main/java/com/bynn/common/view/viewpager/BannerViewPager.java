package com.bynn.common.view.viewpager;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bynn.common.R;
import com.bynn.common.qmui.QMUIDisplayHelper;
import com.bynn.common.view.MyBanner;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BannerViewPager extends LinearLayout {
    /**
     * 上下文
     */
    private Context      mContext;
    /**
     * 容器
     */
    private FrameLayout  mFlContainer;
    /**
     * ViewPager,实现图片切换
     */
    private ViewPager    mViewPager;
    /**
     * PagerAdapter
     */
    private PagerAdapter mPagerAdapter;
    /**
     * 图片集合
     */
    private List<String> mImageList;
    /**
     * 上次选中的位置
     */
    private int          mLastPosition;
    /**
     * rxjava避免内存泄露，这里直接理解为清除定时器
     */
    private Disposable   mDisposable;
    /**
     * 是否自动播放
     */
    private boolean      mIsAutoPlay;
    /**
     * 是否循环播放
     */
    private boolean      mIsLoop;
    /**
     * 指示器
     */
    private Indicator    mIndicator;

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerViewPager(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BannerViewPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 初始化参数
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        mContext = context;

        mIndicator = new Indicator(mContext);
        mIndicator.setItemSize(QMUIDisplayHelper.dp2px(mContext, 6));
        mIndicator.setColor(ContextCompat.getColor(mContext, R.color.common_white));
        mIndicator.setCheckedColor(ContextCompat.getColor(mContext, R.color.common_colorAccent));
        mIndicator.setItemMargin(QMUIDisplayHelper.dp2px(mContext, 8));

        mFlContainer = new FrameLayout(mContext);
        mFlContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFlContainer.addView(mIndicator);

        mLastPosition = 0;
        mIsAutoPlay = true;
        mIsLoop = true;
    }

    public void setImageList(@NotNull List<String> imageList) {
        mImageList = imageList;
        int length = imageList.size();
        mPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                // 初始化时，往容器中添加图片

                position = position % length; // 计算实际的位置
                ImageView imageView = new ImageView(mContext);
                Glide.with(mContext)
                        .load(mImageList.get(position))
                        .into(imageView);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                // 销毁时，操作容器移除图片
                container.removeView((View) object);
            }
        };

        mViewPager.setAdapter(mPagerAdapter);
        // 一次加载2张，加上本身ViewPgaer的1张，总共3张
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % length;
                mIndicator.setCheckedItem(position);
                mLastPosition = position;
                if (!mIsLoop) {
                    stopPlay();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // SCROLL_STATE_IDLE ：空闲状态
                // SCROLL_STATE_DRAGGING ：滑动状态
                // SCROLL_STATE_SETTLING ：滑动后滑翔的状态
            }
        });
        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mIsAutoPlay) {
                            stopPlay();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mIsAutoPlay) {
                            startPlay();
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        mViewPager.setPageMargin(100);
        mViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            private static final float MIN_SCALE = 0.70f;
            private static final float MIN_ALPHA = 0.5f;

            @Override
            public void transformPage(@NonNull View page, float position) {
                if (position < -1 || position > 1) {
                    page.setAlpha(MIN_ALPHA);
                    page.setScaleX(MIN_SCALE);
                    page.setScaleY(MIN_SCALE);
                } else if (position <= 1) { // [-1,1]
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    if (position < 0) {
                        float scaleX = 1 + 0.3f * position;
                        Log.e("google_lenve_fb", "transformPage: scaleX:" + scaleX);
                        page.setScaleX(scaleX);
                        page.setScaleY(scaleX);
                    } else {
                        float scaleX = 1 - 0.3f * position;
                        page.setScaleX(scaleX);
                        page.setScaleY(scaleX);
                    }
                    page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
                }
            }
        });

        mIndicator.show();
    }

    /**
     * 每页的外边距
     *
     * @param marginPixels
     */
    public void setPageMargin(int marginPixels) {
        if (null != mViewPager) {
            mViewPager.setPageMargin(marginPixels);
        }
    }

    /**
     * 设置当前选中
     *
     * @param item 位置
     */
    public void setCurrentItem(int item) {
        if (null != mViewPager) {
            mViewPager.setCurrentItem(item);
        }
    }

    /**
     * 设置当前选中
     *
     * @param item         位置
     * @param smoothScroll 是否平滑滚动
     */
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (null != mViewPager) {
            mViewPager.setCurrentItem(item, smoothScroll);
        }
    }

    /**
     * 是否自动播放
     *
     * @return
     */
    public boolean isAutoPlay() {
        return mIsAutoPlay;
    }

    /**
     * 是否循环播放
     *
     * @return
     */
    public boolean isLoop() {
        return mIsLoop;
    }

    /**
     * 设置是否循环播放
     *
     * @param isLoop
     */
    public void setLoop(boolean isLoop) {
        mIsLoop = isLoop;
    }

    /**
     * 设置是否自动播放
     *
     * @param isAutoPlay
     */
    public void setAutoPlay(boolean isAutoPlay) {
        mIsAutoPlay = isAutoPlay;
    }

    /**
     * 开始播放
     */
    public void startPlay() {
        Observable.interval(1, 5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 停止播放
     */
    public void stopPlay() {
        if (null != mDisposable && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    /**
     * 设置指示器
     */
    public void setIndicator(@NonNull Indicator indicator) {
        mIndicator = indicator;
    }

    /**
     * 设置指示器是否显示
     *
     * @param visible
     */
    public void setIndicatorVisible(boolean visible) {
        mIndicator.setVisible(visible);
    }
}
