package com.prime.primeclient.AnalyticsActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.prime.primeclient.Helper;
import com.prime.primeclient.IPC_Application;
import com.prime.primeclient.Initialization;
import com.prime.primeclient.LoginActivity.LoginActivity;
import com.prime.primeclient.MainActivity.MainActivity;
import com.prime.primeclient.R;
import com.prime.primeclient.responses.AnalyticsResponse;
import com.prime.primeclient.responses.EmptyContentResponse;
import com.prime.primeclient.responses.loginResponse;
import com.prime.primeclient.responses.Responses;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.prime.primeclient.LoginActivity.LoginActivity.PREFERENCE;

public class AnalyticsLoginActivity extends AppCompatActivity implements Initialization {
    public static final String TAG = "PRIMECLIENTLOG";
    private static final  String REQUESTNAME = "loginAnalytics";
    public  static final String  PREFERENCE = "com.prime.primeclient.TOKEN";
    private TextInputLayout passwordWrapper;
    private TextInputEditText password;
    private Button signin;
    private SweetAlertDialog pDialog;
    private SweetAlertDialog sDialog;
    private SweetAlertDialog eDialog;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        managePreferences();
        initFields();
        initViews();
        setFields();
        setViews();
    }



    @Override
    public void initFields() {

    }

    @Override
    public void initViews() {

        passwordWrapper = (TextInputLayout) findViewById(R.id.analytics_password_wrapper);
        password = (TextInputEditText) findViewById(R.id.analytics_password_et);
        signin  = (Button) findViewById(R.id.analytics_login_btn);
    }

    @Override
    public void setViews() {
        passwordWrapper.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE){
                    validateLoginFields();
                }
                return true;
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateLoginFields();
            }
        });
    }

    @Override
    public void setFields() {

    }

    public void validateLoginFields(){
        if(Helper.isConnected(AnalyticsLoginActivity.this)){

                if(Helper.isValidPassword(passwordWrapper.getEditText().getText().toString())){
                    String pass = passwordWrapper.getEditText().getText().toString();
                    Log.d(TAG, "validateLoginFields: " + REQUESTNAME);
                    tryToSignIn(REQUESTNAME,pass,pref.getString(PREFERENCE,"empty"),"2016-10-01","2016-10-01");
                }

                else{

                    Toast.makeText(AnalyticsLoginActivity.this, "You have missed something in your password. Password must containt at least 1 lower case letter, one uppercase, one number, and at least 8 character long... Try again.", Toast.LENGTH_LONG).show();
                }

        }
        else{
            showError("Connect to Internet and try again.");
        }

    }

    public void tryToSignIn(String... data){

        IPC_Application.i().w().analyticsLogin(data[0],data[1],data[2]).enqueue(new Callback<Responses<EmptyContentResponse>>() {
            @Override
            public void onResponse(Call<Responses<EmptyContentResponse>> call, Response<Responses<EmptyContentResponse>> response) {
                if(response.code()==200){
                    if(response.body().message.equalsIgnoreCase("success")){


                        goTo(ShowAnalytics.class);

                    }

                    else{
                        Toast.makeText(AnalyticsLoginActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Responses<EmptyContentResponse>> call, Throwable t) {
                showError("Something really bad is happening. Try again later or directly contact support.");
            }
        });
    }


    private void managePreferences(){

        pref = getApplicationContext().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = pref.edit();
    }
//    private void preStartActions(){
//        if(pref.getString(PREFERENCE,"empty").equalsIgnoreCase("empty")){
//            Toast.makeText(this, "Please Sign in to continue.", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            goTo(MainActivity.class);
//        }
//
//    }

    private void goTo(Class to){
        Intent intent = new Intent(this,to);
        intent.putExtra("token",getIntent().getStringExtra("token"));
        startActivity(intent);
    }

    public void progressManager(Boolean show){
        if(show){
            pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        else{
            if(pDialog==null){
                pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.dismiss();
            }
            pDialog.dismiss();
            pDialog=null;
        }

    }


    public void showError(String s){
        eDialog =new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        eDialog. setTitleText("Oops...")
                .setContentText(s);
        eDialog.show();
        eDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
            }
        });

    }

    public void showSuccess(String s) {

        sDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        sDialog.setTitleText("Good job!")
                .setContentText(s);
        sDialog.show();

        sDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                setResult(1);
                finish();
            }
        });
    }
}
