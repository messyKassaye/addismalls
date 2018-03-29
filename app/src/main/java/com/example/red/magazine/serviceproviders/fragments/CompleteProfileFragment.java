package com.example.red.magazine.serviceproviders.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.red.magazine.R;
import com.example.red.magazine.malls.model.FileUtils;
import com.example.red.magazine.model.ResponseMessage;
import com.example.red.magazine.serviceproviders.clients.ServicerProvidersUsersClient;
import com.example.red.magazine.serviceproviders.model.Categories;
import com.example.red.magazine.serviceproviders.model.MallData;
import com.example.red.magazine.serviceproviders.model.RetailerProfile;
import com.example.red.magazine.statics.ProjectStatic;

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

public class CompleteProfileFragment extends Fragment {
   private TextView welcome,info1;
    private ProgressBar pr;
    private String token="";
    private SharedPreferences preferences;
    private Spinner spinner;
    private ArrayList<String> spinner_data;
    private ArrayAdapter<String> adapter;
    private TextView floor_no,office_no;
    private String mall_name;
    private Button send;
    private EditText first_name,last_name,company_name,floor_no_input,office_no_input;
    private static final  int MY_PERMISSION_REQUEST=100;
    private static final  int PICK_IMAGE_FROM_GALLERY_REQUEST=1;
    private Uri uri=null;
    private Uri uri2=null;
    private TextView selected_file;
    View first_view,second_view,last_view;
    ViewFlipper flipper;
    TextView congratualation,start;
    TimerTask timerTask;
    Timer timers;
    Button post_new_item,manage_my_post,upload_new_item,send_new_item,upload;
    private EditText price,caption;
    boolean result_two=false;

