package com.bynn.marketll.module_custom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bynn.marketll.module_custom.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomFragment extends Fragment {
    public static CustomFragment newInstance() {

        Bundle args = new Bundle();

        CustomFragment fragment = new CustomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CustomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.custom_fragment_customization, container, false);
    }
}
