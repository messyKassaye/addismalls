package com.example.red.magazine.serviceproviders.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.red.magazine.R;
import com.example.red.magazine.helpers.CustomLayout;
import com.example.red.magazine.model.ResponseMessage;
import com.example.red.magazine.serviceproviders.adapters.MenuPagerAdapter;
import com.example.red.magazine.serviceproviders.clients.ServicerProvidersUsersClient;
import com.example.red.magazine.serviceproviders.model.Menu;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostMenu extends Fragment {
    private View first_view,add_menu_layout,menu_add_success,manage_menu_layout;
    private ViewFlipper flipper;
    private Button add_menu,manage_menu,upload_meu_image,send_menu_image;
    private RelativeLayout menu_image;
    private ImageView back_Image;
    private LinearLayout buttons_layout;
    private LinearLayout main_layout;
    private ProgressBar menu_pr;
    private static final  int PICK_IMAGE_FROM_GALLERY_REQUEST=1;
    private Uri uri;
    private SharedPreferences preferences;
    private String token;
    TimerTask timerTask;
    Timer timers;
    ViewPager viewPager;
    ArrayList<String> image_paths;
    Button change_menu,delete_menu;
    public PostMenu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_post_menu, container, false);
        preferences=getActivity().getSharedPreferences("service_provider_data",0);
        token=preferences.getString("token","");

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


        first_view=LayoutInflater.from(getActivity()).inflate(R.layout.menu_first_view,null);
        add_menu_layout=LayoutInflater.from(getActivity()).inflate(R.layout.add_menu_layout,null);
        menu_add_success=LayoutInflater.from(getActivity()).inflate(R.layout.menu_add_success,null);
        manage_menu_layout=LayoutInflater.from(getActivity()).inflate(R.layout.manage_menu_layout,null);

        image_paths =new ArrayList<>();
        viewPager=(ViewPager)manage_menu_layout.findViewById(R.id.pager);
        change_menu=(Button)manage_menu_layout.findViewById(R.id.change_menu);
        delete_menu=(Button)manage_menu_layout.findViewById(R.id.delete_menu);
        change_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView=(ImageView)viewPager.getChildAt(viewPager.getCurrentItem());

            }
        });



        flipper=(ViewFlipper)view.findViewById(R.id.flipper);
        flipper.addView(first_view);
        flipper.showNext();
        add_menu=(Button)first_view.findViewById(R.id.add_menu);
        manage_menu=(Button)first_view.findViewById(R.id.manage_menu);
        upload_meu_image=(Button)add_menu_layout.findViewById(R.id.upload_menu);
        menu_image=(RelativeLayout)add_menu_layout.findViewById(R.id.menu_image);
        back_Image=(ImageView)add_menu_layout.findViewById(R.id.background_image);
        buttons_layout=(LinearLayout)add_menu_layout.findViewById(R.id.select_menu_layout);
        main_layout=(LinearLayout)add_menu_layout.findViewById(R.id.main_layout);

        menu_pr=(ProgressBar)add_menu_layout.findViewById(R.id.menu_pr);
        send_menu_image=(Button)add_menu_layout.findViewById(R.id.send_menu_image);
        send_menu_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu_pr.setVisibility(View.VISIBLE);
                File file = new File(uri.getPath());
                RequestBody photopart= RequestBody.create(MediaType.parse("image/*"),file);
                MultipartBody.Part photo = MultipartBody.Part.createFormData("photo_path",file.getName(),photopart);


                Retrofit retrofit=getUserAPIretrofit();
                ServicerProvidersUsersClient client=retrofit.create(ServicerProvidersUsersClient.class);
                Call<ResponseMessage>call=client.upload_menu(token,photo);
                call.enqueue(new retrofit2.Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                      if (response.body().getStatus().equals("ok")){
                          menu_pr.setVisibility(View.GONE);
                          flipper.addView(menu_add_success);
                          flipper.showNext();
                          timers.schedule(timerTask,3000);
                      }

                    }

                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {

                    }
                });
            }
        });


        upload_meu_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,PICK_IMAGE_FROM_GALLERY_REQUEST);

            }
        });

        add_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipper.removeView(add_menu_layout);
                flipper.addView(add_menu_layout);
                flipper.showNext();
            }
        });
        manage_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Retrofit retrofit =getUserAPIretrofit();
                ServicerProvidersUsersClient client=retrofit.create(ServicerProvidersUsersClient.class);
                Call<List<Menu>> call=client.get_menu_image(token);
                call.enqueue(new retrofit2.Callback<List<Menu>>() {
                    @Override
                    public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                        for (int i=0;i<response.body().size();i++){
                            image_paths.add(response.body().get(i).getPhoto_path());
                        }
                        viewPager.setAdapter(new MenuPagerAdapter(getActivity(),image_paths));
                        CircleIndicator indicator = (CircleIndicator)manage_menu_layout.findViewById(R.id.indicator);
                        indicator.setViewPager(viewPager);
                    }
                    @Override
                    public void onFailure(Call<List<Menu>> call, Throwable t) {

                    }
                });
                flipper.removeView(manage_menu_layout);
                flipper.addView(manage_menu_layout);
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

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_FROM_GALLERY_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            uri=data.getData();
            File file=new File(uri.getPath());
            buttons_layout.setVisibility(View.GONE);
            menu_image.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(uri).placeholder(R.drawable.header).into(back_Image);
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
