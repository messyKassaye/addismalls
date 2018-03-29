package com.example.red.magazine.serviceproviders.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.model.ResponseMessage;
import com.example.red.magazine.serviceproviders.clients.ServicerProvidersUsersClient;
import com.example.red.magazine.statics.ProjectStatic;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class SupplyFragment extends Fragment {

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
    public SupplyFragment() {
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
        View view= inflater.inflate(R.layout.fragment_supply, container, false);
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.READ_CONTACTS},MY_PERMISSION_REQUEST);
        }
        preferences=getActivity().getSharedPreferences("service_provider_data",0);
        token=preferences.getString("token","");

        description=(EditText)view.findViewById(R.id.describe_your_demand);
        quantity=(EditText)view.findViewById(R.id.input_quantity);
        price=(EditText)view.findViewById(R.id.input_price);
        upload=(TextView) view.findViewById(R.id.upload_demand_photo);
        selected_file=(TextView)view.findViewById(R.id.selected_product_photo);
        pr=(ProgressBar)view.findViewById(R.id.demand_pr);

        spinner=(Spinner)view.findViewById(R.id.delivery_time);
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

        upload_btn=(Button)view.findViewById(R.id.post_supply_btn);
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pr.setVisibility(View.VISIBLE);
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
                    upload_supply(token,description_value,quantity_value,price_value,uri,spinner.getSelectedItem().toString());
                }

            }
        });
        return  view;
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
    public void upload_supply(String token, final String description, String quantity, String price, Uri uri,String availability){
        RequestBody descriptions = RequestBody.create(MultipartBody.FORM,description);
        RequestBody quantitys = RequestBody.create(MultipartBody.FORM,quantity);
        RequestBody prices = RequestBody.create(MultipartBody.FORM,price);
        RequestBody avail = RequestBody.create(MultipartBody.FORM,availability);
        File file = new File(uri.getPath());
        RequestBody photopart= RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo_path",file.getName(),photopart);
        Retrofit retrofit=getUserAPIretrofit();
        ServicerProvidersUsersClient client=retrofit.create(ServicerProvidersUsersClient.class);
        Call<ResponseMessage> call=client.upload_supply(token,descriptions,quantitys,prices,avail,photo);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.body().getStatus().equals("ok")){
                    pr.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),"your Supply is Submitted successfully):",Toast.LENGTH_LONG).show();
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
