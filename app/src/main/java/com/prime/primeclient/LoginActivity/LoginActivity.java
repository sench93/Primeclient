package com.prime.primeclient.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.prime.primeclient.MainActivity.MainActivity;
import com.prime.primeclient.R;
import com.prime.primeclient.responses.LoginResponse;
import com.prime.primeclient.responses.Responses;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements Initialization {
    public static final String TAG = "PRIMECLIENTLOG";
    private static final  String REQUESTNAME = "login";
    public  static final String  PREFERENCE = "com.prime.primeclient.TOKEN";
    private TextInputEditText mail,password;
    private TextInputLayout mailWrapper,passwordWrapper;
    private Button signin;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        managePreferences();
        preStartActions();
        initFields();
        initViews();
        setViews();
        setFields();
    }

    @Override
    public void initFields() {

    }

    @Override
    public void initViews() {
        mail = (TextInputEditText) findViewById(R.id.login_mail_et);
        password = (TextInputEditText) findViewById(R.id.login_password_et);
        mailWrapper = (TextInputLayout) findViewById(R.id.login_mail_wrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.login_password_wrapper);
        signin = (Button) findViewById(R.id.login_login_btn);
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
        if(Helper.isConnected(LoginActivity.this)){
            if(Helper.isValidEmail(mailWrapper.getEditText().getText().toString())){
                if(Helper.isValidPassword(passwordWrapper.getEditText().getText().toString())){
                    String pass = passwordWrapper.getEditText().getText().toString();
                    String log = mailWrapper.getEditText().getText().toString();
                    tryToSignIn(REQUESTNAME,log,pass);
                }

                else{

                    Toast.makeText(LoginActivity.this, "You have missed something in your password. Password must containt at least 1 lower case letter, one uppercase, one number, and at least 8 character long... Try again.", Toast.LENGTH_LONG).show();
                }
            }

            else{

                Toast.makeText(LoginActivity.this, "Not correct email fomr", Toast.LENGTH_SHORT).show();
            }

        }

        else{

            Toast.makeText(LoginActivity.this, "No Internet Connection ...", Toast.LENGTH_SHORT).show();

        }

    }
    public void tryToSignIn(String... data){

        IPC_Application.i().w().login(data[0],data[1],data[2]).enqueue(new Callback<Responses<List<LoginResponse>>>() {
            @Override
            public void onResponse(Call<Responses<List<LoginResponse>>> call, Response<Responses<List<LoginResponse>>> response) {
                editor.putString(PREFERENCE,response.body().content.get(0).token);
                editor.commit();
                goTo(MainActivity.class);
            }

            @Override
            public void onFailure(Call<Responses<List<LoginResponse>>> call, Throwable t) {

            }
        });
    }


    private void managePreferences(){

        pref = getApplicationContext().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    private void preStartActions(){
        if(pref.getString(PREFERENCE,"empty").equalsIgnoreCase("empty")){
            Toast.makeText(this, "Please Sign in to continue.", Toast.LENGTH_SHORT).show();
        }
        else{
            goTo(MainActivity.class);
        }

    }

    private void goTo(Class to){
        Intent intent = new Intent(this,to);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            Toast.makeText(this, "Please Sign in", Toast.LENGTH_SHORT).show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
