package com.example.red.magazine.malls.admin.mallAdminfragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
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
import com.example.red.magazine.malls.adapter.LeaseSpaceRecyclerviewadapter;
import com.example.red.magazine.malls.adaptermodel.LeaseSpaceAdapterModel;
import com.example.red.magazine.malls.client.ServicePrClient;

import com.example.red.magazine.malls.model.*;
import com.example.red.magazine.malls.model.LeaseSpace;
import com.example.red.magazine.model.ResponseMessage;
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
public class ManageLeaseSpace extends Fragment {

    private ViewFlipper flipper;
    private View first_view,add_lease_space_layout,lease_space_add_success,manage_lease_layout;
    private Button add_lease_space,manage_lease_space;
    private ImageView lease_space_photo;
    private LinearLayout lease_space_btn_layout;
    private Button upload_lease_space;
    private Uri uri;
    private static final  int PICK_IMAGE_FROM_GALLERY_REQUEST=1;
    private Button post_lease_space;
    private EditText lease_space_floor_no,lease_space_area,lease_space_purpose,lease_sapce_descr;
    private String token;
    private TimerTask timerTask;
    private Timer timers;
    private ProgressBar pr;
    private RecyclerView recyclerView;
    private List<LeaseSpaceAdapterModel> leaseList;
    private LeaseSpaceRecyclerviewadapter leaseSpaceadapter;
    public ManageLeaseSpace() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mange_lease_space, container, false);
        SharedPreferences preferences=getActivity().getSharedPreferences("token",0);
        token= preferences.getString("token_value","");
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

        flipper=(ViewFlipper)view.findViewById(R.id.lease_space_flipper);
        first_view=LayoutInflater.from(getActivity()).inflate(R.layout.lease_space_firsview,null);
        add_lease_space_layout=LayoutInflater.from(getActivity()).inflate(R.layout.lease_space_add_leasespace,null);
        lease_space_add_success=LayoutInflater.from(getActivity()).inflate(R.layout.lease_space_add_success,null);
        manage_lease_layout=LayoutInflater.from(getActivity()).inflate(R.layout.lease_space_manage_layout,null);
       leaseList=new ArrayList<>();
        leaseSpaceadapter=new LeaseSpaceRecyclerviewadapter(getActivity(),leaseList);
        recyclerView=(RecyclerView)manage_lease_layout.findViewById(R.id.lease_space_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        flipper.addView(first_view);
        flipper.showNext();

        add_lease_space=(Button)first_view.findViewById(R.id.add_lease_space);
        manage_lease_space=(Button)first_view.findViewById(R.id.manage_lease_space);
        manage_lease_space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit retrofit=getUserAPIretrofit();
                ServicePrClient client=retrofit.create(ServicePrClient.class);
                Call<List<LeaseSpace>> call=client.get_lease_space(token);
                call.enqueue(new Callback<List<LeaseSpace>>() {
                    @Override
                    public void onResponse(Call<List<LeaseSpace>> call, Response<List<LeaseSpace>> response) {

                     for (int i=0;i<response.body().size();i++){
                         LeaseSpaceAdapterModel model=new LeaseSpaceAdapterModel();
                         model.setDescription(response.body().get(i).getDescription());
                         model.setId(response.body().get(i).getId());
                         model.setDescription(response.body().get(i).getDescription());
                         leaseList.add(model);
                     }
                     recyclerView.setAdapter(leaseSpaceadapter);
                     leaseSpaceadapter.notifyDataSetChanged();

                     }

                    @Override
                    public void onFailure(Call<List<LeaseSpace>> call, Throwable t) {

                    }
                });
                flipper.removeView(manage_lease_layout);
                flipper.addView(manage_lease_layout);
                flipper.showNext();
            }
        });
        add_lease_space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipper.removeView(add_lease_space_layout);
                flipper.addView(add_lease_space_layout);
                flipper.showNext();
            }
        });

        lease_space_btn_layout=(LinearLayout)add_lease_space_layout.findViewById(R.id.lease_space_buttons_layout);
        lease_space_photo=(ImageView)add_lease_space_layout.findViewById(R.id.lease_space_photo);
        upload_lease_space=(Button)add_lease_space_layout.findViewById(R.id.upload_lease_space);
        pr=(ProgressBar)add_lease_space_layout.findViewById(R.id.lease_pr);

        upload_lease_space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,PICK_IMAGE_FROM_GALLERY_REQUEST);
                //startActivityForResult(Intent.createChooser(intent,"Select picture"),PICK_IMAGE_FROM_GALLERY_REQUEST);
            }
        });

        lease_space_floor_no=(EditText)add_lease_space_layout.findViewById(R.id.lease_floor_no);
        lease_space_area=(EditText)add_lease_space_layout.findViewById(R.id.lease_area);
        lease_space_purpose=(EditText)add_lease_space_layout.findViewById(R.id.lease_purpose);
        lease_sapce_descr=(EditText)add_lease_space_layout.findViewById(R.id.lease_space_descr);
        post_lease_space=(Button)add_lease_space_layout.findViewById(R.id.post_lease_space);
        post_lease_space.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                String floor_value=lease_space_floor_no.getText().toString();
                String area_value=lease_space_area.getText().toString();
                String purpose_value=lease_space_purpose.getText().toString();
                String desc_value= lease_sapce_descr.getText().toString();
                if (floor_value.equals("")){
                    Toast.makeText(getActivity(),"Please add your lease space description",Toast.LENGTH_LONG).show();
                }else if (area_value.equals("")){
                    Toast.makeText(getActivity(),"Please add your lease space floor no",Toast.LENGTH_LONG).show();
                }else if (purpose_value.equals("")){
                    Toast.makeText(getActivity(),"Please add your lease space area",Toast.LENGTH_LONG).show();
                }else if (desc_value.equals("")){
                    Toast.makeText(getActivity(),"Please add your lease space purpose",Toast.LENGTH_LONG).show();
                } else if (uri.getPath().equals("")){
                    Toast.makeText(getActivity(),"Please add your lease space photo ): ",Toast.LENGTH_LONG).show();
                }else{
                    pr.setVisibility(View.VISIBLE);
                    Retrofit retrofit= getUserAPIretrofit();
                    ServicePrClient client= retrofit.create(ServicePrClient.class);

                    RequestBody floor_no = RequestBody.create(MultipartBody.FORM, floor_value);
                    RequestBody area = RequestBody.create(MultipartBody.FORM, area_value);
                    RequestBody purpose = RequestBody.create(MultipartBody.FORM, purpose_value);
                    RequestBody desc = RequestBody.create(MultipartBody.FORM, desc_value);

                    //File file = new File(uri.getPath());

                    File file = FileUtils.getFile(getActivity(), uri);
                    final RequestBody requestFile =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"),
                                    file
                            );
                    RequestBody photopart = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part photo = MultipartBody.Part.createFormData("photo_path", file.getName(), photopart);

                    Call<ResponseMessage> call= client.post_lease_space(token,floor_no,area,purpose,desc,photo);
                    call.enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                            if (response.body().getStatus().equals("ok")){
                                pr.setVisibility(View.GONE);
                                flipper.removeView(lease_space_add_success);
                                flipper.addView(lease_space_add_success);
                                flipper.showNext();
                                timers.schedule(timerTask,3000);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseMessage> call, Throwable t) {
                                 Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==PICK_IMAGE_FROM_GALLERY_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            uri=data.getData();
            Toast.makeText(getActivity(),uri.toString(),Toast.LENGTH_LONG).show();
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
