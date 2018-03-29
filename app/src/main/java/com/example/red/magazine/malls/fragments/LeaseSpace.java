package com.example.red.magazine.malls.fragments;


import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.MainActivity;
import com.example.red.magazine.R;
import com.example.red.magazine.malls.adaptermodel.OuterLeaseSpaceAdapterModel;
import com.example.red.magazine.malls.adapter.OuterLeaseSpaceRecyclerviewAdapter;
import com.example.red.magazine.malls.client.MallsClient;
import com.example.red.magazine.malls.model.MallLeaseSpace;
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
public class LeaseSpace extends Fragment {
    private SharedPreferences preferences;
    private String token;
    private TextView no_space;
    private RecyclerView recyclerView;
    private List<OuterLeaseSpaceAdapterModel> listmodel;
    private OuterLeaseSpaceRecyclerviewAdapter adapter;

    public LeaseSpace() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lease_space2, container, false);
        preferences=getActivity().getSharedPreferences("app_data_container",0);
        token=preferences.getString("company_id","");


        listmodel=new ArrayList<>();
        adapter=new OuterLeaseSpaceRecyclerviewAdapter(getActivity(),listmodel);
        recyclerView=(RecyclerView)view.findViewById(R.id.space_recyclerview);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        RecyclerView.LayoutManager mLayoutManager=null;

        if (width>760&&this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mLayoutManager = new GridLayoutManager(getActivity(), 1);
        } else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            mLayoutManager = new GridLayoutManager(getActivity(), 2);
        }else if (width<=600){
            mLayoutManager = new GridLayoutManager(getActivity(), 1);
        }
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //Toast.makeText(getActivity(),token,Toast.LENGTH_LONG).show();
        Retrofit retrofit=getUserAPIretrofit();
        MallsClient client=retrofit.create(MallsClient.class);
        Call<List<MallLeaseSpace>> call=client.get_spaces(token);
        call.enqueue(new Callback<List<MallLeaseSpace>>() {
            @Override
            public void onResponse(Call<List<MallLeaseSpace>> call, Response<List<MallLeaseSpace>> response) {
               for (int i=0;i<response.body().size();i++){
                   OuterLeaseSpaceAdapterModel model=new OuterLeaseSpaceAdapterModel();
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

            }

            @Override
            public void onFailure(Call<List<MallLeaseSpace>> call, Throwable t) {
               Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        return view;
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
