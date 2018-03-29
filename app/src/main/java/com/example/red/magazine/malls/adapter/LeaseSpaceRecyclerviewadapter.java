package com.example.red.magazine.malls.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.red.magazine.R;
import com.example.red.magazine.malls.adaptermodel.LeaseSpaceAdapterModel;
import com.example.red.magazine.malls.adaptermodel.ServiceprovidersAdapterModel;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.topsellers.TopSellersActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by RED on 10/13/2017.
 */

public class LeaseSpaceRecyclerviewadapter extends RecyclerView.Adapter<LeaseSpaceRecyclerviewadapter.MyViewHolder> {

private Context mContext;
private List<LeaseSpaceAdapterModel> albumList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView description;
    public ImageView lease_space_photo;


    public MyViewHolder(View view) {
        super(view);
        description = (TextView) view.findViewById(R.id.lease_space_descr);
        lease_space_photo = (ImageView) view.findViewById(R.id.lease_space_photo);
    }
}


    public LeaseSpaceRecyclerviewadapter(Context mContext, List<LeaseSpaceAdapterModel> albumList) {
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
                .inflate(R.layout.lease_space_recyclerview_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        LeaseSpaceAdapterModel model = albumList.get(position);
        holder.description.setText(model.getDescription());

        // loading album cover using Glide library
        Glide.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(holder.lease_space_photo);
        holder.description.setTag(model.getId());
        holder.lease_space_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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