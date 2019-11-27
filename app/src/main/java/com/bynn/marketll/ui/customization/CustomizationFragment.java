package com.bynn.marketll.ui.customization;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bynn.marketll.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomizationFragment extends Fragment {
    public static CustomizationFragment newInstance() {

        Bundle args = new Bundle();

        CustomizationFragment fragment = new CustomizationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CustomizationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customization, container, false);
    }
}
