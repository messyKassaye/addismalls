package com.example.red.magazine.client;

import com.example.red.magazine.model.TopMall;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by RED on 10/1/2017.
 */

public interface MallsClient {
    @GET("topmalls")
    Call<List<TopMall>> getTopMall();

    @GET("all_malls")
    Call<List<TopMall>>getAllMall();
}
