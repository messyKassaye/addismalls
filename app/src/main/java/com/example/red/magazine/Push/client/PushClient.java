package com.example.red.magazine.Push.client;

import com.example.red.magazine.Push.models.PushModel;
import com.example.red.magazine.Push.models.PushPostModel;
import com.example.red.magazine.malls.model.ResponseMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Meseret on 11/6/2017.
 */

public interface PushClient {

    @GET("push")
    Call<List<PushModel>> get_push(@Query("code")String code);

     @POST("push_post")
    Call<ResponseMessage> push_post(@Body PushPostModel model);
}
