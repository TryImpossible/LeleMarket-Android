package com.bynn.marketll.module_main.activity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bynn.common.base.BaseActivity;
import com.bynn.marketll.module_main.R;
import com.bynn.marketll.module_main.R2;
import com.bynn.marketll.module_main.fragment.SearchKeywordFragment;
import com.bynn.marketll.module_main.fragment.SearchRecordFragment;
import com.bynn.marketll.module_main.fragment.SearchResultFragment;

public class SearchActivity extends BaseActivity {

    @BindView(R2.id.iv_back)
    ImageView mIvBack;
    @BindView(R2.id.et_keyword)
    EditText mEtKeyword;
    @BindView(R2.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R2.id.frameLayout)
    FrameLayout mFrameLayout;

    private SearchRecordFragment mSearchRecordFragment;
    private SearchKeywordFragment mSearchKeywordFragment;
    private SearchResultFragment mSearchResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_search);
        mUnbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mEtKeyword.setFocusable(true);
        mEtKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSearchRecordFragment = SearchRecordFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, mSearchRecordFragment)
                .commit();
    }
}
