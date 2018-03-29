package com.example.red.magazine.malls.fragments;


import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.malls.adapter.VacanyRecyclerviewAdapter;
import com.example.red.magazine.malls.adaptermodel.VacanyAdapterModel;
import com.example.red.magazine.malls.client.MallsClient;
import com.example.red.magazine.malls.model.VacancyModel;
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
public class Vacancy extends Fragment {
    private SharedPreferences preferences;
    private String token;
    private List<VacanyAdapterModel> listmodel;
    private VacanyRecyclerviewAdapter adapter;
    private RecyclerView recyclerView;

    public Vacancy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_vacancy, container, false);

        preferences=getActivity().getSharedPreferences("app_data_container",0);
        token=preferences.getString("company_id","");

        listmodel=new ArrayList<>();
        adapter=new VacanyRecyclerviewAdapter(getActivity(),listmodel);
        recyclerView=(RecyclerView)view.findViewById(R.id.vacancy_recyclerview);
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
        Retrofit retrofit=getUserAPIretrofit();
        MallsClient client=retrofit.create(MallsClient.class);
        Call<List<VacancyModel>> call=client.get_vacancy(token);
        call.enqueue(new Callback<List<VacancyModel>>() {
            @Override
            public void onResponse(Call<List<VacancyModel>> call, Response<List<VacancyModel>> response) {
                for (int i=0;i<response.body().size();i++){
                    VacanyAdapterModel model=new VacanyAdapterModel();
                    model.setJob_title(response.body().get(i).getJob_title());
                    model.setName(response.body().get(i).getName());
                    model.setSalary(response.body().get(i).getSalary());
                    model.setEmployment_type(response.body().get(i).getEmployment_type());
                    model.setQualifications(response.body().get(i).getQualifications());
                    model.setDeadline(response.body().get(i).getDeadline());
                    model.setYears_exp(response.body().get(i).getYears_exp());
                    model.setId(response.body().get(i).getId());
                    listmodel.add(model);
                }
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<VacancyModel>> call, Throwable t) {

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
