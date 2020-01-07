package com.bynn.marketll.module_main.fragment;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.fragment.BaseFragment;
import com.bynn.lib_basic.network.ResponseException;
import com.bynn.lib_basic.qmui.QMUIDisplayHelper;
import com.bynn.marketll.module_main.MainPresenter;
import com.bynn.marketll.module_main.R;
import com.bynn.marketll.module_main.R2;
import com.bynn.marketll.module_main.activity.SearchActivity;
import com.bynn.marketll.module_main.adapter.SearchKeywordAdapter;
import com.bynn.marketll.module_main.bean.KeywordBean;
import com.bynn.marketll.module_main.bean.KeywordResult;
import com.bynn.marketll.module_main.dagger.DaggerMainComponent;
import com.bynn.marketll.module_main.dagger.MainComponent;
import com.bynn.marketll.module_main.dagger.MainModule;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchKeywordFragment extends BaseFragment {

    @BindView(R2.id.recyclerView) RecyclerView mRecyclerView;

    private MainPresenter        mMainPresenter;
    private SearchKeywordAdapter mKeywordAdapter;

    public static SearchKeywordFragment newInstance() {

        Bundle args = new Bundle();

        SearchKeywordFragment fragment = new SearchKeywordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SearchKeywordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_fragment_search_keyword, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        injectPresenter();
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMainPresenter.getKeyword(((SearchActivity) getActivity()).getKeyword());
    }

    @Override
    public void onSuccess(Object successObj) {
        super.onSuccess(successObj);
        if (successObj instanceof KeywordResult) {
            List<KeywordBean> data = ((KeywordResult) successObj).getData();
            if (data != null) {
                mKeywordAdapter.setNewData(data);
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
        mKeywordAdapter = new SearchKeywordAdapter(new ArrayList<>());
        mKeywordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                KeywordBean bean = mKeywordAdapter.getItem(position);
                ((SearchActivity) getActivity()).setKeyword(bean.getName());
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mKeywordAdapter);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                int count = parent.getChildCount();
                Paint paint = new Paint();
                paint.setColor(ContextCompat.getColor(getContext(), R.color.basic_divide_line));
                for (int i = 0; i < count - 1; i++) {
                    View child = parent.getChildAt(i);
                    c.drawRect(new Rect(child.getLeft() + QMUIDisplayHelper.dp2px(getContext(), 16), child.getBottom(), child.getRight(), child.getBottom() + QMUIDisplayHelper.dp2px(getContext(), 1)), paint);
                }
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if (position < mKeywordAdapter.getData().size() - 1) {
                    outRect.bottom = QMUIDisplayHelper.dp2px(getContext(), 1);
                }
            }
        });
    }

    /**
     * 搜索，提供给外部调用
     */
    public void startSearch() {
        mMainPresenter.getKeyword(((SearchActivity) getActivity()).getKeyword());
    }
}
