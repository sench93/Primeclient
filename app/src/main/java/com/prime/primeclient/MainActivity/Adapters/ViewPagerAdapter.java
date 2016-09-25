package com.prime.primeclient.MainActivity.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.prime.primeclient.MainActivity.Fragments.BonusFragment;
import com.prime.primeclient.MainActivity.Fragments.PaymentFragment;
import com.prime.primeclient.MainActivity.Fragments.SettingsFragment;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumbOfTabs;



    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }


    @Override
    public Fragment getItem(int position) {

        if(position == 0)
        {
            BonusFragment bonus = new BonusFragment();
            return bonus;
        }
        if(position == 1){

            PaymentFragment payment = new PaymentFragment();
            return payment;
        }
        else
        {
            SettingsFragment settings = new SettingsFragment();
            return settings;
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