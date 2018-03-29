package com.example.red.magazine.topsellers;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.red.magazine.MainActivity;
import com.example.red.magazine.R;
import com.example.red.magazine.adaptermodels.SponserAdapterModel;
import com.example.red.magazine.adapters.SponserPagerAdapter;
import com.example.red.magazine.client.SponserClient;
import com.example.red.magazine.client.TopSellerClient;
import com.example.red.magazine.model.Sponser;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.topsellers.adaptermodels.PaymentSupporterBanksAdapterModel;
import com.example.red.magazine.topsellers.adaptermodels.PurchasingAdapterModel;
import com.example.red.magazine.topsellers.adapters.PaymentSupporterbanksRecyclerViewAdapter;
import com.example.red.magazine.topsellers.adapters.PurchasingAdapter;
import com.example.red.magazine.topsellers.client.TopSellersClient;
import com.example.red.magazine.topsellers.model.PaymentSupporterBanks;
import com.example.red.magazine.topsellers.model.PurchasingModel;
import com.example.red.magazine.topsellers.model.PurchasingPhoto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceProviderNavigation extends AppCompatActivity{
    private Toolbar toolbar;
  private SharedPreferences preferences;

    private ViewPager mPager;
    private static int currentPage = 0;
    private ArrayList<PurchasingAdapterModel> sponsorslist;
    private TextView name,price,description;
    private ImageView product_photo;
    private Button purchase_btn,subscribe_btn;

    private ViewFlipper flipper;
    private View first_view,second_view,subscribe_view;

    private RecyclerView bank_recyclerview;
    private List<PaymentSupporterBanksAdapterModel> list;
    private PaymentSupporterbanksRecyclerViewAdapter adapter;

    private EditText subscribed_email_address;
    private Button subscribe_me_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_navigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Malladdis");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        flipper=(ViewFlipper)findViewById(R.id.purchase_flipper);

        first_view= LayoutInflater.from(this).inflate(R.layout.purchase_first_view,null);

        String id=getIntent().getStringExtra("id");

        name=(TextView)first_view.findViewById(R.id.purchasing_product_name);
        price=(TextView)first_view.findViewById(R.id.purchasing_price);
        description=(TextView)first_view.findViewById(R.id.purchasing_description);
        product_photo=(ImageView)first_view.findViewById(R.id.purchase_product_photo);

        purchase_btn=(Button)first_view.findViewById(R.id.purchase_btn);
        subscribe_btn=(Button)first_view.findViewById(R.id.subscribe_btn);

        Retrofit retrofit=getUserAPIretrofit();
        TopSellersClient client=retrofit.create(TopSellersClient.class);
        Call<List<PurchasingModel>> call=client.getPurchasing(id);
        call.enqueue(new Callback<List<PurchasingModel>>() {
            @Override
            public void onResponse(Call<List<PurchasingModel>> call, Response<List<PurchasingModel>> response) {
                toolbar.setTitle(response.body().get(0).getName());
                name.setText("Product Name: "+response.body().get(0).getCaption());
                price.setText("Product Price: "+response.body().get(0).getPrice()+" ETB");
                description.setText(response.body().get(0).getDescription());
                Picasso.with(getApplicationContext()).load(ProjectStatic.IMAPGE_PATH+response.body().get(0).getProduct_photo()).placeholder(R.drawable.background_tile2_small).into(product_photo);
        }

            @Override
            public void onFailure(Call<List<PurchasingModel>> call, Throwable t) {

            }
        });



        purchase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        flipper.addView(first_view);



        second_view=LayoutInflater.from(this).inflate(R.layout.purchase_bank_selection_layout,null);

        list=new ArrayList<>();
        adapter=new PaymentSupporterbanksRecyclerViewAdapter(this,list);
        bank_recyclerview=(RecyclerView)second_view.findViewById(R.id.banks_recyclerview);
        bank_recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        bank_recyclerview.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit1=getUserAPIretrofit();
        TopSellersClient client1=retrofit1.create(TopSellersClient.class);
        Call<List<PaymentSupporterBanks>>call1=client1.getPaymentSupporterBanks();
        call1.enqueue(new Callback<List<PaymentSupporterBanks>>() {
            @Override
            public void onResponse(Call<List<PaymentSupporterBanks>> call, Response<List<PaymentSupporterBanks>> response) {
                for (int i=0;i<response.body().size();i++){
                    PaymentSupporterBanksAdapterModel model=new PaymentSupporterBanksAdapterModel();
                    model.setBank_name(response.body().get(i).getBank_name());
                    model.setLogo(response.body().get(i).getLogo());
                    model.setPayment_link(response.body().get(i).getPayment_link());
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
                bank_recyclerview.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<PaymentSupporterBanks>> call, Throwable t) {

            }
        });


        purchase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipper.removeView(first_view);
                flipper.addView(second_view);
                flipper.showNext();
            }
        });

        subscribe_view=LayoutInflater.from(this).inflate(R.layout.subscribe_layout,null);

        subscribed_email_address=(EditText)subscribe_view.findViewById(R.id.subscribe_email_address);
        subscribe_me_btn=(Button)subscribe_view.findViewById(R.id.subscribe_me_btn);

        subscribe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipper.removeAllViews();
                flipper.addView(subscribe_view);
                flipper.showNext();
            }
        });




        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        // set the animation type's to ViewFlipperActivity
        flipper.setInAnimation(in);
        flipper.setOutAnimation(out);
        // set interval time for flipping between views
        flipper.setFlipInterval(3000);
        flipper.setDisplayedChild(4);



    }


    private void init(String id) {
        sponsorslist=new ArrayList<>();

        Retrofit retrofit=getUserAPIretrofit();
        TopSellersClient client=retrofit.create(TopSellersClient.class);
        Call<List<PurchasingPhoto>> call=client.getPurchasingPhoto(id);
        call.enqueue(new Callback<List<PurchasingPhoto>>() {
            @Override
            public void onResponse(Call<List<PurchasingPhoto>> call, Response<List<PurchasingPhoto>> response) {
                for (int i=0;i<response.body().size();i++){
                    PurchasingAdapterModel model=new PurchasingAdapterModel();
                    model.setPhoto(response.body().get(i).getPhoto());
                    sponsorslist.add(model);
                }
                for(int i=0;i<sponsorslist.size();i++)
                    mPager = (ViewPager) findViewById(R.id.sponsors_viewpager);
                mPager.setAdapter(new PurchasingAdapter(ServiceProviderNavigation.this,sponsorslist));

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
            public void onFailure(Call<List<PurchasingPhoto>> call, Throwable t) {

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
