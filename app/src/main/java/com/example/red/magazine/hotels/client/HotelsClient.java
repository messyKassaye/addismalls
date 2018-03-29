package com.example.red.magazine.hotels.client;

import com.example.red.magazine.hotels.RoomsDetails;
import com.example.red.magazine.hotels.adaptermodel.RoomsAdapterModel;
import com.example.red.magazine.hotels.models.HotelDisheModel;
import com.example.red.magazine.hotels.models.Hotels;
import com.example.red.magazine.hotels.models.MenusModel;
import com.example.red.magazine.hotels.models.OpeningHours;
import com.example.red.magazine.hotels.models.RoomDetailsModel;
import com.example.red.magazine.hotels.models.RoomsModel;
import com.example.red.magazine.malls.model.AboutMall;
import com.example.red.magazine.malls.model.SingleMallsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Meseret on 11/5/2017.
 */

public interface HotelsClient {
    @GET("hotels")
    Call<List<Hotels>> getHotels();

    @GET("hotel_detail")
    Call<List<SingleMallsModel>> getHotelDetail(@Query("company_id")String company_id);

    @GET("hotel_address")
    Call<List<AboutMall>> get_about_hotel(@Query("company_id")String company_id);

    @GET("hotel_room")
    Call<List<RoomsModel>> getRooms(@Query("company_id")String company_id);

    @GET("room_details")
    Call<List<RoomDetailsModel>> getRoomsDetails(@Query("id")String id);

    @GET("get_menus")
    Call<List<MenusModel>> getMenus(@Query("company_id")String company_id);

    @GET("hotel_dishes")
    Call<List<HotelDisheModel>> getHotelsDishes(@Query("company_id")String company_id);

    @GET("hours")
    Call<List<OpeningHours>> getHours(@Query("company_id")String company_id);
}