    //layouts
    LinearLayout post_first_layout,post_second_layout,inputs_layout;
    public CompleteProfileFragment() {
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
        View view= inflater.inflate(R.layout.fragment_complete_profile, container, false);

        timerTask=new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        flipper.removeView(last_view);
                        flipper.addView(last_view);
                        flipper.showNext();
                    }
                });
            }
        };
        timers =new Timer();

        preferences=getActivity().getSharedPreferences("service_provider_data",0);
        token=preferences.getString("token","");

        flipper=(ViewFlipper)view.findViewById(R.id.complete_flipper);

        first_view=LayoutInflater.from(getActivity()).inflate(R.layout.complete_first_view,null);
        last_view=LayoutInflater.from(getActivity()).inflate(R.layout.post_new_item_to_sale,null);
        second_view=LayoutInflater.from(getActivity()).inflate(R.layout.complete_profile_registration_success,null);

        post_first_layout=(LinearLayout)last_view.findViewById(R.id.post_first_layout);
        post_second_layout=(LinearLayout)last_view.findViewById(R.id.post_new_item_layout);
        inputs_layout=(LinearLayout)last_view.findViewById(R.id.inputs_layout);
        upload_new_item=(Button)last_view.findViewById(R.id.upload_product);
        send_new_item=(Button)last_view.findViewById(R.id.post_my_product);
        price = (EditText)last_view.findViewById(R.id.post_price);
        caption=(EditText)last_view.findViewById(R.id.caption);

        post_new_item=(Button)last_view.findViewById(R.id.post_new_item_for_sale);
        post_new_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_first_layout.setVisibility(View.GONE);
                post_second_layout.setVisibility(View.VISIBLE);

            }
        });
        upload_new_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result_two=true;
                Intent intent=new Intent();
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select picture"),PICK_IMAGE_FROM_GALLERY_REQUEST);

            }
        });
        send_new_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String price_value=price.getText().toString();
                String caption_value=caption.getText().toString();
                if (price_value.equals("")){
                    Toast.makeText(getActivity(),"Please provide your product price",Toast.LENGTH_LONG).show();
                }else {
                    Retrofit retrofit=getUserAPIretrofit();
                    ServicerProvidersUsersClient client=retrofit.create(ServicerProvidersUsersClient.class);
                    RequestBody price_re = RequestBody.create(MultipartBody.FORM,price_value);
                    RequestBody caption_re = RequestBody.create(MultipartBody.FORM,caption_value);
                     File file = new File(uri2.getPath());
                    RequestBody photopart= RequestBody.create(MediaType.parse("image/*"),file);
                    MultipartBody.Part photo = MultipartBody.Part.createFormData("photo_path",file.getName(),photopart);

                    Call<ResponseMessage> call=client.post_new_item(token,price_re,caption_re,photo);
                    call.enqueue(new Callback<ResponseMessage>() {
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
        congratualation=(TextView)second_view.findViewById(R.id.congratulation);
        start=(TextView)second_view.findViewById(R.id.start);
        flipper.addView(first_view);

        welcome =(TextView)first_view.findViewById(R.id.serviceprovider_welcome);
        info1=(TextView)first_view.findViewById(R.id.service_provider_info1);
        spinner=(Spinner)first_view.findViewById(R.id.company_category);
        floor_no=(TextView)first_view.findViewById(R.id.floor_number);
        office_no=(TextView)first_view.findViewById(R.id.office_no_number);

        first_name=(EditText)first_view.findViewById(R.id.retailer_first_name);
        last_name=(EditText)first_view.findViewById(R.id.retailers_last_name);
        company_name=(EditText)first_view.findViewById(R.id.company_name);
        floor_no_input=(EditText)first_view.findViewById(R.id.floor_no_editext);
        office_no_input=(EditText)first_view.findViewById(R.id.office_no_editext);
        pr=(ProgressBar)first_view.findViewById(R.id.service_provider_pr);

        upload=(Button) first_view.findViewById(R.id.upload_logo_btn);
        selected_file=(TextView)first_view.findViewById(R.id.selected_file);

        send=(Button)first_view.findViewById(R.id.add_my_profile);
        Animation in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
        Animation out = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_left);
        // set the animation type's to ViewFlipperActivity
        flipper.setInAnimation(in);
        flipper.setOutAnimation(out);
        // set interval time for flipping between views
        flipper.setFlipInterval(3000);
        flipper.setDisplayedChild(4);

        spinner_data=new ArrayList<>();
        Retrofit retrofit= getUserAPIretrofit();
        ServicerProvidersUsersClient client=retrofit.create(ServicerProvidersUsersClient.class);
        final Call<List<MallData>> call= client.get_mall_owner(token);
        call.enqueue(new Callback<List<MallData>>() {
            @Override
            public void onResponse(Call<List<MallData>> call, Response<List<MallData>> response) {
                floor_no.setText("Your floor number at "+response.body().get(0).getName());
                office_no.setText("your office number at "+response.body().get(0).getName());
                mall_name=response.body().get(0).getName();
                welcome.setText("Welcome to "+response.body().get(0).getName());
                info1.setText("You are registered as the retailer of "+response.body().get(0).getName()+".Complete the following forms and start posting your products for sale.");
            }

            @Override
            public void onFailure(Call<List<MallData>> call, Throwable t) {

            }
        });

        Retrofit retrofit1=getUserAPIretrofit();
        ServicerProvidersUsersClient client1=retrofit1.create(ServicerProvidersUsersClient.class);
        Call<List<Categories>> call1=client1.get_categories(token);
        call1.enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                spinner_data.add("Select your company category");
                for (int i=0;i<response.body().size();i++){
                   spinner_data.add(response.body().get(i).getName());
               }
               adapter= new ArrayAdapter<String>(getActivity(),
                       R.layout.spinner_item, spinner_data);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {

            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,PICK_IMAGE_FROM_GALLERY_REQUEST);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                String first_name_value=first_name.getText().toString().trim();
                String last_name_value=last_name.getText().toString().trim();
                String company_name_value=company_name.getText().toString();
                String floor_no_value=floor_no_input.getText().toString();
                String office_no_value=office_no_input.getText().toString();
                String category_name_value=spinner.getSelectedItem().toString();
                if (first_name_value.equals("")){

                }else if (last_name_value.equals("")){
                    Toast.makeText(getActivity(),"Please add your name",Toast.LENGTH_LONG).show();
                }else if (company_name_value.equals("")){
                    Toast.makeText(getActivity(),"Please add your father's name",Toast.LENGTH_LONG).show();

                }else if (floor_no_value.equals("")){
                    Toast.makeText(getActivity(),"Please add your company floor number",Toast.LENGTH_LONG).show();

                }else if (office_no_value.equals("")){
                    Toast.makeText(getActivity(),"Please add your company office number",Toast.LENGTH_LONG).show();

                }else if (category_name_value.equals("Select your company category")){
                    Toast.makeText(getActivity(),"Please select your company category type",Toast.LENGTH_LONG).show();

                }else if (uri.toString().equals("")){
                    Toast.makeText(getActivity(),"Please Select your company photo",Toast.LENGTH_LONG).show();
                }else {
                    pr.setVisibility(View.VISIBLE);
                    upload_data(token,first_name_value,last_name_value,company_name_value,category_name_value,floor_no_value,office_no_value,uri);
                   /* RetailerProfile profile=new RetailerProfile(first_name_value,last_name_value,company_name_value,category_name_value,floor_no_value,office_no_value);
                    Retrofit retrofit2 = getUserAPIretrofit();
                    ServicerProvidersUsersClient client2=retrofit2.create(ServicerProvidersUsersClient.class);
                    Call<ResponseMessage> call2 =client2.complete_profile(token,profile);
                    call2.enqueue(new Callback<ResponseMessage>() {
                       @Override
                       public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                           pr.setVisibility(View.GONE);
                           Toast.makeText(getActivity(),response.body().getStatus(),Toast.LENGTH_LONG).show();
                       }

                       @Override
                       public void onFailure(Call<ResponseMessage> call, Throwable t) {

                       }
                   });*/
                }
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (result_two){
            if (requestCode==PICK_IMAGE_FROM_GALLERY_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
                uri2=data.getData();
                File file=new File(uri.getPath());
                inputs_layout.setVisibility(View.VISIBLE);
                //selected_file.setText(file.getName());
                //upload_and_createAccount(uri);
            }
            result_two=false;
        }else {
            if (requestCode==PICK_IMAGE_FROM_GALLERY_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
                uri=data.getData();
                File file=new File(uri.getPath());
                selected_file.setVisibility(View.VISIBLE);
                selected_file.setText(file.getName());
                //upload_and_createAccount(uri);
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void upload_data(String token, final String first_names, String last_names, String names, String category, String floor, String office, Uri uri){
        RequestBody first_name = RequestBody.create(MultipartBody.FORM,first_names);
        RequestBody last_name = RequestBody.create(MultipartBody.FORM,last_names);
        RequestBody company_name = RequestBody.create(MultipartBody.FORM,names);
        RequestBody category_type = RequestBody.create(MultipartBody.FORM,category);
        RequestBody floor_no = RequestBody.create(MultipartBody.FORM,floor);
        RequestBody office_no = RequestBody.create(MultipartBody.FORM,office);
        File file = FileUtils.getFile(getActivity(), uri);
        final RequestBody photopart =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        file
                );
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo_path",file.getName(),photopart);

        Retrofit retrofit=getUserAPIretrofit();
        ServicerProvidersUsersClient client=retrofit.create(ServicerProvidersUsersClient.class);
        Call<ResponseMessage> call=client.complete_profile(token,first_name,last_name,company_name,category_type,floor_no,office_no,photo);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.body().getStatus().equals("ok")){
                    pr.setVisibility(View.GONE);
                    flipper.removeView(second_view);
                    flipper.addView(second_view);
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Post new items");
                    congratualation.setText("Congratulation you are completed your profile registration.");
                    start.setText("Start posting your products to sale for all over the world and increase your income.");
                    flipper.showNext();
                    timers.schedule(timerTask,5000);
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
