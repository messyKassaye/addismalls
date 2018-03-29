package com.example.red.magazine.agents.companyAgent.clients;

import com.example.red.magazine.agents.companyAgent.models.AgentModel;
import com.example.red.magazine.agents.companyAgent.models.Companys;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Meseret on 11/7/2017.
 */

public interface CompanyAgentClient {

    @GET("agent_detail")
    Call<AgentModel> get_agent_detail(@Query("token")String token);

    @GET("company_list")
    Call<List<Companys>> get_companys(@Query("token")String token);

}
