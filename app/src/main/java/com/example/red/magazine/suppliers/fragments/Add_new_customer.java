package com.example.red.magazine.suppliers.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.red.magazine.R;
import com.example.red.magazine.malls.model.ResponseMessage;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.suppliers.adapters.CafeAndRestauranRecyclerviewAdapter;
import com.example.red.magazine.suppliers.adapters.HotelsRecyclerviewAdapter;
import com.example.red.magazine.suppliers.adaptersmodel.HotelsAdapterModel;
import com.example.red.magazine.suppliers.clients.SuppliersClient;
import com.example.red.magazine.suppliers.model.Customer;
import com.example.red.magazine.suppliers.model.HotelsModel;
import com.example.red.magazine.suppliers.model.Suppliers;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class Add_new_customer extends Fragment {

   private ViewFlipper flipper;
    private View first_view,add_hotels_view,add_cafeandres_view,add_others_view;

    private Button add_hotels_btn,add_cafeandres_btn,add_others_btn;

    private RecyclerView hotels_recyclerview,cafeandres_recyclerview;

    private String token;
    private SharedPreferences preferences;

    private List<HotelsAdapterModel> modelList,cafemodellist;
    private HotelsRecyclerviewAdapter hotelAdapter;
    private CafeAndRestauranRecyclerviewAdapter caferes_adapter;

    private EditText customer_name_edt,customer_phone_edt,customer_location_edt;
    private Button add_other_customr_btn;
    private TextView customer_error;

    private LinearLayout add_new_customer_layout,success_message_layout;
    private TextView referral_code_displayer,success_shower;
    public Add_new_customer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_new_customer, container, false);

        preferences=getActivity().getSharedPreferences("suppliers_data",0);
        token=preferences.getString("token","");

        flipper=(ViewFlipper)view.findViewById(R.id.sup_flipper);

        first_view=LayoutInflater.from(getActivity()).inflate(R.layout.sup_add_new_customer_layout,null);

        add_hotels_view= LayoutInflater.from(getActivity()).inflate(R.layout.sup_add_hotels_layout,null);

        modelList=new ArrayList<>();
        hotelAdapter=new HotelsRecyclerviewAdapter(getActivity(),modelList);
        hotels_recyclerview=(RecyclerView)add_hotels_view.findViewById(R.id.sup_add_hotel_recyclerview);
        hotels_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        hotels_recyclerview.setItemAnimator(new DefaultItemAnimator());


        add_cafeandres_view=LayoutInflater.from(getActivity()).inflate(R.layout.sup_add_cafeandres_layout,null);

        cafemodellist=new ArrayList<>();
        caferes_adapter=new CafeAndRestauranRecyclerviewAdapter(getActivity(),cafemodellist);
        cafeandres_recyclerview=(RecyclerView)add_cafeandres_view.findViewById(R.id.sup_add_cafeandres_recyclerview);
        cafeandres_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        cafeandres_recyclerview.setItemAnimator(new DefaultItemAnimator());

        add_others_view=LayoutInflater.from(getActivity()).inflate(R.layout.sup_others_layout,null);

        add_new_customer_layout=(LinearLayout)add_others_view.findViewById(R.id.add_new_customer_layout);
        success_message_layout=(LinearLayout)add_others_view.findViewById(R.id.customer_added_successation_layout);

        success_shower=(TextView)add_others_view.findViewById(R.id.added_message);
        referral_code_displayer=(TextView)add_others_view.findViewById(R.id.added__referral_code);



        customer_name_edt=(EditText)add_others_view.findViewById(R.id.customer_name);
        customer_phone_edt=(EditText)add_others_view.findViewById(R.id.customer_phone_no);
        customer_location_edt=(EditText)add_others_view.findViewById(R.id.customer_location);
        add_others_btn=(Button)add_others_view.findViewById(R.id.add_customer_btn);
        customer_error=(TextView)add_others_view.findViewById(R.id.others_error);
        add_others_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customer_name_value=customer_name_edt.getText().toString().trim();
                String customer_phone_value= customer_phone_edt.getText().toString();
                String customer_location_value=customer_location_edt.getText().toString();
                if (customer_name_value.equals("")){
                    customer_error.setVisibility(View.VISIBLE);
                    customer_error.setText("Please add your customer name");

                }else if (customer_phone_value.equals("")){
                    customer_error.setVisibility(View.VISIBLE);
                    customer_error.setText("Please add your customer phone");
                }else if (customer_location_value.equals("")){
                    customer_error.setVisibility(View.VISIBLE);
                    customer_error.setText("Please add your customer location like megenagna,mexico");
                }else {
                    Retrofit retrofit= getUserAPIretrofit();
                    SuppliersClient client= retrofit.create(SuppliersClient.class);
                    Customer customer=new Customer(customer_name_value,customer_phone_value,customer_location_value);
                    Call<ResponseMessage> call= client.add_others_customer(token,customer);
                    call.enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                          if (response.body().getToken().equals("ok")){
                              add_new_customer_layout.setVisibility(View.GONE);
                              success_message_layout.setVisibility(View.VISIBLE);
                              success_shower.setText("Well done your customer is added successfully");
                              referral_code_displayer.setText("Your customer code is: "+response.body().getStatus());
                          }
                        }

                        @Override
                        public void onFailure(Call<ResponseMessage> call, Throwable t) {

                        }
                    });
                }
            }
        });

        flipper.addView(first_view);
        flipper.showNext();

        add_hotels_btn=(Button)first_view.findViewById(R.id.sup_hotels);
        add_cafeandres_btn=(Button)first_view.findViewById(R.id.sup_cafe_and_res);
        add_others_btn=(Button)first_view.findViewById(R.id.sup_others);

        add_hotels_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipper.removeView(add_hotels_view);
                flipper.addView(add_hotels_view);
                flipper.showNext();
                Retrofit retrofit= getUserAPIretrofit();
                SuppliersClient client=retrofit.create(SuppliersClient.class);
                Call<List<HotelsModel>> call= client.get_hotels_list(token);
                call.enqueue(new Callback<List<HotelsModel>>() {
                    @Override
                    public void onResponse(Call<List<HotelsModel>> call, Response<List<HotelsModel>> response) {

                        for (int i=0;i<response.body().size();i++){
                            HotelsAdapterModel model=new HotelsAdapterModel();
                            model.setName(response.body().get(i).getName());
                            model.setPhoto_path(response.body().get(i).getPhoto_path());
                            model.setId(response.body().get(i).getId());
                            modelList.add(model);
                        }
                        hotels_recyclerview.setAdapter(hotelAdapter);
                        hotelAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<List<HotelsModel>> call, Throwable t) {

                    }
                });

            }
        });

        add_cafeandres_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipper.removeView(add_cafeandres_view);
                flipper.addView(add_cafeandres_view);
                flipper.showNext();

                Retrofit retrofit=getUserAPIretrofit();
                SuppliersClient client=retrofit.create(SuppliersClient.class);
                Call<List<HotelsModel>> call=client.get_restaurants(token);
                call.enqueue(new Callback<List<HotelsModel>>() {
                    @Override
                    public void onResponse(Call<List<HotelsModel>> call, Response<List<HotelsModel>> response) {
                        for (int i=0;i<response.body().size();i++){
                            HotelsAdapterModel model=new HotelsAdapterModel();
                            model.setName(response.body().get(i).getName());
                            model.setPhoto_path(response.body().get(i).getPhoto_path());
                            model.setId(response.body().get(i).getId());
                            cafemodellist.add(model);
                        }
                        cafeandres_recyclerview.setAdapter(caferes_adapter);
                        caferes_adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<List<HotelsModel>> call, Throwable t) {

                    }
                });
            }
        });

        add_others_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipper.removeView(add_others_view);
                flipper.addView(add_others_view);
                flipper.showNext();
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
