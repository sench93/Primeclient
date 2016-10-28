package com.prime.primeclient.AnalyticsActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowAnalytics extends AppCompatActivity  {

    public static ArrayList<String> headerList;
    public static HashMap<String,ArrayList<TypeOne>> objectsByYear;
    public static HashMap<String,ArrayList<TypeTwo>>objectsByYear2;

    private TextView totalIncome,totalExpenses;
    private ImageButton seeMore;
    private int  totalI=0, totalE=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_analytics);
        totalIncome = (TextView) findViewById(R.id.totalIncomeShow);
        totalExpenses = (TextView) findViewById(R.id.totalExpensesShow);
        seeMore = (ImageButton) findViewById(R.id.seemore);
        getData("analytics","4oohgs3td5jc0shhvfqf9ombtg","2016-07-01","2016-11-11");
        seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goTo();
            }
        });
    }

    public void getData(String... data){
        IPC_Application.i().w().showAnalytics(data[0],data[1],data[2],data[3]).enqueue(new Callback<Responses<AnalyticsResponse>>() {
            @Override
            public void onResponse(Call<Responses<AnalyticsResponse>> call, Response<Responses<AnalyticsResponse>> response) {
                if(response.code()==200){
                    if(response.body().message.equalsIgnoreCase("success")){
                        ArrayList<TypeOne> t1 = response.body().content.typeOne;
                        ArrayList<TypeTwo> t2 = response.body().content.typeTwo;
                        if(t1!=null){
                            for (TypeOne t11:
                                 t1) {
                                totalI+=t11.restaurantBonusAmount;
                            }

                        }

                        if(t2!=null){
                            for (TypeTwo t22:
                                    t2) {
                                totalE+=t22.paymentAmount;
                            }
                        }
                        manageWithData(t1,t2);
                        totalIncome.setText(String.valueOf(totalE));
                        totalExpenses.setText(String.valueOf(totalI));
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

    }

    public void goTo(){
        Intent i = new Intent(this, DetailedAnalytics.class);
//        i.putExtra("headerData",headerList);
//        i.putExtra("allData",objectsByYear);
//        i.putExtra("allData2",objectsByYear2);
        startActivity(i);
    }

}
