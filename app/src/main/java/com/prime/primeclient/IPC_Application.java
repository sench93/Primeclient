package com.prime.primeclient;

import android.app.Application;

import com.prime.primeclient.network.JacksonConverterFactory;
import com.prime.primeclient.requests.I_Requests;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class IPC_Application extends Application{

    private static IPC_Application instance;
    private I_Requests iWebEndpoint;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static IPC_Application i() {
        return instance;
    }


    public I_Requests w() {
        if(this.iWebEndpoint == null){
            initRetrofit();
        }
        return iWebEndpoint;
    }

    private void initRetrofit(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build();

        client.readTimeoutMillis();

        this.iWebEndpoint = new Retrofit.Builder()
                .baseUrl(I_Requests.address)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(I_Requests.class);
    }
}
