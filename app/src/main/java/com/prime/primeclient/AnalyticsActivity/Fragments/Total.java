package com.prime.primeclient.AnalyticsActivity.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prime.primeclient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Total extends Fragment {


    public Total() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_total, container, false);
    }

}
