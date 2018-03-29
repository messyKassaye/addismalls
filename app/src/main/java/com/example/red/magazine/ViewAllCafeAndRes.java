package com.example.red.magazine;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.red.magazine.adaptermodels.CafeResAdapterModel;
import com.example.red.magazine.adapters.CafeAndResRecyclerviewAdapterOuter;
import com.example.red.magazine.client.CafeAndResClient;
import com.example.red.magazine.model.CafeResModel;
import com.example.red.magazine.statics.ProjectStatic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewAllCafeAndRes extends AppCompatActivity {

    private List<CafeResAdapterModel>cafereslist;
    private CafeAndResRecyclerviewAdapterOuter caferesadapter;
    private RecyclerView caferes_recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_cafe_and_res);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cafereslist=new ArrayList<>();
        caferesadapter=new CafeAndResRecyclerviewAdapterOuter(this,cafereslist);
        caferes_recyclerview=(RecyclerView)findViewById(R.id.viewall_cafeandres_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        caferes_recyclerview.setLayoutManager(mLayoutManager);
        caferes_recyclerview.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        caferes_recyclerview.setItemAnimator(new DefaultItemAnimator());

        Retrofit retrofit= getUserAPIretrofit();
        CafeAndResClient client= retrofit.create(CafeAndResClient.class);
        Call<List<CafeResModel>> call= client.getAllCafeAndRes();
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

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        return super.onCreateOptionsMenu(menu);
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
