package com.prime.primeclient.AnalyticsActivity.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.androidifygeeks.library.fragment.TabDialogFragment;
import com.prime.primeclient.AnalyticsActivity.Adapters.ExpandableListAdapter;
import com.prime.primeclient.AnalyticsActivity.DetailedAnalytics.DetailedAnalytics;
import com.prime.primeclient.IPC_Application;
import com.prime.primeclient.R;
import com.prime.primeclient.responses.AnalyticsResponse;
import com.prime.primeclient.responses.Responses;
import com.prime.primeclient.responses.TypeOne;
import com.prime.primeclient.responses.TypeTwo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Income extends Fragment {
    private View v;
    ArrayList<String> headerList;
    HashMap<String,ArrayList<TypeOne>> objectsByYear;
    HashMap<String,ArrayList<TypeTwo>>objectsByYear2;
    public Income() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v= inflater.inflate(R.layout.fragment_income, container, false);

        //getData("analytics","4oohgs3td5jc0shhvfqf9ombtg","2016-07-01","2016-11-11");
        dialogManager();
        return v;
    }


    public void dialogManager(){
        TabDialogFragment.createBuilder(getActivity(), getActivity().getSupportFragmentManager())
                .setTitle("hello")
                .setSubTitle("subtitle")
                .setTabButtonText(new CharSequence[]{"Tab1", "Tab2"})
                .setPositiveButtonText("Love")
                .setNegativeButtonText("Hate")
                .setNeutralButtonText("WTF?")
                .setRequestCode(1001)
                .show();
    }



    public void getData(String... data){
        IPC_Application.i().w().showAnalytics(data[0],data[1],data[2],data[3]).enqueue(new Callback<Responses<AnalyticsResponse>>() {
            @Override
            public void onResponse(Call<Responses<AnalyticsResponse>> call, Response<Responses<AnalyticsResponse>> response) {
                if(response.code()==200){
                    if(response.body().message.equalsIgnoreCase("success")){
                        ArrayList<TypeOne> t1 = response.body().content.typeOne;
                        ArrayList<TypeTwo> t2 = response.body().content.typeTwo;

                        manageWithData(t1,t2);

                    }
                }
            }

            @Override
            public void onFailure(Call<Responses<AnalyticsResponse>> call, Throwable t) {

            }
        });
    }

    public void manageWithData(ArrayList<TypeOne> t1,ArrayList<TypeTwo> t2){

        Collections.reverse(t1);
        objectsByYear = new HashMap<String,ArrayList<TypeOne>>();
        LinkedHashSet<String> headers = new LinkedHashSet<String>();
        for (TypeOne obj : t1) {
            ArrayList<TypeOne> yearList = objectsByYear.get(obj.date);
            if (yearList == null) {
                objectsByYear.put(obj.date, yearList = new ArrayList<TypeOne>());
                headers.add(obj.date);
            }
            yearList.add(obj);
        }

        Collections.reverse(t2);
        objectsByYear2 = new HashMap<String,ArrayList<TypeTwo>>();
        for (TypeTwo obj : t2) {
            ArrayList<TypeTwo> yearList = objectsByYear2.get(obj.date);
            if (yearList == null) {
                objectsByYear2.put(obj.date, yearList = new ArrayList<TypeTwo>());
                headers.add(obj.date);
            }
            yearList.add(obj);
        }
        headerList = new ArrayList<>();
        headerList.addAll(headers);

        goTo();

    }

    public void goTo(){
        Intent i = new Intent(getActivity(), DetailedAnalytics.class);
        i.putExtra("headerData",headerList);
        i.putExtra("allData",objectsByYear);
        i.putExtra("allData2",objectsByYear2);
        startActivity(i);
    }
}
