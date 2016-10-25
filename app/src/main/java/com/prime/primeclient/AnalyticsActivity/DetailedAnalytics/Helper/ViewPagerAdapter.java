package com.prime.primeclient.AnalyticsActivity.DetailedAnalytics.Helper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.prime.primeclient.AnalyticsActivity.DetailedAnalytics.DetailedAnalytics;
import com.prime.primeclient.MainActivity.Fragments.BonusFragment;
import com.prime.primeclient.MainActivity.Fragments.PaymentFragment;
import com.prime.primeclient.MainActivity.Fragments.SettingsFragment;
import com.prime.primeclient.responses.TypeOne;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumbOfTabs;
    ArrayList<String> headerList;
    HashMap<String,ArrayList<TypeOne>> objectByYear;


    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, ArrayList<String> headerList, HashMap<String,ArrayList<TypeOne>> objectByYear) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.headerList= headerList;
        this.objectByYear = objectByYear;
    }


    @Override
    public Fragment getItem(int position) {
        DetailedFragmentTemplate dft = new DetailedFragmentTemplate();
        Bundle args = new Bundle();
        ArrayList<TypeOne> t1List = objectByYear.get(headerList.get(position));
        args.putSerializable("data",t1List);
        args.putString("date",headerList.get(position));
        dft.setArguments(args);
            return dft;
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }



    @Override
    public int getCount() {
        return NumbOfTabs;
    }


}