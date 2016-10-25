package com.prime.primeclient.MainActivity.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.prime.primeclient.Helper;
import com.prime.primeclient.IPC_Application;
import com.prime.primeclient.Initialization;
import com.prime.primeclient.LoginActivity.LoginActivity;
import com.prime.primeclient.R;
import com.prime.primeclient.responses.EmptyContentResponse;
import com.prime.primeclient.responses.Responses;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.prime.primeclient.LoginActivity.LoginActivity.PREFERENCE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BonusFragment extends Fragment implements Initialization{
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static final  String REQUESTNAME = "bonusPayment";
    private TextInputEditText cardNumber,amount;
    private TextInputLayout cardNumberWrapper,amountWrapper;
    private Button accumulate;
    private View v;
    private SweetAlertDialog pDialog;
    private SweetAlertDialog sDialog;
    private SweetAlertDialog eDialog;


        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            v=inflater.inflate(R.layout.fragment_bonus,container,false);
            managePreferences();
            initFields();
            initViews();
            setViews();
            setFields();
            return v;
        }


    @Override
    public void initFields() {

    }

    @Override
    public void initViews() {
        cardNumber = (TextInputEditText) v.findViewById(R.id.bonus_cardnumber_et);
        amount = (TextInputEditText) v.findViewById(R.id.bonus_amount_et);
        cardNumberWrapper = (TextInputLayout) v.findViewById(R.id.bonus_cardnumber_wrapper);
        amountWrapper = (TextInputLayout) v.findViewById(R.id.bonus_amount_wrapper);
        accumulate = (Button) v.findViewById(R.id.bonus_accumulate_btn);

    }

    @Override
    public void setViews() {
        amountWrapper.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE){
                    hideKeyboard();
                    validateLoginFields();
                }
                return true;
            }
        });

        accumulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                validateLoginFields();
            }
        });


    }

    @Override
    public void setFields() {

    }

    private void managePreferences(){

        pref = getActivity().getApplicationContext().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    public void validateLoginFields(){
        progressManager(true);
        if(Helper.isConnected(getActivity())){
            if(Helper.isValidCardNumber(cardNumberWrapper.getEditText().getText().toString())){
                String cardNumber = cardNumberWrapper.getEditText().getText().toString();
                String amount = amountWrapper.getEditText().getText().toString();
                if(amount.startsWith("0")){

                    progressManager(false);
                    showError("Amount can not start with 0 !");

                }

                else{
                    int temp  = -2222222;
                    Toast.makeText(getActivity(), String.valueOf(TextUtils.isEmpty(amount)), Toast.LENGTH_SHORT).show();
                    if(!TextUtils.isEmpty(amount)){
                        try{
                            temp = Integer.parseInt(amount);
                        }
                        catch (Exception e){
                            progressManager(false);
                            showError("Inputed Amount is not a number");
                        }
                            if(temp>=100 && temp<=9999999){

                            tryToAccumulate(REQUESTNAME,cardNumber,amount,pref.getString(PREFERENCE,"empty"));
                        }
                            else{
                                if(temp!=-2222222 && temp<100){
                                    progressManager(false);
                                    showError("For accumulation less than 100 AMD. Contact Service.");
                                }

                                else{
                                    if(temp>9999999){
                                        progressManager(false);
                                        showError("For accumulation more than 9.999.999 AMD. Contact Service.");
                                    }
                                    else{
                                        progressManager(false);
                                        showError("UnHandled exception");
                                    }

                                }
                            }


                    }
                    else{
                        progressManager(false);
                        showError("Amount is empty ");

                    }
                }


            }
            else{
                progressManager(false);
                showError("Card number format is not correct");

            }

        }

    }

    public void tryToAccumulate(String... data) {
        if (data[3].equalsIgnoreCase("empty")) {
            goTo(LoginActivity.class);
        } else {
            IPC_Application.i().w().accumulate(data[0],data[1],data[2],data[3]).enqueue(new Callback<Responses<EmptyContentResponse>>() {
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
                        showError("Oops something is wrong with server :( Fuck Hayk.");
                    }
                }

                @Override
                public void onFailure(Call<Responses<EmptyContentResponse>> call, Throwable t) {
                    progressManager(false);
                    showError(t.getMessage());
                }
            });

        }
    }




    private void goTo(Class to){
        Intent intent = new Intent(getActivity(),to);
        startActivity(intent);
    }

    public void showError(String s){
        eDialog =new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
        eDialog. setTitleText("Oops...")
                .setContentText(s);
        eDialog.show();

    }

    public void showSuccess(String s) {

        sDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
        sDialog.setTitleText("Good job!")
                .setContentText(s);
        sDialog.show();
    }
    public void progressManager(Boolean show){
        if(show){
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        else{
            if(pDialog==null){
                pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                pDialog.dismiss();
            }
            pDialog.dismiss();
            pDialog=null;
        }

    }
    private void hideKeyboard(){
        InputMethodManager inputManager =
                (InputMethodManager) getActivity().
                        getSystemService(getActivity().INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
