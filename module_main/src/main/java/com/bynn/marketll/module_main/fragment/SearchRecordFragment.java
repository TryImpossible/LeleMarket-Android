package com.bynn.marketll.module_main.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bynn.common.base.BaseActivity;
import com.bynn.common.base.BaseApplication;
import com.bynn.common.base.BaseFragment;
import com.bynn.marketll.module_main.MainPresenter;
import com.bynn.marketll.module_main.R;
import com.bynn.marketll.module_main.R2;
import com.bynn.marketll.module_main.activity.SearchActivity;
import com.bynn.marketll.module_main.adapter.SearchRecordAdapter;
import com.bynn.marketll.module_main.bean.KeywordBean;
import com.bynn.marketll.module_main.bean.KeywordResult;
import com.bynn.marketll.module_main.bean.SearchRecordBean;
import com.bynn.marketll.module_main.dagger.DaggerMainComponent;
import com.bynn.marketll.module_main.dagger.MainComponent;
import com.bynn.marketll.module_main.dagger.MainModule;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchRecordFragment extends BaseFragment {

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

    private SearchRecordAdapter mAdapter;
    private MainPresenter       mMainPresenter;

    public static SearchRecordFragment newInstance() {

        Bundle args = new Bundle();

        SearchRecordFragment fragment = new SearchRecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SearchRecordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_fragment_search_record, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        injectPresenter();
        initView();
        return view;
    }

    @Override
    public void onSuccess(Object successObj) {
        super.onSuccess(successObj);
        if (successObj instanceof KeywordResult) {
            List<KeywordBean> data = ((KeywordResult) successObj).getData();
            if (data != null && data.size() > 0) {
                mAdapter.addData(1, new SearchRecordBean(data, SearchRecordBean.TYPE_HOT));
            }
        }
    }

    private void injectPresenter() {
        MainComponent component = DaggerMainComponent.builder()
                .appComponent(BaseApplication.getAppComponent())
                .mainModule(new MainModule(this))
                .build();
        mMainPresenter = component.getMainPresenter();
        getLifecycle().addObserver(mMainPresenter);
    }

    private void initView() {

        List<SearchRecordBean> data = new ArrayList<>();
        data.add(new SearchRecordBean(true, "热门搜索"));
        data.add(new SearchRecordBean(true, "最近搜索"));
        mAdapter = new SearchRecordAdapter(data);
        mAdapter.setOnKeywordClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = (String) v.getTag();
                if (!TextUtils.isEmpty(keyword)) {
                    ((SearchActivity) getActivity()).setKeyword(keyword);
                }
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        mMainPresenter.getRecommand();
    }
}
