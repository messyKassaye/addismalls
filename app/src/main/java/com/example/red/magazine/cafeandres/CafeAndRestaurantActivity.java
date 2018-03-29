package com.example.red.magazine.cafeandres;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.red.magazine.R;
import com.example.red.magazine.adapters.MallViewPagerAdapter;
import com.example.red.magazine.cafeandres.clients.CafeAndResClient;
import com.example.red.magazine.cafeandres.fragments.CafeAndResAmbienceFragment;
import com.example.red.magazine.cafeandres.fragments.CafeAndResBookTableFragment;
import com.example.red.magazine.cafeandres.fragments.CafeAndResDishesFragment;
import com.example.red.magazine.cafeandres.fragments.CafeAndResMenuFramgment;
import com.example.red.magazine.malls.client.MallsClient;
import com.example.red.magazine.malls.fragments.AboutUs;
import com.example.red.magazine.malls.fragments.Amenties;
import com.example.red.magazine.malls.fragments.LeaseSpace;
import com.example.red.magazine.malls.fragments.Location;
import com.example.red.magazine.malls.fragments.NewsAndEvents;
import com.example.red.magazine.malls.fragments.Show_Retailers;
import com.example.red.magazine.malls.fragments.Vacancy;
import com.example.red.magazine.malls.model.SingleMallsModel;
import com.example.red.magazine.statics.ProjectStatic;

import java.lang.reflect.Field;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CafeAndRestaurantActivity extends AppCompatActivity {
    private TabLayout htab_layout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView header_image;
    private ViewPager viewPager;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private TextView mall_name;
    private String fontPath = "fonts/Allura-Regular.otf";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_and_restaurant);

        toolbar=(Toolbar)findViewById(R.id.htab_toolbar);
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
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.htab_collapse_toolbar);
        header_image=(ImageView)findViewById(R.id.htab_header);

        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        mall_name=(TextView)findViewById(R.id.mall_name_top);
        mall_name.setTypeface(tf);
        final Intent intent=getIntent();
        preferences=getSharedPreferences("cafe_and_res_data",0);
        editor= preferences.edit();
        editor.putString("company_id",intent.getStringExtra("company_id"));
        editor.commit();
        Retrofit retrofit=getUserAPIretrofit();
        CafeAndResClient client=retrofit.create(CafeAndResClient.class);
        Call<List<SingleMallsModel>> call=client.getCafeAndRes(intent.getStringExtra("company_id"));
        call.enqueue(new Callback<List<SingleMallsModel>>() {
            @Override
            public void onResponse(Call<List<SingleMallsModel>> call, Response<List<SingleMallsModel>> response) {
                toolbar.setTitle(response.body().get(0).getName());
                mall_name.setText(response.body().get(0).getName());
                Glide.with(getApplicationContext()).load(ProjectStatic.IMAPGE_PATH+response.body().get(0).getPhoto_path()).into(header_image);
            }
            @Override
            public void onFailure(Call<List<SingleMallsModel>> call, Throwable t) {
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


        viewPager=(ViewPager)findViewById(R.id.mall_viewpager);
        setUpViewPager(viewPager);

        htab_layout=(TabLayout)findViewById(R.id.htab_tabs);
        htab_layout.setupWithViewPager(viewPager);
        for (int i = 0; i < htab_layout.getTabCount(); i++) {
            TabLayout.Tab tab = htab_layout.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(this).inflate(R.layout.custom_tablayout, htab_layout, false);

            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);
        }
    }

    public void  setUpViewPager(ViewPager viewPager){
        MallViewPagerAdapter adapter=new MallViewPagerAdapter(getSupportFragmentManager());
        CafeAndResMenuFramgment menu=new CafeAndResMenuFramgment();
        CafeAndResDishesFragment dishes=new CafeAndResDishesFragment();
        CafeAndResAmbienceFragment ambience=new CafeAndResAmbienceFragment();
        CafeAndResBookTableFragment booktable=new CafeAndResBookTableFragment();
        adapter.addFrag(menu,"Our Menus");
        adapter.addFrag(dishes,"Our Dishes");
        adapter.addFrag(ambience,"Ambience");
        adapter.addFrag(booktable,"Book a table");
        viewPager.setAdapter(adapter);
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
