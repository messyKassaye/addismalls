package com.example.red.magazine.client;

import com.example.red.magazine.malls.model.MallLeaseSpace;
import com.example.red.magazine.model.OuterLeaseSpaceModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Meseret on 11/12/2017.
 */

public interface OuterLeaseSpaceClient {

    @GET("outer_lease_space")
    Call<List<OuterLeaseSpaceModel>> getLeaseSpace();
}
