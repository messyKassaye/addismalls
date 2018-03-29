package com.example.red.magazine.hotels;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.hotels.client.HotelsClient;
import com.example.red.magazine.hotels.models.RoomDetailsModel;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomsDetails extends AppCompatActivity {
  private TextView title,subtitle,description,key_features;
    private ImageView cover;
    private RecyclerView recyclerView;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        title=(TextView)findViewById(R.id.room_detail_title);
        subtitle=(TextView)findViewById(R.id.room_detail_sub_title);
        description=(TextView)findViewById(R.id.room_detail_description);
        key_features=(TextView)findViewById(R.id.room_detail_key_features_list);
        cover=(ImageView)findViewById(R.id.room_detail_photo);
        recyclerView=(RecyclerView)findViewById(R.id.room_detail_amenity_recyclerview);

        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        Retrofit retrofit= getUserAPIretrofit();
        HotelsClient client= retrofit.create(HotelsClient.class);
        Call<List<RoomDetailsModel>>call=client.getRoomsDetails(id);
        call.enqueue(new Callback<List<RoomDetailsModel>>() {
            @Override
            public void onResponse(Call<List<RoomDetailsModel>> call, Response<List<RoomDetailsModel>> response) {
                 if (response.isSuccessful()){
                     title.setText(response.body().get(0).getTitle());
                     subtitle.setText(response.body().get(0).getSub_title());
                     description.setText(response.body().get(0).getDescription());
                     key_features.setText("Size: "+response.body().get(0).getNo_rooms()+", accommodate: "+response.body().get(0).getAccomodation()+" peoples");
                     Picasso.with(getApplicationContext()).load(ProjectStatic.IMAPGE_PATH+response.body().get(0).getCover()).placeholder(R.drawable.background_tile2_small).into(cover);

                 }
            }

            @Override
            public void onFailure(Call<List<RoomDetailsModel>> call, Throwable t) {

            }
        });
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
    public Retrofit getUserAPIretrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ProjectStatic.URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit;
    }
}
