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
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prime.primeclient.Helper;
import com.prime.primeclient.IPC_Application;
import com.prime.primeclient.Initialization;
import com.prime.primeclient.LoginActivity.LoginActivity;
import com.prime.primeclient.MainActivity.PasscodeActivity;
import com.prime.primeclient.R;
import com.prime.primeclient.responses.EmptyContentResponse;
import com.prime.primeclient.responses.Responses;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.prime.primeclient.LoginActivity.LoginActivity.PREFERENCE;


public class PaymentFragment extends Fragment implements Initialization {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static final  String REQUESTNAME = "payment";
    private TextInputEditText cardNumber,amount;
    private TextInputLayout cardNumberWrapper,amountWrapper;
    private Button pay;
    private View v;
    private SweetAlertDialog pDialog;
    private SweetAlertDialog sDialog;
    private SweetAlertDialog eDialog;
    private EditText pin;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_payment,container,false);
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
        cardNumber = (TextInputEditText) v.findViewById(R.id.payment_cardnumber_et);
        amount = (TextInputEditText) v.findViewById(R.id.payment_amount_et);
        cardNumberWrapper = (TextInputLayout) v.findViewById(R.id.payment_cardnumber_wrapper);
        amountWrapper = (TextInputLayout) v.findViewById(R.id.payment_amount_wrapper);
        pay = (Button) v.findViewById(R.id.payment_pay_btn);
        pin = new EditText(getActivity());
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

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                validateLoginFields();
            }
        });
        pin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE){
                    hideKeyboard();
                }
                return true;
            }
        });
    }

    @Override
    public void setFields() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101){
            if(resultCode==1){
                cardNumber.setText("");
                amount.setText("");
            }

            else{
                Toast.makeText(getActivity(), "Payment is not done. Try Again.", Toast.LENGTH_SHORT).show();
            }
        }
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
                    if(!TextUtils.isEmpty(amount)){

                        try{
                            temp = Integer.parseInt(amount);
                        }
                        catch (Exception e){
                            progressManager(false);
                            showError("Inputed Amount is not a number");
                        }
                        if(temp>=100 && temp<=9999999){
                            progressManager(false);
                          goTo(PasscodeActivity.class);
                            //inputDialog();
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
                        showError("Amount is empty");

                    }


                }

            }
            else{
                progressManager(false);
                showError("Card number format is not correct");

            }

        }

    }
    public void tryToPay(String... data) {
        if (data[3].equalsIgnoreCase("empty")) {
            goTo(LoginActivity.class);
        }
        else{
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

    private void managePreferences(){

        pref = getActivity().getApplicationContext().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    public void inputDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(350, 0, 350, 0);
        if(pin!=null){
            ViewGroup parent = (ViewGroup) pin.getParent();
            if (parent != null) {
                parent.removeView(pin);
            }
        }
        pin.setInputType(InputType.TYPE_CLASS_NUMBER);
        pin.setImeOptions(EditorInfo.IME_ACTION_DONE);
        layout.addView(pin,params);
        alert.setView(layout);
        alert.setTitle("Please enter your pin");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String cardNumber = cardNumberWrapper.getEditText().getText().toString();
                String amount = amountWrapper.getEditText().getText().toString();
                String pinCode= pin.getText().toString();
                hideKeyboard();
                dialog.dismiss();
                progressManager(true);
                tryToPay(REQUESTNAME,cardNumber,amount,pinCode,pref.getString(PREFERENCE,"empty"));

            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        alert.setCancelable(false);
        alert.show();
    }
    private void goTo(Class to){
        String cardNumber = cardNumberWrapper.getEditText().getText().toString();
        String amount = amountWrapper.getEditText().getText().toString();
        Intent intent = new Intent(getActivity(),to);
        intent.putExtra("requestName",REQUESTNAME);
        intent.putExtra("cardNumber",cardNumber);
        intent.putExtra("amount",amount);
        intent.putExtra("token",pref.getString(PREFERENCE,"empty"));
        startActivityForResult(intent,101);
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
