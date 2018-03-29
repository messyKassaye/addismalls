package com.example.red.magazine.serviceproviders.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.serviceproviders.AdapterModel.DemandAdapterModel;
import com.example.red.magazine.serviceproviders.adapters.DemandDashboardrecyclverviewAdapter;
import com.example.red.magazine.serviceproviders.clients.ServicerProvidersUsersClient;
import com.example.red.magazine.serviceproviders.model.Demand;
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
public class ServiceProviderDashboardFragment extends Fragment {

   private SharedPreferences preferences;
    private String token="";
    private List<DemandAdapterModel> demandList;
    private DemandDashboardrecyclverviewAdapter adapter;
    private RecyclerView recyclerView;
    public ServiceProviderDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.service_provider_dashboard, container, false);
        preferences=getActivity().getSharedPreferences("service_provider_data",0);
        token=preferences.getString("token","");
        demandList=new ArrayList<>();
        adapter=new DemandDashboardrecyclverviewAdapter(getActivity(),demandList);
        recyclerView =(RecyclerView)view.findViewById(R.id.service_providers_dashboard_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Retrofit retrofit=getUserAPIretrofit();
        ServicerProvidersUsersClient client=retrofit.create(ServicerProvidersUsersClient.class);
        Call<List<Demand>> call=client.get_demand_and_supply(token);
        call.enqueue(new Callback<List<Demand>>() {
            @Override
            public void onResponse(Call<List<Demand>> call, Response<List<Demand>> response) {
                for (int i=0;i<response.body().size();i++){
                    DemandAdapterModel model=new DemandAdapterModel();
                    model.setPhoto_path(response.body().get(i).getPhoto_path());
                    model.setName(response.body().get(i).getName());
                    model.setProduct_photo(response.body().get(i).getProduct_photo());
                    model.setAvailability(response.body().get(i).getAvailability());
                    model.setPrice(response.body().get(i).getPrice());
                    model.setDescription(response.body().get(i).getDescription());
                    demandList.add(model);
                }
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Demand>> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();
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
