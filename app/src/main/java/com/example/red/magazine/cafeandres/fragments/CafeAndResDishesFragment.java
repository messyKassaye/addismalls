package com.example.red.magazine.cafeandres.fragments;


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
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.ViewAllHotels;
import com.example.red.magazine.cafeandres.adapterModel.CafeResDishesAdapterModel;
import com.example.red.magazine.cafeandres.adapters.CafeResDisheRecyclerviewAdapter;
import com.example.red.magazine.cafeandres.clients.CafeAndResClient;
import com.example.red.magazine.cafeandres.model.CafeResDishes;
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
public class CafeAndResDishesFragment extends Fragment {
  private RecyclerView recyclerView;
  private SharedPreferences preferences;
    private List<CafeResDishesAdapterModel> list;
    private CafeResDisheRecyclerviewAdapter adapter;
    public CafeAndResDishesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cafe_and_res_dishes, container, false);

        preferences=getActivity().getSharedPreferences("cafe_and_res_data",0);

        recyclerView=(RecyclerView)view.findViewById(R.id.cafe_and_resturant_dishe);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        list=new ArrayList<>();
        adapter=new CafeResDisheRecyclerviewAdapter(getActivity(),list);

        Retrofit retrofit=getUserAPIretrofit();
        CafeAndResClient client=retrofit.create(CafeAndResClient.class);
        Call<List<CafeResDishes>> call= client.getDsihe("s59dda63a6efc3");
        call.enqueue(new Callback<List<CafeResDishes>>() {
            @Override
            public void onResponse(Call<List<CafeResDishes>> call, Response<List<CafeResDishes>> response) {
                if (response.body().size()>0){
                    for (int i=0;i<response.body().size();i++){
                        CafeResDishesAdapterModel model=new CafeResDishesAdapterModel();
                        model.setName(response.body().get(i).getName());
                        model.setPrice(response.body().get(i).getPrice());
                        model.setDescription(response.body().get(i).getDescription());
                        list.add(model);
                        Toast.makeText(getActivity(),model.getName(),Toast.LENGTH_LONG).show();
                    }
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CafeResDishes>> call, Throwable t) {

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
