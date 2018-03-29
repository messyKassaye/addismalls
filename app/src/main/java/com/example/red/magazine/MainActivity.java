package com.example.red.magazine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.Push.Push;
import com.example.red.magazine.adaptermodels.CafeResAdapterModel;
import com.example.red.magazine.adaptermodels.CategoryAdapterModel;
import com.example.red.magazine.adaptermodels.SponserAdapterModel;
import com.example.red.magazine.adaptermodels.TopMallsAdapterModel;
import com.example.red.magazine.adapters.CafeAndResRecyclerviewAdapterOuter;
import com.example.red.magazine.adapters.CategoryAdapter;
import com.example.red.magazine.adapters.SponserPagerAdapter;
import com.example.red.magazine.adapters.TopMallAdapter;
import com.example.red.magazine.client.CafeAndResClient;
import com.example.red.magazine.client.CategoryClient;
import com.example.red.magazine.client.MallsClient;
import com.example.red.magazine.client.SponserClient;
import com.example.red.magazine.hotels.adaptermodel.HotelAdapter;
import com.example.red.magazine.hotels.adapters.HotelRecyclerviewAdapter;
import com.example.red.magazine.hotels.client.HotelsClient;
import com.example.red.magazine.hotels.models.Hotels;
import com.example.red.magazine.model.*;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.updates.UpdateMall;
import com.example.red.magazine.updates.VersionClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager mPager;
    private static int currentPage = 0;
    private ArrayList<SponserAdapterModel> sponsorslist;
    private RelativeLayout bottom_layout;

    //=============
    //sign in and register view
    private Button sign_in;
    private TextView register;

    //=====================
    //top malls variables
    //=================
    private RecyclerView topmalls_recyclerview;
    private List<TopMallsAdapterModel> topmalls_list;
    private TopMallAdapter topMallAdapter;


    //=====================
    //top sellers variables
    //=================
    private RecyclerView top_seller_recyclerview;
    private List<HotelAdapter> topsellerlist;
    private HotelRecyclerviewAdapter topSellersAdapter;

    private RecyclerView caferes_recyclerview;
    private List<CafeResAdapterModel> cafereslist;
    private CafeAndResRecyclerviewAdapterOuter caferesadapter;
    private TextView cafe_all_veiw;

    //=====================
    //category variables
    //=================
    private RecyclerView category_recyclerview;
    private List<CategoryAdapterModel> categoryAdapterList;
    private CategoryAdapter categoryAdapter;
    private TextView topmall_viewall, topsell_viewall;

    private LinearLayout watsapplayout, emailiconlayout, emaillayout;
    private TimerTask timerTask;
    private Timer timer;
    private Button push, call,lease_sapce,vacancy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTask=new TimerTask() {
            @Override
            public void run() {
               Retrofit retrofit= getUserAPIretrofit();
                VersionClient client=retrofit.create(VersionClient.class);
                Call<ResponseMessage> call=client.get_version();
                call.enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                         String version= response.body().getStatus();
                         String version_name= BuildConfig.VERSION_NAME;

                        if (!version.equals(version_name)){
                            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("There is newer version of this application available, click UPGRADE to upgrade now?").setPositiveButton("Upgrade", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    UpdateMall updateMall=new UpdateMall(MainActivity.this);
                                    updateMall.execute();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.create().show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {

                    }
                });
            }
        };
        timer=new Timer();
        timer.schedule(timerTask,1000);
        if (!isOnline()) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setMessage("Connection is not available.please set your connection").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("M");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);


        //sign in and registration button initialization
        sign_in = (Button) findViewById(R.id.sign_in);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("turn", "login");
                startActivity(intent);
            }
        });
        EndCallListener callListener = new EndCallListener();
        TelephonyManager mTM = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        mTM.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);

        push = (Button) findViewById(R.id.push);
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Push.class);
                startActivity(intent);
            }
        });
        call = (Button) findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:0944181080"));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                   return;
                }
                startActivity(callIntent);
            }
        });

        lease_sapce=(Button)findViewById(R.id.lease_space);
        vacancy=(Button)findViewById(R.id.vacancy);

        lease_sapce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MainOuterLeaseSpace.class);
                startActivity(intent);
            }
        });

        vacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,OuterVacancy.class);
                startActivity(intent);
            }
        });

         init();
         getTopMall();
         getHotels();
         getCafeAndRestaurant();
         getCategory();


    }
    public  boolean isOnline(){
        ConnectivityManager cm =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()){
            return true;
        }
        return false;
    }
    private void init() {
        sponsorslist=new ArrayList<>();

        Retrofit retrofit=getUserAPIretrofit();
        SponserClient client=retrofit.create(SponserClient.class);
        Call<List<Sponser>> call=client.getSponsers();
        call.enqueue(new Callback<List<Sponser>>() {
            @Override
            public void onResponse(Call<List<Sponser>> call, Response<List<Sponser>> response) {
                for (int i=0;i<response.body().size();i++){
                     SponserAdapterModel model=new SponserAdapterModel();
                     model.setName(response.body().get(i).getName());
                     model.setPhoto_path(response.body().get(i).getPhoto_path());
                     model.setCompany_id(response.body().get(i).getCompany_id());
                     model.setWebsite_link(response.body().get(i).getWebsite_link());
                     sponsorslist.add(model);
                 }
                for(int i=0;i<sponsorslist.size();i++)
                mPager = (ViewPager) findViewById(R.id.sponsors_viewpager);
                mPager.setAdapter(new SponserPagerAdapter(MainActivity.this,sponsorslist));

                // Auto start of viewpager
                final Handler handler = new Handler();
                final Runnable Update = new Runnable() {
                    public void run() {
                        if (currentPage == sponsorslist.size()) {
                            currentPage = 0;
                        }
                        mPager.setCurrentItem(currentPage++, true);
                    }
                };
                Timer swipeTimer = new Timer();
                swipeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, 2500, 2500);
            }

            @Override
            public void onFailure(Call<List<Sponser>> call, Throwable t) {

            }
        });

    }

    public void getTopMall(){
        topmalls_list=new ArrayList<>();
        topMallAdapter=new TopMallAdapter(this,topmalls_list);
        topmalls_recyclerview=(RecyclerView)findViewById(R.id.top_malls_recyclverview);
        topmalls_recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        topmalls_recyclerview.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit = getUserAPIretrofit();
        MallsClient client=retrofit.create(MallsClient.class);
        Call<List<TopMall>> call=client.getTopMall();
        call.enqueue(new Callback<List<TopMall>>() {
            @Override
            public void onResponse(Call<List<TopMall>> call, Response<List<TopMall>> response) {
              for (int i=0;i<response.body().size();i++){
                  TopMallsAdapterModel model=new TopMallsAdapterModel();
                  model.setName(response.body().get(i).getName());
                  model.setPhoto_path(response.body().get(i).getPhoto_path());
                  model.setCompany_id(response.body().get(i).getCompany_id());
                  topmalls_list.add(model);
              }
                 topmalls_recyclerview.setAdapter(topMallAdapter);
                topMallAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TopMall>> call, Throwable t) {

            }
        });
        topmall_viewall=(TextView)findViewById(R.id.topmall_viewall);
        topmall_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ViewAllMalls.class);
                startActivity(intent);
            }
        });
    }

    public void getHotels(){
        topsellerlist=new ArrayList<>();
        topSellersAdapter=new HotelRecyclerviewAdapter(this,topsellerlist);
        top_seller_recyclerview=(RecyclerView)findViewById(R.id.top_sellers);
        top_seller_recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        top_seller_recyclerview.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit = getUserAPIretrofit();
        HotelsClient client=retrofit.create(HotelsClient.class);
        Call<List<Hotels>> call=client.getHotels();
        call.enqueue(new Callback<List<Hotels>>() {
            @Override
            public void onResponse(Call<List<Hotels>> call, Response<List<Hotels>> response) {
                for (int i=0;i<response.body().size();i++){
                    HotelAdapter model=new HotelAdapter();
                    model.setName(response.body().get(i).getName());
                    model.setPhoto_path(response.body().get(i).getPhoto_path());
                    model.setCompany_id(response.body().get(i).getCompany_id());
                    model.setDescription(response.body().get(i).getDescription());
                    topsellerlist.add(model);
                }
                top_seller_recyclerview.setAdapter(topSellersAdapter);
                topSellersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Hotels>> call, Throwable t) {

            }
        });
        topsell_viewall=(TextView)findViewById(R.id.topsell_viewall);
        topsell_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(MainActivity.this,ViewAllHotels.class);
                startActivity(intent);
            }
        });
    }

    public void getCafeAndRestaurant(){

        cafereslist=new ArrayList<>();
        caferesadapter=new CafeAndResRecyclerviewAdapterOuter(this,cafereslist);
        caferes_recyclerview=(RecyclerView)findViewById(R.id.main_cafe_and_restaurant_recyclerview);
        caferes_recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        caferes_recyclerview.setItemAnimator(new DefaultItemAnimator());

        Retrofit retrofit= getUserAPIretrofit();
        CafeAndResClient client= retrofit.create(CafeAndResClient.class);
        Call<List<CafeResModel>> call= client.getCafeAndRes();
        call.enqueue(new Callback<List<CafeResModel>>() {
            @Override
            public void onResponse(Call<List<CafeResModel>> call, Response<List<CafeResModel>> response) {
                for (int i=0;i<response.body().size();i++){
                    CafeResAdapterModel model=new CafeResAdapterModel();
                    model.setName(response.body().get(i).getName());
                    model.setPhoto_path(response.body().get(i).getPhoto_path());
                    model.setCompany_id(response.body().get(i).getCompany_id());
                    cafereslist.add(model);
                }
                caferes_recyclerview.setAdapter(caferesadapter);
                caferesadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CafeResModel>> call, Throwable t) {

            }
        });

        cafe_all_veiw=(TextView)findViewById(R.id.cafe_vaiew);
        cafe_all_veiw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ViewAllCafeAndRes.class);
                startActivity(intent);
            }
        });

    }



    public void getCategory(){

        categoryAdapterList=new ArrayList<>();
        categoryAdapter=new CategoryAdapter(this,categoryAdapterList);
        category_recyclerview=(RecyclerView)findViewById(R.id.category_recyclerview);
        category_recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        category_recyclerview.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit=getUserAPIretrofit();
        CategoryClient client=retrofit.create(CategoryClient.class);
        Call<List<CategoryModel>>call=client.getCategory();
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                for (int i=0;i<response.body().size();i++){
                    CategoryAdapterModel model=new CategoryAdapterModel();
                    model.setId(response.body().get(i).getId());
                    model.setName(response.body().get(i).getName());
                    model.setPhoto_path(response.body().get(i).getPhoto_path());
                    categoryAdapterList.add(model);
                }
                category_recyclerview.setAdapter(categoryAdapter);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.share:
                Dialog send_dialog= new Dialog(this);
                send_dialog.requestWindowFeature(1);
                send_dialog.setContentView(R.layout.share_layout_dialog);
               watsapplayout = (LinearLayout) send_dialog.findViewById(R.id.watsapplayout);
               emailiconlayout = (LinearLayout)send_dialog.findViewById(R.id.emailconlayout);
                emaillayout = (LinearLayout)send_dialog.findViewById(R.id.emaillayout);
                final EditText share_email_address = (EditText)send_dialog.findViewById(R.id.email_to_shares);
                share_email_address.requestFocus();
                final TextView dialog_error = (TextView)send_dialog.findViewById(R.id.dialog_email_error);
                final Button send_email = (Button)send_dialog.findViewById(R.id.send_to_emails);
                ImageView sharewatsapp = (ImageView)send_dialog.findViewById(R.id.watsappicon);
                ImageView shareemail = (ImageView)send_dialog.findViewById(R.id.emailicon);
                sharewatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String message = "";

                        if (Build.VERSION.SDK_INT >= 24) {
                            message = "Hi.friends.Do you want to find the perfect shopping center around your area.Mall addis brings a new technology for you. start downloading MallAddis  and get your needs like fashion shoes,clothes and more.Download from malladdis.com/MallAddis.apk";
                        } else {
                            message = "Hi.friends.Do you want to find the perfect shopping center around your area.Mall addis brings a new technology for you. start downloading MallAddis and get your needs like fashion shoes,clothes and more.Download from malladdis.com/MallAddis.apk";
                        }
                        Intent sendIntent = new Intent("android.intent.action.MAIN");
                        sendIntent.putExtra("android.intent.extra.TEXT", message);
                        sendIntent.setAction("android.intent.action.SEND");
                        sendIntent.setPackage("com.whatsapp");
                        sendIntent.setType("text/plain");
                        MainActivity.this.startActivity(sendIntent);
                    }
                });
                shareemail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        watsapplayout.setVisibility(View.GONE);
                        emailiconlayout.setVisibility(View.GONE);
                        emaillayout.setVisibility(View.VISIBLE);

                    }
                });
                send_email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String emai_value=share_email_address.getText().toString();
                        if (emai_value.equals("")){
                            dialog_error.setText("Please enter email address");
                            dialog_error.setTextColor(Color.RED);
                            dialog_error.setVisibility(View.VISIBLE);
                        }else {
                            if (!isValidEmail(emai_value)){
                               dialog_error.setText("incorrect email address is used");
                                dialog_error.setTextColor(Color.RED);
                                dialog_error.setVisibility(View.VISIBLE);
                            }else {
                                Toast.makeText(getApplicationContext(),"email is sent",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });





                send_dialog.show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private class EndCallListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if(TelephonyManager.CALL_STATE_RINGING == state) {
                Log.i("", "RINGING, number: " + incomingNumber);
            }
            if(TelephonyManager.CALL_STATE_OFFHOOK == state) {
                //wait for phone to go offhook (probably set a boolean flag) so you know your app initiated the call.
                Log.i("", "OFFHOOK");
            }
            if(TelephonyManager.CALL_STATE_IDLE == state) {
                //when this state occurs, and your flag is set, restart your app
                Log.i("", "IDLE");
            }
        }
    }

}
