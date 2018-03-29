package com.example.red.magazine.hotels.fragments;


import android.content.SharedPreferences;
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
import com.example.red.magazine.hotels.adaptermodel.OpeningHoursAdapterModel;
import com.example.red.magazine.hotels.adapters.HotelHoursRecyclerviewAdapter;
import com.example.red.magazine.hotels.client.HotelsClient;
import com.example.red.magazine.hotels.models.OpeningHours;
import com.example.red.magazine.statics.ProjectStatic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class Hours extends Fragment {

    private SharedPreferences preferences;
    private List<OpeningHoursAdapterModel> modelList;
    private HotelHoursRecyclerviewAdapter adapter;
    private RecyclerView recyclerView;
    public Hours() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_hours, container, false);

        preferences= getActivity().getSharedPreferences("hotel_container",0);

        modelList=new ArrayList<>();
        adapter=new HotelHoursRecyclerviewAdapter(getActivity(),modelList);
        recyclerView=(RecyclerView)view.findViewById(R.id.hours_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit= getUserAPIretrofit();
        HotelsClient client= retrofit.create(HotelsClient.class);
        Call<List<OpeningHours>> call= client.getHours(preferences.getString("company_id",""));
        call.enqueue(new Callback<List<OpeningHours>>() {
            @Override
            public void onResponse(Call<List<OpeningHours>> call, Response<List<OpeningHours>> response) {
                if (response.isSuccessful()){
                    for (int i=0;i<response.body().size();i++){
                        OpeningHoursAdapterModel model=new OpeningHoursAdapterModel();
                        model.setFriday(response.body().get(i).getFriday());
                        model.setMonday(response.body().get(i).getMonday());
                        model.setSatureday(response.body().get(i).getSatureday());
                        model.setSunday(response.body().get(i).getSunday());
                        model.setThursday(response.body().get(i).getThursday());
                        model.setWendsday(response.body().get(i).getWendsday());
                        model.setTuesday(response.body().get(i).getTuesday());
                        modelList.add(model);
                    }
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getActivity(),"Something is not Good ):",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<OpeningHours>> call, Throwable t) {

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
