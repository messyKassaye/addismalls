package com.example.red.magazine.topsellers.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.red.magazine.R;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.topsellers.ServiceProviderNavigation;
import com.example.red.magazine.topsellers.adaptermodels.TopSellersItemAdapterModel;

import java.util.List;

/**
 * Created by RED on 10/3/2017.
 */

public class TopSellersItemRecyclerviewAdapter extends RecyclerView.Adapter<TopSellersItemRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<TopSellersItemAdapterModel> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView caption;
        public ImageView product_photo;
        public TextView price,check_more;

        public MyViewHolder(View view) {
            super(view);
            caption = (TextView) view.findViewById(R.id.caption);
            product_photo = (ImageView) view.findViewById(R.id.product_photo);
            price=(TextView) view.findViewById(R.id.price);
            check_more=(TextView)view.findViewById(R.id.check_more);
        }
    }


    public TopSellersItemRecyclerviewAdapter(Context mContext, List<TopSellersItemAdapterModel> albumList) {
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
                .inflate(R.layout.top_sellers_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        TopSellersItemAdapterModel model = albumList.get(position);
       holder.caption.setText(model.getCaption());
       holder.price.setText("Price : "+model.getPrice()+" ETB");
        holder.check_more.setTag(model.getId());
        // loading album cover using Glide library
        Glide.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getProduct_photo()).into(holder.product_photo);
        holder.product_photo.setTag(model.getId());
        holder.product_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, ServiceProviderNavigation.class);
                intent.putExtra("id",view.getTag().toString());
                mContext.startActivity(intent);
            }
        });
        holder.check_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, ServiceProviderNavigation.class);
                intent.putExtra("id",view.getTag().toString());
                mContext.startActivity(intent);
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
}