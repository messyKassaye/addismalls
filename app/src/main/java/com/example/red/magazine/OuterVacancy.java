package com.example.red.magazine;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.red.magazine.adaptermodels.OuterVacancyAdapterModel;
import com.example.red.magazine.client.OuterVacancyClient;
import com.example.red.magazine.malls.client.MallsClient;
import com.example.red.magazine.malls.model.VacancyDetailModel;
import com.example.red.magazine.model.OuterVacancyModel;
import com.example.red.magazine.statics.ProjectStatic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OuterVacancy extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<OuterVacancyAdapterModel>listmodel;
    private OuterVacancyRecyclerviewAdapter adapter;

    private ViewFlipper flipper;
    private View first_view,detail_view;

    private TextView category,salary,employment_type,educational_level,experience,deadline,phone,company_name,description;
   private Button back_to_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outer_vacancy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Vacancy");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        flipper=(ViewFlipper)findViewById(R.id.outer_flipper);

        first_view=  LayoutInflater.from(getApplicationContext()).inflate(R.layout.outer_vacancy_firstview,null);
        detail_view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.outer_vacancy_detail_layout,null);

        //===============================
        //view
        category=(TextView)detail_view.findViewById(R.id.vacancy_detail_category);
        salary=(TextView)detail_view.findViewById(R.id.vacancy_detail_salary);
        employment_type=(TextView)detail_view.findViewById(R.id.vacancy_detail_employment_type);
        educational_level=(TextView)detail_view.findViewById(R.id.vacancy_detail_educational_level);
        experience=(TextView)detail_view.findViewById(R.id.vacancy_detail_year_exp);
        deadline=(TextView)detail_view.findViewById(R.id.vacancy_detail_deadline);
        description=(TextView)detail_view.findViewById(R.id.vacancy_detail_description);
        phone=(TextView)detail_view.findViewById(R.id.vacancy_detail_phone_no);
        company_name=(TextView)detail_view.findViewById(R.id.vacancy_detail_company_name);
        back_to_main=(Button)detail_view.findViewById(R.id.back_to_main);
        back_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipper.removeView(first_view);
                flipper.addView(first_view);
                firstTask();
                flipper.showNext();
            }
        });
        //===========================

        flipper.addView(first_view);
        flipper.showNext();
        listmodel=new ArrayList<>();
        adapter=new OuterVacancyRecyclerviewAdapter(this,listmodel);
        recyclerView=(RecyclerView)first_view.findViewById(R.id.outer_vacancy_recyclreview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        firstTask();
        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        // set the animation type's to ViewFlipperActivity
        flipper.setInAnimation(in);
        flipper.setOutAnimation(out);
        // set interval time for flipping between views
        flipper.setFlipInterval(3000);
        flipper.setDisplayedChild(6);
    }

    public void firstTask(){
        Retrofit retrofit=getUserAPIretrofit();
        OuterVacancyClient client= retrofit.create(OuterVacancyClient.class);
        Call<List<OuterVacancyModel>> call=client.getVacancy();
        call.enqueue(new Callback<List<OuterVacancyModel>>() {
            @Override
            public void onResponse(Call<List<OuterVacancyModel>> call, Response<List<OuterVacancyModel>> response) {
                if (response.body().size()>0){
                    adapter.clearApplications();
                }
                for (int i=0;i<response.body().size();i++){
                    OuterVacancyAdapterModel model=new OuterVacancyAdapterModel();
                    model.setJob_title(response.body().get(i).getJob_title());
                    model.setName(response.body().get(i).getName());
                    model.setSalary(response.body().get(i).getSalary());
                    model.setEmployment_type(response.body().get(i).getEmployment_type());
                    model.setQualifications(response.body().get(i).getQualifications());
                    model.setDeadline(response.body().get(i).getDeadline());
                    model.setYears_exp(response.body().get(i).getYears_exp());
                    model.setId(response.body().get(i).getId());
                    listmodel.add(model);
                }

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<OuterVacancyModel>> call, Throwable t) {

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

    public class OuterVacancyRecyclerviewAdapter extends RecyclerView.Adapter<OuterVacancyRecyclerviewAdapter.MyViewHolder> {

        private Context mContext;
        private List<OuterVacancyAdapterModel> albumList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView job_title,category,salary,employment_type,education_level,year_exp,deadline;
            private Button see_more;


            public MyViewHolder(View view) {
                super(view);
                job_title = (TextView) view.findViewById(R.id.job_title);
                category = (TextView) view.findViewById(R.id.vacancy_category);
                salary=(TextView)view.findViewById(R.id.vacancy_salary);
                employment_type=(TextView)view.findViewById(R.id.vacancy_employment_type);
                education_level=(TextView)view.findViewById(R.id.vacancy_education_level);
                year_exp=(TextView)view.findViewById(R.id.vacancy_experience);
                deadline=(TextView)view.findViewById(R.id.vacancy_deadline);
                see_more=(Button) view.findViewById(R.id.outer_vacancy_see_more);


            }
        }


        public OuterVacancyRecyclerviewAdapter(Context mContext, List<OuterVacancyAdapterModel> albumList) {
            this.mContext = mContext;
            this.albumList = albumList;
        }
        public void clearApplications() {
            int size = this.albumList.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    albumList.remove(0);
                }

                this.notifyItemRangeRemoved(0, size);
            }
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.outer_vacancy_recyclerview_layout, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final OuterVacancyAdapterModel model = albumList.get(position);
            holder.job_title.setText("Job title: "+model.getJob_title());
            holder.category.setText("Category :"+model.getName());
            holder.salary.setText("Salary: "+model.getSalary());
            holder.employment_type.setText("Employment type: "+model.getEmployment_type());
            holder.education_level.setText("Educational level: "+model.getQualifications());
            holder.year_exp.setText("Experience :"+model.getYears_exp()+" years");
            holder.deadline.setText("dead line: "+model.getDeadline());
            holder.see_more.setTag(model.getId());
            holder.see_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                vacancyDetail(view.getTag().toString());
                }
            });


        }


        /**
         * Click listener for popup menu items
         */

        @Override
        public int getItemCount() {
            return albumList.size();
        }
    }

    public void vacancyDetail(String id){
        flipper.removeView(detail_view);
        flipper.addView(detail_view);
        flipper.showNext();
        Retrofit retrofit= getUserAPIretrofit();
        OuterVacancyClient client=retrofit.create(OuterVacancyClient.class);
        Call<List<VacancyDetailModel>> calls=client.vacancy_details(id);
        calls.enqueue(new Callback<List<VacancyDetailModel>>() {
            @Override
            public void onResponse(Call<List<VacancyDetailModel>> call, Response<List<VacancyDetailModel>> response) {
              if (response.isSuccessful()){
                 category.setText("Category :"+response.body().get(0).getName());
                salary.setText("Salary :"+response.body().get(0).getSalary());
                description.setText(response.body().get(0).getDescription());
                phone.setText("Phone no: "+response.body().get(0).getPhone_no());
                company_name.setText("Company name: "+response.body().get(0).getCompany_name());
                employment_type.setText("Employment type: "+response.body().get(0).getEmployment_type());
                educational_level.setText("Educational level: "+response.body().get(0).getQualifications());
                experience.setText("Experience: "+response.body().get(0).getYears_exp()+" years");
              }

            }

            @Override
            public void onFailure(Call<List<VacancyDetailModel>> call, Throwable t) {

            }
        });

    }


}
