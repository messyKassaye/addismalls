package com.example.red.magazine.topsellers.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.red.magazine.R;
import com.example.red.magazine.ViewAllMalls;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.topsellers.adaptermodels.TopSellersItemAdapterModel;
import com.example.red.magazine.topsellers.adapters.TopSellersItemRecyclerviewAdapter;
import com.example.red.magazine.topsellers.client.TopSellersClient;
import com.example.red.magazine.topsellers.model.TopSellersItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OurProduct extends Fragment {

    private TopSellersItemRecyclerviewAdapter adapter;
    private List<TopSellersItemAdapterModel> itemslist;
    RecyclerView.LayoutManager mLayoutManager=null;
    private RecyclerView recyclerView;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public OurProduct() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about_us2, container, false);

        itemslist=new ArrayList<>();
        adapter=new TopSellersItemRecyclerviewAdapter(getActivity(),itemslist);
        recyclerView=(RecyclerView)view.findViewById(R.id.topsellers_mall_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        preferences=getActivity().getSharedPreferences("sellers_data",0);

        String id=preferences.getString("id","");
        gettopSellers(id);

        return view;
    }

    public void gettopSellers(String id){
        Retrofit retrofit=getUserAPIretrofit();
        TopSellersClient client=retrofit.create(TopSellersClient.class);
        Call<List<TopSellersItem>> call =client.getTopSellersItem(id);
        call.enqueue(new Callback<List<TopSellersItem>>() {
            @Override
            public void onResponse(Call<List<TopSellersItem>> call, Response<List<TopSellersItem>> response) {
                if (itemslist.size()>0){
                    adapter.clearApplications();
                }
                for (int i=0;i<response.body().size();i++){
                    TopSellersItemAdapterModel model=new TopSellersItemAdapterModel();
                    model.setId(response.body().get(i).getId());
                    model.setCaption(response.body().get(i).getCaption());
                    model.setProduct_photo(response.body().get(i).getProduct_photo());
                    model.setPrice(response.body().get(i).getPrice());
                    itemslist.add(model);
                }
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<TopSellersItem>> call, Throwable t) {

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


}
