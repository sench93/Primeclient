package com.prime.primeclient;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements Initialization{
    public static String TAG = "PRIMECLIENTLOG";
    private TextInputEditText mail,password;
    private TextInputLayout mailWrapper,passwordWrapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
    }

    @Override
    public void setViews() {
        passwordWrapper.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_NEXT){
                    Log.d(TAG, "onEditorAction: NEXT");
                }
                if(i== EditorInfo.IME_ACTION_DONE){
                    Log.d(TAG, "onEditorAction: DONE");
                }
                return true;
            }
        });
    }
}
