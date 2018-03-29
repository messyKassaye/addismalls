package com.example.red.magazine.client;

import com.example.red.magazine.malls.model.VacancyDetailModel;
import com.example.red.magazine.model.OuterVacancyModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Meseret on 11/12/2017.
 */

public interface OuterVacancyClient {
    @GET("get_vacancy")
    Call<List<OuterVacancyModel>> getVacancy();

    @GET("vacancy_detail")
    Call<List<VacancyDetailModel>> vacancy_details(@Query("id")String id);
}
