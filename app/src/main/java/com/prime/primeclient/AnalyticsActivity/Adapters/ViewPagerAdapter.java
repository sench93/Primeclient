package com.prime.primeclient.AnalyticsActivity.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.prime.primeclient.AnalyticsActivity.Fragments.Income;
import com.prime.primeclient.AnalyticsActivity.Fragments.Outcome;
import com.prime.primeclient.AnalyticsActivity.Fragments.Total;
import com.prime.primeclient.MainActivity.Fragments.BonusFragment;
import com.prime.primeclient.MainActivity.Fragments.PaymentFragment;
import com.prime.primeclient.MainActivity.Fragments.SettingsFragment;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumbOfTabs;



    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }


    @Override
    public Fragment getItem(int position) {

        if(position == 0)
        {
            Income income = new Income();
            return income;
        }
        if(position == 1){

            Outcome outcome = new Outcome();
            return outcome;
        }
        else
        {
            Total total = new Total();
            return total;
        }


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