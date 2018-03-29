package com.example.red.magazine.categories.client;

import com.example.red.magazine.categories.model.CategoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Meseret on 11/8/2017.
 */

public interface CategoryClient {
    @GET("get_by_category")
    Call<List<CategoryModel>> get_by_category(@Query("id")String id);
}
