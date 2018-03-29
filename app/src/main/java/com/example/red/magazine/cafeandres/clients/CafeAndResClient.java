package com.example.red.magazine.cafeandres.clients;

import com.example.red.magazine.cafeandres.model.CafeAndResMenu;
import com.example.red.magazine.cafeandres.model.CafeResDishes;
import com.example.red.magazine.malls.model.SingleMallsModel;
import com.example.red.magazine.model.CafeResModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Meseret on 11/11/2017.
 */

public interface CafeAndResClient {

    @GET("get_cafe_and_res")
    Call<List<SingleMallsModel>> getCafeAndRes(@Query("company_id")String company_id);

    @GET("get_cafe_menu")
   Call<List<CafeAndResMenu>> getCafeAndResMenu(@Query("company_id")String company_id);

    @GET("get_cafe_dishe")
    Call<List<CafeResDishes>> getDsihe(@Query("company_id")String company_id);
}
