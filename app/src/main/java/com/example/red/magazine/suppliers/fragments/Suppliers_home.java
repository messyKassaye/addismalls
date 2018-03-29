package com.example.red.magazine.suppliers.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.suppliers.adapters.SupplierRecyclerviewAdapter;
import com.example.red.magazine.suppliers.adapters.ViewPagerAdapter;
import com.example.red.magazine.suppliers.adaptersmodel.DemandRequestAdapterModel;
import com.example.red.magazine.suppliers.clients.SuppliersClient;
import com.example.red.magazine.suppliers.model.DemandRequest;
import com.example.red.magazine.suppliers.model.SuppliersItem;

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
public class Suppliers_home extends Fragment {

    String token;
    SharedPreferences preferences;
    private TabLayout tabLayout;
    private ArrayList<String> id_holder;
    ViewPagerAdapter adapter;
    SharedPreferences.Editor editor;
    ProgressBar pr;
    List<DemandRequestAdapterModel> demandlist;
    SupplierRecyclerviewAdapter recyclerviewAdapter;
    RecyclerView recyclerView;
    boolean is_truee=false;
    public Suppliers_home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_suppliers_home, container, false);
        id_holder =new ArrayList<>();
        preferences = getActivity().getSharedPreferences("suppliers_data",0);
        editor=preferences.edit();
        token = preferences.getString("token","");
        adapter= new ViewPagerAdapter(((AppCompatActivity)getActivity()).getSupportFragmentManager());

        demandlist=new ArrayList<>();
        recyclerviewAdapter = new SupplierRecyclerviewAdapter(getActivity(),demandlist);
        recyclerView= (RecyclerView)view.findViewById(R.id.supplier_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        tabLayout=(TabLayout)view.findViewById(R.id.tabs);
        pr=(ProgressBar)view.findViewById(R.id.supplier_pr);
        pr.setVisibility(View.VISIBLE);
        //setupViewPager(viewPager);
        Retrofit retrofit=getUserAPIretrofit();
        SuppliersClient client=retrofit.create(SuppliersClient.class);
        Call<List<SuppliersItem>> call= client.get_suppliers_item(token);
        call.enqueue(new Callback<List<SuppliersItem>>() {
            @Override
            public void onResponse(Call<List<SuppliersItem>> call, Response<List<SuppliersItem>> response) {

                if (response.body().size()>0){

                     is_truee=true;
                    pr.setVisibility(View.GONE);
                    for (int i=0;i<response.body().size();i++){
                        TabLayout.Tab tab=tabLayout.newTab();
                        tab.setText(response.body().get(i).getName());
                        id_holder.add(response.body().get(i).getId());
                        tabLayout.addTab(tab);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SuppliersItem>> call, Throwable t) {

            }
        });



        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (is_truee){
                    final String id= id_holder.get(tab.getPosition());
                    Retrofit retrofit1=getUserAPIretrofit();
                    SuppliersClient client1= retrofit1.create(SuppliersClient.class);
                    Call<List<DemandRequest>> call1=client1.get_demand_request(token,id);
                    call1.enqueue(new Callback<List<DemandRequest>>() {
                        @Override
                        public void onResponse(Call<List<DemandRequest>> call, Response<List<DemandRequest>> response) {
                            recyclerviewAdapter.clearApplications();
                            for (int i=0;i<response.body().size();i++){
                                DemandRequestAdapterModel model=new DemandRequestAdapterModel();
                                model.setName(response.body().get(i).getName());
                                model.setLocation(response.body().get(i).getLocation());
                                model.setPhone(response.body().get(i).getPhone());
                                model.setQuantity(response.body().get(i).getQuantity());
                                model.setId(response.body().get(i).getId());
                                demandlist.add(model);
                          }
                            recyclerView.setAdapter(recyclerviewAdapter);
                            recyclerviewAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<List<DemandRequest>> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
