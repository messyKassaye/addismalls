package com.example.red.magazine;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.red.magazine.agents.companyAgent.CompanyAgent;
import com.example.red.magazine.client.UsersClient;
import com.example.red.magazine.malls.admin.MallAdminDashboard;
import com.example.red.magazine.model.LoginUser;
import com.example.red.magazine.model.ResponseMessage;
import com.example.red.magazine.model.User;
import com.example.red.magazine.model.VerificationItemModel;
import com.example.red.magazine.serviceproviders.ServiceProvidersDashBoard;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.suppliers.SuppliersDrawer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView signUp;
    private SignupFragment signupFragment;

    //layouts
    private LinearLayout sign_in_container;
    private ViewFlipper flipper;
    private View view1,view2,view3,view4;
    private Button next,next1,sign_up;

    private LinearLayout already_have_layout;

    //mall owner requirement inputs
    private EditText first_name,last_name,phone_no,mall_name,tin_no,city,floors,password,repassword;
    private TextView error_shower1,error_shower2,error_shower3;


    //registration variable
    private String server_first_name,server_last_name,server_phone_no;
    private String server_mall_name,server_mall_tin_no,server_mall_city,server_floors_no;
    private ProgressBar pr;
    private TextView hello_user,success_message,all_have_account_login;

    private Intent intent;

    private EditText sign_in_edittext,sign_in_password;
    private Button sign_in_button;

    //verfication displayer
    private LinearLayout verification_layout;
    private TextView verify_user,verify_user_success_message,login_error_shower;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.sign_in);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        pr=(ProgressBar)findViewById(R.id.create_pr);
        already_have_layout=(LinearLayout)findViewById(R.id.already_have_account_layout);

        intent=getIntent();
        String turn=intent.getStringExtra("turn");

        flipper=(ViewFlipper)findViewById(R.id.simpleViewFlipper);
        all_have_account_login=(TextView)findViewById(R.id.all_have_account_login);

        view1=LayoutInflater.from(this).inflate(R.layout.tell_us_about_you_layout,null);
        first_name=(EditText)view1.findViewById(R.id.first_name);
        last_name=(EditText)view1.findViewById(R.id.last_name);
        phone_no=(EditText)view1.findViewById(R.id.phone_no);
        error_shower1=(TextView)view1.findViewById(R.id.error_shower);
        next=view1.findViewById(R.id.next);

        view2=LayoutInflater.from(this).inflate(R.layout.tell_us_about_your_mall_layout,null);
        mall_name=(EditText)view2.findViewById(R.id.mall_name_input);
        tin_no=(EditText)view2.findViewById(R.id.tin_no_input);
        city=(EditText)view2.findViewById(R.id.city_input);
        floors=(EditText)view2.findViewById(R.id.no_of_floors_input);
        error_shower2=(TextView)view2.findViewById(R.id.error_shower1);

        next1=view2.findViewById(R.id.next1);

        view3=LayoutInflater.from(this).inflate(R.layout.create_password_layout,null);
        password=(EditText)view3.findViewById(R.id.password_input);
        repassword=(EditText)view3.findViewById(R.id.retype_password_input);
        error_shower3=(TextView)view3.findViewById(R.id.error_shower3);

        sign_up=view3.findViewById(R.id.sign_up_btn);

        view4=LayoutInflater.from(this).inflate(R.layout.registration_success_layout,null);
        hello_user=(TextView)view4.findViewById(R.id.hello_user);
        success_message=(TextView)view4.findViewById(R.id.success_message);
        flipper.addView(view1);
        flipper.addView(view2);
        flipper.addView(view3);
        flipper.addView(view4);
        //sign in container and inputs
        sign_in_container=(LinearLayout)findViewById(R.id.sign_in_containerlayout);

        if (turn.equals("login")){
            sign_in_container.setVisibility(View.VISIBLE);
        }else {
            sign_in_container.setVisibility(View.GONE);
            flipper.setVisibility(View.VISIBLE);
            already_have_layout.setVisibility(View.VISIBLE);
            Animation in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
            Animation out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left);
            // set the animation type's to ViewFlipperActivity
            flipper.setInAnimation(in);
            flipper.setOutAnimation(out);
            // set interval time for flipping between views
            flipper.setFlipInterval(3000);
            toolbar.setTitle("Sign up");
            flipper.setDisplayedChild(4);
        }
        signUp=(TextView)findViewById(R.id.sign_up);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_in_container.setVisibility(View.GONE);
                flipper.setVisibility(View.VISIBLE);
                already_have_layout.setVisibility(View.VISIBLE);
                Animation in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
                Animation out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left);
                // set the animation type's to ViewFlipperActivity
                flipper.setInAnimation(in);
                flipper.setOutAnimation(out);
                // set interval time for flipping between views
                flipper.setFlipInterval(3000);
                toolbar.setTitle("Sign up");
                flipper.setDisplayedChild(4);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String first_name_value=first_name.getText().toString().trim();
                String last_name_value=last_name.getText().toString().trim();
                String phone_no_value=phone_no.getText().toString().trim();
                if (first_name_value.equals("")){
                    first_name.setBackgroundResource(R.drawable.text_field_error);
                    first_name.setPadding(15,0,0,0);
                    error_shower1.setText(R.string.enter_your_name);
                }else if (last_name_value.equals("")){
                    last_name.setBackgroundResource(R.drawable.text_field_error);
                    last_name.setPadding(15,0,0,0);
                    error_shower1.setText(R.string.enter_your_last_name);
                }else if (phone_no_value.equals("")){
                    phone_no.setBackgroundResource(R.drawable.text_field_error);
                    phone_no.setPadding(15,0,0,0);
                    error_shower1.setText(R.string.enter_your_phone);
                }else {
                    server_first_name=first_name_value;
                    server_last_name=last_name_value;
                    server_phone_no=phone_no_value;
                    flipper.showNext();
                }
            }
        });
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String mall_name_value= mall_name.getText().toString().trim();
                String tin_no_value=tin_no.getText().toString().trim();
                String city_value=city.getText().toString().trim();
                String floors_value=floors.getText().toString().trim();
                if (mall_name_value.equals("")){
                    mall_name.setBackgroundResource(R.drawable.text_field_error);
                    mall_name.setPadding(15,0,0,0);
                    error_shower2.setText(R.string.enter_your_mall_name);
                }else if (tin_no_value.equals("")){
                    tin_no.setBackgroundResource(R.drawable.text_field_error);
                    tin_no.setPadding(15,0,0,0);
                    error_shower2.setText(R.string.enter_your_tin_no);
                }else if (city_value.equals("")){
                    city.setBackgroundResource(R.drawable.text_field_error);
                    city.setPadding(15,0,0,0);
                    error_shower2.setText(R.string.enter_your_mall_city);
                }else if (tin_no_value.equals("")){
                    floors.setBackgroundResource(R.drawable.text_field_error);
                    floors.setPadding(15,0,0,0);
                    error_shower2.setText(R.string.enter_your_mall_floors_no);
                }else {
                    server_mall_name=mall_name_value;
                    server_mall_tin_no=tin_no_value;
                    server_mall_city=city_value;
                    server_floors_no=floors_value;
                    flipper.showNext();
                }
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password_value=password.getText().toString().trim();
                String re_password_value=repassword.getText().toString().trim();
                if (password_value.equals("")){
                    password.setBackgroundResource(R.drawable.text_field_error);
                    password.setPadding(15,0,0,0);
                    error_shower3.setText(R.string.enter_your_mall_floors_no);
                }else if (re_password_value.equals("")){
                    repassword.setBackgroundResource(R.drawable.text_field_error);
                    repassword.setPadding(15,0,0,0);
                    error_shower3.setText(R.string.enter_your_retype_password);
                }else if (!password_value.equals(re_password_value)){
                    error_shower3.setText(R.string.password_is_not_match);
                }else {
                  pr.setVisibility(View.VISIBLE);
                    User user=new User(server_first_name,server_last_name,server_phone_no,server_mall_name,server_mall_tin_no,server_mall_city,server_floors_no,password_value);
                   registerUser(user);
                }
            }
        });
        all_have_account_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_in_container.setVisibility(View.VISIBLE);
                flipper.setVisibility(View.GONE);
                already_have_layout.setVisibility(View.GONE);
            }
        });

        sign_in_edittext=(EditText)findViewById(R.id.sign_in_tin_no);
        sign_in_password=(EditText)findViewById(R.id.sign_in_password);
        sign_in_button=(Button)findViewById(R.id.login_btn);
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tin_no_value=sign_in_edittext.getText().toString().trim();
                String password_value=sign_in_password.getText().toString().trim();
                if (tin_no_value.equals("")){
                    verification_layout.setVisibility(View.VISIBLE);
                    login_error_shower.setVisibility(View.VISIBLE);
                    login_error_shower.setText("Please enter your Tin number");
                }else if (tin_no_value.equals("")){
                    verification_layout.setVisibility(View.VISIBLE);
                    login_error_shower.setVisibility(View.VISIBLE);
                    login_error_shower.setText("Please enter your password");
                }else {
                    pr.setVisibility(View.VISIBLE);
                    LoginUser user=new LoginUser(tin_no_value,password_value);
                    Retrofit retrofit=getUserAuthAPIretrofit();
                    UsersClient client=retrofit.create(UsersClient.class);
                    Call<ResponseMessage> call=client.login(user);
                    call.enqueue(new Callback<ResponseMessage>() {
                        @Override
                        public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                            if (response.body().getStatus().equals("ok")){
                                checkVerification(response.body().getToken());
                            }else{
                                pr.setVisibility(View.GONE);
                                verification_layout.setVisibility(View.VISIBLE);
                                login_error_shower.setVisibility(View.VISIBLE);
                                login_error_shower.setText("Incorrect tin no or password is used.Try again ):");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseMessage> call, Throwable t) {

                        }
                    });
                }

            }
        });

        verification_layout=(LinearLayout)findViewById(R.id.verification_shower);
        verify_user=(TextView)findViewById(R.id.verify_user);
        login_error_shower=(TextView)findViewById(R.id.login_error_shower);
        verify_user_success_message=(TextView)findViewById(R.id.verification_success_message);
    }

    public void checkVerification(final String token){
        Retrofit retrofit=getUserAPIretrofit();
        UsersClient client=retrofit.create(UsersClient.class);
        Call<VerificationItemModel> call=client.checkverification(token);
        call.enqueue(new Callback<VerificationItemModel>() {
            @Override
            public void onResponse(Call<VerificationItemModel> call, Response<VerificationItemModel> response) {
              if (response.body().getStatus().equals("ok")){
                  if (response.body().getRole_type().equals("m")){
                      Intent intent=new Intent(getApplicationContext(), MallAdminDashboard.class);
                      intent.putExtra("token",token);
                      pr.setVisibility(View.GONE);
                      startActivity(intent);
                      finish();
                  }else if (response.body().getRole_type().equals("s")){
                      Intent intent=new Intent(getApplicationContext(), ServiceProvidersDashBoard.class);
                      intent.putExtra("token",token);
                      pr.setVisibility(View.GONE);
                      startActivity(intent);
                      finish();
                  }else if (response.body().getRole_type().equals("p")){
                      Intent intent=new Intent(getApplicationContext(), SuppliersDrawer.class);
                      intent.putExtra("token",token);
                      pr.setVisibility(View.GONE);
                      startActivity(intent);
                      finish();
                  }else if (response.body().getRole_type().equals("ca")){
                      Intent intent=new Intent(getApplicationContext(), CompanyAgent.class);
                      intent.putExtra("token",token);
                      pr.setVisibility(View.GONE);
                      startActivity(intent);
                      finish();
                  }
              }else if (response.body().getStatus().equals("waiting")) {
                  pr.setVisibility(View.GONE);
                  verification_layout.setVisibility(View.VISIBLE);
                  verify_user.setText("MallAddis Group");
                  verify_user_success_message.setText("We are making a research on your mall registration request.after your request is approved we will notify you through your phone. \nThank you for your patient ):");
              }else {
                  Toast.makeText(getApplicationContext(),"Something is not good.Please Check your connection",Toast.LENGTH_LONG).show();

              }
            }

            @Override
            public void onFailure(Call<VerificationItemModel> call, Throwable t) {

            }
        });
    }
 public void registerUser(final User user){
       Retrofit retrofit=getUserAuthAPIretrofit();
     UsersClient client=retrofit.create(UsersClient.class);
     Call<ResponseMessage> call=client.registerUser(user);
     call.enqueue(new Callback<ResponseMessage>() {
         @Override
         public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
              if (response.body().getStatus().equals("ok")){
                  pr.setVisibility(View.GONE);
                  hello_user.setText("Hello,"+user.getFirst_name());
                  success_message.setText(R.string.success_message);
                  flipper.showNext();
              }
         }

         @Override
         public void onFailure(Call<ResponseMessage> call, Throwable t) {
             Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

         }
     });
 }
    public Retrofit getUserAuthAPIretrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ProjectStatic.AUTH_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit;
    }
    public Retrofit getUserAPIretrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ProjectStatic.URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mall_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
