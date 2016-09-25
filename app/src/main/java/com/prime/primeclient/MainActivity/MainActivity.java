package com.prime.primeclient.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.prime.primeclient.Initialization;
import com.prime.primeclient.LoginActivity.LoginActivity;
import com.prime.primeclient.MainActivity.Adapters.ViewPagerAdapter;
import com.prime.primeclient.R;

import static com.prime.primeclient.LoginActivity.LoginActivity.PREFERENCE;

public class MainActivity extends AppCompatActivity implements Initialization{
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static final  String REQUESTNAME = "bonusPayment";
    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private CharSequence Titles[]={"Bonus","Payment","Settings"};
    private int Numboftabs =3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);
        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);

    }

    @Override
    public void setViews() {
        pager.setAdapter(adapter);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.primeBlue);
            }
        });
        tabs.setViewPager(pager);

    }

    @Override
    public void setFields() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    private void managePreferences(){

        pref = getApplicationContext().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    private void preStartActions(){
        if(isUserLoggedIn()){
        }
        else{
            goTo(LoginActivity.class);
        }

    }

    public boolean isUserLoggedIn(){
        if(pref.getString(PREFERENCE,"empty").equalsIgnoreCase("empty")){
            return false;
        }
        else{
            Toast.makeText(this, pref.getString(PREFERENCE,"empty"), Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    private void goTo(Class to){
        Intent intent = new Intent(this,to);
        startActivity(intent);
    }

    @Override
    protected void onResume() {

        // TODO: 9/25/2016
        super.onResume();
    }

    @Override
    protected void onRestart() {
        if (!isUserLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        super.onRestart();
    }

}
