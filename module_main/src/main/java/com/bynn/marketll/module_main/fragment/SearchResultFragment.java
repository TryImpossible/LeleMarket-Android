package com.bynn.marketll.module_main.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bynn.common.base.BaseFragment;
import com.bynn.marketll.module_main.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends BaseFragment {

    public static SearchResultFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SearchResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_fragment_search_result, container, false);
    }

}
