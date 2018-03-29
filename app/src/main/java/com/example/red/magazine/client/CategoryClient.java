package com.example.red.magazine.client;

import com.example.red.magazine.model.CategoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by RED on 10/1/2017.
 */

public interface CategoryClient {
    @GET("categories")
    Call<List<CategoryModel>> getCategory();
}
