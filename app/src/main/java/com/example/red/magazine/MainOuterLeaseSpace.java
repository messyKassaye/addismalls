package com.example.red.magazine;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.red.magazine.adaptermodels.LeaseSpaceOuterAdapterModel;
import com.example.red.magazine.adapters.LeaseSpaceRecylerviewAdapter;
import com.example.red.magazine.client.OuterLeaseSpaceClient;
import com.example.red.magazine.malls.adapter.OuterLeaseSpaceRecyclerviewAdapter;
import com.example.red.magazine.malls.adaptermodel.OuterLeaseSpaceAdapterModel;
import com.example.red.magazine.malls.client.MallsClient;
import com.example.red.magazine.malls.fragments.LeaseSpace;
import com.example.red.magazine.malls.model.MallLeaseSpace;
import com.example.red.magazine.model.OuterLeaseSpaceModel;
import com.example.red.magazine.statics.ProjectStatic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainOuterLeaseSpace extends AppCompatActivity {
    private List<LeaseSpaceOuterAdapterModel> listmodel;
    private LeaseSpaceRecylerviewAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outer_lease_space);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Lease space");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        listmodel=new ArrayList<>();
        adapter=new LeaseSpaceRecylerviewAdapter(this,listmodel);
        recyclerView=(RecyclerView)findViewById(R.id.outer_lease_space);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //Toast.makeText(getActivity(),token,Toast.LENGTH_LONG).show();
        Retrofit retrofit=getUserAPIretrofit();
        OuterLeaseSpaceClient client=retrofit.create(OuterLeaseSpaceClient.class);
        Call<List<OuterLeaseSpaceModel>> call=client.getLeaseSpace();
        call.enqueue(new Callback<List<OuterLeaseSpaceModel>>() {
            @Override
            public void onResponse(Call<List<OuterLeaseSpaceModel>> call, Response<List<OuterLeaseSpaceModel>> response) {
                if(response.body().size()>0){
                    for (int i=0;i<response.body().size();i++){
                        LeaseSpaceOuterAdapterModel model=new LeaseSpaceOuterAdapterModel();
                        model.setArea(response.body().get(i).getArea());
                        model.setDescription(response.body().get(i).getDescription());
                        model.setFloor_no(response.body().get(i).getFloor_no());
                        model.setPurpose(response.body().get(i).getPurpose());
                        model.setPhoto_path(response.body().get(i).getPhoto_path());
                        model.setPhone_no(response.body().get(i).getPhone_no());
                        listmodel.add(model);
                    }
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{

                }

            }

            @Override
            public void onFailure(Call<List<OuterLeaseSpaceModel>> call, Throwable t) {
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
