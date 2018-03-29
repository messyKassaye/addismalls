package com.example.red.magazine.serviceproviders.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.red.magazine.R;
import com.example.red.magazine.adapters.TopMallAdapter;
import com.example.red.magazine.model.ResponseMessage;
import com.example.red.magazine.serviceproviders.AdapterModel.DailyDemandAdapterModel;
import com.example.red.magazine.serviceproviders.adapters.DailyDemandRecyclerviewAdapter;
import com.example.red.magazine.serviceproviders.clients.ServicerProvidersUsersClient;
import com.example.red.magazine.serviceproviders.model.DailyDemand;
import com.example.red.magazine.serviceproviders.model.Id;
import com.example.red.magazine.serviceproviders.model.PostResDailyDemand;
import com.example.red.magazine.serviceproviders.model.ResItem;
import com.example.red.magazine.serviceproviders.model.RestaurantItmes;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Picasso;

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
public class Restaurants_Manage_Daily_Demand extends Fragment {
  private ViewFlipper flipper;
    private View first_view,add_demand_view,manage_demand_view,res_damand_confirmation;
    private TextView add_daily_demand,manage_daily_demand;
    private ArrayList<String> spinner_data,id_data;
    private ArrayAdapter<String> adapter;
    private Spinner spinner;
    private SharedPreferences preferences;
    private String token="";
    private LinearLayout demand_detail_layout;
    private TextView name,category_name,type_name,pacakge_name,supplier_name,description;
    private TextView confirmation1,confirmation2;
    private ImageView daily_demand_pic;
    private EditText quantity;
    private Button send;
    private String confirmation_category_name;
    private ProgressBar pr;
    private TimerTask timerTask;
    private Handler handler=new Handler();
    private Timer timers;

    private RecyclerView manage_recyclerview;
    private List<DailyDemandAdapterModel> demandlist;
    private DailyDemandRecyclerviewAdapter demand_adapter;


