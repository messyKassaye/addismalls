package com.example.red.magazine.malls.client;

import com.example.red.magazine.malls.model.AboutMall;
import com.example.red.magazine.malls.model.Amenity;
import com.example.red.magazine.malls.model.CategoryModel;
import com.example.red.magazine.malls.model.Event;
import com.example.red.magazine.malls.model.JobCategory;
import com.example.red.magazine.malls.model.MallLeaseSpace;
import com.example.red.magazine.malls.model.NewRetailers;
import com.example.red.magazine.malls.model.PostVacancy;
import com.example.red.magazine.malls.model.ResponseMessage;
import com.example.red.magazine.malls.model.ServiceProvidersModel;
import com.example.red.magazine.malls.model.SingleMallsModel;

import com.example.red.magazine.malls.model.VacancyDetailModel;
import com.example.red.magazine.malls.model.VacancyModel;
import com.example.red.magazine.model.TopSellers;
import com.squareup.okhttp.ResponseBody;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by RED on 10/2/2017.
 */

public interface MallsClient {

    @GET("mallsdetail")
    Call<List<SingleMallsModel>> getMallDetail(@Query("company_id")String company_id);

    @GET("getcategory")
    Call<List<CategoryModel>> getCategoryInMall(@Query("id")String id);

    @GET("getserviceproviders")
    Call<List<ServiceProvidersModel>> getServiceproviders(@Query("company_id")String company_id,@Query("category_name")String category_name);

    @GET("show_retailers")
    Call<List<TopSellers>> show_retailers(@Query("company_id")String company_id);

    @POST("add_new_retailers")
    Call<ResponseMessage> add_new_retailers(@Query("token")String token, @Body NewRetailers retailers);

    @GET("about_mall")
    Call<List<AboutMall>> get_about_mall(@Query("company_id")String company_id);

    @GET("amenities")
    Call<List<Amenity>> get_amenity(@Query("company_id")String company_id);

    @GET("events")
    Call<List<Event>> getEvents(@Query("company_id")String company_id);

    @GET("spaces")
    Call<List<MallLeaseSpace>> get_spaces(@Query("company_id")String company_id);

    @GET("vacancy")
    Call<List<VacancyModel>> get_vacancy(@Query("company_id")String company_id);

    @GET("vacancy_details")
    Call<List<VacancyDetailModel>> vacancy_details(@Query("id")String id);

    @GET("get_vacancy_cate")
    Call<List<JobCategory>> get_vacancy_cate(@Query("token")String token);

    @POST("post_vacancy")
    Call<ResponseMessage> post_vacancy(@Query("token")String token, @Body PostVacancy postVacancy);


}
