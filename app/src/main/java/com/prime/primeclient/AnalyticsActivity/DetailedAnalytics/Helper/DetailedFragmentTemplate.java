package com.prime.primeclient.AnalyticsActivity.DetailedAnalytics.Helper;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prime.primeclient.AnalyticsActivity.Adapters.RecyclerAdapter;
import com.prime.primeclient.Initialization;
import com.prime.primeclient.R;
import com.prime.primeclient.responses.TypeOne;
import com.prime.primeclient.responses.TypeTwo;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailedFragmentTemplate extends Fragment implements Initialization {
    private ArrayList<TypeOne> objectByYear;
    private ArrayList<TypeTwo> objectByYear2;
    private String date;
    private View v;
    private RecyclerView rl,rr;
    private RecyclerView.Adapter mAdapterL,mAdapterR;
    private RecyclerView.LayoutManager mLayoutManagerL,mLayoutManagerR;
    private TextView incomeTotal, outcomeTotal;
    private int totalIncome=0,totalExpenses=0;
    public DetailedFragmentTemplate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_detailed_template, container, false);
        initFields();
        calculateTotals(objectByYear,objectByYear2);
        initViews();
        setFields();
        setViews();;
        return v;
    }

    @Override
    public void initFields() {
        objectByYear = (ArrayList<TypeOne>) getArguments().get("data1");
        objectByYear2 = (ArrayList<TypeTwo>) getArguments().get("data2");
        date = (String) getArguments().get("date");
    }

    @Override
    public void initViews() {
        rl = (RecyclerView) v.findViewById(R.id.leftRes);
        rr = (RecyclerView) v.findViewById(R.id.rightRes);
        mLayoutManagerL = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapterL = new RecyclerAdapter(objectByYear,null,getActivity());
        mLayoutManagerR = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapterR = new RecyclerAdapter(null,objectByYear2,getActivity());
        incomeTotal = (TextView) v.findViewById(R.id.totalAmount);
        outcomeTotal = (TextView) v.findViewById(R.id.totalExpense);
    }

    @Override
    public void setViews() {
        rl.hasFixedSize();
        rl.setLayoutManager(mLayoutManagerL);
        rl.setAdapter(mAdapterL);
        rr.hasFixedSize();
        rr.setLayoutManager(mLayoutManagerR);
        rr.setAdapter(mAdapterR);
        incomeTotal.setText("Total  " + String.valueOf(totalExpenses));
        outcomeTotal.setText("Total  " + String.valueOf(totalIncome));
    }

    @Override
    public void setFields() {

    }

    public void calculateTotals(ArrayList<TypeOne> t1,ArrayList<TypeTwo> t2){
        if(t1!=null){
        for (TypeOne t11:
             t1) {
                totalIncome+=t11.restaurantBonusAmount;
            }
        }
        if(t2!=null){
        for (TypeTwo t22:
                t2) {
            totalExpenses+=t22.paymentAmount;
            }
        }

    }
}
