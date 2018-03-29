package com.example.red.magazine.malls.admin.mallAdminfragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.red.magazine.R;
import com.example.red.magazine.malls.client.MallsClient;
import com.example.red.magazine.malls.model.JobCategory;
import com.example.red.magazine.malls.model.PostVacancy;
import com.example.red.magazine.malls.model.ResponseMessage;
import com.example.red.magazine.statics.ProjectStatic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ManageVacancy extends Fragment {
    private String token;
  private ViewFlipper flipper;
    private View first_view,second_view,third_view;
    private Button post_vacancy,manage_vacancy;

    private RadioGroup group;
    private Spinner spinner,emplyment_spinner;
    private ArrayList<String> spinner_data,spinner_data2;
    private ArrayAdapter<String> adapter,adapter2;
    private EditText job_title,educational_level,years_exp,more_info,deadline,salary;
    private LinearLayout salary_layout,fixed_salary_layout;
    private Button submit_vacancy;
    private String salary_type="";
    public ManageVacancy() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_manage_vacancy, container, false);
        SharedPreferences preferences=getActivity().getSharedPreferences("token",0);
        token= preferences.getString("token_value","");

        flipper=(ViewFlipper)view.findViewById(R.id.manage_vacancy_flipper);

        first_view= LayoutInflater.from(getActivity()).inflate(R.layout.vacancy_first_view,null);
        flipper.addView(first_view);
        flipper.showNext();

        post_vacancy=(Button)first_view.findViewById(R.id.add_vacancy);
        manage_vacancy=(Button)first_view.findViewById(R.id.manage_vacancys);

        second_view=LayoutInflater.from(getActivity()).inflate(R.layout.vacancy_post_layout,null);

        job_title=(EditText)second_view.findViewById(R.id.job_title);
        educational_level=(EditText)second_view.findViewById(R.id.educational_level);
        years_exp=(EditText)second_view.findViewById(R.id.years_exp);
        more_info=(EditText)second_view.findViewById(R.id.more_info_how_to_apply);
        deadline=(EditText)second_view.findViewById(R.id.dead_line);
        salary=(EditText)second_view.findViewById(R.id.fixed_salary_value);
        submit_vacancy=(Button)second_view.findViewById(R.id.post_vacancy);

        spinner=(Spinner) second_view.findViewById(R.id.job_category);
        emplyment_spinner=(Spinner)second_view.findViewById(R.id.employment_type);
        spinner_data2=new ArrayList<>();
        spinner_data2.add("Select Employment type");
        spinner_data2.add("Full-time");
        spinner_data2.add("Part-time");
        adapter2= new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, spinner_data2);
        emplyment_spinner.setAdapter(adapter2);


        spinner_data=new ArrayList<>();
        Retrofit retrofit= getUserAPIretrofit();
        MallsClient client=retrofit.create(MallsClient.class);
        Call<List<JobCategory>> call=client.get_vacancy_cate(token);
        call.enqueue(new Callback<List<JobCategory>>() {
            @Override
            public void onResponse(Call<List<JobCategory>> call, Response<List<JobCategory>> response) {
                spinner_data.add("Select Job category");
                for (int i=0;i<response.body().size();i++){
                    spinner_data.add(response.body().get(i).getName());
                }
                adapter= new ArrayAdapter<String>(getActivity(),
                        R.layout.spinner_item, spinner_data);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<JobCategory>> call, Throwable t) {

            }
        });


        salary_layout=(LinearLayout)second_view.findViewById(R.id.salary_Layout);
        fixed_salary_layout=(LinearLayout)second_view.findViewById(R.id.fixed_salary_layout);

        group=(RadioGroup)second_view.findViewById(R.id.group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
               RadioButton but= radioGroup.findViewById(i);
                if (but.getText().equals("Negotiable")){
                  salary_type="Negotiable";
                }else {
                    salary_type="fixed";
                    salary_layout.setVisibility(View.GONE);
                    fixed_salary_layout.setVisibility(View.VISIBLE);
                }
            }
        });

        submit_vacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String job_title_value=job_title.getText().toString();
                String job_category_value=spinner.getSelectedItem().toString();
                String salary_value="";
                if (salary_type.equals("fixed")){
                  salary_value=salary.getText().toString();
                }else {
                    salary_value="Negotiable";
                }
                String employment_type=emplyment_spinner.getSelectedItem().toString();
                String educational_level_value=educational_level.getText().toString();
                String experience_value=years_exp.getText().toString();
                String how_to_apply_value=more_info.getText().toString();
                String deadline_value=deadline.getText().toString();

                if (job_title_value.equals("")){
                    Toast.makeText(getActivity(),"Please enter job title",Toast.LENGTH_LONG).show();
                }else if (job_category_value.equals("Select Job category")){
                    Toast.makeText(getActivity(),"Please Select job category",Toast.LENGTH_LONG).show();

                }else if (salary_value.equals("")){
                    Toast.makeText(getActivity(),"Please Select salary type",Toast.LENGTH_LONG).show();

                }else if (educational_level_value.equals("")){
                    Toast.makeText(getActivity(),"Please enter educational level",Toast.LENGTH_LONG).show();
                }else if (deadline_value.equals("")){
                    Toast.makeText(getActivity(),"Please enter dead line",Toast.LENGTH_LONG).show();

                }else {
                    Retrofit retrofit1= getUserAPIretrofit();
                    MallsClient client1=retrofit1.create(MallsClient.class);
                    PostVacancy postVacancy=new PostVacancy(job_title_value,job_category_value,salary_value,employment_type,experience_value,educational_level_value,how_to_apply_value,deadline_value);

                    Call<ResponseMessage> call1=client1.post_vacancy(token,postVacancy);
                    call1.enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                            Toast.makeText(getActivity(),response.body().getStatus(),Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseMessage> call, Throwable t) {

                        }
                    });
                }

            }
        });

        post_vacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipper.removeView(second_view);
                flipper.addView(second_view);
                flipper.showNext();
            }
        });

        third_view=LayoutInflater.from(getActivity()).inflate(R.layout.vacancy_manage_layout,null);


        Animation in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
        Animation out = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_left);
        // set the animation type's to ViewFlipperActivity
        flipper.setInAnimation(in);
        flipper.setOutAnimation(out);
        // set interval time for flipping between views
        flipper.setFlipInterval(3000);
        flipper.setDisplayedChild(6);

        return view;
    }

    public Retrofit getUserAPIretrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ProjectStatic.URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit;
    }


}
