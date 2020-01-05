package com.bynn.marketll.module_main.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bynn.common.base.BaseActivity;
import com.bynn.common.qmui.QMUIDisplayHelper;
import com.bynn.common.utils.SoftInputUtils;
import com.bynn.marketll.module_main.R;
import com.bynn.marketll.module_main.R2;
import com.bynn.marketll.module_main.adapter.SearchResultAdapter;
import com.bynn.marketll.module_main.fragment.SearchKeywordFragment;
import com.bynn.marketll.module_main.fragment.SearchRecordFragment;
import com.bynn.marketll.module_main.fragment.SearchResultFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @BindView(R2.id.iv_back)
    ImageView mIvBack;
    @BindView(R2.id.ll_input_keyword)
    LinearLayout mLlInputKeyword;
    @BindView(R2.id.et_keyword)
    EditText mEtKeyword;
    @BindView(R2.id.iv_close)
    ImageView mIvClose;
    @BindView(R2.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R2.id.frameLayout)
    FrameLayout mFrameLayout;

    private SearchRecordFragment mSearchRecordFragment;
    private SearchKeywordFragment mSearchKeywordFragment;
    private SearchResultFragment mSearchResultFragment;
    private Fragment mLastShowFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_search);
        mUnbinder = ButterKnife.bind(this);
        initView();
    }

    @OnClick(R2.id.iv_back)
    public void onBackClick(View view) {
        finish();
    }

    @OnClick(R2.id.iv_close)
    public void onCloseClick() {
        mEtKeyword.setText("");
    }

    @OnClick(R2.id.tv_cancel)
    public void onCancelClick() {
        if (mSearchResultFragment == null) {
            finish();
        } else {
            SoftInputUtils.hideSoftInput(getApplicationContext(), mEtKeyword);
            mEtKeyword.setFocusable(false);

            showBackIconAnima(null);
            showBackIconAnima(null);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (mLastShowFragment != null) {
                ft.hide(mLastShowFragment);
            }
            if (mSearchResultFragment == null) {
                mSearchResultFragment = SearchResultFragment.newInstance();
                ft.add(R.id.frameLayout, mSearchResultFragment);
            } else {
                ft.show(mSearchResultFragment);
            }
            ft.commit();
            mLastShowFragment = mSearchResultFragment;
        }
    }

    private void initView() {
        mEtKeyword.setFocusable(true);
        mEtKeyword.setFocusableInTouchMode(true);
        mEtKeyword.requestFocus();
        SoftInputUtils.showSoftInput(SearchActivity.this, mEtKeyword);
        mEtKeyword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEtKeyword.setFocusable(true);
                mEtKeyword.setFocusableInTouchMode(true);
                mEtKeyword.requestFocus();
                return false;
            }
        });


        mEtKeyword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    SoftInputUtils.showSoftInput(SearchActivity.this, mEtKeyword);
                    hideBackIconAnima(new Runnable() {
                        @Override
                        public void run() {
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            if (mLastShowFragment != null) {
                                ft.hide(mLastShowFragment);
                            }
                            if (mSearchKeywordFragment == null) {
                                mSearchKeywordFragment = SearchKeywordFragment.newInstance();
                                ft.add(R.id.frameLayout, mSearchKeywordFragment);
                            } else {
                                ft.show(mSearchKeywordFragment);
                            }
                            ft.commit();
                            mLastShowFragment = mSearchKeywordFragment;
                        }
                    });
                }
            }
        });
        mEtKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEtKeyword.getText().toString().trim().length() > 0) {
                    mIvClose.setVisibility(View.VISIBLE);
                } else {
                    mIvClose.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mEtKeyword.isFocused()) {
                    return;
                }
                if (mEtKeyword.getText().toString().trim().length() > 0) {
                    showKeywordFragment();
                } else {
                    showRecordFragment();
                }
            }
        });
        mEtKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //do something;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String title = mEtKeyword.getText().toString().trim();
                            if (TextUtils.isEmpty(title)) {
                                return;
                            }
