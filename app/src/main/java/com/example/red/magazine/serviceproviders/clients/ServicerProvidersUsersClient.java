package com.example.red.magazine.serviceproviders.clients;

import com.example.red.magazine.model.ResponseMessage;
import com.example.red.magazine.serviceproviders.model.Categories;
import com.example.red.magazine.serviceproviders.model.DailyDemand;
import com.example.red.magazine.serviceproviders.model.Demand;
import com.example.red.magazine.serviceproviders.model.Dishe;
import com.example.red.magazine.serviceproviders.model.Id;
import com.example.red.magazine.serviceproviders.model.MallData;
import com.example.red.magazine.serviceproviders.model.Menu;
import com.example.red.magazine.serviceproviders.model.PostDishe;
import com.example.red.magazine.serviceproviders.model.PostResDailyDemand;
import com.example.red.magazine.serviceproviders.model.PostSale;
import com.example.red.magazine.serviceproviders.model.ResItem;
import com.example.red.magazine.serviceproviders.model.RestaurantItmes;
import com.example.red.magazine.serviceproviders.model.RetailerProfile;
import com.example.red.magazine.serviceproviders.model.ServicerProviderUser;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by RED on 10/8/2017.
 */

public interface ServicerProvidersUsersClient {

    @GET("mall_owner")
    Call<List<MallData>> get_mall_owner(@Query("token")String token);

    @GET("check_retailer_profile_completion")
    Call<List<ServicerProviderUser>> check_retailer_profile_completion(@Query("token")String token);

    @GET("get_categories")
    Call<List<Categories>> get_categories(@Query("token")String token);
    @Multipart
    @POST("complete_profile")
    Call<ResponseMessage> complete_profile(@Query("token")String token, @Part("first_name") RequestBody first_name,
                                           @Part("last_name")RequestBody last_name,
                                           @Part("name") RequestBody name,
                                           @Part("category_type") RequestBody category_type,
                                           @Part("floor_no") RequestBody floor_no,
                                           @Part("office_no") RequestBody office_no,
                                           @Part MultipartBody.Part photo);

    @GET("get_demand_and_supply")
    Call<List<Demand>> get_demand_and_supply(@Query("token")String token);

    @Multipart
    @POST("upload_demand")
    Call<ResponseMessage> upload_demand(@Query("token")String token,@Part("description") RequestBody description,
                                             @Part("quantity")RequestBody quantity,
                                             @Part("price") RequestBody price,
                                             @Part("availability") RequestBody availability,
                                             @Part MultipartBody.Part photo);

    @Multipart
    @POST("upload_supply")
    Call<ResponseMessage> upload_supply(@Query("token")String token,@Part("description") RequestBody description,
                                        @Part("quantity")RequestBody quantity,
                                        @Part("price") RequestBody price,
                                        @Part("availability") RequestBody availability,
                                        @Part MultipartBody.Part photo);

    @GET("get_res_item")
    Call<List<ResItem>> getResItem(@Query("token")String token);

    @POST("get_selected_item_detail")
    Call<List<RestaurantItmes>> get_items_detail(@Query("token")String token, @Body Id id);

    @POST("send_demand")
    Call<ResponseMessage> send_demand(@Query("token")String token, @Body PostResDailyDemand demnd);

    @GET("manage_demand")
    Call<List<DailyDemand>> demand_manage(@Query("token")String token);

    @Multipart
    @POST("post_dish")
    Call<ResponseMessage> post_dish(@Query("token")String token,@Part("name") RequestBody name,
                                    @Part("price")RequestBody price,
                                    @Part("description") RequestBody description,
                                    @Part MultipartBody.Part photo);

    @GET("get_my_dish_list")
    Call<List<PostDishe>> get_my_dish_list(@Query("token")String token);

    @Multipart
    @POST("upload_menu_image")
    Call<ResponseMessage> upload_menu(@Query("token")String token,@Part MultipartBody.Part photo);


    @GET("get_menu_image")
    Call<List<Menu>> get_menu_image(@Query("token")String token);

    @Multipart
    @POST("post_new_item")
    Call<ResponseMessage> post_new_item(@Query("token")String token,@Part("price") RequestBody price,
                                        @Part("caption")RequestBody caption,
                                        @Part MultipartBody.Part photo);

    @GET("get_my_posted_item")
    Call<List<PostSale>> get_posted_itemd(@Query("token")String token);

}
