package com.example.red.magazine.malls.admin.mallAdminfragments;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.red.magazine.R;

import com.example.red.magazine.malls.adapter.Retailers_mange_recyclerview_adapter;
import com.example.red.magazine.malls.adaptermodel.ManageRetailersModelAdapter;
import com.example.red.magazine.malls.client.MallsClient;
import com.example.red.magazine.malls.client.ServicePrClient;
import com.example.red.magazine.malls.model.ManageRetailersModel;
import com.example.red.magazine.malls.model.NewRetailers;
import com.example.red.magazine.malls.model.ResponseMessage;
import com.example.red.magazine.serviceproviders.clients.ServicerProvidersUsersClient;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewRetailers extends Fragment {

    private EditText retailers_password,retailers_tin_no;
    private ImageView plus_retailers;
    private LinearLayout editext_layout;
    private int id_numers=1;
    private ArrayList id_holder=new ArrayList();
    private List<EditText> editTextdata=new ArrayList<>();
    private Button add_all;
    private String token="";
    private ProgressBar pr;

    private ViewFlipper flipper;
    private View first_view,add_new_retailers_view,add_success,manage_retailers_layout;
    private Button add_retailer,manage_retailers;
    private TimerTask timerTask;
    private Timer timers;
    private RecyclerView floor1,floor2,floor3,floor4,floor5;
    public AddNewRetailers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_new_retailers, container, false);
        SharedPreferences preferences=getActivity().getSharedPreferences("token",0);

        timerTask=new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        flipper.removeView(first_view);
                        flipper.addView(first_view);
                        flipper.showNext();
                    }
                });
            }
        };
        timers =new Timer();

        token= preferences.getString("token_value","");

        flipper=(ViewFlipper)view.findViewById(R.id.retailers_flipper);
        first_view=LayoutInflater.from(getActivity()).inflate(R.layout.retailers_first_view,null);
         add_new_retailers_view=LayoutInflater.from(getActivity()).inflate(R.layout.retailers_add_layout,null);
         add_success=LayoutInflater.from(getActivity()).inflate(R.layout.retailers_added_success,null);
         manage_retailers_layout=LayoutInflater.from(getActivity()).inflate(R.layout.retailers_manage_layout,null);
         flipper.addView(first_view);
         flipper.showNext();

        //recyc;er
        floor1=(RecyclerView)manage_retailers_layout.findViewById(R.id.first_floor);
        floor2=(RecyclerView)manage_retailers_layout.findViewById(R.id.second_floor);
        floor3=(RecyclerView)manage_retailers_layout.findViewById(R.id.third_floor);
        floor4=(RecyclerView)manage_retailers_layout.findViewById(R.id.fourth_floor);
        floor5=(RecyclerView)manage_retailers_layout.findViewById(R.id.fifth_floor);

        //

        add_retailer=(Button)first_view.findViewById(R.id.add_retailers);
        manage_retailers=(Button)first_view.findViewById(R.id.manage_retailers);

        add_retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipper.removeView(add_new_retailers_view);
                flipper.addView(add_new_retailers_view);
                flipper.showNext();
            }
        });

        manage_retailers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Your retailers");
                flipper.removeView(manage_retailers_layout);
                flipper.addView(manage_retailers_layout);
                flipper.showNext();
                getfirstfloor(token);
                getseconfloor(token);
                getthirfloor(token);
                getfourthfloor(token);
                getfifthfloor(token);

            }
        });





        retailers_password=(EditText)add_new_retailers_view.findViewById(R.id.retailers_password);
        retailers_tin_no=(EditText)add_new_retailers_view.findViewById(R.id.retailers_tin_no_edittext);
        add_all=(Button)add_new_retailers_view.findViewById(R.id.add_new_user);
        pr=(ProgressBar)add_new_retailers_view.findViewById(R.id.new_retailer_pr);


        add_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password_value= retailers_password.getText().toString().trim();
                final String tin_no_value=retailers_tin_no.getText().toString();
                 if (password_value.equals("")){
                     Toast.makeText(getActivity(),"Please enter your constant password",Toast.LENGTH_LONG).show();
                 }else if (tin_no_value.equals("")) {
                     Toast.makeText(getActivity(),"Please enter retailers tin no",Toast.LENGTH_LONG).show();
                 }else {
                     pr.setVisibility(View.VISIBLE);
                     NewRetailers retailers=new NewRetailers(password_value,tin_no_value);
                     Retrofit retrofit= getUserAPIretrofit();
                     MallsClient client=retrofit.create(MallsClient.class);
                     Call<ResponseMessage> call=client.add_new_retailers(token,retailers);
                     call.enqueue(new Callback<ResponseMessage>() {
                         @Override
                         public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                             if (response.body().getStatus().equals("ok")){
                                 flipper.removeView(add_success);
                                 flipper.addView(add_success);
                                 flipper.showNext();
                                 timers.schedule(timerTask,3000);
                             }else if (response.body().getStatus().equals("registered")){
                                 pr.setVisibility(View.GONE);
                                 Toast.makeText(getActivity(),"Some retailers(tekeray) is registered by "+tin_no_value+" tin no.Please check the tin no ):",Toast.LENGTH_LONG).show();
                             }
                         }

                         @Override
                         public void onFailure(Call<ResponseMessage> call, Throwable t) {


                         }
                     });
                 }
            }
        });

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

    public void getfirstfloor(String token){
        final List<ManageRetailersModelAdapter> managelist=new ArrayList<>();
        final Retailers_mange_recyclerview_adapter adapterfloor1=new Retailers_mange_recyclerview_adapter(getActivity(),managelist);
        floor1.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        floor1.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit= getUserAPIretrofit();
        ServicePrClient client= retrofit.create(ServicePrClient.class);
        Call<List<ManageRetailersModel>> call= client.get_retailers(token,"1");
        call.enqueue(new Callback<List<ManageRetailersModel>>() {
            @Override
            public void onResponse(Call<List<ManageRetailersModel>> call, Response<List<ManageRetailersModel>> response) {
                for (int i=0;i<response.body().size();i++){
                    ManageRetailersModelAdapter model=new ManageRetailersModelAdapter();
                    model.setName(response.body().get(i).getName());
                    model.setPhoto_path(response.body().get(i).getPhoto_path());
                    model.setOffice_no(response.body().get(i).getOffice_no());
                    managelist.add(model);
                }
                floor1.setAdapter(adapterfloor1);
                adapterfloor1.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ManageRetailersModel>> call, Throwable t) {

            }
        });
    }
    public void getseconfloor(String token){
        final List<ManageRetailersModelAdapter> managelist=new ArrayList<>();
        final Retailers_mange_recyclerview_adapter adapterfloor1=new Retailers_mange_recyclerview_adapter(getActivity(),managelist);
        floor2.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        floor2.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit= getUserAPIretrofit();
        ServicePrClient client= retrofit.create(ServicePrClient.class);
        Call<List<ManageRetailersModel>> call= client.get_retailers(token,"2");
        call.enqueue(new Callback<List<ManageRetailersModel>>() {
            @Override
            public void onResponse(Call<List<ManageRetailersModel>> call, Response<List<ManageRetailersModel>> response) {
                for (int i=0;i<response.body().size();i++){
                    ManageRetailersModelAdapter model=new ManageRetailersModelAdapter();
                    model.setName(response.body().get(i).getName());
                    model.setPhoto_path(response.body().get(i).getPhoto_path());
                    model.setOffice_no(response.body().get(i).getOffice_no());
                    managelist.add(model);
                }
                floor2.setAdapter(adapterfloor1);
                adapterfloor1.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ManageRetailersModel>> call, Throwable t) {

            }
        });
    }
    public void  getthirfloor(String token){
        final List<ManageRetailersModelAdapter> managelist=new ArrayList<>();
        final Retailers_mange_recyclerview_adapter adapterfloor1=new Retailers_mange_recyclerview_adapter(getActivity(),managelist);
        floor3.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        floor3.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit= getUserAPIretrofit();
        ServicePrClient client= retrofit.create(ServicePrClient.class);
        Call<List<ManageRetailersModel>> call= client.get_retailers(token,"3");
        call.enqueue(new Callback<List<ManageRetailersModel>>() {
            @Override
            public void onResponse(Call<List<ManageRetailersModel>> call, Response<List<ManageRetailersModel>> response) {
                for (int i=0;i<response.body().size();i++){
                    ManageRetailersModelAdapter model=new ManageRetailersModelAdapter();
                    model.setName(response.body().get(i).getName());
                    model.setPhoto_path(response.body().get(i).getPhoto_path());
                    model.setOffice_no(response.body().get(i).getOffice_no());
                    managelist.add(model);
                }
                floor3.setAdapter(adapterfloor1);
                adapterfloor1.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ManageRetailersModel>> call, Throwable t) {

            }
        });
    }
    public void getfourthfloor(String token){
        final List<ManageRetailersModelAdapter> managelist=new ArrayList<>();
        final Retailers_mange_recyclerview_adapter adapterfloor1=new Retailers_mange_recyclerview_adapter(getActivity(),managelist);
        floor4.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        floor4.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit= getUserAPIretrofit();
        ServicePrClient client= retrofit.create(ServicePrClient.class);
        Call<List<ManageRetailersModel>> call= client.get_retailers(token,"4");
        call.enqueue(new Callback<List<ManageRetailersModel>>() {
            @Override
            public void onResponse(Call<List<ManageRetailersModel>> call, Response<List<ManageRetailersModel>> response) {
                for (int i=0;i<response.body().size();i++){
                    ManageRetailersModelAdapter model=new ManageRetailersModelAdapter();
                    model.setName(response.body().get(i).getName());
                    model.setPhoto_path(response.body().get(i).getPhoto_path());
                    model.setOffice_no(response.body().get(i).getOffice_no());
                    managelist.add(model);
                }
                floor4.setAdapter(adapterfloor1);
                adapterfloor1.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ManageRetailersModel>> call, Throwable t) {

            }
        });
    }
    public void  getfifthfloor(String token){
        final List<ManageRetailersModelAdapter> managelist=new ArrayList<>();
        final Retailers_mange_recyclerview_adapter adapterfloor1=new Retailers_mange_recyclerview_adapter(getActivity(),managelist);
        floor5.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        floor5.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit= getUserAPIretrofit();
        ServicePrClient client= retrofit.create(ServicePrClient.class);
        Call<List<ManageRetailersModel>> call= client.get_retailers(token,"5");
        call.enqueue(new Callback<List<ManageRetailersModel>>() {
            @Override
            public void onResponse(Call<List<ManageRetailersModel>> call, Response<List<ManageRetailersModel>> response) {
                for (int i=0;i<response.body().size();i++){
                    ManageRetailersModelAdapter model=new ManageRetailersModelAdapter();
                    model.setName(response.body().get(i).getName());
                    model.setPhoto_path(response.body().get(i).getPhoto_path());
                    model.setOffice_no(response.body().get(i).getOffice_no());
                    managelist.add(model);
                }
                floor5.setAdapter(adapterfloor1);
                adapterfloor1.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ManageRetailersModel>> call, Throwable t) {

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


}