    public Restaurants_Manage_Daily_Demand() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        timerTask=new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        };
        timers =new Timer();


        View view= inflater.inflate(R.layout.fragment_restaurants__manage__daily__demand, container, false);
        preferences=getActivity().getSharedPreferences("service_provider_data",0);
        token=preferences.getString("token","");

        flipper=(ViewFlipper)view.findViewById(R.id.demand_flipper);
        first_view=LayoutInflater.from(getActivity()).inflate(R.layout.daily_demand_first,null);

        add_demand_view=LayoutInflater.from(getActivity()).inflate(R.layout.add_res_demand,null);
        res_damand_confirmation=LayoutInflater.from(getActivity()).inflate(R.layout.res_demand_confirmation,null);
        confirmation1=(TextView)res_damand_confirmation.findViewById(R.id.confirmation1);
        confirmation2=(TextView)res_damand_confirmation.findViewById(R.id.confirmation2);

        demand_detail_layout=(LinearLayout)add_demand_view.findViewById(R.id.demand_detail_layout);
        pr=(ProgressBar)add_demand_view.findViewById(R.id.res_pr);
        name=(TextView)add_demand_view.findViewById(R.id.name);
        category_name=(TextView)add_demand_view.findViewById(R.id.category_name);
        type_name=(TextView)add_demand_view.findViewById(R.id.type_name);
        pacakge_name=(TextView)add_demand_view.findViewById(R.id.package_name);
        supplier_name=(TextView)add_demand_view.findViewById(R.id.supplier_name);
        description=(TextView)add_demand_view.findViewById(R.id.supplier_name);
        daily_demand_pic=(ImageView)add_demand_view.findViewById(R.id.daily_demand_pic);
        quantity=(EditText)add_demand_view.findViewById(R.id.demand_quantity);
        send=(Button)add_demand_view.findViewById(R.id.send_res_demand_btn);

        spinner=(Spinner)add_demand_view.findViewById(R.id.item_spinner);

        manage_demand_view=LayoutInflater.from(getActivity()).inflate(R.layout.manage_res_demand,null);
        manage_recyclerview=(RecyclerView)manage_demand_view.findViewById(R.id.manage_res_demand_recycler_view);

        demandlist=new ArrayList<>();
        demand_adapter=new DailyDemandRecyclerviewAdapter(getActivity(),demandlist);

        manage_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        manage_recyclerview.setItemAnimator(new DefaultItemAnimator());

        flipper.addView(first_view);

        add_daily_demand=(TextView)view.findViewById(R.id.add_daily_demand);
        manage_daily_demand=(TextView)view.findViewById(R.id.manage_daily_demand);
        add_daily_demand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipper.addView(add_demand_view);
                spinner_data=new ArrayList<String>();
                id_data=new ArrayList<String>();

                 Retrofit retrofit=getUserAPIretrofit();
                ServicerProvidersUsersClient client= retrofit.create(ServicerProvidersUsersClient.class);
                Call<List<ResItem>> call=client.getResItem(token);
                call.enqueue(new Callback<List<ResItem>>() {
                    @Override
                    public void onResponse(Call<List<ResItem>> call, Response<List<ResItem>> response) {
                        spinner_data.add("Select ordered items here");
                        for (int i=0;i<response.body().size();i++){
                            spinner_data.add(response.body().get(i).getName());
                            id_data.add(response.body().get(i).getId());
                        }
                        adapter= new ArrayAdapter<String>(getActivity(),
                                R.layout.spinner_item, spinner_data);
                        spinner.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<ResItem>> call, Throwable t) {

                    }
                });
                flipper.showNext();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              if (!spinner.getSelectedItem().toString().equals("Select ordered items here")){
                  Id id=new Id(id_data.get(spinner.getSelectedItemPosition()-1));
                  Retrofit retrofit=getUserAPIretrofit();
                  ServicerProvidersUsersClient client=retrofit.create(ServicerProvidersUsersClient.class);
                  Call<List<RestaurantItmes>> call= client.get_items_detail(token,id);
                  call.enqueue(new Callback<List<RestaurantItmes>>() {
                      @Override
                      public void onResponse(Call<List<RestaurantItmes>> call, Response<List<RestaurantItmes>> response) {
                        if (response.body().size()>0){
                            confirmation_category_name= response.body().get(0).getCategory_name();
                            if (demand_detail_layout.getVisibility()==View.VISIBLE){
                                Picasso.with(getActivity()).load(ProjectStatic.IMAPGE_PATH+response.body().get(0).getPhoto_path()).into(daily_demand_pic);
                                SpannableStringBuilder builder = new SpannableStringBuilder();
                                SpannableString str1= new SpannableString("Type : ");
                                str1.setSpan(new ForegroundColorSpan(Color.parseColor("#589cb4")), 0, str1.length(), 0);
                                builder.append(str1);
                                SpannableString str2= new SpannableString(response.body().get(0).getName());
                                builder.append(str2);
                                name.setText(builder);
                                daily_demand_pic.setTag(response.body().get(0).getId());

                                SpannableStringBuilder builder2 = new SpannableStringBuilder();
                                SpannableString str12= new SpannableString("Category name: ");
                                str12.setSpan(new ForegroundColorSpan(Color.parseColor("#589cb4")), 0, str12.length(), 0);
                                builder2.append(str12);
                                SpannableString str22= new SpannableString(response.body().get(0).getCategory_name());
                                builder2.append(str22);
                                category_name.setText(builder2);

                                SpannableStringBuilder builder3 = new SpannableStringBuilder();
                                SpannableString str13= new SpannableString("Type name: ");
                                str13.setSpan(new ForegroundColorSpan(Color.parseColor("#589cb4")), 0, str13.length(), 0);
                                builder3.append(str13);
                                SpannableString str23= new SpannableString(response.body().get(0).getType_name());
                                builder3.append(str23);
                                type_name.setText(builder3);

                                SpannableStringBuilder builder4 = new SpannableStringBuilder();
                                SpannableString str14= new SpannableString("Package name: ");
                                str14.setSpan(new ForegroundColorSpan(Color.parseColor("#589cb4")), 0, str14.length(), 0);
                                builder4.append(str14);
                                SpannableString str24= new SpannableString(response.body().get(0).getPackage_name());
                                builder4.append(str24);
                                pacakge_name.setText(builder4);

                                SpannableStringBuilder builder5 = new SpannableStringBuilder();
                                SpannableString str15= new SpannableString("Supplied by: ");
                                str15.setSpan(new ForegroundColorSpan(Color.parseColor("#589cb4")), 0, str15.length(), 0);
                                builder5.append(str15);
                                SpannableString str25= new SpannableString(response.body().get(0).getSupplier_name());
                                builder5.append(str25);
                                supplier_name.setText(builder5);
                                description.setText(response.body().get(0).getDescription());
                            }else {
                                demand_detail_layout.setVisibility(View.VISIBLE);
                                Picasso.with(getActivity()).load(ProjectStatic.IMAPGE_PATH+response.body().get(0).getPhoto_path()).into(daily_demand_pic);
                                SpannableStringBuilder builder = new SpannableStringBuilder();
                                SpannableString str1= new SpannableString("Type : ");
                                str1.setSpan(new ForegroundColorSpan(Color.parseColor("#589cb4")), 0, str1.length(), 0);
                                builder.append(str1);
                                SpannableString str2= new SpannableString(response.body().get(0).getName());
                                builder.append(str2);
                                name.setText(builder);
                                daily_demand_pic.setTag(response.body().get(0).getId());

                                SpannableStringBuilder builder2 = new SpannableStringBuilder();
                                SpannableString str12= new SpannableString("Category name: ");
                                str12.setSpan(new ForegroundColorSpan(Color.parseColor("#589cb4")), 0, str12.length(), 0);
                                builder2.append(str12);
                                SpannableString str22= new SpannableString(response.body().get(0).getCategory_name());
                                builder2.append(str22);
                                category_name.setText(builder2);

                                SpannableStringBuilder builder3 = new SpannableStringBuilder();
                                SpannableString str13= new SpannableString("Type name: ");
                                str13.setSpan(new ForegroundColorSpan(Color.parseColor("#589cb4")), 0, str13.length(), 0);
                                builder3.append(str13);
                                SpannableString str23= new SpannableString(response.body().get(0).getType_name());
                                builder3.append(str23);
                                type_name.setText(builder3);

                                SpannableStringBuilder builder4 = new SpannableStringBuilder();
                                SpannableString str14= new SpannableString("Package name: ");
                                str14.setSpan(new ForegroundColorSpan(Color.parseColor("#589cb4")), 0, str14.length(), 0);
                                builder4.append(str14);
                                SpannableString str24= new SpannableString(response.body().get(0).getPackage_name());
                                builder4.append(str24);
                                pacakge_name.setText(builder4);

                                SpannableStringBuilder builder5 = new SpannableStringBuilder();
                                SpannableString str15= new SpannableString("Supplied by: ");
                                str15.setSpan(new ForegroundColorSpan(Color.parseColor("#589cb4")), 0, str15.length(), 0);
                                builder5.append(str15);
                                SpannableString str25= new SpannableString(response.body().get(0).getSupplier_name());
                                builder5.append(str25);
                                supplier_name.setText(builder5);
                                description.setText(response.body().get(0).getDescription());
                            }
                        }
                      }

                      @Override
                      public void onFailure(Call<List<RestaurantItmes>> call, Throwable t) {

                      }
                  });
              }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantity_value= quantity.getText().toString();
                if (quantity_value.equals("")){
                    Toast.makeText(getActivity(),"Please ente how much you need",Toast.LENGTH_LONG).show();
                }else {
                    pr.setVisibility(View.VISIBLE);
                    PostResDailyDemand demand=new PostResDailyDemand(daily_demand_pic.getTag().toString(),quantity_value);
                    Retrofit retrofit= getUserAPIretrofit();
                    ServicerProvidersUsersClient client=retrofit.create(ServicerProvidersUsersClient.class);
                    Call<ResponseMessage> call=client.send_demand(token,demand);
                    call.enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                          if (response.body().getStatus().equals("ok")){
                              pr.setVisibility(View.GONE);
                              confirmation1.setText("Your "+confirmation_category_name+" demand request is send successfully");
                              confirmation2.setText("They will bring it to you as soon as possible.\nThank you ):");
                              flipper.addView(res_damand_confirmation);
                              flipper.showNext();
                              timers.schedule(timerTask,3000);
                          }

                        }

                        @Override
                        public void onFailure(Call<ResponseMessage> call, Throwable t) {

                        }
                    });
                }
            }
        });
        manage_daily_demand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Demand request list");
                flipper.removeView(manage_demand_view);
                flipper.addView(manage_demand_view);
                flipper.showNext();
                Retrofit retrofit=getUserAPIretrofit();
                ServicerProvidersUsersClient client=retrofit.create(ServicerProvidersUsersClient.class);
                Call<List<DailyDemand>> call =client.demand_manage(token);
                call.enqueue(new Callback<List<DailyDemand>>() {
                    @Override
                    public void onResponse(Call<List<DailyDemand>> call, Response<List<DailyDemand>> response) {
                       for (int i=0;i<response.body().size();i++){
                           DailyDemandAdapterModel model=new DailyDemandAdapterModel();
                           model.setId(response.body().get(i).getId());
                           model.setName(response.body().get(i).getName());
                           model.setQuantity(response.body().get(i).getQuantity());
                           model.setCreated_at(response.body().get(i).getCreated_at());
                           model.setPhoto_path(response.body().get(i).getPhoto_path());
                           demandlist.add(model);
                       }
                       manage_recyclerview.setAdapter(demand_adapter);
                        demand_adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<DailyDemand>> call, Throwable t) {

                    }
                });
            }
        });

        Animation in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
        Animation out = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_left);
        // set the animation type's to ViewFlipperActivity
        flipper.setInAnimation(in);
        flipper.setOutAnimation(out);
        // set interval time for flipping between views
        flipper.setFlipInterval(3000);
        flipper.setDisplayedChild(4);

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
