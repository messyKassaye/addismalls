package com.example.red.magazine.malls.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.malls.adapter.AmenitiesRecyclerviewAdapter;
import com.example.red.magazine.malls.adaptermodel.AmenityAdapterModel;
import com.example.red.magazine.malls.client.MallsClient;
import com.example.red.magazine.malls.model.Amenity;
import com.example.red.magazine.statics.ProjectStatic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Amenties extends Fragment {
    private SharedPreferences preferences;
    private String token;
    private RecyclerView recyclerView;
    private List<AmenityAdapterModel> amenityList;
    private AmenitiesRecyclerviewAdapter adapter;
    public Amenties() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_amenties, container, false);

        preferences=getActivity().getSharedPreferences("app_data_container",0);
        token=preferences.getString("company_id","");

        amenityList=new ArrayList<>();
        adapter=new AmenitiesRecyclerviewAdapter(getActivity(),amenityList);
        recyclerView=(RecyclerView)view.findViewById(R.id.amenities_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit=getUserAPIretrofit();
        MallsClient client= retrofit.create(MallsClient.class);
        Call<List<Amenity>> call= client.get_amenity(token);
        call.enqueue(new Callback<List<Amenity>>() {
            @Override
            public void onResponse(Call<List<Amenity>> call, Response<List<Amenity>> response) {
             for (int i=0;i<response.body().size();i++){
                 AmenityAdapterModel model=new AmenityAdapterModel();
                 model.setName(response.body().get(i).getName());
                 model.setDescription(response.body().get(i).getDescription());
                 amenityList.add(model);
             }
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Amenity>> call, Throwable t) {

            }
        });
        return view;
    }
    public Retrofit getUserAPIretrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ProjectStatic.URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit;
    }



}
