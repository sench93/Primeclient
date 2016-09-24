package com.prime.primeclient.requests;
import com.prime.primeclient.responses.loginResponse;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface I_Requests {

    static final String address = "http://85.143.210.101";

    @FormUrlEncoded
    @POST("/Restaurants")
    Call<loginResponse> login(
                    @Field("requestName") String requestName,
                    @Field("mail") String login,
                    @Field("password") String password
            );


}
