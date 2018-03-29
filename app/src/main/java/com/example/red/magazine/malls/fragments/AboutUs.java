package com.example.red.magazine.malls.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.R;

import com.example.red.magazine.malls.client.MallsClient;
import com.example.red.magazine.malls.model.AboutMall;
import com.example.red.magazine.statics.ProjectStatic;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AboutUs extends Fragment {
   private SharedPreferences preferences;
    private String token;
    private TextView textView,address,full_address,email,website,phone_no;
    public AboutUs() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about_us, container, false);
        preferences=getActivity().getSharedPreferences("app_data_container",0);
        textView =(TextView)view.findViewById(R.id.descr);
        address=(TextView)view.findViewById(R.id.address);
        full_address=(TextView)view.findViewById(R.id.full_address);
        email=(TextView)view.findViewById(R.id.email);
        website=(TextView)view.findViewById(R.id.website);
        phone_no=(TextView)view.findViewById(R.id.phone_no);

        Retrofit retrofit=getUserAPIretrofit();
        MallsClient client= retrofit.create(MallsClient.class);
        Call<List<AboutMall>> call=client.get_about_mall(preferences.getString("company_id",""));
        call.enqueue(new Callback<List<AboutMall>>() {
            @Override
            public void onResponse(Call<List<AboutMall>> call, Response<List<AboutMall>> response) {
                  textView.setText(response.body().get(0).getDescription());
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
