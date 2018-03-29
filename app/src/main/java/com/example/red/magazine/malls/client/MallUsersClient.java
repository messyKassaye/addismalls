package com.example.red.magazine.malls.client;

import com.example.red.magazine.model.AdminUsersDetail;
import com.example.red.magazine.model.ResponseMessage;
import com.example.red.magazine.model.Sub_city;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by RED on 10/8/2017.
 */

public interface MallUsersClient {
    @GET("mallusersdetail")
    Call<AdminUsersDetail> getMallAdmin_Users_detail(@Query("token")String token);
    @POST("add_address")
    Call<ResponseMessage> add_address(@Query("token")String token, @Body Sub_city sub_city);

}
