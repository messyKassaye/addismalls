package com.example.red.magazine.suppliers.clients;

import com.example.red.magazine.malls.model.ResponseMessage;
import com.example.red.magazine.serviceproviders.model.DailyDemand;
import com.example.red.magazine.suppliers.model.Customer;
import com.example.red.magazine.suppliers.model.DemandRequest;
import com.example.red.magazine.suppliers.model.HotelsModel;
import com.example.red.magazine.suppliers.model.Suppliers;
import com.example.red.magazine.suppliers.model.SuppliersItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by RED on 10/12/2017.
 */

public interface SuppliersClient {

    @GET("suppliers_info")
    Call<List<Suppliers>> get_suppliers_info(@Query("token")String token);
    @GET("get_suppliers_item")
    Call<List<SuppliersItem>> get_suppliers_item(@Query("token")String token);

    @GET("get_suppliers_daily_demand_request")
    Call<List<DemandRequest>> get_demand_request(@Query("token")String token,@Query("id")String id);

    @GET("update_daily_demand")
    Call<List<DemandRequest>> update_daily_demand(@Query("token")String token,@Query("id")String id,@Query("br_id")String br_id);

    @GET("show_hotels_to_be_added_as_customers")
    Call<List<HotelsModel>> get_hotels_list(@Query("token")String token);

    @GET("add_hotels_as_customer")
    Call<ResponseMessage> add_hotels_as_customers(@Query("token")String token,@Query("id")String id);

    @GET("add_caferes_as_customer")
    Call<ResponseMessage> add_caferes_as_customers(@Query("token")String token,@Query("id")String id);

    @POST("add_others_customer")
    Call<ResponseMessage> add_others_customer(@Query("token")String token, @Body Customer customer);

    @GET("get_restaurants_for_customers_addition")
    Call<List<HotelsModel>> get_restaurants(@Query("token")String token);

}
