package com.bynn.marketll.module_main.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bynn.common.base.BaseActivity;
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
    // 搜索关键字，用static保存便于传递
    public static String sKeyword;

    @BindView(R2.id.iv_back)
    ImageView   mIvBack;
    @BindView(R2.id.et_keyword)
    EditText    mEtKeyword;
    @BindView(R2.id.tv_cancel)
    TextView    mTvCancel;
    @BindView(R2.id.frameLayout)
    FrameLayout mFrameLayout;

    private SearchRecordFragment  mSearchRecordFragment;
    private SearchKeywordFragment mSearchKeywordFragment;
    private SearchResultFragment  mSearchResultFragment;
    private Fragment              mLastShowFragment;

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

    private void initView() {
        mEtKeyword.setFocusable(true);
        mEtKeyword.setFocusableInTouchMode(true);
        mEtKeyword.requestFocus();
        mEtKeyword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEtKeyword.setFocusable(true);
                mEtKeyword.setFocusableInTouchMode(true);
                mEtKeyword.requestFocus();
                return true;
            }
        });
        mEtKeyword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showKeywordFragment();
                }
            }
        });
        mEtKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showKeywordFragment();
            }
        });

        mSearchRecordFragment = SearchRecordFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, mSearchRecordFragment)
                .commit();
        mLastShowFragment = mSearchRecordFragment;
    }

    private void showKeywordFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(mLastShowFragment);
        if (mSearchKeywordFragment == null) {
            mSearchKeywordFragment = SearchKeywordFragment.newInstance();
            ft.add(R.id.frameLayout, mSearchKeywordFragment).commit();
        } else {
            ft.show(mSearchKeywordFragment).commit();
            mSearchKeywordFragment.startSearch();
        }
        mLastShowFragment = mSearchKeywordFragment;
    }

    /**
     * 设置搜索关键字
     *
     * @param keyword
     */
    public void setKeyword(String keyword) {
        if (mEtKeyword != null) {
            sKeyword = keyword;
            mEtKeyword.setText(keyword);
            mEtKeyword.setSelection(keyword.length());
            mEtKeyword.setFocusable(false);

            SoftInputUtils.hideSoftInput(this, mEtKeyword);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mLastShowFragment);
            if (mSearchResultFragment == null) {
                mSearchResultFragment = SearchResultFragment.newInstance();
                ft.add(R.id.frameLayout, mSearchResultFragment).commit();
            } else {
                ft.show(mSearchResultFragment).commit();
                mSearchResultFragment.startSearch();
            }
            mLastShowFragment = mSearchResultFragment;
        }
    }
}
