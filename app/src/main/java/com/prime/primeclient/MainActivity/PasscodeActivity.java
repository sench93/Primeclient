package com.prime.primeclient.MainActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.prime.primeclient.Helper;
import com.prime.primeclient.IPC_Application;
import com.prime.primeclient.Initialization;
import com.prime.primeclient.R;
import com.prime.primeclient.responses.EmptyContentResponse;
import com.prime.primeclient.responses.Responses;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasscodeActivity extends AppCompatActivity implements Initialization{
    private TextView inputField;
    private SweetAlertDialog pDialog;
    private SweetAlertDialog sDialog;
    private SweetAlertDialog eDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);
        initViews();
    }

    @Override
    public void initFields() {

    }

    @Override
    public void initViews() {
        inputField = (TextView) findViewById(R.id.inputField);
    }

    @Override
    public void setViews() {

    }

    @Override
    public void setFields() {

    }

    public void onClick(View view) {
        Button temp = (Button)findViewById(view.getId());
        switch(view.getId()){
            case R.id.cancel:
                setResult(0);
                finish();
                // TODO: 10/24/2016
                break;
            case R.id.clear:
                if(inputField.getText().length()>0){
                    inputField.setText(inputField.getText().toString().substring(0,inputField.getText().length()-1));
                }
                break;
            default:
                numClickBehaviour(temp.getText().toString());
                break;
        }


    }

    public void numClickBehaviour(String s){

        if(inputField.getText().length()<3){
            inputField.setText(inputField.getText().toString() + s);
        }

        else if(inputField.getText().length()==3){
            if(Helper.isConnected(this)){

                inputField.setText(inputField.getText().toString() + s);
                String requestName = getIntent().getStringExtra("requestName");
                String cardNumber = getIntent().getStringExtra("cardNumber");
                String amount = getIntent().getStringExtra("amount");
                String token = getIntent().getStringExtra("token");
                tryToPay(requestName,cardNumber,amount,inputField.getText().toString(),token);
            }
            else{
                showError("Connect to Internet and try again.");
            }
        }

    }



    public void tryToPay(String... data){

        IPC_Application.i().w().pay(data[0],data[1],data[2],data[3],data[4]).enqueue(new Callback<Responses<EmptyContentResponse>>() {
            @Override
            public void onResponse(Call<Responses<EmptyContentResponse>> call, Response<Responses<EmptyContentResponse>> response) {
                if(response.code()==200){
                    if(response.body().status==200 && response.body().message.equalsIgnoreCase("success")){
                        progressManager(false);
                        showSuccess("Accumulation is done with " +response.body().message + ". Thank you.");
                    }
                    else{
                        progressManager(false);
                        showError(response.body().message + ".Try again");
                    }
                }

                else{
                    progressManager(false);
                    showError("Oops something is wrong with server :( Try again.");
                }
            }

            @Override
            public void onFailure(Call<Responses<EmptyContentResponse>> call, Throwable t) {
                progressManager(false);
                showError("Something really bad is happening. Try again later or directly contact support.");
            }
        });

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
                inputField.setText("");
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
