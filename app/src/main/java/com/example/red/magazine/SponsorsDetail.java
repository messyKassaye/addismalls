package com.example.red.magazine;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.adaptermodels.SponsorsAmenityAdapterModel;
import com.example.red.magazine.adaptermodels.SponsorsBranchAdaptorModel;
import com.example.red.magazine.adapters.SponsorsAmenityRecyclerviewAdapter;
import com.example.red.magazine.adapters.SponsorsbranchRecyclerviewAdapter;
import com.example.red.magazine.client.SponserClient;
import com.example.red.magazine.model.SponsorDetail;
import com.example.red.magazine.model.SponsorsAmenity;
import com.example.red.magazine.model.Sponsors_branch;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SponsorsDetail extends AppCompatActivity {
  private String company_id;
    private Intent intent;
    private TextView description;
    private ImageView sponsor_compant_logo;
    private Toolbar toolbar;
    private RecyclerView recyclerView,amenity_recyclerview;
    private List<SponsorsBranchAdaptorModel> list;
    private SponsorsbranchRecyclerviewAdapter adapter;
    private List<SponsorsAmenityAdapterModel> amenityList;
    private SponsorsAmenityRecyclerviewAdapter amenity_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors_detail);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        intent=getIntent();
        company_id=intent.getStringExtra("company_id");

        sponsor_compant_logo=(ImageView)findViewById(R.id.sponsored_company_logo);
        description=(TextView)findViewById(R.id.sponsors_description);

        list=new ArrayList<>();
        adapter=new SponsorsbranchRecyclerviewAdapter(this,list);
        Retrofit retrofit=getUserAPIretrofit();
        SponserClient client=retrofit.create(SponserClient.class);
        Call<List<SponsorDetail>> call=client.getSponserosDetail(company_id);
        call.enqueue(new Callback<List<SponsorDetail>>() {
            @Override
            public void onResponse(Call<List<SponsorDetail>> call, Response<List<SponsorDetail>> response) {
                if (response.isSuccessful()){
                    toolbar.setTitle(response.body().get(0).getName());
                    Picasso.with(getApplicationContext()).load(ProjectStatic.IMAPGE_PATH+response.body().get(0).getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(sponsor_compant_logo);
                    description.setText(response.body().get(0).getDescription());
                }
            }

            @Override
            public void onFailure(Call<List<SponsorDetail>> call, Throwable t) {

            }
        });

        recyclerView=(RecyclerView)findViewById(R.id.sponsors_branach_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit1=getUserAPIretrofit();
        SponserClient client1=retrofit1.create(SponserClient.class);
        Call<List<Sponsors_branch>> call1=client1.getSponsorsBranch(company_id);
        call1.enqueue(new Callback<List<Sponsors_branch>>() {
            @Override
            public void onResponse(Call<List<Sponsors_branch>> call, Response<List<Sponsors_branch>> response) {
                if (response.isSuccessful()){
                    for (int i=0;i<response.body().size();i++){
                        SponsorsBranchAdaptorModel model=new SponsorsBranchAdaptorModel();
                        model.setPhoto_path(response.body().get(i).getPhoto_path());
                        model.setName(response.body().get(i).getName());
                        list.add(model);
                    }
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Sponsors_branch>> call, Throwable t) {

            }
        });

        amenityList=new ArrayList<>();
        amenity_adapter=new SponsorsAmenityRecyclerviewAdapter(this,amenityList);
        amenity_recyclerview=(RecyclerView)findViewById(R.id.sponsors_amenity_recyclerview);
        amenity_recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        amenity_recyclerview.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit2 = getUserAPIretrofit();
        SponserClient client2=retrofit2.create(SponserClient.class);
        Call<List<SponsorsAmenity>> call2=client2.getSponsorsAmenity(company_id);
        call2.enqueue(new Callback<List<SponsorsAmenity>>() {
            @Override
            public void onResponse(Call<List<SponsorsAmenity>> call, Response<List<SponsorsAmenity>> response) {
                if(response.isSuccessful()){
                    for (int i=0;i<response.body().size();i++){
                        SponsorsAmenityAdapterModel model=new SponsorsAmenityAdapterModel();
                        model.setName(response.body().get(i).getName());
                        model.setDescription(response.body().get(i).getDescription());
                        amenityList.add(model);
                    }
                    amenity_adapter.notifyDataSetChanged();
                    amenity_recyclerview.setAdapter(amenity_adapter);
                }
            }

            @Override
            public void onFailure(Call<List<SponsorsAmenity>> call, Throwable t) {

            }
        });



    }

    public Retrofit getUserAPIretrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ProjectStatic.URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
