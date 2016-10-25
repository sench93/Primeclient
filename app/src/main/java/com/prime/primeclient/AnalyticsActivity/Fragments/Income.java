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

import com.prime.primeclient.AnalyticsActivity.Adapters.ExpandableListAdapter;
import com.prime.primeclient.AnalyticsActivity.DetailedAnalytics.DetailedAnalytics;
import com.prime.primeclient.IPC_Application;
import com.prime.primeclient.R;
import com.prime.primeclient.responses.AnalyticsResponse;
import com.prime.primeclient.responses.Responses;
import com.prime.primeclient.responses.TypeOne;
import com.prime.primeclient.responses.TypeOneComperator;
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
    private Button b;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ArrayList<String> headerList;
    HashMap<String,ArrayList<TypeOne>> objectsByYear;

    public Income() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v= inflater.inflate(R.layout.fragment_income, container, false);
        // b=(Button) v.findViewById(R.id.testBut);
//         b.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 getData("analytics","4oohgs3td5jc0shhvfqf9ombtg","2016-07-01","2016-11-11");
//             }
//         });
        getData("analytics","4oohgs3td5jc0shhvfqf9ombtg","2016-07-01","2016-11-11");
        return v;
    }


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
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
        headerList = new ArrayList<>();
        headerList.addAll(headers);

        expListView = (ExpandableListView) v.findViewById(R.id.lvExp);
        //prepareListData();
        listAdapter = new ExpandableListAdapter(getActivity(), headerList, objectsByYear);
        expListView.setAdapter(listAdapter);
        Log.e("ZHAR", "manageWithData: aaaaa" );
        goTo();

    }

    public void goTo(){
        Intent i = new Intent(getActivity(), DetailedAnalytics.class);
        i.putExtra("headerData",headerList);
        i.putExtra("allData",objectsByYear);
        startActivity(i);
    }
}
