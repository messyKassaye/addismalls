package com.example.red.magazine.client;

import com.example.red.magazine.model.ResponseMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by RED on 10/8/2017.
 */

public interface PhotoClient {

    @GET("checkmallphoto")
    Call<ResponseMessage> checkmallphoto(@Query("token")String token);
}
