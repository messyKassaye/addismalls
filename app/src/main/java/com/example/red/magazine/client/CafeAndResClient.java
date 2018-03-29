package com.example.red.magazine.client;

import com.example.red.magazine.model.CafeResModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Meseret on 11/8/2017.
 */

public interface CafeAndResClient {
    @GET("cafe_and_res")
    Call<List<CafeResModel>> getCafeAndRes();

    @GET("get_all_cafe_res")
    Call<List<CafeResModel>>getAllCafeAndRes();
}
