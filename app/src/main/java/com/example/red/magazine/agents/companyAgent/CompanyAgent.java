package com.example.red.magazine.agents.companyAgent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.agents.companyAgent.clients.CompanyAgentClient;
import com.example.red.magazine.agents.companyAgent.fragments.CompanyAgentRegisterMalls;
import com.example.red.magazine.agents.companyAgent.fragments.CompanyAgentRegisterTenant;
import com.example.red.magazine.agents.companyAgent.models.AgentModel;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.suppliers.fragments.Suppliers_home;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyAgent extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
private TextView agent_name,agent_type;
    private String token;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_agent2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Malladdis agent");
        setSupportActionBar(toolbar);

        Intent intent= getIntent();
        token= intent.getStringExtra("token");

        preferences= getSharedPreferences("company_agent",0);
        editor= preferences.edit();
        editor.putString("token",token);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_suppliers_drawer, navigationView, false);
        navigationView.addHeaderView(headerView);

        agent_name=(TextView)headerView.findViewById(R.id.supplier_fullname);
        agent_type=(TextView)headerView.findViewById(R.id.supplier_company_name);

        Retrofit retrofit= getUserAPIretrofit();
        CompanyAgentClient client= retrofit.create(CompanyAgentClient.class);
        Call<AgentModel> call= client.get_agent_detail(token);
        call.enqueue(new Callback<AgentModel>() {
            @Override
            public void onResponse(Call<AgentModel> call, Response<AgentModel> response) {
                agent_name.setText(response.body().getFirst_name()+" "+response.body().getLast_name());
                agent_type.setText("Malladdis agent");
            }

            @Override
            public void onFailure(Call<AgentModel> call, Throwable t) {

            }
        });

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
        getMenuInflater().inflate(R.menu.company_agent, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.company_agent_register_home) {
            // Handle the camera action
        } else if (id == R.id.company_agent_register_malls) {
            Fragment fragment = null;
            getSupportActionBar().setTitle("Registration Form");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new CompanyAgentRegisterMalls();
            ft.replace(R.id.placeholder, fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.company_agents_show) {
            Fragment fragment = null;
            getSupportActionBar().setTitle("Show registrations");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new CompanyAgentRegisterTenant();
            ft.replace(R.id.placeholder, fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
