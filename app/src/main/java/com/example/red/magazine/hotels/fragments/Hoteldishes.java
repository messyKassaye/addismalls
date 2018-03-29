package com.example.red.magazine.hotels.fragments;


import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.ViewAllHotels;
import com.example.red.magazine.hotels.adaptermodel.HotelDisheAdapterModel;
import com.example.red.magazine.hotels.adapters.HotelDisheRecyclerviewAdapter;
import com.example.red.magazine.hotels.client.HotelsClient;
import com.example.red.magazine.hotels.models.HotelDisheModel;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.suppliers.adaptersmodel.HotelsAdapterModel;

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
public class Hoteldishes extends Fragment {

private SharedPreferences preferences;
    private RecyclerView recyclerView;
    private TextView has_not_addes;
    private List<HotelDisheAdapterModel> modelList;
    private HotelDisheRecyclerviewAdapter adapter;
    public Hoteldishes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_hoteldishes, container, false);
        preferences=getActivity().getSharedPreferences("hotel_container",0);

        modelList=new ArrayList<>();
        adapter=new HotelDisheRecyclerviewAdapter(getActivity(),modelList);

        recyclerView=(RecyclerView)view.findViewById(R.id.hotel_dishes_recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Retrofit retrofit=getUserAPIretrofit();
        HotelsClient client= retrofit.create(HotelsClient.class);
        Call<List<HotelDisheModel>> call=client.getHotelsDishes(preferences.getString("company_id",""));
        call.enqueue(new Callback<List<HotelDisheModel>>() {
            @Override
            public void onResponse(Call<List<HotelDisheModel>> call, Response<List<HotelDisheModel>> response) {
                if (response.isSuccessful()){
                   //recyclerView.setVisibility(View.VISIBLE);
                  if (response.body().size()>0){
                    // recyclerView.setVisibility(View.VISIBLE);
                     for (int i=0;i<response.body().size();i++){
                         HotelDisheAdapterModel model=new HotelDisheAdapterModel();
                         model.setName(response.body().get(i).getName());
                         model.setDescription(response.body().get(i).getDescription());
                         model.setPhoto_path(response.body().get(i).getPhoto_path());
                         modelList.add(model);
                     }
                      recyclerView.setAdapter(adapter);
                      adapter.notifyDataSetChanged();
                  }else {
                  }
                }else {
                   Toast.makeText(getActivity(),"Something is not good ):",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<HotelDisheModel>> call, Throwable t) {

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
