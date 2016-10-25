package com.prime.primeclient.AnalyticsActivity.DetailedAnalytics.Helper;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prime.primeclient.R;
import com.prime.primeclient.responses.TypeOne;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailedFragmentTemplate extends Fragment {
    ArrayList<TypeOne> objectByYear;
    String date;
    TextView tv;
    View v;
    public DetailedFragmentTemplate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        objectByYear = (ArrayList<TypeOne>) getArguments().get("data");
        date = (String) getArguments().get("date");
        v = inflater.inflate(R.layout.fragment_detailed_template, container, false);
        tv = (TextView) v.findViewById(R.id.sample);
        tv.setText(String.valueOf(objectByYear.get(0).bonusAmount));
        return v;
    }

}
