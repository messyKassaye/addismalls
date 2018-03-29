package com.example.red.magazine.topsellers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.red.magazine.R;
import com.example.red.magazine.malls.model.SingleMallsModel;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.topsellers.adapters.SellersViewpagerAdapter;
import com.example.red.magazine.topsellers.client.TopSellersClient;
import com.example.red.magazine.topsellers.fragments.Discounts;
import com.example.red.magazine.topsellers.fragments.NewsAndEvents;
import com.example.red.magazine.topsellers.fragments.OurProduct;
import com.example.red.magazine.topsellers.fragments.WorkingHours;
import com.example.red.magazine.topsellers.model.TopSellersItem;
import com.example.red.magazine.topsellers.model.TopsellersModel;

import java.lang.reflect.Field;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopSellersActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout htab_layout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView header_image;
    private ViewPager viewPager;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private TextView top_sellers_name;
    private String fontPath = "fonts/Allura-Regular.otf";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_sellers);

        toolbar=(Toolbar)findViewById(R.id.topsellers_htab_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        try {
            Field declaredField = toolbar.getClass().getDeclaredField("mTitleTextView");
            declaredField.setAccessible(true);
            TextView titleTextView = (TextView) declaredField.get(toolbar);
            ViewGroup.LayoutParams layoutParams = titleTextView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            titleTextView.setLayoutParams(layoutParams);
            titleTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        } catch (Exception e) {
            //"Error!"
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        header_image=(ImageView)findViewById(R.id.topsellers_htab_header);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.topsellers_htab_collapse_toolbar);
        htab_layout=(TabLayout)findViewById(R.id.topsellers_htab_tabs);

        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        top_sellers_name=(TextView)findViewById(R.id.top_sellers_company_name);
        top_sellers_name.setTypeface(tf);
        final Intent intent=getIntent();
        preferences=getSharedPreferences("sellers_data",0);
        editor= preferences.edit();
        editor.putString("id",intent.getStringExtra("id"));
        editor.commit();

        Retrofit retrofit=getUserAPIretrofit();
        TopSellersClient client=retrofit.create(TopSellersClient.class);
        Call<List<TopsellersModel>> call=client.getTopSellers(intent.getStringExtra("id"));
        call.enqueue(new Callback<List<TopsellersModel>>() {
            @Override
            public void onResponse(Call<List<TopsellersModel>> call, Response<List<TopsellersModel>> response) {
                toolbar.setTitle(response.body().get(0).getName());
                top_sellers_name.setText(response.body().get(0).getName());
                Glide.with(getApplicationContext()).load(ProjectStatic.IMAPGE_PATH+response.body().get(0).getPhoto_path()).into(header_image);
            }
            @Override
            public void onFailure(Call<List<TopsellersModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.header);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @SuppressWarnings("ResourceType")
                @Override
                public void onGenerated(Palette palette) {
                    collapsingToolbarLayout.setContentScrimColor(R.color.primary_500);
                    collapsingToolbarLayout.setStatusBarScrimColor(R.color.primary_700);
                }
            });

        } catch (Exception e) {
            // if Bitmap fetch fails, fallback to primary colors
            collapsingToolbarLayout.setContentScrimColor(
                    ContextCompat.getColor(this, R.color.primary_500)
            );
            collapsingToolbarLayout.setStatusBarScrimColor(
                    ContextCompat.getColor(this, R.color.primary_700)
            );
        }
        viewPager=(ViewPager)findViewById(R.id.top_sellers_viewpager);
        setUpViewPager(viewPager);

        htab_layout=(TabLayout)findViewById(R.id.topsellers_htab_tabs);
        htab_layout.setupWithViewPager(viewPager);

    }

    public void  setUpViewPager(ViewPager viewPager){
        SellersViewpagerAdapter adapter=new SellersViewpagerAdapter(getSupportFragmentManager());
        OurProduct ourProduct=new OurProduct();
        WorkingHours hours=new WorkingHours();
        Discounts discounts=new Discounts();
        NewsAndEvents events=new NewsAndEvents();
        adapter.addFrag(ourProduct,"Our product");
        adapter.addFrag(discounts,"Discount");
        adapter.addFrag(hours,"Hours");
        adapter.addFrag(events,"News & events");
        viewPager.setAdapter(adapter);
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


    public Retrofit getUserAPIretrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ProjectStatic.URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit;
    }
}
