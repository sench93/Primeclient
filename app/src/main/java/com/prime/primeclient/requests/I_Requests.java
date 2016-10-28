package com.prime.primeclient.requests;
import com.prime.primeclient.responses.AnalyticsResponse;
import com.prime.primeclient.responses.EmptyContentResponse;
import com.prime.primeclient.responses.loginResponse;
import com.prime.primeclient.responses.Responses;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface I_Requests {

    String address = "http://85.143.210.101";

    @FormUrlEncoded
    @POST("/Restaurants")
    Call<Responses<loginResponse>> login(
                    @Field("requestName") String requestName,
                    @Field("mail") String login,
                    @Field("password") String password
            );

    @FormUrlEncoded
    @POST("/Restaurants")
    Call<Responses<EmptyContentResponse>> accumulate(
            @Field("requestName") String requestName,
            @Field("primeCardNumber") String cardNumber,
            @Field("amount") String amount,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("/Restaurants")
    Call<Responses<EmptyContentResponse>> pay(
            @Field("requestName") String requestName,
            @Field("primeCardNumber") String cardNumber,
            @Field("amount") String amount,
            @Field("primeCardCode") String pinCode,
            @Field("token") String token
    );



    @FormUrlEncoded
    @POST("/Restaurants")
    Call<Responses<EmptyContentResponse>> analyticsLogin(
            @Field("requestName") String requestName,
            @Field("password") String password,
            @Field("token") String token
    );


    @FormUrlEncoded
    @POST("/Restaurants")
    Call<Responses<AnalyticsResponse>> showAnalytics(
            @Field("requestName") String requestName,
            @Field("token") String token,
            @Field("startDate") String startDate,
            @Field("endDate") String endDate
    );

}
