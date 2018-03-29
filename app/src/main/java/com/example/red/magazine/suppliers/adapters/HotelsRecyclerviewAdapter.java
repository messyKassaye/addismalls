package com.example.red.magazine.suppliers.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.adaptermodels.TopMallsAdapterModel;
import com.example.red.magazine.adapters.TopMallAdapter;
import com.example.red.magazine.malls.MallActivity;
import com.example.red.magazine.malls.model.ResponseMessage;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.suppliers.adaptersmodel.HotelsAdapterModel;
import com.example.red.magazine.suppliers.clients.SuppliersClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Meseret on 11/6/2017.
 */

public class HotelsRecyclerviewAdapter extends RecyclerView.Adapter<HotelsRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<HotelsAdapterModel> albumList;
    private SharedPreferences preferences;
    private String token;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView hotel_name;
        public ImageView hotel_photo;
        private Button add;

        public MyViewHolder(View view) {
            super(view);
            hotel_name = (TextView) view.findViewById(R.id.hotel_name);
            hotel_photo = (ImageView) view.findViewById(R.id.hotel_image);
            add=(Button)view.findViewById(R.id.add_as_customer);
        }
    }


    public HotelsRecyclerviewAdapter(Context mContext, List<HotelsAdapterModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sup_ad_hotels_recyclerview_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        HotelsAdapterModel model = albumList.get(position);
        holder.hotel_name.setText(model.getName());
        holder.add.setTag(model.getId());
        preferences=mContext.getSharedPreferences("suppliers_data",0);
        token=preferences.getString("token","");
        // loading album cover using Glide library
        //Glide.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).into(holder.mall_phtoto);
         Picasso.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(holder.hotel_photo);
         holder.add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Retrofit retrofit=getUserAPIretrofit();
               SuppliersClient client=retrofit.create(SuppliersClient.class);
               Call<ResponseMessage>call=client.add_hotels_as_customers(token,view.getTag().toString());
               call.enqueue(new Callback<ResponseMessage>() {
                   @Override
                   public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                       Toast.makeText(mContext,response.body().getStatus(),Toast.LENGTH_LONG).show();
                   }

                   @Override
                   public void onFailure(Call<ResponseMessage> call, Throwable t) {

                   }
               });
           }
       });

    }


    /**
     * Click listener for popup menu items
     */

    @Override
    public int getItemCount() {
        return albumList.size();
    }
    public Retrofit getUserAPIretrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ProjectStatic.URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit;
    }
}