package com.example.red.magazine.client;

import com.example.red.magazine.model.Sponser;
import com.example.red.magazine.model.SponsorDetail;
import com.example.red.magazine.model.SponsorsAmenity;
import com.example.red.magazine.model.Sponsors_branch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by RED on 9/27/2017.
 */

public interface SponserClient {

        @GET("sponsors")
        Call<List<Sponser>> getSponsers();

        @GET("sponsors_detail")
        Call<List<SponsorDetail>> getSponserosDetail(@Query("company_id")String company_id);

       @GET("sponsors_branch")
       Call<List<Sponsors_branch>> getSponsorsBranch(@Query("company_id")String company_id);

      @GET("sponsors_amenity")
      Call<List<SponsorsAmenity>> getSponsorsAmenity(@Query("company_id")String company_id);

}
