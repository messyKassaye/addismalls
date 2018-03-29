package com.example.red.magazine.hotels.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.hotels.client.HotelsClient;
import com.example.red.magazine.malls.client.MallsClient;
import com.example.red.magazine.malls.model.AboutMall;
import com.example.red.magazine.statics.ProjectStatic;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUs extends Fragment {

    private SharedPreferences preferences;
    private String token;
    private TextView hotel_about_us;
    private TextView full_address,email,website,phone_no;
    public AboutUs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about_us3, container, false);

        preferences=getActivity().getSharedPreferences("hotel_container",0);

        hotel_about_us=(TextView)view.findViewById(R.id.hotel_about_us);
        hotel_about_us.setText(preferences.getString("about",""));

        full_address=(TextView)view.findViewById(R.id.full_address);
        email=(TextView)view.findViewById(R.id.email);
        website=(TextView)view.findViewById(R.id.website);
        phone_no=(TextView)view.findViewById(R.id.phone_no);

        Retrofit retrofit=getUserAPIretrofit();
        HotelsClient client= retrofit.create(HotelsClient.class);
        Call<List<AboutMall>> call=client.get_about_hotel(preferences.getString("company_id",""));
        call.enqueue(new Callback<List<AboutMall>>() {
            @Override
            public void onResponse(Call<List<AboutMall>> call, Response<List<AboutMall>> response) {
                full_address.setText(response.body().get(0).getCountry()+", "+response.body().get(0).getCity()+", "+response.body().get(0).getSub_city());
                email.setText("Email: "+response.body().get(0).getEmail());
                website.setText("Website: "+response.body().get(0).getWebsite());
                phone_no.setText("Phone no: "+response.body().get(0).getPhone_no());
            }

            @Override
            public void onFailure(Call<List<AboutMall>> call, Throwable t) {

            }
        });
        return  view;
    }

    public Retrofit getUserAPIretrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ProjectStatic.URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit;
    }

}
