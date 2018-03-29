package com.example.red.magazine.serviceproviders;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.MainActivity;
import com.example.red.magazine.R;
import com.example.red.magazine.malls.admin.mallAdminfragments.AddNewRetailers;
import com.example.red.magazine.malls.fragments.Notification_fragment;
import com.example.red.magazine.serviceproviders.clients.ServicerProvidersUsersClient;
import com.example.red.magazine.serviceproviders.fragments.CompleteProfileFragment;
import com.example.red.magazine.serviceproviders.fragments.DemandFragment;
import com.example.red.magazine.serviceproviders.fragments.ManageAmbience;
import com.example.red.magazine.serviceproviders.fragments.ManageDishes;
import com.example.red.magazine.serviceproviders.fragments.ManagePost;
import com.example.red.magazine.serviceproviders.fragments.PostMenu;
import com.example.red.magazine.serviceproviders.fragments.PostTodasySpecail;
import com.example.red.magazine.serviceproviders.fragments.Restaurants_Manage_Daily_Demand;
import com.example.red.magazine.serviceproviders.fragments.ServiceProviderDashboardFragment;
import com.example.red.magazine.serviceproviders.fragments.SupplyFragment;
import com.example.red.magazine.serviceproviders.model.ServicerProviderUser;
import com.example.red.magazine.statics.ProjectStatic;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceProvidersDashBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String token="";
    private Intent intent;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private int category_id=0;
    private TextView user_name,company_name;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_providers_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        intent=getIntent();
        token = intent.getStringExtra("token");

        preferences=getSharedPreferences("service_provider_data",0);
        editor=preferences.edit();
        editor.putString("token",token);
        editor.commit();

        user_name=(TextView)findViewById(R.id.user_name);
        company_name=(TextView)findViewById(R.id.service_company_name);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        Retrofit retrofit=getUserAPIretrofit();
        ServicerProvidersUsersClient client=retrofit.create(ServicerProvidersUsersClient.class);
        Call<List<ServicerProviderUser>> call=client.check_retailer_profile_completion(token);
        call.enqueue(new Callback<List<ServicerProviderUser>>() {
            @Override
            public void onResponse(Call<List<ServicerProviderUser>> call, Response<List<ServicerProviderUser>> response) {
               if (response.body().get(0).getCategory_id().equals("0")){
                   Fragment fragment = null;
                   getSupportActionBar().setTitle("Profile completion");
                   FragmentManager fm = getFragmentManager();
                   FragmentTransaction ft = fm.beginTransaction();
                   fragment = new CompleteProfileFragment();
                   ft.replace(R.id.placeholder, fragment);
                   ft.addToBackStack(null);
                   ft.commit();
               }else {
                   category_id=Integer.parseInt(response.body().get(0).getCategory_id());
                   if (response.body().get(0).getCategory_id().equals("1")){
                       navigationView.getMenu().clear(); //clear old inflated items.
                       navigationView.inflateMenu(R.menu.restaurant_menu);

                   }
                   Fragment fragment = null;
                   getSupportActionBar().setTitle("Dashboards");
                   FragmentManager fm = getFragmentManager();
                   FragmentTransaction ft = fm.beginTransaction();
                   fragment = new ServiceProviderDashboardFragment();
                   ft.replace(R.id.placeholder, fragment);
                   ft.addToBackStack(null);
                   ft.commit();
               }
            }

            @Override
            public void onFailure(Call<List<ServicerProviderUser>> call, Throwable t) {

            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);



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
    public Retrofit getUserAPIretrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ProjectStatic.URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.service_providers_dash_board, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            Intent intent = new Intent(ServiceProvidersDashBoard.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
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
        } else if (id == R.id.post_demand) {
            Fragment fragment=null;
            getSupportActionBar().setTitle("Post demand");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new DemandFragment();
            ft.replace(R.id.placeholder, fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.post_supply) {
            Fragment fragment=null;
            getSupportActionBar().setTitle("Post demand");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new SupplyFragment();
            ft.replace(R.id.placeholder, fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.post_menu) {
            Fragment fragment=null;
            getSupportActionBar().setTitle("Post Menu");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new PostMenu();
            ft.replace(R.id.placeholder, fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.manage_dishes) {
            Fragment fragment=null;
            getSupportActionBar().setTitle("Today's Special");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new ManageDishes();
            ft.replace(R.id.placeholder, fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.daily_demand) {
            Fragment fragment=null;
            getSupportActionBar().setTitle("Daily demand");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new Restaurants_Manage_Daily_Demand();
            ft.replace(R.id.placeholder, fragment);
            ft.addToBackStack(null);
            ft.commit();

        }else if (id==R.id.manage_ambience){
            Fragment fragment=null;
            getSupportActionBar().setTitle("Manage ambience");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new ManageAmbience();
            ft.replace(R.id.placeholder, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }else if (id==R.id.post_sale){
            Fragment fragment=null;
            getSupportActionBar().setTitle("Manage ambience");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new ManagePost();
            ft.replace(R.id.placeholder, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
