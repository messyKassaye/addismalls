package com.example.red.magazine.hotels.fragments;


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
import android.widget.Toast;

import com.example.red.magazine.MainActivity;
import com.example.red.magazine.R;
import com.example.red.magazine.adapters.CategoryAdapter;
import com.example.red.magazine.hotels.adaptermodel.RoomsAdapterModel;
import com.example.red.magazine.hotels.adapters.RoomsRecyclerviewAdapter;
import com.example.red.magazine.hotels.client.HotelsClient;
import com.example.red.magazine.hotels.models.RoomsModel;
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
public class Rooms extends Fragment {
    private SharedPreferences preferences;
    private RecyclerView recyclerView;
    private List<RoomsAdapterModel> roomsList;
    private RoomsRecyclerviewAdapter adapter;

    public Rooms() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_rooms, container, false);

        preferences=getActivity().getSharedPreferences("hotel_container",0);
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

        roomsList=new ArrayList<>();
        adapter=new RoomsRecyclerviewAdapter(getActivity(),roomsList);
        recyclerView=(RecyclerView)view.findViewById(R.id.rooms_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit=getUserAPIretrofit();
        HotelsClient client=retrofit.create(HotelsClient.class);
        Call<List<RoomsModel>> call= client.getRooms(preferences.getString("company_id",""));
        call.enqueue(new Callback<List<RoomsModel>>() {
            @Override
            public void onResponse(Call<List<RoomsModel>> call, Response<List<RoomsModel>> response) {
                  if (response.isSuccessful()){
                      for (int i=0;i<response.body().size();i++){
                          RoomsAdapterModel model=new RoomsAdapterModel();
                          model.setId(response.body().get(i).getId());
                          model.setCover(response.body().get(i).getCover());
                          model.setDescription(response.body().get(i).getDescription());
                          model.setTitle(response.body().get(i).getTitle());
                          model.setPrice(response.body().get(i).getPrice());
                          roomsList.add(model);
                      }
                      recyclerView.setAdapter(adapter);
                      adapter.notifyDataSetChanged();
                  }
            }

            @Override
            public void onFailure(Call<List<RoomsModel>> call, Throwable t) {

            }
        });
        return  view;
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
