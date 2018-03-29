package com.example.red.magazine.malls.fragments;


import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.red.magazine.R;
import com.example.red.magazine.ViewAllMalls;
import com.example.red.magazine.adaptermodels.TopSellersAdapterModel;
import com.example.red.magazine.malls.adapter.Show_Retailers_adapter;
import com.example.red.magazine.malls.client.MallsClient;
import com.example.red.magazine.model.TopSellers;
import com.example.red.magazine.statics.ProjectStatic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class Show_Retailers extends Fragment {

    private RecyclerView recyclerView;
    private List<TopSellersAdapterModel> topsellerlist;
    private Show_Retailers_adapter topSellersAdapter;
    public Show_Retailers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.show_retailers, container, false);
        SharedPreferences preferences=getActivity().getSharedPreferences("app_data_container",0);
        getTopSellers(view,preferences.getString("company_id",""));

        return view;
    }
    public void getTopSellers(View view,String company_id) {
        topsellerlist = new ArrayList<>();
        topSellersAdapter = new Show_Retailers_adapter(getActivity(), topsellerlist);
        recyclerView = (RecyclerView)view.findViewById(R.id.show_retailers);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));

        Retrofit retrofit = getUserAPIretrofit();
        MallsClient client = retrofit.create(MallsClient.class);
        Call<List<TopSellers>> call = client.show_retailers(company_id);
        call.enqueue(new Callback<List<TopSellers>>() {
            @Override
            public void onResponse(Call<List<TopSellers>> call, Response<List<TopSellers>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    TopSellersAdapterModel model = new TopSellersAdapterModel();
                    model.setName(response.body().get(i).getName());
                    model.setPhoto_path(response.body().get(i).getPhoto_path());
                    model.setId(response.body().get(i).getId());
                    model.setFloor_no(response.body().get(i).getFloor_no());
                    model.setOffice_no(response.body().get(i).getOffice_no());
                    topsellerlist.add(model);
                }
                recyclerView.setAdapter(topSellersAdapter);
                topSellersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TopSellers>> call, Throwable t) {

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
