package com.bynn.marketll.module_home.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bynn.marketll.module_home.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OtherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtherFragment extends Fragment {
    private static final String ID = "id";

    private Unbinder mUnbinder;
    // 表示TopNav类型
    private int      mId;

    private StringBuilder mIds = new StringBuilder();

    public OtherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Parameter 1.
     * @return A new instance of fragment OtherFragment.
     */
    public static OtherFragment newInstance(int id) {
        OtherFragment fragment = new OtherFragment();
        Bundle args = new Bundle();
        args.putInt(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getInt(ID);
            mIds.append(mId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.common_fragment_other, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        if (null != mUnbinder) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
