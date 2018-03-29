package com.example.red.magazine.malls.client;

import com.example.red.magazine.malls.model.LeaseSpace;
import com.example.red.magazine.malls.model.ManageRetailersModel;
import com.example.red.magazine.model.ResponseMessage;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by RED on 10/13/2017.
 */

public interface ServicePrClient {

    @GET("get_retailers")
    Call<List<ManageRetailersModel>> get_retailers(@Query("token")String token,@Query("floor_no")String floor_no);

    @Multipart
    @POST("post_lease_space")
    Call<ResponseMessage> post_lease_space(@Query("token")String token,
                                           @Part("floor_no") RequestBody floor_no,
                                           @Part("area") RequestBody area,
                                           @Part("purpose") RequestBody purpose,
                                           @Part("description") RequestBody first_name,
                                           @Part MultipartBody.Part photo);

    @GET("get_lease_space")
    Call<List<LeaseSpace>> get_lease_space(@Query("token")String token);
}
