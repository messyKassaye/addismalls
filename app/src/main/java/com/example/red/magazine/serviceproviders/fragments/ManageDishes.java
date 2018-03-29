package com.example.red.magazine.serviceproviders.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.red.magazine.R;
import com.example.red.magazine.model.ResponseMessage;
import com.example.red.magazine.serviceproviders.AdapterModel.PostDisheAdapterModel;
import com.example.red.magazine.serviceproviders.adapters.DailyDemandRecyclerviewAdapter;
import com.example.red.magazine.serviceproviders.adapters.DisheRecyclerviewAdapter;
import com.example.red.magazine.serviceproviders.clients.ServicerProvidersUsersClient;
import com.example.red.magazine.serviceproviders.model.Dishe;
import com.example.red.magazine.serviceproviders.model.PostDishe;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
public class ManageDishes extends Fragment {
    private ViewFlipper flipper;
    private View first_view,post_new_dishe_view,success_view,manage_dish_view;
    private TextView post_new_dishe,manage_dishe;
    private LinearLayout dish_selection_layout,dish_image_layout;
    private TextView upload_dishe;
    private static final  int MY_PERMISSION_REQUEST=100;
    private static final  int PICK_IMAGE_FROM_GALLERY_REQUEST=1;
    private Uri uri;
    private ImageView dish_image;
    private Button post_dish;
    private EditText dishName,dishprice,dishdescription;
    private TextView backtomain,upload_new_image;
    private SharedPreferences preferences;
    private String token;
    private TimerTask timerTask;
    private Timer timers;
    private ProgressBar pr;
    private TextView confirmation1,confirmation2,confirmation_price,confimation_describ;
    private RecyclerView recyclerView;
    private List<PostDisheAdapterModel> dishlist;
    private DisheRecyclerviewAdapter recyclerviewAdapter;
    public ManageDishes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.res_dishe_layout, container, false);

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

        preferences=getActivity().getSharedPreferences("service_provider_data",0);
        token=preferences.getString("token","");

        flipper=(ViewFlipper)view.findViewById(R.id.dishe_flipper);

        first_view=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_manage_dishes,null);
        flipper.addView(first_view);

        success_view=LayoutInflater.from(getActivity()).inflate(R.layout.manage_dish_success_layout,null);
        confirmation1=(TextView)success_view.findViewById(R.id.dishe_success);
        confirmation2=(TextView)success_view.findViewById(R.id.dish_name_success);
        confirmation_price=(TextView)success_view.findViewById(R.id.dish_price_success);
        confimation_describ=(TextView)success_view.findViewById(R.id.dish_description_success);

        manage_dish_view=LayoutInflater.from(getActivity()).inflate(R.layout.manage_res_dish_layout,null);

        dishlist=new ArrayList<>();
        recyclerviewAdapter=new DisheRecyclerviewAdapter(getActivity(),dishlist);
        recyclerView=(RecyclerView)manage_dish_view.findViewById(R.id.dish_manage_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        post_new_dishe_view=LayoutInflater.from(getActivity()).inflate(R.layout.post_new_dishe_layout,null);
        upload_dishe=(TextView)post_new_dishe_view.findViewById(R.id.select_dishe_image);
        dish_image=(ImageView)post_new_dishe_view.findViewById(R.id.dishe_image);
        dish_image_layout=(LinearLayout)post_new_dishe_view.findViewById(R.id.dish_image_layout);
        dish_selection_layout=(LinearLayout)post_new_dishe_view.findViewById(R.id.dish_selection_layout);
        post_dish=(Button)post_new_dishe_view.findViewById(R.id.post_dish);
        backtomain=(TextView)post_new_dishe_view.findViewById(R.id.backtomain);
        upload_new_image=(TextView)post_new_dishe_view.findViewById(R.id.upload_new_dish);
        post_dish=(Button)post_new_dishe_view.findViewById(R.id.post_dish);
        pr=(ProgressBar)post_new_dishe_view.findViewById(R.id.dish_pr);
        dishName=(EditText)post_new_dishe_view.findViewById(R.id.dish_name);
        dishprice=(EditText)post_new_dishe_view.findViewById(R.id.dish_pric);
        dishdescription=(EditText)post_new_dishe_view.findViewById(R.id.describe_your_dish);

        upload_new_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dishdescription.setText("");
                dishName.setText("");
                dishprice.setText("");
                Intent intent=new Intent();
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select picture"),PICK_IMAGE_FROM_GALLERY_REQUEST);

            }
        });

        upload_dishe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select picture"),PICK_IMAGE_FROM_GALLERY_REQUEST);

            }
        });



        post_new_dishe=(TextView)first_view.findViewById(R.id.add_dishes);
        manage_dishe=(TextView)first_view.findViewById(R.id.manage_dishes);
        manage_dishe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit retrofit=getUserAPIretrofit();
                ServicerProvidersUsersClient client=retrofit.create(ServicerProvidersUsersClient.class);
                Call<List<PostDishe>> call=client.get_my_dish_list(token);
                call.enqueue(new Callback<List<PostDishe>>() {
                    @Override
                    public void onResponse(Call<List<PostDishe>> call, Response<List<PostDishe>> response) {
                      for (int i=0;i<response.body().size();i++){
                          PostDisheAdapterModel model=new PostDisheAdapterModel();
                          model.setName(response.body().get(i).getName());
                          model.setId(response.body().get(i).getId());
                          model.setPrice(response.body().get(i).getPrice());
                          model.setPhoto_path(response.body().get(i).getPhoto_path());
                          model.setCreated_at(response.body().get(i).getCreated_at());
                          dishlist.add(model);
                      }

                        recyclerView.setAdapter(recyclerviewAdapter);
                        recyclerviewAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<List<PostDishe>> call, Throwable t) {
                        Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My dishes list");
                flipper.removeView(manage_dish_view);
                flipper.addView(manage_dish_view);
                flipper.showNext();
            }
        });
        post_new_dishe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipper.removeView(post_new_dishe_view);
                flipper.addView(post_new_dishe_view);
                flipper.showNext();
            }
        });

        post_dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dishname_value=dishName.getText().toString();
                String dishprice_value=dishprice.getText().toString();
                String dishdescription_value=dishdescription.getText().toString();
                if (dishname_value.equals("")){
                    Toast.makeText(getActivity(),"Please enter your dish name",Toast.LENGTH_LONG).show();
                }else if (dishprice_value.equals("")){
                    Toast.makeText(getActivity(),"Please enter your dish price",Toast.LENGTH_LONG).show();
                }else {
                    pr.setVisibility(View.VISIBLE);
                    upload_post(dishname_value,dishprice_value,dishdescription_value,uri);
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
        flipper.setDisplayedChild(4);
                return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_FROM_GALLERY_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            uri=data.getData();
            File file=new File(uri.getPath());
            dish_selection_layout.setVisibility(View.GONE);

            dish_image_layout.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(uri).placeholder(R.drawable.header).into(dish_image);
            post_dish.setVisibility(View.VISIBLE);
            //upload_and_createAccount(uri);
        }
    }
    public void upload_post(final String name, final String price, final String describe, Uri uri){
        final RequestBody dishnames = RequestBody.create(MultipartBody.FORM,name);
        RequestBody dishprices = RequestBody.create(MultipartBody.FORM,price);
        final RequestBody dishdescrs = RequestBody.create(MultipartBody.FORM,describe);

        File file = new File(uri.getPath());
        RequestBody photopart= RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo_path",file.getName(),photopart);



        Retrofit retrofit=getUserAPIretrofit();
        ServicerProvidersUsersClient client=retrofit.create(ServicerProvidersUsersClient.class);
       Call<ResponseMessage> call=client.post_dish(token,dishnames,dishprices,dishdescrs,photo);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
               if (response.body().getStatus().equals("ok")){
                   pr.setVisibility(View.GONE);
                   flipper.removeView(success_view);
                   flipper.addView(success_view);
                   confirmation1.setText("Your dishe is posted successfully");
                   confirmation2.setText("Dish name: "+name);
                   confirmation_price.setText("Price: "+price);
                   confimation_describ.setText("Decription: "+describe);

                   flipper.showNext();
                   timers.schedule(timerTask,5000);
               }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();

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
