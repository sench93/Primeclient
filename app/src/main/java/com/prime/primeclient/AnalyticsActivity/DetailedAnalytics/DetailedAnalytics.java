package com.prime.primeclient.AnalyticsActivity.DetailedAnalytics;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.prime.primeclient.AnalyticsActivity.DetailedAnalytics.Helper.ViewPagerAdapter;
import com.prime.primeclient.AnalyticsActivity.ShowAnalytics;
import com.prime.primeclient.Initialization;
import com.prime.primeclient.MainActivity.SlidingTabLayout;
import com.prime.primeclient.R;
import com.prime.primeclient.responses.TypeOne;
import com.prime.primeclient.responses.TypeTwo;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailedAnalytics extends AppCompatActivity implements Initialization {
    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private CharSequence[] Titles;
    private int Numboftabs =3;
    ArrayList<String> headerList;
    HashMap<String,ArrayList<TypeOne>> objectsByYear;
    HashMap<String,ArrayList<TypeTwo>> objectsByYear2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_analytics);
        getDataFromIntent();
        initFields();
        initViews();
        setViews();
        setFields();
    }

    private void getDataFromIntent() {
//        headerList = (ArrayList<String>) getIntent().getExtras().get("headerData");
//        objectsByYear = (HashMap<String,ArrayList<TypeOne>>)getIntent().getExtras().get("allData");
//        objectsByYear2 = (HashMap<String,ArrayList<TypeTwo>>)getIntent().getExtras().get("allData2");

        headerList = ShowAnalytics.headerList;
        objectsByYear = ShowAnalytics.objectsByYear;
        objectsByYear2 = ShowAnalytics.objectsByYear2;
    }

    @Override
    public void initFields() {

    }

    @Override
    public void initViews() {
        Numboftabs = headerList.size();
        Titles  = new CharSequence[headerList.size()];
        for (int i= 0;i<headerList.size();i++) {
            Titles[i] = headerList.get(i);
        }
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs,headerList,objectsByYear,objectsByYear2);
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
