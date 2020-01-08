package com.bynn.marketll.module_main.fragment;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bynn.lib_basic.BaseApplication;
import com.bynn.lib_basic.fragment.BaseFragment;
import com.bynn.lib_basic.interfaces.ICallback;
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
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mMainPresenter.getRecentSearch();
        }
    }

    @Override
    public void onSuccess(Object successObj) {
        super.onSuccess(successObj);
        if (successObj instanceof KeywordResult) {
            mAdapter.addHotData(((KeywordResult) successObj).getData());
        }
        if (successObj instanceof List<?>) {
            mAdapter.addRecentData((List<String>) successObj);
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
        mAdapter = new SearchRecordAdapter(new ArrayList<>(), getContext());
        mAdapter.setOnKeywordClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = (String) v.getTag();
                if (!TextUtils.isEmpty(keyword)) {
                    ((SearchActivity) getActivity()).setKeyword(keyword);
                }
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SearchRecordBean bean = (SearchRecordBean) mAdapter.getItem(position);
                if (bean.t instanceof String) {
                    String keyword = (String) bean.t;
                    if (!TextUtils.isEmpty(keyword)) {
                        ((SearchActivity) getActivity()).setKeyword(keyword);
                    }
                }
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_del) {
                    showToast("删除最近搜索");
                    mAdapter.removeRecentData();

//                    mMainPresenter.delRecentSearch(new ICallback() {
//                        @Override
//                        public void onSuccess(Object successObj) {
//                            mAdapter.removeRecentData();
//                        }
//
//                        @Override
//                        public void onFailure(Throwable e) {
//
//                        }
//                    });
                }
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                int count = mAdapter.getData().size();
                for (int i = 1; i < count; i++) {
                    if (mAdapter.getItem(i).isHeader) {
                        View child = parent.getChildAt(i);
                        Rect rect = new Rect(child.getLeft(), child.getTop() - QMUIDisplayHelper.dp2px(getContext(), 4), child.getRight(), child.getTop());
                        Paint paint = new Paint();
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.basic_activity_background));
                        c.drawRect(rect, paint);
                    }
                    if (i > 2 && i < count - 1) {
                        View child = parent.getChildAt(i);
                        Rect rect = new Rect(child.getLeft() + QMUIDisplayHelper.dp2px(getContext(), 16), child.getBottom(), child.getRight(), child.getBottom() + QMUIDisplayHelper.dp2px(getContext(), 1) / 2);
                        Paint paint = new Paint();
                        paint.setColor(ContextCompat.getColor(getContext(), R.color.basic_activity_background));
                        c.drawRect(rect, paint);
                    }
                }
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if (position < 0 || position > mAdapter.getData().size() - 1) {
                    return;
                }
                if (mAdapter.getItem(position).isHeader && position != 0) {
                    outRect.top = QMUIDisplayHelper.dp2px(getContext(), 4);
                }
                if (position > 2 && position < mAdapter.getData().size() - 1) {
                    outRect.bottom = QMUIDisplayHelper.dp2px(getContext(), 1) / 2;
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mMainPresenter.getRecommand();
        mMainPresenter.getRecentSearch();
    }
}
