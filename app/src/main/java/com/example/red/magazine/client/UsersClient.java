package com.example.red.magazine.client;

import com.example.red.magazine.model.AdminUsersDetail;
import com.example.red.magazine.model.LoginUser;
import com.example.red.magazine.model.MallRequestAllowModel;
import com.example.red.magazine.model.MallRequestModel;
import com.example.red.magazine.model.ResponseMessage;
import com.example.red.magazine.model.Retailers;
import com.example.red.magazine.model.Sub_city;
import com.example.red.magazine.model.User;
import com.example.red.magazine.model.VerificationItemModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by RED on 10/3/2017.
 */

public interface UsersClient {
    @POST("signup")
    Call<ResponseMessage> registerUser(@Body User user);

    @POST("login")
    Call<ResponseMessage> login(@Body LoginUser user);

    @GET("checkverification")
    Call<VerificationItemModel> checkverification(@Query("token")String token);
    @Multipart
    @POST("upload_mall_photo")
    Call<ResponseMessage> upload_mall_photo(@Query("token") String token,
                                             @Part MultipartBody.Part photo);

    @GET("checkmall_photo_uploaded_and_address_addess")
    Call<ResponseMessage> checkMall_photo_and_address_added(@Query("token")String token);


    @GET("users_detail")
    Call<AdminUsersDetail> getAdmin_Users_detail(@Query("token")String token);

    @POST("add_new_retailers")
    Call<ResponseMessage> add_new_retailer(@Query("token")String token,@Body Retailers retailers);

    @GET("mall_request")
    Call<List<MallRequestModel>> get_mall_request(@Query("token")String token);
    @POST("allow_request")
    Call<ResponseMessage> allow_request(@Query("token")String token,@Body MallRequestAllowModel allowModel);
}
