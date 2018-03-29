package com.example.red.magazine.topsellers.client;

import com.example.red.magazine.topsellers.model.PaymentSupporterBanks;
import com.example.red.magazine.topsellers.model.PurchasingModel;
import com.example.red.magazine.topsellers.model.PurchasingPhoto;
import com.example.red.magazine.topsellers.model.TopSellersItem;
import com.example.red.magazine.topsellers.model.TopsellersModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by RED on 10/3/2017.
 */

public interface TopSellersClient {

    @GET("topsellersdetail")
    Call<List<TopsellersModel>> getTopSellers(@Query("id")String id);

   @GET("topsellersitem")
    Call<List<TopSellersItem>> getTopSellersItem(@Query("id")String id);

    @GET("purchasing")
    Call<List<PurchasingModel>> getPurchasing(@Query("id")String id);

    @GET("puchasingProductPhoto")
    Call<List<PurchasingPhoto>> getPurchasingPhoto(@Query("id")String id);

    @GET("payment_bank")
    Call<List<PaymentSupporterBanks>> getPaymentSupporterBanks();



}
