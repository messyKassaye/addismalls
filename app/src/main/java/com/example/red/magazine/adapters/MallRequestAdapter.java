package com.example.red.magazine.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.adaptermodels.CategoryAdapterModel;
import com.example.red.magazine.adaptermodels.MallRequestAdapterModel;
import com.example.red.magazine.categories.CategoriesActivity;
import com.example.red.magazine.client.UsersClient;
import com.example.red.magazine.model.MallRequestAllowModel;
import com.example.red.magazine.model.ResponseMessage;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RED on 10/4/2017.
 */

public class MallRequestAdapter extends RecyclerView.Adapter<MallRequestAdapter.MyViewHolder> {

    private Context mContext;
    private List<MallRequestAdapterModel> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mall_name,request_by,show_detail,allow_request;


        public MyViewHolder(View view) {
            super(view);
            mall_name = (TextView) view.findViewById(R.id.request_mall_name);
            request_by=(TextView)view.findViewById(R.id.mall_request_sender_name);
            show_detail=(TextView)view.findViewById(R.id.show_request_detail);
            allow_request=(TextView)view.findViewById(R.id.allo_mall_request);

        }
    }


    public MallRequestAdapter(Context mContext, List<MallRequestAdapterModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }
    public void clearApplications() {
        int size = this.albumList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                albumList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mall_request_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        MallRequestAdapterModel model = albumList.get(position);
        holder.mall_name.setText("Mall name: "+model.getMall_name());
        holder.request_by.setText("by "+model.getFirst_name()+" "+model.getLast_name());
        holder.show_detail.setText("Show details");
        holder.allow_request.setText("allow request");
        holder.allow_request.setTag(model.getCompany_id());
        holder.allow_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences=mContext.getSharedPreferences("token",0);
                String token=preferences.getString("token_value","");

                MallRequestAllowModel allow=new MallRequestAllowModel(view.getTag().toString());
                Retrofit retrofit=getUserAPIretrofit();
                UsersClient client=retrofit.create(UsersClient.class);
                Call<ResponseMessage> call=client.allow_request(token,allow);
                call.enqueue(new retrofit2.Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                        if (response.body().getStatus().equals("ok")){
                            Toast.makeText(mContext,"Permission is granted",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {

                    }
                });
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

    /**
     * Click listener for popup menu items
     */

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
