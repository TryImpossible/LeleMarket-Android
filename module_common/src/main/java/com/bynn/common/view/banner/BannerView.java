package com.bynn.common.view.banner;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bynn.common.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BannerView extends FrameLayout {
    /**
     * 上下文
     */
    private Context      mContext;
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

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 初始化参数
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;

        View view = LayoutInflater.from(context).inflate(R.layout.common_banner_viewpager, this);
        mViewPager = view.findViewById(R.id.view_pager);
        mIndicator = view.findViewById(R.id.indicator);

        mLastPosition = 0;
        mIsAutoPlay = true;
        mIsLoop = true;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonBannerView, defStyleAttr, 0);
        if (null != typedArray) {
            mIsAutoPlay = typedArray.getBoolean(R.styleable.CommonBannerView_auto_play, mIsAutoPlay);
            mIsLoop = typedArray.getBoolean(R.styleable.CommonBannerView_loop, mIsLoop);
        }
    }

    public void setImageList(@NonNull List<String> imageList) {
        setImageList(imageList, 0);
    }

    public void setImageList(@NotNull List<String> imageList, int roundingRadius) {
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
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                container.addView(imageView);

                if (roundingRadius > 0) {
                    RoundedCorners roundedCorners = new RoundedCorners(roundingRadius);
                    RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
                    Glide.with(mContext)
                            .load(mImageList.get(position))
                            .apply(options)
                            .into(imageView);
                } else {
                    Glide.with(mContext)
                            .load(mImageList.get(position))
                            .into(imageView);
                }
                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                // 销毁时，操作容器移除图片
                container.removeView((View) object);
            }
        };

        mViewPager.setAdapter(mPagerAdapter);
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

        mIndicator.show(length);
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

//    /**
//     * 设置指示器
//     *
//     * @param layoutId
//     */
//    public void setViewPager(@LayoutRes int layoutId) {
//        mViewPager = (ViewPager) LayoutInflater.from(mContext).inflate(layoutId, this);
//    }

    /**
     * 获取ViewPager
     *
     * @return
     */
    public ViewPager getViewPgaer() {
        return mViewPager;
    }

    /**
     * 设置指示器
     *
     * @param layoutId
     */
    public void setIndicator(@LayoutRes int layoutId) {
        mIndicator = (Indicator) LayoutInflater.from(mContext).inflate(layoutId, this);
    }

    /**
     * 获取指示器
     *
     * @return
     */
    public Indicator getIndicator() {
        return mIndicator;
    }
}
