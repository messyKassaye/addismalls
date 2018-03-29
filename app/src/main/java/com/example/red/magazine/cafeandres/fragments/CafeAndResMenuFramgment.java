package com.example.red.magazine.cafeandres.fragments;


import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.cafeandres.adapterModel.CafeResMenuAdapterModel;
import com.example.red.magazine.cafeandres.adapters.CafeAndResMenuRecyclerviewAdapter;
import com.example.red.magazine.cafeandres.clients.CafeAndResClient;
import com.example.red.magazine.cafeandres.model.CafeAndResMenu;
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
public class CafeAndResMenuFramgment extends Fragment {
  private RecyclerView recyclerView;
  SharedPreferences preferences;
    private List<CafeResMenuAdapterModel> list;
    private CafeAndResMenuRecyclerviewAdapter adapter;

    public CafeAndResMenuFramgment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cafe_and_res_menu_framgment, container, false);
        preferences=getActivity().getSharedPreferences("cafe_and_res_data",0);

        recyclerView=(RecyclerView)view.findViewById(R.id.cafe_and_resturant_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        list=new ArrayList<>();
        adapter=new CafeAndResMenuRecyclerviewAdapter(getActivity(),list);

        Retrofit retrofit=getUserAPIretrofit();
        CafeAndResClient client=retrofit.create(CafeAndResClient.class);
        Call<List<CafeAndResMenu>> call=client.getCafeAndResMenu(preferences.getString("company_id",""));
        call.enqueue(new Callback<List<CafeAndResMenu>>() {
            @Override
            public void onResponse(Call<List<CafeAndResMenu>> call, Response<List<CafeAndResMenu>> response) {
                if (response.body().size()>0){
                    for (int i=0;i<response.body().size();i++){
                        CafeResMenuAdapterModel model=new CafeResMenuAdapterModel();
                        model.setPhoto_path(response.body().get(i).getPhoto_path());
                        model.setId(response.body().get(i).getId());
                        list.add(model);
                    }
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<CafeAndResMenu>> call, Throwable t) {
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
