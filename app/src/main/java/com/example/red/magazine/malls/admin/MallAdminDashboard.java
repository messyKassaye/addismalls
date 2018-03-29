package com.example.red.magazine.malls.admin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.MainActivity;
import com.example.red.magazine.R;
import com.example.red.magazine.client.PhotoClient;
import com.example.red.magazine.client.UsersClient;
import com.example.red.magazine.malls.admin.mallAdminfragments.AddNewRetailers;
import com.example.red.magazine.malls.admin.mallAdminfragments.DashboardFragment;
import com.example.red.magazine.malls.admin.mallAdminfragments.ManageLeaseSpace;
import com.example.red.magazine.malls.admin.mallAdminfragments.ManageTenant;
import com.example.red.magazine.malls.admin.mallAdminfragments.ManageVacancy;
import com.example.red.magazine.malls.client.MallUsersClient;
import com.example.red.magazine.malls.fragments.Notification_fragment;
import com.example.red.magazine.malls.model.FileUtils;
import com.example.red.magazine.model.AdminUsersDetail;
import com.example.red.magazine.model.ResponseMessage;
import com.example.red.magazine.model.Sub_city;
import com.example.red.magazine.statics.ProjectStatic;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MallAdminDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Intent intent;
    private String token="";
    private TextView upload,uploaded_photo_name,added_specific_address;
    private LinearLayout specific_name_input_layout;
    private static final  int MY_PERMISSION_REQUEST=100;
    private static final  int PICK_IMAGE_FROM_GALLERY_REQUEST=1;

    private LinearLayout left_item_layout,mall_photo_uploaded_layout,address_added_layout;
    private TextView address_not_added_text;
    private EditText specific_address_input;
    private TextView specific_name_submit_btn;

    //side nav component
    private TextView full_name,mall_name;
    private LinearLayout header_image;

    private Button notifCount;
    private boolean left_item_is_on=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        if (ContextCompat.checkSelfPermission(MallAdminDashboard.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MallAdminDashboard.this,new String[]{android.Manifest.permission.READ_CONTACTS},MY_PERMISSION_REQUEST);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        full_name=(TextView)findViewById(R.id.admin_user_full_names);
        mall_name=(TextView)findViewById(R.id.admin_side_nav_mall_name);

        intent = getIntent();
        token=intent.getStringExtra("token");

        SharedPreferences preferences=getSharedPreferences("token",0);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("token_value",token);
        editor.commit();

        getUserDetail(token);

        left_item_layout=(LinearLayout)findViewById(R.id.left_item_layout);
        mall_photo_uploaded_layout=(LinearLayout)findViewById(R.id.mall_photo_upload_layout);
        address_added_layout=(LinearLayout)findViewById(R.id.address_added_layout);


        upload=(TextView)findViewById(R.id.upload_mall_photo_btn);
        uploaded_photo_name=(TextView)findViewById(R.id.uploaded_photo_name);
        added_specific_address=(TextView)findViewById(R.id.add_specific_name);

        specific_name_input_layout=(LinearLayout)findViewById(R.id.specific_name_input_layout);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,PICK_IMAGE_FROM_GALLERY_REQUEST);

            }
        });

        added_specific_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              specific_name_input_layout.setVisibility(View.VISIBLE);
            }
        });

        added_specific_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                specific_name_input_layout.setVisibility(View.VISIBLE);

                specific_address_input=(EditText) findViewById(R.id.specific_name_edittext);
                specific_name_submit_btn=(TextView)findViewById(R.id.add_specific_name_btn);
                specific_name_submit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String specific_name_value=specific_address_input.getText().toString().trim();
                        if (specific_name_value.equals("")){
                            Toast.makeText(getApplicationContext(),"Please add your mall specific address name",Toast.LENGTH_LONG).show();
                        }else {
                            Sub_city sub_city=new Sub_city(specific_name_value);
                            Retrofit retrofit1=getUserAPIretrofit();
                            MallUsersClient client1=retrofit1.create(MallUsersClient.class);
                            Call<ResponseMessage> call1=client1.add_address(token,sub_city);
                            call1.enqueue(new Callback<ResponseMessage>() {
                                @Override
                                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                                 if (response.body().getStatus().equals("ok")){
                                     left_item_layout.setVisibility(View.GONE);
                                     Fragment fragment = null;
                                     getSupportActionBar().setTitle("New retailers");
                                     FragmentManager fm = getFragmentManager();
                                     FragmentTransaction ft = fm.beginTransaction();
                                     fragment = new AddNewRetailers();
                                     ft.replace(R.id.placeholder, fragment);
                                     ft.addToBackStack(null);
                                     ft.commit();

                                 }else {

                                 }


                                }
                                @Override
                                public void onFailure(Call<ResponseMessage> call, Throwable t) {

                                }
                            });
                        }
                    }
                });
            }
        });






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);


        Retrofit retrofit1 =getUserAPIretrofit();
        PhotoClient client1=retrofit1.create(PhotoClient.class);
        Call<ResponseMessage>call1=client1.checkmallphoto(token);
        call1.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.body().getStatus().equals("added")){
                    Fragment fragment = null;
                    getSupportActionBar().setTitle("Dashboard");
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    fragment = new AddNewRetailers();
                    ft.replace(R.id.placeholder, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    left_item_is_on=false;
                }else {
                    left_item_layout.setVisibility(View.VISIBLE);
                    left_item_is_on=true;
                }

            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {

            }
        });


    }

    public void removeLeftItem(){
         if (left_item_is_on){
             left_item_layout.setVisibility(View.GONE);
         }
    }

    public void getUserDetail(String token){

        Retrofit retrofit=getUserAPIretrofit();
        MallUsersClient client=retrofit.create(MallUsersClient.class);
        Call<AdminUsersDetail> call= client.getMallAdmin_Users_detail(token);
        call.enqueue(new Callback<AdminUsersDetail>() {
            @Override
            public void onResponse(Call<AdminUsersDetail> call, Response<AdminUsersDetail> response) {

            }

            @Override
            public void onFailure(Call<AdminUsersDetail> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_FROM_GALLERY_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            Uri uri=data.getData();
            File file = new File(uri.getPath());
            upload_mall_photo(uri);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void upload_mall_photo(Uri uri){
        File file = FileUtils.getFile(this, uri);
        final RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        file
                );
        RequestBody photopart= RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("mall_photo",file.getName(),photopart);
        Retrofit retrofit=getUserAPIretrofit();
        UsersClient client=retrofit.create(UsersClient.class);
        Call<ResponseMessage> call=client.upload_mall_photo(token,photo);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
              if (response.body().getStatus().equals("ok")){
                  uploaded_photo_name.setText("your mall photo is  uploaded successfully");
              }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                 Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mall_admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id==R.id.mall_admin_bell){
            removeLeftItem();
            getSupportActionBar().setTitle("Notification");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new Notification_fragment();
            ft.replace(R.id.placeholder, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }else if (id==R.id.mall_admin_menu_search){

        }else if (id==R.id.logouts){
            Intent intent=new Intent(MallAdminDashboard.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.manage_retailers) {
            removeLeftItem();
            getSupportActionBar().setTitle("New Retailers");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new AddNewRetailers();
            ft.replace(R.id.placeholder, fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.manage_lease_space) {
            removeLeftItem();
            getSupportActionBar().setTitle("Manage Lease Space");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new ManageLeaseSpace();
            ft.replace(R.id.placeholder, fragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.manage_vacany) {
            removeLeftItem();
            getSupportActionBar().setTitle("Manage Vacancy");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fragment = new ManageVacancy();
            ft.replace(R.id.placeholder, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }else if (id==R.id.my_photo){
            removeLeftItem();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
