package com.example.red.magazine.serviceproviders.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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
import com.example.red.magazine.serviceproviders.AdapterModel.PostSaleAdapterModel;
import com.example.red.magazine.serviceproviders.adapters.PostSaleRecyclerviewAdapter;
import com.example.red.magazine.serviceproviders.clients.ServicerProvidersUsersClient;
import com.example.red.magazine.serviceproviders.model.PostSale;
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
public class ManagePost extends Fragment {

    private ViewFlipper flipper;
    private View first_view,second_view,post_success,manage_post_view;
    private Button post_new_item_btn,manage_post_item_btn;
    private EditText price,caption;
    private Button upload_photo,send_btn;
    private ProgressBar pr;
    private static final  int PICK_IMAGE_FROM_GALLERY_REQUEST=1;
    private Uri uri;
    private TextView success_message,success_message2;
    LinearLayout post_new_item_layout;
    private ImageView product_photo;
    private SharedPreferences preferences;
    private String token;
    private TimerTask timerTask;
    private Timer timers;
    private List<PostSaleAdapterModel> saleList;
    private PostSaleRecyclerviewAdapter adapter;
    private RecyclerView recyclerView;
    public ManagePost() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_manage_post, container, false);

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
        flipper=(ViewFlipper)view.findViewById(R.id.post_sale_flipper);

        first_view=LayoutInflater.from(getActivity()).inflate(R.layout.post_sale_firstview,null);
        second_view=LayoutInflater.from(getActivity()).inflate(R.layout.post_sale_second_view,null);
        post_success=LayoutInflater.from(getActivity()).inflate(R.layout.post_sale_success_layout,null);
        manage_post_view=LayoutInflater.from(getActivity()).inflate(R.layout.manage_post_sale_layout,null);
       saleList=new ArrayList<>();
        adapter=new PostSaleRecyclerviewAdapter(getActivity(),saleList);
        recyclerView=(RecyclerView)manage_post_view.findViewById(R.id.manage_post_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        flipper.addView(first_view);
        flipper.showNext();

        post_new_item_btn=(Button)first_view.findViewById(R.id.post_new_item_for_sale);
        manage_post_item_btn=(Button)first_view.findViewById(R.id.manage_post_item_for_sale);

        post_new_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipper.removeView(second_view);
                flipper.addView(second_view);
                flipper.showNext();
            }
        });

        manage_post_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipper.removeView(manage_post_view);
                flipper.addView(manage_post_view);
                flipper.showNext();

                Retrofit retrofit=getUserAPIretrofit();
                ServicerProvidersUsersClient client=retrofit.create(ServicerProvidersUsersClient.class);
                Call<List<PostSale>> call=client.get_posted_itemd(token);
                call.enqueue(new Callback<List<PostSale>>() {
                    @Override
                    public void onResponse(Call<List<PostSale>> call, Response<List<PostSale>> response) {
                      for (int i=0;i<response.body().size();i++){
                          PostSaleAdapterModel model=new PostSaleAdapterModel();
                          model.setCaption(response.body().get(i).getCaption());
                          model.setPrice(response.body().get(i).getPrice());
                          model.setProduct_photo(response.body().get(i).getProduct_photo());
                          saleList.add(model);
                      }
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<PostSale>> call, Throwable t) {

                    }
                });
            }
        });

        upload_photo=(Button)second_view.findViewById(R.id.upload_product_for_sale);
        product_photo=(ImageView) second_view.findViewById(R.id.post_sale_product_photo);
        post_new_item_layout=(LinearLayout)second_view.findViewById(R.id.post_new_item_layout);
        send_btn=(Button)second_view.findViewById(R.id.post_my_product_for_sale);
        price=(EditText)second_view.findViewById(R.id.post_sale_price);
        caption=(EditText)second_view.findViewById(R.id.post_sale_caption);
        pr=(ProgressBar)second_view.findViewById(R.id.my_pr);

        success_message=(TextView)post_success.findViewById(R.id.post_success);
        success_message.setText("Your product is posted for all over the world!");
        success_message2=(TextView)post_success.findViewById(R.id.success_message_sale);
        success_message2.setText("You did good ):");
        upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select picture"),PICK_IMAGE_FROM_GALLERY_REQUEST);

            }
        });
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String price_value=price.getText().toString();
                String caption_value=caption.getText().toString();
                if (price_value.equals("")){
                    Toast.makeText(getActivity(),"Please provide your product price",Toast.LENGTH_LONG).show();
                }else {
                    pr.setVisibility(View.VISIBLE);
                    Retrofit retrofit = getUserAPIretrofit();
                    ServicerProvidersUsersClient client = retrofit.create(ServicerProvidersUsersClient.class);
                    RequestBody price_re = RequestBody.create(MultipartBody.FORM, price_value);
                    RequestBody caption_re = RequestBody.create(MultipartBody.FORM, caption_value);
                    File file = new File(uri.getPath());
                    RequestBody photopart = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part photo = MultipartBody.Part.createFormData("photo_path", file.getName(), photopart);

                    Call<ResponseMessage> call = client.post_new_item(token, price_re, caption_re, photo);
                    call.enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                            if (response.body().getStatus().equals("ok")){
                              pr.setVisibility(View.GONE);
                              flipper.addView(post_success);
                              flipper.showNext();
                              timers.schedule(timerTask,4000);
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
     return  view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode==PICK_IMAGE_FROM_GALLERY_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
                uri=data.getData();
                File file=new File(uri.getPath());
                 post_new_item_layout.setVisibility(View.GONE);
                 product_photo.setVisibility(View.VISIBLE);
                 Picasso.with(getActivity()).load(uri).into(product_photo);
                //upload_and_createAccount(uri);

        }
    }
    public Retrofit getUserAPIretrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ProjectStatic.URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit;
    }

}
