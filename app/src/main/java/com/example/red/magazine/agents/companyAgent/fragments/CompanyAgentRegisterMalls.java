package com.example.red.magazine.agents.companyAgent.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.agents.companyAgent.CompanyAgent;
import com.example.red.magazine.agents.companyAgent.clients.CompanyAgentClient;
import com.example.red.magazine.agents.companyAgent.models.Companys;
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
public class CompanyAgentRegisterMalls extends Fragment {

    private SharedPreferences preferences;
    private String token;
    public CompanyAgentRegisterMalls() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_company_agent_register_malls, container, false);

        preferences= getActivity().getSharedPreferences("company_agent",0);
        token= preferences.getString("token","");

        Retrofit retrofit=getUserAPIretrofit();
        CompanyAgentClient client= retrofit.create(CompanyAgentClient.class);
        Call<List<Companys>> call= client.get_companys(token);
        call.enqueue(new Callback<List<Companys>>() {
            @Override
            public void onResponse(Call<List<Companys>> call, Response<List<Companys>> response) {
                Toast.makeText(getActivity(),response.body().get(0).getName(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Companys>> call, Throwable t) {

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
