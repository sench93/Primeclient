package com.prime.primeclient.AnalyticsActivity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.prime.primeclient.IPC_Application;
import com.prime.primeclient.Initialization;
import com.prime.primeclient.AnalyticsActivity.Adapters.ViewPagerAdapter;
import com.prime.primeclient.MainActivity.SlidingTabLayout;
import com.prime.primeclient.R;
import com.prime.primeclient.responses.AnalyticsResponse;
import com.prime.primeclient.responses.Responses;
import com.prime.primeclient.responses.TypeOne;
import com.prime.primeclient.responses.TypeOneComperator;
import com.prime.primeclient.responses.TypeTwo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowAnalytics extends AppCompatActivity implements Initialization {
    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private CharSequence Titles[]={"Income","Outcome","Total"};
    private int Numboftabs =3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_analytics);
        initFields();
        initViews();
        setViews();
        setFields();
    }

    @Override
    public void initFields() {

    }

    @Override
    public void initViews() {
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);
        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
    }

    @Override
    public void setViews() {
        pager.setAdapter(adapter);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.primeBlue);
            }
        });
        tabs.setViewPager(pager);
    }

    @Override
    public void setFields() {

    }

}