//                            saveSearchRecord(title);
                            SoftInputUtils.hideSoftInput(getApplicationContext(), mEtKeyword);
                            mEtKeyword.setFocusable(false);
                            showBackIconAnima(new Runnable() {
                                @Override
                                public void run() {
                                    showResultFragment();
                                }
                            });
                        }
                    }, 200);
                    return true;
                }
                return false;
            }
        });

        showRecordFragment();
    }


    /**
     * 设置搜索关键字
     *
     * @param keyword
     */
    public void setKeyword(String keyword) {
        if (mEtKeyword != null) {
            SoftInputUtils.hideSoftInput(this, mEtKeyword);

            mEtKeyword.setFocusable(false);
            mEtKeyword.setText(keyword);
            mEtKeyword.setSelection(keyword.length());
            mIvClose.setVisibility(View.GONE);

            showBackIconAnima(new Runnable() {
                @Override
                public void run() {
                    showResultFragment();
                }
            });
        }
    }

    public String getKeyword() {
        if (mEtKeyword != null) {
            return mEtKeyword.getText().toString().trim();
        }
        return "";
    }

    private void showRecordFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mLastShowFragment != null) {
            ft.hide(mLastShowFragment);
        }
        if (mSearchRecordFragment == null) {
            mSearchRecordFragment = SearchRecordFragment.newInstance();
            ft.add(R.id.frameLayout, mSearchRecordFragment);
        } else {
            ft.show(mSearchRecordFragment);
        }
        ft.commit();
        mLastShowFragment = mSearchRecordFragment;
    }

    private void showKeywordFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mLastShowFragment != null) {
            ft.hide(mLastShowFragment);
        }
        if (mSearchKeywordFragment == null) {
            mSearchKeywordFragment = SearchKeywordFragment.newInstance();
            ft.add(R.id.frameLayout, mSearchKeywordFragment).commit();
        } else {
            ft.show(mSearchKeywordFragment).commit();
            mSearchKeywordFragment.startSearch();
        }
        mLastShowFragment = mSearchKeywordFragment;
    }

    private void showResultFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mLastShowFragment != null) {
            ft.hide(mLastShowFragment);
        }
        if (mSearchResultFragment == null) {
            mSearchResultFragment = SearchResultFragment.newInstance();
            ft.add(R.id.frameLayout, mSearchResultFragment).commit();
        } else {
            ft.show(mSearchResultFragment).commit();
            mSearchResultFragment.startSearch();
        }
        mLastShowFragment = mSearchResultFragment;
    }

    private void showBackIconAnima(Runnable runnable) {
        mLlInputKeyword.animate()
                .translationX(mIvBack.getWidth() - mLlInputKeyword.getPaddingLeft())
                .setDuration(100)
                .start();
        mTvCancel.animate()
                .translationX(mIvBack.getWidth() - mLlInputKeyword.getPaddingLeft())
                .alpha(0)
                .withEndAction(runnable)
                .setDuration(100)
                .start();

        // TODO: 补间动画实现有问题，改用属性动画
//        TranslateAnimation translateAnima = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.f, Animation.ABSOLUTE, mIvBack.getWidth() - mLlInputKeyword.getPaddingLeft(), Animation.RELATIVE_TO_SELF, 0.f, Animation.RELATIVE_TO_SELF, 0.f);
//        translateAnima.setDuration(100);
//        translateAnima.setFillAfter(true);
//        translateAnima.setFillBefore(false);
//        mLlInputKeyword.setAnimation(translateAnima);
//        translateAnima.start();
//
//        AnimationSet animaSet = new AnimationSet(false);
//        animaSet.addAnimation(new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.f, Animation.ABSOLUTE, mIvBack.getWidth() - mLlInputKeyword.getPaddingLeft(), Animation.RELATIVE_TO_SELF, 0.f, Animation.RELATIVE_TO_SELF, 0.f));
//        animaSet.addAnimation(new AlphaAnimation(1, 0));
//        animaSet.setDuration(100);
//        animaSet.setFillAfter(true);
//        animaSet.setFillBefore(false);
//        if (listener != null) {
//            animaSet.setAnimationListener(listener);
//        }
//        mTvCancel.setAnimation(animaSet);
//        animaSet.start();

    }

    private void hideBackIconAnima(Runnable runnable) {
        mLlInputKeyword.animate()
                .translationX(0.f)
                .setDuration(100)
                .start();
        mTvCancel.animate()
                .translationX(0.f)
                .alpha(1)
                .withEndAction(runnable)
                .setDuration(100)
                .start();

        // TODO: 补间动画实现有问题，改用属性动画
//        TranslateAnimation translateAnima = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.f, Animation.ABSOLUTE, 0.f, Animation.RELATIVE_TO_SELF, 0.f, Animation.RELATIVE_TO_SELF, 0.f);
//        translateAnima.setDuration(100);
//        translateAnima.setFillAfter(true);
//        translateAnima.setFillBefore(false);
//        mLlInputKeyword.setAnimation(translateAnima);
//        translateAnima.start();
//
//        AnimationSet animaSet = new AnimationSet(false);
//        animaSet.addAnimation(new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.f, Animation.ABSOLUTE, 0.f, Animation.RELATIVE_TO_SELF, 0.f, Animation.RELATIVE_TO_SELF, 0.f));
//        animaSet.addAnimation(new AlphaAnimation(0, 1));
//        animaSet.setDuration(100);
//        animaSet.setFillAfter(true);
//        animaSet.setFillBefore(false);
//        if (listener != null) {
//            animaSet.setAnimationListener(listener);
//        }
//        mTvCancel.setAnimation(animaSet);
//        animaSet.start();
    }
}
