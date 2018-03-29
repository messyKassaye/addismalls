package com.example.red.magazine.malls;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v7.graphics.Palette;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
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
import com.example.red.magazine.malls.client.MallsClient;
import com.example.red.magazine.malls.fragments.AboutUs;
import com.example.red.magazine.malls.fragments.Amenties;
import com.example.red.magazine.malls.fragments.LeaseSpace;
import com.example.red.magazine.malls.fragments.Location;
import com.example.red.magazine.malls.fragments.NewsAndEvents;
import com.example.red.magazine.malls.fragments.Show_Retailers;
import com.example.red.magazine.malls.fragments.Vacancy;
import com.example.red.magazine.malls.model.CategoryModel;
import com.example.red.magazine.malls.model.SingleMallsModel;
import com.example.red.magazine.statics.ProjectStatic;

import java.lang.reflect.Field;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MallActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout htab_layout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView header_image;
    private ViewPager viewPager;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private TextView mall_name;
    private String fontPath = "fonts/Allura-Regular.otf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall);

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
        preferences=getSharedPreferences("app_data_container",0);
        editor= preferences.edit();
        editor.putString("company_id",intent.getStringExtra("company_id"));
        editor.commit();

        Retrofit retrofit=getUserAPIretrofit();
        MallsClient client=retrofit.create(MallsClient.class);
        Call<List<SingleMallsModel>> call=client.getMallDetail(intent.getStringExtra("company_id"));
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
        Show_Retailers retailers=new Show_Retailers();
        AboutUs aboutUs=new AboutUs();
        Amenties amenties=new Amenties();
        Location location=new Location();
        NewsAndEvents events=new NewsAndEvents();
        LeaseSpace leaseSpace=new LeaseSpace();
        Vacancy vacancy=new Vacancy();
        adapter.addFrag(retailers,"Our Retailers");
        adapter.addFrag(aboutUs,"About us");
        adapter.addFrag(amenties,"Amenities");
        adapter.addFrag(events,"News & events");
        adapter.addFrag(leaseSpace,"Lease space");
        adapter.addFrag(vacancy,"Vacancy");
        //adapter.addFrag(location,"Location");
        viewPager.setAdapter(adapter);
    }
  public void getServiceProvider(int id){
      String mall_id=String.valueOf(id);
      Retrofit retrofit=getUserAPIretrofit();
      MallsClient client=retrofit.create(MallsClient.class);
      Call<List<CategoryModel>> call=client.getCategoryInMall(mall_id);
      call.enqueue(new Callback<List<CategoryModel>>() {
          @Override
          public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
              if (response.body().size()<=0){
                  htab_layout.addTab(htab_layout.newTab().setText("Coming Soon"));
              }else {
                  for (int i = 0; i < response.body().size(); i++) {
                    htab_layout.addTab(htab_layout.newTab().setText(response.body().get(i).getCategory_type()));
                  }
              }
          }

          @Override
          public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
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
}
