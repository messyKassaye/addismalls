package com.example.red.magazine;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.red.magazine.adaptermodels.TopMallsAdapterModel;
import com.example.red.magazine.adapters.TopMallAdapter;
import com.example.red.magazine.client.MallsClient;
import com.example.red.magazine.model.TopMall;
import com.example.red.magazine.statics.ProjectStatic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewAllMalls extends AppCompatActivity {

    //=====================
    //top malls variables
    //=================
    private RecyclerView topmalls_recyclerview;
    private List<TopMallsAdapterModel> topmalls_list;
    private TopMallAdapter topMallAdapter;
    private Toolbar toolbar;

    private TextView title;
    private ImageView search_image;
    private RelativeLayout title_layout;
    private AutoCompleteTextView auto_search;

    private ArrayList<String> search_item;
    private ArrayAdapter<String> search_adapter;
    String[] fruits = {"Apple", "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_malls);

        toolbar=(Toolbar)findViewById(R.id.toolbar);

        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        search_item= new ArrayList<>();
        search_adapter= new ArrayAdapter(this,android.R.layout.select_dialog_item,fruits);
        title_layout=(RelativeLayout)findViewById(R.id.title_layout);
        auto_search=(AutoCompleteTextView)findViewById(R.id.search_edit);



        title=(TextView)findViewById(R.id.title_text);
        title.setText("All malls");
        search_image=(ImageView)findViewById(R.id.search_icon);
        search_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title_layout.setVisibility(View.GONE);
                auto_search.setVisibility(View.VISIBLE);
                /*Retrofit retrofit = getUserAPIretrofit();
                MallsClient client=retrofit.create(MallsClient.class);
                Call<List<TopMall>> call=client.getAllMall();
                call.enqueue(new Callback<List<TopMall>>() {
                    @Override
                    public void onResponse(Call<List<TopMall>> call, Response<List<TopMall>> response) {
                        for (int i=0;i<response.body().size();i++){
                          search_item.add(response.body().get(i).getName());
                        }
                        auto_search.setAdapter(search_adapter);
                    }

                    @Override
                    public void onFailure(Call<List<TopMall>> call, Throwable t) {

                    }
                });*/
            }
        });
        auto_search.setThreshold(1);
        auto_search.setTextColor(Color.WHITE);


        getTopMall();
    }

    public void getTopMall(){
        topmalls_list=new ArrayList<>();
        topMallAdapter=new TopMallAdapter(this,topmalls_list);
        topmalls_recyclerview=(RecyclerView)findViewById(R.id.viewall_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        topmalls_recyclerview.setLayoutManager(mLayoutManager);
        topmalls_recyclerview.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        topmalls_recyclerview.setItemAnimator(new DefaultItemAnimator());

        Retrofit retrofit = getUserAPIretrofit();
        MallsClient client=retrofit.create(MallsClient.class);
        Call<List<TopMall>> call=client.getAllMall();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

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
