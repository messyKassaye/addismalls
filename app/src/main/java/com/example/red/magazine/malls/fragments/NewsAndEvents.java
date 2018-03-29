package com.example.red.magazine.malls.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.malls.adaptermodel.EventAdapterModel;
import com.example.red.magazine.malls.client.MallsClient;
import com.example.red.magazine.malls.model.Event;
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
public class NewsAndEvents extends Fragment {

    private SharedPreferences preferences;
    private String token;
    private RecyclerView event_recyclerview,news_recyclerview;
    public NewsAndEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_news_and_events, container, false);
        preferences=getActivity().getSharedPreferences("app_data_container",0);
        getEvents(preferences.getString("company_id",""));
        return view;
    }

    public void  getEvents(String company_id){
        Retrofit retrofit= getUserAPIretrofit();
        MallsClient client= retrofit.create(MallsClient.class);
        Call<List<Event>> call=client.getEvents(company_id);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
              for (int i=0;i<response.body().size();i++){
                  EventAdapterModel model=new EventAdapterModel();
                  model.setCompany_name(response.body().get(i).getCompany_name());
                  model.setDescription(response.body().get(i).getDescription());
                  model.setPhoto_path(response.body().get(i).getPhoto_path());
                  model.setEnding_date(response.body().get(i).getEnding_date());
                  model.setStarting_date(response.body().get(i).getStarting_date());
                  model.setEvent_title(response.body().get(i).getEvent_title());
              }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });

    }
    public void getNews(){

    }
    public Retrofit getUserAPIretrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ProjectStatic.URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit;
    }

}
