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
import com.example.red.magazine.hotels.adaptermodel.MenusAdapterModel;
import com.example.red.magazine.hotels.adapters.MenusRecyclerviewAdapter;
import com.example.red.magazine.hotels.client.HotelsClient;
import com.example.red.magazine.hotels.models.MenusModel;
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
public class Menus extends Fragment {

private SharedPreferences preferences;
    private String company_id;
    private RecyclerView recyclerView;
    private List<MenusAdapterModel> menuslist;
    private MenusRecyclerviewAdapter adapter;
    public Menus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_menus, container, false);


        preferences=getActivity().getSharedPreferences("hotel_container",0);
        company_id=preferences.getString("company_id","");

        menuslist=new ArrayList<>();
        adapter=new MenusRecyclerviewAdapter(getActivity(),menuslist);
        recyclerView=(RecyclerView)view.findViewById(R.id.menus_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit=getUserAPIretrofit();
        HotelsClient client=retrofit.create(HotelsClient.class);
        Call<List<MenusModel>> call=client.getMenus(company_id);
        call.enqueue(new Callback<List<MenusModel>>() {
            @Override
            public void onResponse(Call<List<MenusModel>> call, Response<List<MenusModel>> response) {
                if (response.isSuccessful()){
                    for (int i=0;i<response.body().size();i++){
                        MenusAdapterModel model=new MenusAdapterModel();
                        model.setPhoto_path(response.body().get(i).getPhoto_path());
                        model.setId(response.body().get(i).getId());
                        menuslist.add(model);
                    }
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<MenusModel>> call, Throwable t) {

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
