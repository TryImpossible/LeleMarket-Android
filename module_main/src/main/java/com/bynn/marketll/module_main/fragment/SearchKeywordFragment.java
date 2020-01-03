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
public class SearchKeywordFragment extends BaseFragment {

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
        return inflater.inflate(R.layout.main_fragment_search_keyword, container, false);
    }

}
