package com.example.red.magazine.suppliers;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.red.magazine.MainActivity;
import com.example.red.magazine.R;
import com.example.red.magazine.malls.admin.mallAdminfragments.AddNewRetailers;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.suppliers.clients.SuppliersClient;
import com.example.red.magazine.suppliers.fragments.Add_new_customer;
import com.example.red.magazine.suppliers.fragments.Show_my_customer;
import com.example.red.magazine.suppliers.fragments.SupplierSetting;
import com.example.red.magazine.suppliers.fragments.Suppliers_home;
import com.example.red.magazine.suppliers.model.Suppliers;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SuppliersDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String token="";
    private Intent intent;
    Toolbar toolbar;
    TextView supplier_name,company_name;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppliers_drawer);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();
        token=intent.getStringExtra("token");

        preferences=getSharedPreferences("suppliers_data",0);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("token",token);
        editor.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_suppliers_drawer, navigationView, false);
        navigationView.addHeaderView(headerView);

        supplier_name=(TextView)headerView.findViewById(R.id.supplier_fullname);
        company_name=(TextView)headerView.findViewById(R.id.supplier_company_name);

        Retrofit retrofit=getUserAPIretrofit();
        SuppliersClient client= retrofit.create(SuppliersClient.class);
        Call<List<Suppliers>> call= client.get_suppliers_info(token);
        call.enqueue(new Callback<List<Suppliers>>() {
            @Override
            public void onResponse(Call<List<Suppliers>> call, Response<List<Suppliers>> response) {
                toolbar.setTitle(response.body().get(0).getName()+" supplier");
                supplier_name.setText(response.body().get(0).getFirst_name()+" "+response.body().get(0).getLast_name());
                company_name.setText(response.body().get(0).getName());
            }
            @Override
            public void onFailure(Call<List<Suppliers>> call, Throwable t) {

            }
        });

        first_view();
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
        getMenuInflater().inflate(R.menu.suppliers_drawer, menu);
        return true;
    }

    public Retrofit getUserAPIretrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ProjectStatic.URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit;
    }
    public void  first_view(){
        Fragment fragment = null;
        getSupportActionBar().setTitle("Dashboard");
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        fragment = new Suppliers_home();
        ft.replace(R.id.placeholder, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            Intent intent = new Intent(SuppliersDrawer.this, MainActivity.class);
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

        if (id == R.id.sup_home) {
            // Handle the camera action
        } else if (id == R.id.sup_add_new_customer) {
            Fragment fragment = null;
            getSupportActionBar().setTitle("Add new customers");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new Add_new_customer();
            ft.replace(R.id.placeholder, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.sup_show_customers) {
            Fragment fragment = null;
            getSupportActionBar().setTitle("My customers");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new Show_my_customer();
            ft.replace(R.id.placeholder, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.sup_setting) {
            Fragment fragment = null;
            getSupportActionBar().setTitle("Setting");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new SupplierSetting();
            ft.replace(R.id.placeholder, fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
