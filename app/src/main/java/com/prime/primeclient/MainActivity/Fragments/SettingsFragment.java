package com.prime.primeclient.MainActivity.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.prime.primeclient.AnalyticsActivity.AnalyticsLoginActivity;
import com.prime.primeclient.Initialization;
import com.prime.primeclient.LoginActivity.LoginActivity;
import com.prime.primeclient.R;

import static com.prime.primeclient.LoginActivity.LoginActivity.PREFERENCE;


public class SettingsFragment extends Fragment implements Initialization {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
        private View v;
        private Button logout,statistics;
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            v=inflater.inflate(R.layout.fragment_settings,container,false);
            initFields();
            initViews();
            setViews();
            setFields();
            managePreferences();
            return v;
        }

        @Override
        public void initFields() {

        }

        @Override
        public void initViews() {
            logout = (Button) v.findViewById(R.id.settings_logout_btn);
            statistics = (Button) v.findViewById(R.id.settings_statistics_btn);
        }

        @Override
        public void setViews() {
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editor.remove(LoginActivity.PREFERENCE);
                    editor.commit();
                    goTo(LoginActivity.class);
                }
            });

            statistics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goTo(AnalyticsLoginActivity.class);
                }
            });
        }

        @Override
        public void setFields() {

    }

    public void goTo(Class to){
        Intent intent = new Intent(getActivity(),to);
        startActivity(intent);
    }

    private void managePreferences(){

        pref = getActivity().getApplicationContext().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = pref.edit();
    }
}
