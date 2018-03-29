package com.example.red.magazine.serviceproviders.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.red.magazine.R;
import com.example.red.magazine.model.ResponseMessage;
import com.example.red.magazine.serviceproviders.AdapterModel.DemandAdapterModel;
import com.example.red.magazine.serviceproviders.adapters.DemandDashboardrecyclverviewAdapter;
import com.example.red.magazine.serviceproviders.clients.ServicerProvidersUsersClient;
import com.example.red.magazine.serviceproviders.model.Demand;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.suppliers.model.DemandRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class DemandFragment extends Fragment {

    private EditText description,quantity,price;
    private TextView upload,selected_file;
    private Button upload_btn;
    private static final  int MY_PERMISSION_REQUEST=100;
    private static final  int PICK_IMAGE_FROM_GALLERY_REQUEST=1;
    private Uri uri=null;
    private String token="";
    private SharedPreferences preferences;
    private ProgressBar pr;

    private Spinner spinner;
    private ArrayList<String> spinner_data;
    private ArrayAdapter<String> adapter;
    private View first_view,post_new_demand_layout,manage_daily_demand_view;
    private Button post_new_demand_btn,manage_demand_btn;
    private ViewFlipper flipper;

    private List<DemandAdapterModel> demandList;
    private DemandDashboardrecyclverviewAdapter recyclerviewadapter;
    private RecyclerView recyclerView;
    public DemandFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.READ_CONTACTS},MY_PERMISSION_REQUEST);
        }
        View view= inflater.inflate(R.layout.fragment_demand, container, false);
        preferences=getActivity().getSharedPreferences("service_provider_data",0);
        token=preferences.getString("token","");

        flipper=(ViewFlipper)view.findViewById(R.id.demand_flipper);
        first_view=LayoutInflater.from(getActivity()).inflate(R.layout.demand_first_view,null);
        post_new_demand_layout=LayoutInflater.from(getActivity()).inflate(R.layout.deman_post_new_demand_layout,null);
        manage_daily_demand_view=LayoutInflater.from(getActivity()).inflate(R.layout.demand_manage_layout,null);

        demandList=new ArrayList<>();
        recyclerviewadapter=new DemandDashboardrecyclverviewAdapter(getActivity(),demandList);
        recyclerView =(RecyclerView)manage_daily_demand_view.findViewById(R.id.manage_demand_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        flipper.addView(first_view);
        flipper.showNext();

        post_new_demand_btn=(Button)first_view.findViewById(R.id.post_new_demand_btn);
        manage_demand_btn=(Button)first_view.findViewById(R.id.manage_demand_btn);

        post_new_demand_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipper.removeView(post_new_demand_layout);
                flipper.addView(post_new_demand_layout);
                flipper.showNext();
            }
        });

        manage_demand_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit=getUserAPIretrofit();
                ServicerProvidersUsersClient client=retrofit.create(ServicerProvidersUsersClient.class);
                Call<List<Demand>> call= client.get_demand_and_supply(token);
                call.enqueue(new Callback<List<Demand>>() {
                    @Override
                    public void onResponse(Call<List<Demand>> call, Response<List<Demand>> response) {
                        for (int i=0;i<response.body().size();i++){
                            DemandAdapterModel model=new DemandAdapterModel();
                            model.setPhoto_path(response.body().get(i).getPhoto_path());
                            model.setName(response.body().get(i).getName());
                            model.setProduct_photo(response.body().get(i).getProduct_photo());
                            model.setAvailability(response.body().get(i).getAvailability());
                            model.setPrice(response.body().get(i).getPrice());
                            model.setDescription(response.body().get(i).getDescription());
                            demandList.add(model);
                        }
                        recyclerView.setAdapter(recyclerviewadapter);
                        recyclerviewadapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<List<Demand>> call, Throwable t) {

                    }
                });



                flipper.removeView(manage_daily_demand_view);
                flipper.addView(manage_daily_demand_view);
                flipper.showNext();
            }
        });


        description=(EditText)post_new_demand_layout.findViewById(R.id.describe_your_demand);
        quantity=(EditText)post_new_demand_layout.findViewById(R.id.input_quantity);
        price=(EditText)post_new_demand_layout.findViewById(R.id.input_price);
        upload=(TextView) post_new_demand_layout.findViewById(R.id.upload_demand_photo);
        selected_file=(TextView)post_new_demand_layout.findViewById(R.id.selected_product_photo);
        pr=(ProgressBar)post_new_demand_layout.findViewById(R.id.demand_pr);

        spinner=(Spinner)post_new_demand_layout.findViewById(R.id.delivery_time);
        spinner_data=new ArrayList<>();
        spinner_data.add("Select your delivering time");
        spinner_data.add("1 day");
        spinner_data.add("2 day");
        spinner_data.add("4 day");
        spinner_data.add("5 day");
        spinner_data.add("2 week");
        spinner_data.add("3 week");
        spinner_data.add("1 month");
        spinner_data.add("2 month");
        spinner_data.add("3 month");
        spinner_data.add("10 month");
        spinner_data.add("1 year");
        adapter= new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, spinner_data);
        spinner.setAdapter(adapter);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select picture"),PICK_IMAGE_FROM_GALLERY_REQUEST);
            }
        });

        upload_btn=(Button)post_new_demand_layout.findViewById(R.id.post_demand_btn);
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description_value=description.getText().toString();
                String quantity_value=quantity.getText().toString();
                String price_value=price.getText().toString();
                if (description_value.equals("")){
                    Toast.makeText(getActivity(),"Please enter your description",Toast.LENGTH_LONG).show();
                }else if (quantity_value.equals("")){
                    Toast.makeText(getActivity(),"Please enter your Quantity",Toast.LENGTH_LONG).show();
                }else if (price_value.equals("")){
                    Toast.makeText(getActivity(),"Please enter your price",Toast.LENGTH_LONG).show();
                }else if (spinner.getSelectedItem().equals("Select your delivering time")){
                    Toast.makeText(getActivity(),"Please Select your deliver time",Toast.LENGTH_LONG).show();
                }else {
                    pr.setVisibility(View.VISIBLE);
                    upload_demand(token,description_value,quantity_value,price_value,uri,spinner.getSelectedItem().toString());
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_FROM_GALLERY_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            uri=data.getData();
            File file=new File(uri.getPath());
            selected_file.setText(file.getName());
            //upload_and_createAccount(uri);
        }
    }
    public void upload_demand(String token, final String description, String quantity, String price, Uri uri,String availability){
        RequestBody descriptions = RequestBody.create(MultipartBody.FORM,description);
        RequestBody quantitys = RequestBody.create(MultipartBody.FORM,quantity);
        RequestBody prices = RequestBody.create(MultipartBody.FORM,price);
        RequestBody avail = RequestBody.create(MultipartBody.FORM,availability);
        File file = new File(uri.getPath());
        RequestBody photopart= RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo_path",file.getName(),photopart);
        Retrofit retrofit=getUserAPIretrofit();
        ServicerProvidersUsersClient client=retrofit.create(ServicerProvidersUsersClient.class);
        Call<ResponseMessage> call=client.upload_demand(token,descriptions,quantitys,prices,avail,photo);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.body().getStatus().equals("ok")){
                    pr.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),"your demand is Submitted successfully):",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {

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
