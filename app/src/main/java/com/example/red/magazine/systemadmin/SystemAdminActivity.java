package com.example.red.magazine.systemadmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.adaptermodels.MallRequestAdapterModel;
import com.example.red.magazine.adapters.MallRequestAdapter;
import com.example.red.magazine.client.UsersClient;
import com.example.red.magazine.model.MallRequestModel;
import com.example.red.magazine.statics.ProjectStatic;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SystemAdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Intent intent;
    private String token;

    private List<MallRequestAdapterModel> mall_list;
    private RecyclerView recyclerView;
    private MallRequestAdapter adapter;
    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mall_list=new ArrayList<>();
        adapter=new MallRequestAdapter(this,mall_list);
        recyclerView=(RecyclerView)findViewById(R.id.system_admin_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        intent = getIntent();
        token=intent.getStringExtra("token");
        SharedPreferences preferences=getSharedPreferences("token",0);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("token_value",token);
        editor.commit();
        find_request(token);

       /*TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            find_request(token);
                        }
                    },100);
            }
        };
        Timer timers=new Timer();
        timers.schedule(timerTask, 0, 5000);*/
    }
    public void find_request(String token){
        Retrofit retrofit=getUserAPIretrofit();
        UsersClient client=retrofit.create(UsersClient.class);
        Call<List<MallRequestModel>> call=client.get_mall_request(token);
        call.enqueue(new Callback<List<MallRequestModel>>() {
            @Override
            public void onResponse(Call<List<MallRequestModel>> call, Response<List<MallRequestModel>> response) {
                if (response.body().size()>0){
                  adapter.clearApplications();
                }
                for (int i=0;i<response.body().size();i++){
                    MallRequestAdapterModel model=new MallRequestAdapterModel();
                    model.setFirst_name(response.body().get(i).getFirst_name());
                    model.setLast_name(response.body().get(i).getLast_name());
                    model.setMall_name(response.body().get(i).getMall_name());
                    model.setCompany_id(response.body().get(i).getCompany_id());
                    mall_list.add(model);
                }
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onFailure(Call<List<MallRequestModel>> call, Throwable t) {

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.system_admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id==R.id.refresh_system){
            find_request(token);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
