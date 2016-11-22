package com.prime.primeclient.AnalyticsActivity;

import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidifygeeks.library.fragment.PageFragment;
import com.androidifygeeks.library.fragment.TabDialogFragment;
import com.androidifygeeks.library.iface.IFragmentListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.prime.primeclient.AnalyticsActivity.DetailedAnalytics.DetailedAnalytics;
import com.prime.primeclient.IPC_Application;
import com.prime.primeclient.R;
import com.prime.primeclient.responses.AnalyticsResponse;
import com.prime.primeclient.responses.Responses;
import com.prime.primeclient.responses.TypeOne;
import com.prime.primeclient.responses.TypeTwo;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowAnalytics extends AppCompatActivity implements IFragmentListener {

    public static ArrayList<String> headerList;
    public static HashMap<String, ArrayList<TypeOne>> objectsByYear;
    public static HashMap<String, ArrayList<TypeTwo>> objectsByYear2;

    private TextView totalIncome, totalExpenses, happyCustomersT;
    private ImageButton seeMore;
    private int totalI = 0, totalE = 0, happyCustomers;
    private SweetAlertDialog pDialog;
    private SweetAlertDialog sDialog;
    private SweetAlertDialog eDialog;
    private Calendar myCalendar;
    EditText startDate = null, endDate = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_analytics);
        totalIncome = (TextView) findViewById(R.id.totalIncomeShow);
        totalExpenses = (TextView) findViewById(R.id.totalExpensesShow);
        happyCustomersT = (TextView) findViewById(R.id.totalCustomersShow);
        seeMore = (ImageButton) findViewById(R.id.seemore);
        dialogManager();
        //getData("analytics","4oohgs3td5jc0shhvfqf9ombtg","2016-05-01","2016-10-29");
//        seeMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                goTo();
//            }
//        });


    }


    @Override
    public void onFragmentViewCreated(Fragment fragment) {
        int selectedTabPosition = fragment.getArguments().getInt(PageFragment.ARG_DAY_INDEX, 0);
        View rootContainer = fragment.getView().findViewById(R.id.root_container);


        Button b;

        switch (selectedTabPosition) {
            case 0:
                // add view in container for first tab
                View tabProductDetailLayout = getLayoutInflater().inflate(R.layout.tab_one_layout, (ViewGroup) rootContainer);



                startDate = (EditText) tabProductDetailLayout.findViewById(R.id.startDate);
                startDate.setInputType(InputType.TYPE_NULL);


                myCalendar = Calendar.getInstance();
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel(startDate);
                    }

                };

                startDate.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        new DatePickerDialog(ShowAnalytics.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });




                b = (Button) tabProductDetailLayout.findViewById(R.id.button);
                b.setText("By Day");
                break;
            case 1:
                View tabProductDetailLayout2 = getLayoutInflater().inflate(R.layout.tab_one_layout, (ViewGroup) rootContainer);
                b = (Button) tabProductDetailLayout2.findViewById(R.id.button);
                b.setText("By Month");
                break;
        }
    }


    private void updateLabel(EditText editText) {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onFragmentAttached(Fragment fragment) {

    }

    @Override
    public void onFragmentDetached(Fragment fragment) {

    }


    public void dialogManager() {
        TabDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setTitle("Choose Period")
                .setTabButtonText(new CharSequence[]{"By Day", "By Month"})
                .setRequestCode(1001)
                .show();
    }

    public void getData(String... data) {
        progressManager(true);
        IPC_Application.i().w().showAnalytics(data[0], data[1], data[2], data[3]).enqueue(new Callback<Responses<AnalyticsResponse>>() {
            @Override
            public void onResponse(Call<Responses<AnalyticsResponse>> call, Response<Responses<AnalyticsResponse>> response) {
                if (response.code() == 200) {
                    if (response.body().message.equalsIgnoreCase("success")) {
                        ArrayList<TypeOne> t1 = response.body().content.typeOne;
                        ArrayList<TypeTwo> t2 = response.body().content.typeTwo;
                        happyCustomers = response.body().content.customersCount;
                        if (t1 != null) {
                            for (TypeOne t11 :
                                    t1) {
                                totalI += t11.restaurantBonusAmount;
                            }

                        }

                        if (t2 != null) {
                            for (TypeTwo t22 :
                                    t2) {
                                totalE += t22.paymentAmount;
                            }
                        }
                        manageWithData(t1, t2);
//                        totalIncome.setText(String.valueOf(totalE));
//                        totalExpenses.setText(String.valueOf(totalI));
                        progressManager(false);
                        animateTextView(0, totalE, totalIncome);
                        animateTextView(0, totalI, totalExpenses);
                        animateTextView(0, happyCustomers, happyCustomersT);
                    } else {
                        progressManager(false);
                        showError("Bad server config.");
                    }
                } else {
                    progressManager(false);
                    showError("Bad connection");
                }
            }

            @Override
            public void onFailure(Call<Responses<AnalyticsResponse>> call, Throwable t) {
                showError(t.getMessage());
            }
        });
    }

    public void animateTextView(int initialValue, int finalValue, final TextView textview) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
        valueAnimator.setDuration(1500);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                textview.setText(valueAnimator.getAnimatedValue().toString());

            }
        });
        valueAnimator.start();

    }

    public void manageWithData(ArrayList<TypeOne> t1, ArrayList<TypeTwo> t2) {

        Collections.reverse(t1);
        objectsByYear = new HashMap<String, ArrayList<TypeOne>>();
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
        objectsByYear2 = new HashMap<String, ArrayList<TypeTwo>>();
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

    public void goTo() {
        Intent i = new Intent(this, DetailedAnalytics.class);
//        i.putExtra("headerData",headerList);
//        i.putExtra("allData",objectsByYear);
//        i.putExtra("allData2",objectsByYear2);
        startActivity(i);
    }

    public void showError(String s) {
        eDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        eDialog.setTitleText("Oops...")
                .setContentText(s);
        eDialog.show();

    }

    public void showSuccess(String s) {

        sDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        sDialog.setTitleText("Good job!")
                .setContentText(s);
        sDialog.show();
    }

    public void progressManager(Boolean show) {
        if (show) {
            pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
        } else {
            if (pDialog == null) {
                pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.dismiss();
            }
            pDialog.dismiss();
            pDialog = null;
        }

    }

}
