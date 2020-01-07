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

import butterknife.BindView;
import butterknife.ButterKnife;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.fragment.BaseFragment;
import com.bynn.lib_basic.qmui.QMUIDisplayHelper;
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
            if (data != null) {
                mAdapter.addData(1, new SearchRecordBean(data, SearchRecordBean.TYPE_HOT));
            }
        }
        if (successObj instanceof List<?>) {
            List<String> data = (List<String>) successObj;
            for (String keyword : data) {
                mAdapter.addData(new SearchRecordBean(keyword, SearchRecordBean.TYPE_HOSTORY));
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
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                int count = parent.getChildCount();
                for (int i = 1; i < count; i++) {
                    if (!mAdapter.getItem(i).isHeader) {
                        continue;
                    }
                    View child = parent.getChildAt(i);
                    Rect rect = new Rect(child.getLeft(), child.getTop() - QMUIDisplayHelper.dp2px(getContext(), 4), child.getRight(), child.getTop());
                    Paint paint = new Paint();
                    paint.setColor(ContextCompat.getColor(getContext(), R.color.basic_activity_background));
                    c.drawRect(rect, paint);
                }
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if (mAdapter.getItem(position).isHeader && position != 0) {
                    outRect.top = QMUIDisplayHelper.dp2px(getContext(), 4);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mMainPresenter.getRecommand();
        mMainPresenter.getHistorySearch();
    }
}
