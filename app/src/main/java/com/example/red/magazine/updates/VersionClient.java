package com.example.red.magazine.updates;

import com.example.red.magazine.model.ResponseMessage;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ezedin on 10/30/17.
 */
public interface VersionClient {

    @GET("version")
    Call<ResponseMessage> get_version();
}
