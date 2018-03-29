package com.example.red.magazine.cafeandres.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.red.magazine.R;
import com.example.red.magazine.cafeandres.MenuDisplayer;
import com.example.red.magazine.cafeandres.adapterModel.CafeResDishesAdapterModel;
import com.example.red.magazine.cafeandres.adapterModel.CafeResMenuAdapterModel;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Meseret on 12/17/2017.
 */

public class CafeResDisheRecyclerviewAdapter extends RecyclerView.Adapter<CafeResDisheRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<CafeResDishesAdapterModel> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dishe_name,dishe_price,dishe_description;
        public ImageView mall_phtoto;


        public MyViewHolder(View view) {
            super(view);
            mall_phtoto = (ImageView) view.findViewById(R.id.cafe_res_dishe_image);
            dishe_name=(TextView)view.findViewById(R.id.cafe_res_dishe_name);
            dishe_price=(TextView)view.findViewById(R.id.cafe_res_dishe_price);
            dishe_description=(TextView)view.findViewById(R.id.cafe_res_dishe_description);
        }
    }


    public CafeResDisheRecyclerviewAdapter(Context mContext, List<CafeResDishesAdapterModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cafe_res_dishes_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CafeResDishesAdapterModel model = albumList.get(position);
        holder.dishe_name.setText(model.getName());
        holder.dishe_price.setText("Price: "+model.getPrice());
        holder.dishe_description.setText(model.getDescription());

        // loading album cover using Glide library
        //Glide.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).into(holder.mall_phtoto);
        Picasso.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(holder.mall_phtoto);

    }


    /**
     * Click listener for popup menu items
     */

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}