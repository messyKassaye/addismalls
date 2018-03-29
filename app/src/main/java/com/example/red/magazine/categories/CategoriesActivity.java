package com.example.red.magazine.categories;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.red.magazine.MainActivity;
import com.example.red.magazine.R;
import com.example.red.magazine.ViewAllHotels;
import com.example.red.magazine.adapters.CategoryAdapter;
import com.example.red.magazine.categories.adaptermodels.CategoryAdapterModel;
import com.example.red.magazine.categories.adapters.CategoryRecyclerviewAdpterOuter;
import com.example.red.magazine.categories.client.CategoryClient;
import com.example.red.magazine.categories.model.CategoryModel;
import com.example.red.magazine.statics.ProjectStatic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoriesActivity extends AppCompatActivity {
   private Intent intent;
    private RecyclerView cafe_and_restaurant_recyclerview;
    private List<CategoryAdapterModel> categorylist;
    private CategoryRecyclerviewAdpterOuter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        RecyclerView.LayoutManager mLayoutManager=null;

        if (width>760&&this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mLayoutManager = new GridLayoutManager(this, 1);
        } else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            mLayoutManager = new GridLayoutManager(this, 2);
        }else if (width<=600){
            mLayoutManager = new GridLayoutManager(this, 1);
        }

        intent=getIntent();
        toolbar.setTitle(intent.getStringExtra("name"));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        categorylist=new ArrayList<>();
        adapter=new CategoryRecyclerviewAdpterOuter(this,categorylist);
        cafe_and_restaurant_recyclerview=(RecyclerView)findViewById(R.id.cafe_and_restaurant_recyclerview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        cafe_and_restaurant_recyclerview.setLayoutManager(layoutManager);
        cafe_and_restaurant_recyclerview.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        cafe_and_restaurant_recyclerview.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit= getUserAPIretrofit();
        CategoryClient client= retrofit.create(CategoryClient.class);
        Call<List<CategoryModel>>call=client.get_by_category(intent.getStringExtra("id"));
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                   for (int i=0;i<response.body().size();i++){
                       CategoryAdapterModel model=new CategoryAdapterModel();
                       model.setName(response.body().get(i).getName());
                       model.setPhoto_path(response.body().get(i).getPhoto_path());
                       model.setCompany_id(response.body().get(i).getCompany_id());
                       categorylist.add(model);
                   }
                   cafe_and_restaurant_recyclerview.setAdapter(adapter);
                   adapter.notifyDataSetChanged();
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
        getMenuInflater().inflate(R.menu.category_menu, menu);
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
