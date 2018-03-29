package com.example.red.magazine.malls.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.malls.client.MallsClient;
import com.example.red.magazine.malls.model.VacancyDetailModel;
import com.example.red.magazine.malls.model.VacancyModel;
import com.example.red.magazine.statics.ProjectStatic;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VacancyActivity extends AppCompatActivity {
    private String id,title;
    private Intent intent;
    private TextView category,salary,employment_type,educational_level,experience,deadline,phone,company_name,description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        intent=getIntent();
        id=intent.getStringExtra("id");
        title=intent.getStringExtra("title");

        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        category=(TextView)findViewById(R.id.vacancy_detail_category);
        salary=(TextView)findViewById(R.id.vacancy_detail_salary);
        employment_type=(TextView)findViewById(R.id.vacancy_detail_employment_type);
        educational_level=(TextView)findViewById(R.id.vacancy_detail_educational_level);
        experience=(TextView)findViewById(R.id.vacancy_detail_year_exp);
        deadline=(TextView)findViewById(R.id.vacancy_detail_deadline);
        description=(TextView)findViewById(R.id.vacancy_detail_description);
        phone=(TextView)findViewById(R.id.vacancy_detail_phone_no);
        company_name=(TextView)findViewById(R.id.vacancy_detail_company_name);

        Retrofit retrofit= getUserAPIretrofit();
        MallsClient client=retrofit.create(MallsClient.class);
        Call<List<VacancyDetailModel>> call=client.vacancy_details(id);
        call.enqueue(new Callback<List<VacancyDetailModel>>() {
            @Override
            public void onResponse(Call<List<VacancyDetailModel>> call, Response<List<VacancyDetailModel>> response) {
                category.setText("Category :"+response.body().get(0).getName());
                salary.setText("Salary :"+response.body().get(0).getSalary());
                description.setText(response.body().get(0).getDescription());
                phone.setText("Phone no: "+response.body().get(0).getPhone_no());
                company_name.setText("Company name: "+response.body().get(0).getCompany_name());
                employment_type.setText("Employment type: "+response.body().get(0).getEmployment_type());
                educational_level.setText("Educational level: "+response.body().get(0).getQualifications());
                experience.setText("Experience: "+response.body().get(0).getYears_exp()+" years");


             }

            @Override
            public void onFailure(Call<List<VacancyDetailModel>> call, Throwable t) {

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
