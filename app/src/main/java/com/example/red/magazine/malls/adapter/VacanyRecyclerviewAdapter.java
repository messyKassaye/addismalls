package com.example.red.magazine.malls.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.red.magazine.R;
import com.example.red.magazine.malls.adaptermodel.LeaseSpaceAdapterModel;
import com.example.red.magazine.malls.adaptermodel.VacanyAdapterModel;
import com.example.red.magazine.malls.ui.VacancyActivity;
import com.example.red.magazine.statics.ProjectStatic;

import java.util.List;

/**
 * Created by ezedin on 10/29/17.
 */

public class VacanyRecyclerviewAdapter extends RecyclerView.Adapter<VacanyRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<VacanyAdapterModel> albumList;

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
            see_more=(Button) view.findViewById(R.id.vacancy_see_more);


        }
    }


    public VacanyRecyclerviewAdapter(Context mContext, List<VacanyAdapterModel> albumList) {
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
                .inflate(R.layout.vacany_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final VacanyAdapterModel model = albumList.get(position);
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
                Intent intent=new Intent(mContext, VacancyActivity.class);
                intent.putExtra("id",view.getTag().toString());
                intent.putExtra("title",model.getJob_title());
                mContext.startActivity(intent);
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
